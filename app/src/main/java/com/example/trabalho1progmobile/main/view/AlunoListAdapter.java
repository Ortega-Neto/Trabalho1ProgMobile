package com.example.trabalho1progmobile.main.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;

import java.util.List;

public class AlunoListAdapter extends ArrayAdapter<Aluno> {
    private int resourceLayout;
    private Context mContext;

    public AlunoListAdapter(Context context, int resource, List<Aluno> items) {
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

        Aluno a = getItem(position);

        if (a != null) {
            EditText edtNomeDoAluno = (EditText) v.findViewById(R.id.edtNomeDoAluno);
            EditText edtIDAluno = (EditText) v.findViewById(R.id.edtIDAluno);
            EditText edtEmail = (EditText) v.findViewById(R.id.edtEmail);

            edtNomeDoAluno.setText(a.nomeAluno);
            edtIDAluno.setText(a.alunoId);
            edtEmail.setText(a.email);
        }

        return v;
    }

}