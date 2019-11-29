package com.example.fixit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AsyncTask<Usuario, Void, Usuario> {
    Connection conexionMySql = null;
    private Statement st = null;
    private ResultSet rs = null;
    private Usuario columnas = null;
    private UsuarioPerfil perfil = null;

    @Override
    protected Usuario doInBackground(Usuario... datos) {
        String sql = "select carne, nombre, apellido, email, password, id_rol, estado from usuario where email = '"+datos[0].getEmail()+"' and password = '"+datos[0].getPassword() + "'";
        String host = "192.168.43.38";
        String port = "3306";
        String dbName = "fixit";
        String userName = "root";
        String password = "admon";
        try{
            conexionMySql = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbName, userName, password);
            st = conexionMySql.createStatement();
            rs = st.executeQuery(sql);
            if(rs.first())
            {
                do
                {

                   columnas = new Usuario(rs.getInt("carne"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("email"), rs.getString("password"), rs.getInt("id_rol"), rs.getInt("estado"));
                   perfil = new UsuarioPerfil(rs.getInt("carne"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("email"), rs.getString("password"), rs.getInt("id_rol"), rs.getInt("estado"));
                }while(rs.next());
            }
        } catch (SQLException ex) {
            Log.d("Error", ex.getMessage());
        }
        finally
        {
            try
            {
                conexionMySql.close();
                st.close();
                rs.close();

            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return columnas;
    }
}



