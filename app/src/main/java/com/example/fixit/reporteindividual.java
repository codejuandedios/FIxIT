package com.example.fixit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class reporteindividual extends Fragment {

    private TextView fecha, id, usuario, tipo, descripcion, salon, modulo;
    private Spinner estado;
    private Button actualizar;
    private ImageView foto;
    public static String posicion = "hola";
    View vista;
    List<Reporte> ListaReportes;
    Reporte repindividual;

    public reporteindividual(List<Reporte> lista){
        ListaReportes = lista;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_reporteindividual, container, false);
        fecha = vista.findViewById(R.id.fecha);
        id = vista.findViewById(R.id.id);
        usuario = vista.findViewById(R.id.usuario);
        tipo = vista.findViewById(R.id.tipoProblema);
        descripcion = vista.findViewById(R.id.descripcion);
        salon = vista.findViewById(R.id.salon);
        modulo = vista.findViewById(R.id.modulo);
        estado = vista.findViewById(R.id.spinnerEstado);
        repindividual = ListaReportes.get(Integer.parseInt(posicion));
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.EstadoReporte, android.R.layout.simple_spinner_item);
        estado.setAdapter(adapter1);
        actualizar = vista.findViewById(R.id.btnModificar);
        actualizar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                actualizar();
            }

        });
        foto= vista.findViewById(R.id.imageView2);


       try {
            Blob myblob= repindividual.getImagen();
            int bloblenght = (int) repindividual.getImagen().length();
            byte[] myblobAsBytes = myblob.getBytes(1, bloblenght);
            Bitmap bmp = BitmapFactory.decodeByteArray(myblobAsBytes,0, bloblenght);
            foto.setImageBitmap(bmp);


        } catch (SQLException e) {
            e.printStackTrace();
        }




        fecha.setText(repindividual.getFecha());
        id.setText(""+repindividual.getIdReporte());
        usuario.setText(repindividual.getNombre()+" "+repindividual.getApellido());
        tipo.setText(repindividual.getTipoProblema());
        descripcion.setText(repindividual.getDescripcion());
        salon.setText(repindividual.getSalon());
        modulo.setText(repindividual.getModulo());
        estado.setSelection(repindividual.getEstado()-1);
        return vista;
    }
    public void actualizar(){
        try {
            Blob myblob= repindividual.getImagen();
            int bloblenght = (int) repindividual.getImagen().length();
            byte[] myblobAsBytes = myblob.getBytes(1, bloblenght);
            Bitmap bmp = BitmapFactory.decodeByteArray(myblobAsBytes,0, bloblenght);
            foto.setImageBitmap(bmp);


        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"jelooooou",Toast.LENGTH_SHORT).show();
        }


        String sql = "UPDATE reporte SET "+
                "estado="+(estado.getSelectedItemPosition()+1)+" WHERE  idReporte="+Integer.parseInt(id.getText().toString());
        Statement st = null;
        try{
            st = conexionBD().createStatement();
            st.executeUpdate(sql);

            Toast.makeText(getContext(),"REPORTE ACTUALIZADO EXITOSAMENTE",Toast.LENGTH_SHORT).show();

            st.close();
        } catch (SQLException ex) {
            Log.d("Error", ex.getMessage());
        }
    }
    public Connection conexionBD(){
        Connection conexion = null;
        String host = "192.168.43.38";
        String port = "3306";
        String dbName = "fixit";
        String userName = "root";
        String password = "admon";
        try{

            StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName, userName, password);

        }catch (Exception e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }
}

