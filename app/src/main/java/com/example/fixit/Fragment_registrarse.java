package com.example.fixit;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_registrarse.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_registrarse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_registrarse extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_registrarse, container, false);

    }
}