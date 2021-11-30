package com.example.movil.models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.movil.R;
import com.example.movil.interfaces.DetallePresasService;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
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

public class DetallePresas extends AppCompatActivity {

    ImageView imageViewGota;
    TextView textViewNombre;
    TextView textViewCapacidadPresa;
    Button buttonDescargar;
    RelativeLayout layoutButton;
    ListView listView;
    String tipo;
    int id;
    List<PostDetalle> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_presas);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        statusBarColor();
        imageViewGota = findViewById(R.id.imageViewGota);
        textViewNombre = findViewById(R.id.textViewNombreDetalle);
        textViewCapacidadPresa = findViewById(R.id.textViewCapacidadDetalle);
        buttonDescargar = findViewById(R.id.textViewDescargar);
        layoutButton = findViewById(R.id.layoutButton);
        listView = findViewById(R.id.listView);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id_presa");
        tipo = bundle.getString("tipo");
        textViewNombre.setText(bundle.getString("nombre"));
        float capacidad = bundle.getFloat("registro");
        if( capacidad <= 20 ){
            imageViewGota.setImageResource(R.drawable.ic_reddrop);
            textViewCapacidadPresa.setText("Alerta");
        }else if(capacidad > 20 && capacidad < 80){
            imageViewGota.setImageResource(R.drawable.ic_yellowdrop);
            textViewCapacidadPresa.setText("Precaución");
        }else if(capacidad >= 80){
            imageViewGota.setImageResource(R.drawable.ic_greenwater);
            textViewCapacidadPresa.setText("Óptimo");
        }
        if(tipo == "1") {
            layoutButton.setVisibility(View.VISIBLE);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://192.168.100.4:45455/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();
        DetallePresasService detallePresasService = retrofit.create(DetallePresasService.class);
        Call<List<PostDetalle>> call = detallePresasService.find(id);
        call.enqueue(new Callback<List<PostDetalle>>() {
            @Override
            public void onResponse(Call<List<PostDetalle>> call, Response<List<PostDetalle>> response) {
                postList  = response.body();
                ListDetalleAdapter adapter = new ListDetalleAdapter(DetallePresas.this, postList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<PostDetalle>> call, Throwable t) {
                return ;
            }
        });
    }
    public void exportDatos(View view){
        try
        {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Detalle");
            int rownum = 1;
            Row rowTitulos = sheet.createRow(rownum);
            Cell cell = rowTitulos.createCell(0);
            cell.setCellValue("id_registro");
            cell = rowTitulos.createCell(1);
            cell.setCellValue("registro");
            for(PostDetalle postDetalle : postList)
            {
                Row row =   sheet.createRow(rownum++);
                createList(postDetalle, row);
            }
            File file = new File(getExternalFilesDir(null), "Historial.xls");
            FileOutputStream outputStream = null;
            try
            {
                outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
                Toast.makeText(this, "Archivo descargado correctamente.", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Toast.makeText(this, "Algo falló: "+ ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Algo falló: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private static void createList(PostDetalle postDetalle, Row row) // creating cells for each row
    {
        Cell cell = row.createCell(0);
        cell.setCellValue(postDetalle.getId());

//id item
//        cell = row.createCell(1);
//        cell.setCellValue();

        cell = row.createCell(2);
        cell.setCellValue(postDetalle.getRegistro());


    }


    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void statusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}
