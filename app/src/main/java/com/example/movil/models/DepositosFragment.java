package com.example.movil.models;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.movil.R;
import com.example.movil.interfaces.DepositosService;
import com.example.movil.R;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

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

public class DepositosFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listViewDepositos;
    String tipo;
    private String mParam1;
    private String mParam2;

    public static DepositosFragment newInstance(String param1, String param2) {
        DepositosFragment fragment = new DepositosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_depositos, container, false);
        this.listViewDepositos = rootView.findViewById(R.id.listViewDepositos);
        this.tipo = getActivity().getIntent().getExtras().getString("tipo");
        new Retrofit.Builder().baseUrl("https://192.168.100.4:45455/api/").addConverterFactory(GsonConverterFactory.create()).client(getUnsafeOkHttpClient()).build().create(DepositosService.class).find().enqueue(new Callback<List<PostDepositos>>() {
            public void onResponse(Call<List<com.example.movil.models.PostDepositos>> call, Response<List<PostDepositos>> response) {
                final List<PostDepositos> postList = response.body();
                DepositosFragment.this.listViewDepositos.setAdapter(new ListDepositosAdapter(DepositosFragment.this.getActivity(), postList));
                DepositosFragment.this.listViewDepositos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int id = postList.get(i).getId_deposito();
                        float registro = postList.get(i).getRegistro();
                        String nombre = postList.get(i).getNombre();
                        String tipo = DepositosFragment.this.getActivity().getIntent().getStringExtra("tipo");
                        Intent intent = new Intent(DepositosFragment.this.getContext(), DetalleDepositos.class);
                        intent.putExtra("id_deposito", id);
                        intent.putExtra("registro", registro);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("tipo", tipo);
                        DepositosFragment.this.startActivity(intent);
                    }
                });
            }

            public void onFailure(Call<List<PostDepositos>> call, Throwable t) {
            }
        });
        return rootView;
    }
}
