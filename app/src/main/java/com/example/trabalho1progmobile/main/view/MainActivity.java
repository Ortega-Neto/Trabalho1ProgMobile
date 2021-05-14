package com.example.trabalho1progmobile.main.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.BancoDeDados;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoRepository;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoRepository;
import com.example.trabalho1progmobile.inserirAluno.InserirAlunoActivity;
import com.example.trabalho1progmobile.inserirCurso.InserirCursoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static BancoDeDados bancoDeDados;
    private ListView lstViewAlunoCursos;
    private int itemSpinnerSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.listagem_alunos_cursos));

        criarInstanciaBandoDeDados();
        bindComOLayout();
        criarSpinnerAlunoCurso();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        definirListagem(itemSpinnerSelecionado);
    }

    private void criarInstanciaBandoDeDados(){
        AsyncTask.execute(() -> {
            runOnUiThread(()-> {
                bancoDeDados = BancoDeDados.getInstance(this);
            });
        });
    }

    private void bindComOLayout(){
        lstViewAlunoCursos = findViewById(R.id.lstViewAlunoCursos);
        Button btnInserirAluno = findViewById(R.id.btnInserirAluno);
        Button btnInserirCurso = findViewById(R.id.btnInserirCurso);

        btnInserirAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        InserirAlunoActivity.class
                );
                startActivity(intent);
            }
        });

        btnInserirCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        InserirCursoActivity.class
                );
                startActivity(intent);
            }
        });
    }

    private void criarSpinnerAlunoCurso(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerAlunoCurso);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.alunoCurso, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        criarListenerDoSpinner(spinner);
    }

    private void criarListenerDoSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemSpinnerSelecionado = position;
                definirListagem(itemSpinnerSelecionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void definirListagem(int itemSpinnerSelecionado){
        if(itemSpinnerSelecionado == 0) {
            listarAlunos();
        }
        else {
            listarCursos();
        }
    }

    private void listarAlunos(){
        AsyncTask.execute(() -> {
            List<Aluno> listaDeCursos = (ArrayList<Aluno>) AlunoRepository.buscarTodosOsAlunos();

            runOnUiThread(()-> {
                ArrayAdapter arrayAdapterAlunos = new AlunoListAdapter(
                        this,
                        R.layout.list_item_aluno,
                        listaDeCursos
                        );
                lstViewAlunoCursos.setAdapter(arrayAdapterAlunos);
            });
        });
    }

    private void listarCursos(){
        AsyncTask.execute(() -> {
            List<Curso> listaDeCursos = (ArrayList<Curso>) CursoRepository.buscarTodosOsCursos();

            runOnUiThread(()-> {
                ArrayAdapter arrayAdapterCursos = new CursoListAdapter(
                        this,
                        R.layout.list_item_curso,
                        listaDeCursos
                );
                lstViewAlunoCursos.setAdapter(arrayAdapterCursos);
            });
        });
    }
}