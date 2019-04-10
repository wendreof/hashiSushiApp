package com.example.hashisushi.views.fragmant_tab;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hashisushi.R;
import com.example.hashisushi.utils.CustomFonts;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ComboFragmanto extends Fragment  {

    private TextView txtLogoComb;
    private  TextView txtCombo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmant_combo,container,false);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        try {

            txtCombo = (TextView) getActivity().findViewById(R.id.txtCombo);
            txtLogoComb = (TextView) getActivity().findViewById(R.id.txtLogoCombo);

        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(getActivity().getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
