package com.example.movil.models;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movil.R;
import com.example.movil.interfaces.PresasService;
import com.example.movil.interfaces.TanquesService;
import com.example.movil.models.PostTanques;

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

public class TanquesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CHANNEL_ID = "NOTIFICATION";
    private static final int NOTIFICATION_ID = 0;
    ListView listViewTanques;
    SwipeRefreshLayout swipeTanques;
    String tipo;
    private String mParam1;
    private String mParam2;

    public static TanquesFragment newInstance(String param1, String param2) {
        TanquesFragment fragment = new TanquesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_tanques, container, false);
        this.listViewTanques = rootView.findViewById(R.id.listViewTanques);
        this.swipeTanques = rootView.findViewById(R.id.swipeTanques);
        this.tipo = getActivity().getIntent().getExtras().getString("tipo");
        new Retrofit.Builder().baseUrl("https://192.168.100.4:45455/api/").addConverterFactory(GsonConverterFactory.create()).client(getUnsafeOkHttpClient()).build().create(TanquesService.class).find().enqueue(new Callback<List<PostTanques>>() {
            public void onResponse(Call<List<PostTanques>> call, final Response<List<PostTanques>> response) {
                final List<PostTanques> postList = response.body();
                TanquesFragment.this.listViewTanques.setAdapter(new ListTanquesAdapter(TanquesFragment.this.getActivity(), postList));
                TanquesFragment.this.swipeTanques.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    public void onRefresh() {
                        TanquesFragment.this.swipeTanques.setRefreshing(false);
                        List<PostTanques> postList = response.body();
                        ListTanquesAdapter adapter = new ListTanquesAdapter(TanquesFragment.this.getActivity(), postList);
                        TanquesFragment.this.listViewTanques.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        for (PostTanques post : postList) {
                            if (post.registro <= 20) {
                                TanquesFragment.this.createNotificationChannel();
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(TanquesFragment.this.getActivity(), TanquesFragment.CHANNEL_ID);
                                NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender().setHintHideIcon(true);
                                builder.setSmallIcon(R.drawable.ic_reddrop);
                                builder.setContentText("Un tanque de agua cercano a ti presenta niveles alarmantes.");
                                builder.setContentTitle("¡Alerta!");
                                builder.setPriority(0);
                                builder.setLights(SupportMenu.CATEGORY_MASK, 1000, 1000);
                                builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
                                builder.setDefaults(1);
                                builder.extend(wearableExtender);
                                NotificationManagerCompat.from(TanquesFragment.this.getActivity()).notify(0, builder.build());
                                PendingIntent pendingIntent = PendingIntent.getActivity(TanquesFragment.this.getActivity(), 0, new Intent(TanquesFragment.this.getActivity(), MainInv.class).putExtra("tipo", TanquesFragment.this.tipo), PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                                builder.addAction(R.drawable.ic_reddrop, "Más detalles", pendingIntent);
                                NotificationManagerCompat.from(TanquesFragment.this.getActivity()).notify(0, builder.build());
                            }
                        }
                    }
                });
                TanquesFragment.this.listViewTanques.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        int id = postList.get(i).getId_tanque();
                        float registro = postList.get(i).getRegistro();
                        String nombre = postList.get(i).getColonia();
                        String tipo = TanquesFragment.this.getActivity().getIntent().getStringExtra("tipo");
                        Intent intent = new Intent(TanquesFragment.this.getContext(), DetalleTanques.class);
                        intent.putExtra("id_tanque", id);
                        intent.putExtra("registro", registro);
                        intent.putExtra("nombre", nombre);
                        intent.putExtra("tipo", tipo);
                        TanquesFragment.this.startActivity(intent);
                    }
                });
            }

            public void onFailure(Call<List<PostTanques>> call, Throwable t) {
            }
        });
        return rootView;
    }

    /* access modifiers changed from: private */
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(new NotificationChannel(CHANNEL_ID, "Notificación", NotificationManager.IMPORTANCE_DEFAULT));
        }
    }
}
