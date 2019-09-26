package com.example.fixit.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fixit.Fragment_IniciarSesion;
import com.example.fixit.Fragment_registrarse;
import com.example.fixit.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
                Fragment_IniciarSesion iniciar = new Fragment_IniciarSesion();
                return iniciar;
            case 1:
                Fragment_registrarse registrarse = new Fragment_registrarse();
                return registrarse;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String section = null;

        switch (position) {
            case 0:
                section = "Iniciar Sesión";
                break;
            case 1:
                section = "Registrarse";
                break;
        }
        return section;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}