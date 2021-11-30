package com.example.movil.models;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.movil.R;
import com.example.movil.interfaces.UsuariosService;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends Activity {
    Button btnEntrar;
    Button btnInv;
    String email;
    String password;
    EditText txtEmail;
    EditText txtPassword;

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            TrustManager[] trustAllCerts = {new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        statusBarColor();
        this.txtEmail = findViewById(R.id.txtEmail);
        this.txtPassword = findViewById(R.id.txtPassword);
        this.btnInv = findViewById(R.id.buttonInvitado);
        this.btnEntrar = findViewById(R.id.buttonEntrar);
    }

    public void goToMain(View view) {
        this.email = this.txtEmail.getText().toString();
        this.password = this.txtPassword.getText().toString();
        new Retrofit.Builder().baseUrl("https://192.168.100.4:45455/api/").addConverterFactory(GsonConverterFactory.create()).client(getUnsafeOkHttpClient()).build().create(UsuariosService.class).find(this.email).enqueue(new Callback<PostUsuarios>() {
            public void onResponse(Call<PostUsuarios> call, Response<PostUsuarios> response) {
                PostUsuarios postUsuario = response.body();
                if (postUsuario == null) {
                    Toast.makeText(Login.this.getApplicationContext(), "Algo falló, los datos están nulos", Toast.LENGTH_SHORT).show();
                } else if (!postUsuario.getEmail().equalsIgnoreCase(Login.this.email) || !postUsuario.getPassword().equalsIgnoreCase(Login.this.password)) {
                    Toast.makeText(Login.this.getApplicationContext(), "Algo falló, los datos no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login.this.getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this.getApplicationContext(), MainInv.class);
                    intent.putExtra(NotificationCompat.CATEGORY_EMAIL, postUsuario.getEmail());
                    intent.putExtra("id", postUsuario.getId());
                    intent.putExtra("tipo", "1");
                    Login.this.startActivity(intent);
                }
            }

            public void onFailure(Call<PostUsuarios> call, Throwable t) {
            }
        });
    }

    public void goToInv(View view) {
        Toast.makeText(getApplicationContext(), "Bienvenido Invitado", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), MainInv.class);
        intent.putExtra("tipo", "2");
        startActivity(intent);
    }

    private void statusBarColor() {
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
