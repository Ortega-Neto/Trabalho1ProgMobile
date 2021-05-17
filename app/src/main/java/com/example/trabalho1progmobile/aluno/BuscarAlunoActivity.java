package com.example.trabalho1progmobile.aluno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoRepository;
import com.example.trabalho1progmobile.main.view.AlunoListAdapter;
import com.example.trabalho1progmobile.main.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class BuscarAlunoActivity extends AppCompatActivity {
    EditText edtNomeDoAlunoParaBuscar;
    ListView lstViewAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_aluno);
        setTitle("Buscar Aluno");

        bindComOLayout();
    }

    private void bindComOLayout(){
        edtNomeDoAlunoParaBuscar = findViewById(R.id.edtNomeDoAlunoParaBuscar);
        lstViewAluno = findViewById(R.id.lstViewAluno);
        Button btnBuscarAluno = findViewById(R.id.btnBuscarAluno);

        btnBuscarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarAlunoPorNome(edtNomeDoAlunoParaBuscar.getText().toString());
            }
        });
    }

    private void buscarAlunoPorNome(String nome){
        AsyncTask.execute(() -> {
            List<Aluno> listaDeAlunos = (ArrayList<Aluno>) AlunoRepository.buscarAlunoPorNome(nome);

            runOnUiThread(()-> {
                ArrayAdapter arrayAdapterAlunos = new AlunoListAdapter(
                        this,
                        R.layout.list_item_aluno,
                        listaDeAlunos
                );
                lstViewAluno.setAdapter(arrayAdapterAlunos);
            });
        });
    }
}