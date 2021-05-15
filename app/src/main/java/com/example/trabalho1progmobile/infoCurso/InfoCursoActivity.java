package com.example.trabalho1progmobile.infoCurso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;

public class InfoCursoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_curso);

        Curso cursoSelecionado;
        cursoSelecionado = (Curso) getIntent().getSerializableExtra("curso");

        setTitle(cursoSelecionado.nomeCurso);
    }
}