package com.example.trabalho1progmobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.trabalho1progmobile.alunos.AlunoListAdapter;
import com.example.trabalho1progmobile.bancoDeDados.BancoDeDados;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoRepository;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lstViewAlunoCursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        criarInstanciaBandoDeDados();
        bindComOLayout();
        criarSpinnerAlunoCurso();
    }

    private void criarInstanciaBandoDeDados(){
        BancoDeDados.instance = BancoDeDados.getInstance(this);
    }

    private void bindComOLayout(){
        lstViewAlunoCursos = findViewById(R.id.lstViewAlunoCursos);
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
                int itemSelecionado = position;

                if(itemSelecionado == 0){
                    listarAlunos();
                }
                else {
                    listarCursos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void listarAlunos(){
        List<Aluno> listaDeCursos = (ArrayList<Aluno>) AlunoRepository.buscarTodosOsAlunos();
        ArrayAdapter arrayAdapterCursos = new AlunoListAdapter(
                this,
                R.layout.list_item_aluno,
                listaDeCursos
                );
        lstViewAlunoCursos.setAdapter(arrayAdapterCursos);
    }

    private void listarCursos(){
        List<Curso> listaDeCursos = (ArrayList<Curso>) CursoRepository.buscarTodosOsCursos();
        ArrayAdapter arrayAdapterCursos = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaDeCursos
        );
        lstViewAlunoCursos.setAdapter(arrayAdapterCursos);
    }
}