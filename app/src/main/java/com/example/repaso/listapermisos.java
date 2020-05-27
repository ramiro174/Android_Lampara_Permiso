package com.example.repaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class listapermisos extends AppCompatActivity {

    ArrayList<String>  Listapermisos;
    RecyclerView mirecy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listapermisos);
        Bundle bu=getIntent().getExtras();
        Listapermisos=bu.getStringArrayList("Permisos");
        AdaptadorPermisos Ap=new AdaptadorPermisos(Listapermisos,this);
        mirecy=findViewById(R.id.listapermisosNegados);
        mirecy.setHasFixedSize(true);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        mirecy.setLayoutManager(lm);
        mirecy.setAdapter(Ap);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(Listapermisos.contains(permissions[0])){

                        Listapermisos.remove(permissions[0]);

                    }



                }
                AdaptadorPermisos Ap=new AdaptadorPermisos(Listapermisos,this);
                mirecy.setAdapter(Ap);



                if (Listapermisos.size()==0)
                {

                    Intent ipp = new Intent(this, MainActivity.class);

                    startActivity(ipp);
                    finish();
                }
            }





            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

}
