package com.example.trabalho1progmobile.main.view;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoRepository;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoRepository;

import java.util.ArrayList;
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
            TextView edtNomeDoAluno = (TextView) v.findViewById(R.id.edtNomeDoAluno);
            TextView edtCursoDoAluno = (TextView) v.findViewById(R.id.edtCursoDoAluno);

            AsyncTask.execute(() -> {
                String nomeDoCurso = CursoRepository.buscarCursoPorId(a.cursoId);
                Activity activity = (Activity) mContext;

                activity.runOnUiThread(()-> {
                    edtNomeDoAluno.setText(a.nomeAluno);
                    edtCursoDoAluno.setText(nomeDoCurso);
                });
            });

        }

        return v;
    }

}