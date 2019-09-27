package com.example.fixit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static java.lang.Integer.parseInt;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_registrarse.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_registrarse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_registrarse extends Fragment {

    View vistaR;
    private EditText txt_carne;
    private EditText txt_nombre;
    private EditText txt_apellido;
    private EditText txt_correo;
    private EditText txt_password;
    private EditText txt_password2;
    private Button btnRegistrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        vistaR = inflater.inflate(R.layout.fragment_fragment_registrarse, container, false);

        txt_carne = vistaR.findViewById(R.id.txt_carne);
        txt_nombre = vistaR.findViewById(R.id.txt_nombre);
        txt_apellido = vistaR.findViewById(R.id.txt_apellido);
        txt_correo = vistaR.findViewById(R.id.txt_correo);
        txt_password = vistaR.findViewById(R.id.txt_password);
        txt_password2 = vistaR.findViewById(R.id.txt_repPassword);
        btnRegistrar = vistaR.findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarRegistro();
                txt_carne.setText("");
                txt_nombre.setText("");
                txt_apellido.setText("");
                txt_correo.setText("");
                txt_password.setText("");
                txt_password2.setText("");
                btnRegistrar.setText("");

            }
        });
        return vistaR;
    }

    public Connection conexionBD(){
        Connection conexion = null;
        String host = "192.168.1.27";
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

    public void AgregarRegistro(){
        try {

            PreparedStatement pst = conexionBD().prepareStatement("insert into usuario values(?,?,?,?,?,?,?)");
            pst.setInt(1, parseInt(txt_carne.getText().toString()));
            pst.setString(2, txt_nombre.getText().toString());
            pst.setString(3, txt_apellido.getText().toString());
            pst.setString(4, txt_correo.getText().toString());
            pst.setString(5, txt_password.getText().toString());
            pst.setInt(6, 1);
            pst.setInt(7, 1);
            pst.executeUpdate();

            Toast.makeText(getContext(),"REGISTRO EXITOSO",Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }



}