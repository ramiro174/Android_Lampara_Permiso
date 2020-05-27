package com.example.repaso;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorPermisos
    extends RecyclerView.Adapter<AdaptadorPermisos.AdaptadorPermisoHolder> {
  ArrayList<String> Permisos;
  Context co;

  public AdaptadorPermisos(ArrayList<String> permisos, Context co) {
    Permisos = permisos;
    this.co = co;
  }

  @NonNull
  @Override
  public AdaptadorPermisos.AdaptadorPermisoHolder onCreateViewHolder(
      @NonNull ViewGroup parent, int viewType) {

    LayoutInflater lf = LayoutInflater.from(co);
    View v = lf.inflate(R.layout.renglonpermiso, parent, false);
    return new AdaptadorPermisoHolder(v);
  }

  @Override
  public void onBindViewHolder(
      @NonNull AdaptadorPermisos.AdaptadorPermisoHolder holder, int position) {

    holder.setTexto(Permisos.get(position).replace("android.permission.", ""));
    holder.Permiso = Permisos.get(position);
  }

  @Override
  public int getItemCount() {
    return Permisos.size();
  }

  public class AdaptadorPermisoHolder extends RecyclerView.ViewHolder {
    TextView txtpermiso;
    Switch sh;
    String Permiso;

    public AdaptadorPermisoHolder(@NonNull View itemView) {
      super(itemView);
      txtpermiso = itemView.findViewById(R.id.txtrPermiso);
      sh = itemView.findViewById(R.id.sw);
      sh.setOnCheckedChangeListener(
          new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              ActivityCompat.requestPermissions((Activity) co, new String[] {Permiso}, 1);
            }
          });
    }

    public void setTexto(String texto) {
      txtpermiso.setText(texto);
    }
  }
}
