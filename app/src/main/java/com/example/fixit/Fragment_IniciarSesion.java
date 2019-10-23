package com.example.fixit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_IniciarSesion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_IniciarSesion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_IniciarSesion extends Fragment {



    View vista;
    private EditText txtCorreo;
    private EditText txtPassword;
    private Button btnIngresar;
    private ArrayList<Usuario> usuario = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_fragment__iniciar_sesion, container, false);

        btnIngresar = vista.findViewById(R.id.btnIngresar);
        txtCorreo = vista.findViewById(R.id.txtCorreo);
        txtPassword = vista.findViewById(R.id.txtPassword);

        //Metodo que se ejecuta al dar click en el boton de ingresar
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navegacion = new Intent(getContext(), Reporte_Usuario.class);
                Intent navegacion3 = new Intent(getContext(), MenuAdministrador.class);
               // startActivity(navegacion);

               Usuario user = validarLogin();
                if(user != null){
                    if(user.getEstado() == 0){
                        Toast.makeText(getContext(), "Usuario inactivo", Toast.LENGTH_LONG).show();
                    } else if(user.getIdRol() == 1 && user.getEstado() == 1){
                        startActivity(navegacion);
                    } else if(user.getIdRol() == 2 && user.getEstado() == 1){
                        startActivity(navegacion);
                    } else if(user.getIdRol() == 3 && user.getEstado() == 1){
                        startActivity(navegacion3);
                    }
                } else{
                    Toast.makeText(getContext(), "Usuario y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });

        return vista;

    }
    //Funcion que establece la conexion con la base de datos y retorna un objeto de tipo usuario
    public Usuario validarLogin(){
        //Variable que almacenará el resultado de la consulta
        Usuario usuario = null;
        //Asignamos el driver de conexion
        String driver = "com.mysql.jdbc.Driver";
        try{
            //Cargamos el driver con el conector jdbc
            Class.forName(driver).newInstance();
            Usuario user = new Usuario(0, "", "", txtCorreo.getText().toString(), txtPassword.getText().toString(),  0, 0);
            usuario = new Login().execute(user).get();
        } catch(Exception ex){
            Toast.makeText(getContext(), "Error al conectarse a la BD" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return usuario;
    }
}



