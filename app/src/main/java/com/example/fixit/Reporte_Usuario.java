package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class Reporte_Usuario extends AppCompatActivity {

    Spinner Opciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte__usuario);
        Opciones=(Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.Opciones, android.R.layout.simple_spinner_item);
        Opciones.setAdapter(adapter);
    }
}
