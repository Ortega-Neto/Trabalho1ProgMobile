package com.example.trabalho1progmobile.main.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;

import java.util.List;

public class CursoListAdapter extends ArrayAdapter<Curso>  {
    private int resourceLayout;
    private Context mContext;

    public CursoListAdapter(Context context, int resource, List<Curso> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Curso c = getItem(position);

        if (c != null) {
            TextView edtNomeDoCurso = (TextView) v.findViewById(R.id.edtNomeDoCurso);

            edtNomeDoCurso.setText(c.nomeCurso);
        }

        return v;
    }

}