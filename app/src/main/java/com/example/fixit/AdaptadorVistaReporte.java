package com.example.fixit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdaptadorVistaReporte extends RecyclerView.Adapter<AdaptadorVistaReporte.MyViewHolder>{

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
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView color;
        public TextView tipoProblema, Descripcion, Estado, Fecha;
        public Button detalles;
        //context
        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);

            tipoProblema = itemView.findViewById(R.id.lblTipo);
            Descripcion = itemView.findViewById(R.id.lblDescripcion);
            Fecha = itemView.findViewById(R.id.lblFecha);
            Estado = itemView.findViewById(R.id.lblEstado);
            detalles = itemView.findViewById(R.id.detalles);
            context = itemView.getContext();

        }

        void setOnClickListener(){
            detalles.setOnClickListener(this);



        }
        @Override
        public void onClick(View v) {
            Intent navegacion = new Intent(context, Reporte_Usuario.class);
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

        holder.setOnClickListener();

        if (report.getEstado() == 3) {

            holder.Estado.setText("no solucionado");
        } else if (report.getEstado() == 4) {

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
