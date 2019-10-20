package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdaptadorVistaReporte extends RecyclerView.Adapter<AdaptadorVistaReporte.MyViewHolder> {

    private Context mycont;
    private List<Reporte> ListaReportes;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdaptadorVistaReporte(Context mycont, List<Reporte> ListaReportes) {
        this.mycont = mycont;
        this.ListaReportes = ListaReportes;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView color;
        public TextView tipoProblema, Descripcion, Estado, Fecha;

        public MyViewHolder(View itemView) {
            super(itemView);

            color = itemView.findViewById(R.id.color);
            tipoProblema = itemView.findViewById(R.id.lblTipo);
            Descripcion = itemView.findViewById(R.id.lblDescripcion);
            Fecha = itemView.findViewById(R.id.lblFecha);
            Estado = itemView.findViewById(R.id.lblEstado);
        }

    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        view = inflater.inflate(R.layout.lista_layout_reportes, null);

        return new MyViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reporte report = ListaReportes.get(position);

        //cargamos la imagen


        holder.tipoProblema.setText(report.getTipoProblema());
        holder.Descripcion.setText(report.getDescripcion());
        holder.Fecha.setText(report.getFecha());

        if (report.getEstado() == 3) {
            Glide.with(mycont)
                    .load(R.color.colorAccent)
                    .into(holder.color);
            holder.Estado.setText("no solucionado");
        } else if (report.getEstado() == 4) {
            Glide.with(mycont)
                    .load(R.color.colorPrimary)
                    .into(holder.color);
            holder.Estado.setText("solucionado");
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (ListaReportes != null) {
            return ListaReportes.size();
        } else {
            Toast.makeText(mycont, "Usuario inactivo", Toast.LENGTH_LONG).show();
        }

        return 0;
    }


}
