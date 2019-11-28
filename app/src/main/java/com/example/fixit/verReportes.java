package com.example.fixit;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class verReportes extends AppCompatActivity {

    List<Reporte> ListaReportes;
    RecyclerView recyclerView;
    Connection conexionMySql = null;
    private Statement st = null;
    private ResultSet rs = null;
    private Usuario columnas = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_reportes);

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ListaReportes = new ArrayList<>();

       //cargarReportes();

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
            Toast.makeText(verReportes.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }
   /** private void cargarReportes(){

        try{
            String sql = "select * from reporte";
            st = conexionBD().createStatement();
            rs = st.executeQuery(sql);
            if(rs.first())
            {
                do
                {

                    ListaReportes.add(new Reporte(
                            rs.getInt("idReporte"),
                            rs.getInt("carne"),
                            rs.getString("TipoProblema"),
                            rs.getString("Descripcion"),
                            rs.getString("Imagen"),
                            rs.getString("Modulo"),
                            rs.getString("Salon"),
                            rs.getString("fecha"),
                            rs.getInt("estado")));
                }while(rs.next());
            }

           // AdaptadorVistaReporte adapter = new AdaptadorVistaReporte(verReportes.this, ListaReportes, tra);
           // recyclerView.setAdapter(adapter);
        } catch (SQLException ex) {
            Log.d("Error", ex.getMessage());
        }

    }**/
}
