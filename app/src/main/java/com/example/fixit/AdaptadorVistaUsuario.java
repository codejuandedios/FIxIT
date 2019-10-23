package com.example.fixit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdaptadorVistaUsuario extends RecyclerView.Adapter<AdaptadorVistaUsuario.MyViewHolder> {

    private Context mycont;
    private List<Usuario> ListaUsuarios;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdaptadorVistaUsuario(Context mycont, List<Usuario> ListaUsuarios) {
        this.mycont = mycont;
        this.ListaUsuarios = ListaUsuarios;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView icono;
        public TextView nombre,rol,Estado;

        public MyViewHolder(View itemView){
            super(itemView);

            icono = itemView.findViewById(R.id.icono);
            nombre = itemView.findViewById(R.id.lblTipo);
            rol = itemView.findViewById(R.id.lblDescripcion);
            Estado = itemView.findViewById(R.id.lblFecha);
        }

    }



    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        view = inflater.inflate(R.layout.lista_layout, null);

        return new MyViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Usuario user = ListaUsuarios.get(position);

        //cargamos la imagen
        Glide.with(mycont)
                .load(R.drawable.usuario)
                .into(holder.icono);

        holder.nombre.setText(""+user.getNombre()+" "+user.getApellido());
        if(user.getIdRol()==1){
            holder.rol.setText("Usuario");
        }else if(user.getIdRol()==2){
            holder.rol.setText("Moderador");
        }else if(user.getIdRol()==3){
            holder.rol.setText("Administrador");
        }

        if(user.getEstado()==1){
            holder.Estado.setText("activo");
        }else if(user.getEstado()==2){
            holder.Estado.setText("inactivo");
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ListaUsuarios.size();
    }



}
