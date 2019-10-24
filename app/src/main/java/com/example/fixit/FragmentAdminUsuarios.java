package com.example.fixit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAdminUsuarios extends Fragment {

    View vistaR;
    private EditText buscarRol;
    private Button btnBuscarRol;
    private EditText txtCarne;
    private EditText txtCorreo;
    private EditText txtNombre;
    private EditText txtApellido;
    private Spinner spinnerRol;
    private Spinner spinnerEstado;
    ;


    public FragmentAdminUsuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vistaR = inflater.inflate(R.layout.fragment_fragment_admin_usuarios, container, false);
        spinnerRol = (Spinner) vistaR.findViewById(R.id.spinnerRol);
        spinnerEstado = (Spinner) vistaR.findViewById(R.id.spinnerEstado);
        buscarRol = (EditText) vistaR.findViewById(R.id.txtBuscarCarne);
        btnBuscarRol = (Button) vistaR.findViewById(R.id.btnBuscarCarne);
        txtCarne = (EditText) vistaR.findViewById(R.id.txtcarneRol);
        txtCorreo = (EditText) vistaR.findViewById(R.id.txtCorreoRol);
        txtNombre = (EditText) vistaR.findViewById(R.id.txtNombreRol);
        txtApellido = (EditText) vistaR.findViewById(R.id.txtApellidoRol);


        /* String [] values =
                {"Selecine el rol","1. Usuario","2. Moderador","3. Administrador"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerRol.setAdapter(adapter);
        */
        //__________Spinner de ROl____________

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.TipodeRolUsuario, android.R.layout.simple_spinner_item);
        spinnerRol.setAdapter(adapter);

        //__________Spinner de Estado_______
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.EstadoUsuario, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter1);

        return vistaR;
    }
}
