package com.example.fixit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Pattern;


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
    private boolean esVisible;

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

        txt_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!esVisible) {
                    txt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    esVisible = true;
                    ///aqui puedes cambiar el texto del boton, o textview, o cambiar la imagen de un imageView.
                }
                else {
                    txt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    esVisible = false;
                    ///aqui puedes cambiar el texto del boton, o textview, o cambiar la imagen de un imageView.
                }
            }
        });
        txt_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!esVisible) {
                    txt_password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    esVisible = true;
                    ///aqui puedes cambiar el texto del boton, o textview, o cambiar la imagen de un imageView.
                }
                else {
                    txt_password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    esVisible = false;
                    ///aqui puedes cambiar el texto del boton, o textview, o cambiar la imagen de un imageView.
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_carne.getText().toString().isEmpty()&& txt_nombre.getText().toString().isEmpty()&& txt_apellido.getText().toString().isEmpty()&& txt_correo.getText().toString().isEmpty()&& txt_password.getText().toString().isEmpty()&&txt_password2.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Todos los campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                } else if(txt_carne.getText().toString().isEmpty()){ Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                }else if (txt_nombre.getText().toString().isEmpty()&& txt_apellido.getText().toString().isEmpty()&& txt_correo.getText().toString().isEmpty()&& txt_password.getText().toString().isEmpty()&&txt_password2.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                } else if(txt_nombre.getText().toString().isEmpty()){ Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                } else if (txt_apellido.getText().toString().isEmpty()&& txt_correo.getText().toString().isEmpty()&& txt_password.getText().toString().isEmpty()&&txt_password2.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                }else if(txt_apellido.getText().toString().isEmpty()) { Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                }else if (txt_correo.getText().toString().isEmpty()&& txt_password.getText().toString().isEmpty()&&txt_password2.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Campos vacíos, por favor ingrese  los datos requeridos", Toast.LENGTH_LONG).show();
                } else if(txt_correo.getText().toString().isEmpty()) { Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                }else if (txt_password.getText().toString().isEmpty()&&txt_password2.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                }else if(txt_password.getText().toString().isEmpty()) { Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                }else if(txt_password2.getText().toString().isEmpty()) { Toast.makeText(getContext(), "Campos vacíos, por favor ingrese los datos requeridos", Toast.LENGTH_LONG).show();
                } else if (isValidPsw()){  if(isValidEmaillId(txt_correo.getText().toString().trim())){
                    AgregarRegistro();
                    txt_carne.setText("");
                    txt_nombre.setText("");
                    txt_apellido.setText("");
                    txt_correo.setText("");
                    txt_password.setText("");
                    txt_password2.setText("");
                    btnRegistrar.setText("");
                }else{ Toast.makeText(getContext(), "Correo electronico invalido, El correo son las iniciales de tu primer apellido y tu número de carneé.", Toast.LENGTH_SHORT).show(); }
                }


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


    //____________________________________________________________________________METODOS_PARA_VALIDACIONES________________________________________________________________________
    //____________________________________________________________________________VALIDACION EMAIL_______________________________________________________________________________________________
    private boolean isValidEmaillId(String email){
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches(); }

    //_____________________________________________________________________________VALIDACION CONTRASEÑA__________________________________________________________________________________________
    private boolean isValidPsw() {
        boolean retorno=true;
        // Reset errors.

        txt_password.setError(null);
        txt_password2.setError(null);


        // Store values at the time of the login attempt.

        String Password = txt_password.getText().toString().trim();
        String Password2 = txt_password2.getText().toString().trim();



        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(Password)){
            txt_password.setError(getString(R.string.error_field_required));
            txt_password.requestFocus();
            retorno=false;
        }

        if (!Password.matches(".*\\d.*")){
            txt_password.setError(getString(R.string.error_not_find_number));
            txt_password.requestFocus();
            retorno=false;
        }

        if (!Password.matches(".*[a-z].*")){
            txt_password.setError(getString(R.string.error_not_find_lowercase_caracter));
            txt_password.requestFocus();
            retorno=false;
        }

        if (!Password.matches(".*[A-Z].*")){
            txt_password.setError(getString(R.string.error_not_find_uppercase_caracter));
            txt_password.requestFocus();
            retorno=false;
        }

        if (!Password.matches(".{8,15}")){
            txt_password.setError(getString(R.string.error_too_short_password));
            txt_password.requestFocus();
            retorno=false;
        }

        if (Password.matches(".*\\s.*")){
            txt_password.setError(getString(R.string.error_spaces));
            txt_password.requestFocus();
            retorno=false;
        }
        if (Password.equals(Password2)) {

        } else {
            txt_password2.setError(getString(R.string.settings_pwd_not_equal));
            txt_password2.requestFocus();
            retorno=false;
        }
        return retorno;
    }



}