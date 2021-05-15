package com.example.trabalho1progmobile.infoAluno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;

public class InfoAlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_aluno);

        Aluno alunoSelecionado;
        alunoSelecionado = (Aluno) getIntent().getSerializableExtra("aluno");

        setTitle(alunoSelecionado.nomeAluno);
    }
}