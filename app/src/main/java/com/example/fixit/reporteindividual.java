package com.example.fixit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class reporteindividual extends Fragment {

    private TextView hola;
    public static String carne = "hola";
    View vista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_reporteindividual, container, false);
        hola = vista.findViewById(R.id.hola);

        hola.setText(carne);

        return vista;
    }
}

