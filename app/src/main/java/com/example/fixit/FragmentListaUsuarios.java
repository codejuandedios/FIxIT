package com.example.fixit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class FragmentListaUsuarios extends Fragment {

    View vista;
    List<Usuario> ListaUsuarios;
    RecyclerView recyclerView;
    Connection conexionMySql = null;
    private Statement st = null;
    private ResultSet rs = null;
    private Usuario columnas = null;

    public FragmentListaUsuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fragment_lista_usuarios, container, false);


        recyclerView= vista.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ListaUsuarios = new ArrayList<>();

        cargarUsuarios();
        return vista;
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
    private void cargarUsuarios(){

        try{
            String sql = "select * from usuario";
            st = conexionBD().createStatement();
            rs = st.executeQuery(sql);
            if(rs.first())
            {
                do
                {

                    ListaUsuarios.add(new Usuario(
                            rs.getInt("carne"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("id_rol"),
                            rs.getInt("estado")));
                }while(rs.next());
            }

            AdaptadorVistaUsuario adapter = new AdaptadorVistaUsuario(getContext(), ListaUsuarios);
            recyclerView.setAdapter(adapter);
        } catch (SQLException ex) {
            Log.d("Error", ex.getMessage());
        }

    }
}

