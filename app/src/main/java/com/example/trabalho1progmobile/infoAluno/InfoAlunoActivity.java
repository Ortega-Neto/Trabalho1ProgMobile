package com.example.trabalho1progmobile.infoAluno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;

public class InfoAlunoActivity extends AppCompatActivity {
    EditText edtNomeDoAluno;
    EditText edtCpfDoAluno;
    EditText edtEmailDoAluno;
    EditText edtTelefoneDoAluno;
    Button btnSalvarAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_aluno);

        Aluno alunoSelecionado;
        alunoSelecionado = (Aluno) getIntent().getSerializableExtra("aluno");

        setTitle(alunoSelecionado.nomeAluno);

        preencherDadosAluno(alunoSelecionado);
        bindComOLayout();
    }

    private void preencherDadosAluno(Aluno aluno){

    }

    private void bindComOLayout(){
        edtNomeDoAluno = findViewById(R.id.txtViewNomeDoAluno);
        edtCpfDoAluno = findViewById(R.id.edtCpfDoAluno);
        edtEmailDoAluno = findViewById(R.id.edtEmailDoAluno);
        edtTelefoneDoAluno = findViewById(R.id.edtTelefoneDoAluno);
        btnSalvarAluno = findViewById(R.id.btnSalvarAluno);
    }
}