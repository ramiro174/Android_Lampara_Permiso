package com.example.repaso;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private Camera dispCamara;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    verificarPermisos(
        new String[] {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION});
    findViewById(R.id.btn_1).setOnClickListener(this);
    findViewById(R.id.btn_2)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                processOffClick();
              }
            });
  }

  private void verificarPermisos(String[] Permisos) {

    ArrayList<String> PermisosNegados = new ArrayList<String>();

    for (int x = 0; x < Permisos.length; x++) {

      if (ContextCompat.checkSelfPermission(this, Permisos[x])
          != PackageManager.PERMISSION_GRANTED) {

        PermisosNegados.add(Permisos[x]);
      }
    }

    if (PermisosNegados.size() >= 1) {

      Intent ipp = new Intent(this, listapermisos.class);
      ipp.putExtra("Permisos", PermisosNegados);
      startActivity(ipp);
      finish();
    }
  }

  @Override
  public void onClick(View view) {

    if (dispCamara != null) {
      Toast.makeText(getApplicationContext(), "Cámara encontrada", Toast.LENGTH_LONG).show();
      android.hardware.Camera.Parameters parametrosCamara = dispCamara.getParameters();

      // Get supported flash modes
      List modosFlash = parametrosCamara.getSupportedFlashModes();

      if (modosFlash != null && modosFlash.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
        // Set the flash parameter to use the torch
        parametrosCamara.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        try {
          dispCamara.setParameters(parametrosCamara);
          dispCamara.startPreview();
        } catch (Exception e) {
          Toast.makeText(getApplicationContext(), "Error al activar la linterna", Toast.LENGTH_LONG)
              .show();
        }
      } else {
        Toast.makeText(
                getApplicationContext(),
                "El dispositivo no tiene el modo de Flash Linterna",
                Toast.LENGTH_LONG)
            .show();
      }
    } else {
      Toast.makeText(
              getApplicationContext(),
              "No se ha podido acceder al Flash de la cámara",
              Toast.LENGTH_LONG)
          .show();
    }
  }

  private void processOffClick() {
    if (dispCamara != null) {
      android.hardware.Camera.Parameters parametrosCamara = dispCamara.getParameters();
      parametrosCamara.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
      dispCamara.setParameters(parametrosCamara);
    } else {
      Toast.makeText(
              getApplicationContext(),
              "No se ha podido acceder al Flash de la cámara",
              Toast.LENGTH_LONG)
          .show();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    try {
      dispCamara = Camera.open();
    } catch (Exception e) {
      Toast.makeText(
              getApplicationContext(), "No se ha podido acceder a la cámara", Toast.LENGTH_LONG)
          .show();
    }
  }

  @Override
  protected void onPause() {
    if (dispCamara != null) {
      dispCamara.release();
      dispCamara = null;
    }
    super.onPause();
  }
}
