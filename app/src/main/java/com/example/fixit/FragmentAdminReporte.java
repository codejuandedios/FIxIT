package com.example.fixit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminReporte extends Fragment {

    List<Reporte> ListaReportes;
    RecyclerView recyclerView;
    Connection conexionMySql = null;
    private Statement st = null;
    private ResultSet rs = null;
    private Usuario columnas = null;

    View vista;
    public FragmentAdminReporte() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fragment_admin_reporte, container, false);
        recyclerView= vista.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ListaReportes = new ArrayList<>();



        cargarReportes();
        return vista;
    }

    public Connection conexionBD(){
        Connection conexion = null;
        String host = "192.168.1.13";
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
    private void cargarReportes(){

        try{
            String sql = "SELECT reporte.idReporte, reporte.carne, usuario.nombre, usuario.apellido, reporte.TipoProblema, reporte.Descripcion, reporte.Imagen, reporte.Salon, reporte.Modulo, reporte.fecha, reporte.estado FROM reporte, usuario WHERE reporte.carne = usuario.carne and reporte.estado!=2";
            st = conexionBD().createStatement();
            rs = st.executeQuery(sql);
            if(rs.first())
            {
                do
                {

                    ListaReportes.add(new Reporte(
                            rs.getInt("idReporte"),
                            rs.getInt("carne"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("TipoProblema"),
                            rs.getString("Descripcion"),
                            rs.getBlob("Imagen"),
                            rs.getString("Modulo"),
                            rs.getString("Salon"),
                            rs.getString("fecha"),
                            rs.getInt("estado")));

                }while(rs.next());
            }

            Fragment hola = new reporteindividual(ListaReportes);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.contenedor, hola);
            transaction.addToBackStack(null);

            AdaptadorVistaReporte adapter = new AdaptadorVistaReporte(getContext(), ListaReportes, transaction);
            recyclerView.setAdapter(adapter);
        } catch (SQLException ex) {
            Log.d("Error", ex.getMessage());
        }

    }
}
