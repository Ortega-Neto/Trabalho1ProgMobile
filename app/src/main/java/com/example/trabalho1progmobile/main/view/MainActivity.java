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
import com.example.trabalho1progmobile.infoAluno.InfoAlunoActivity;
import com.example.trabalho1progmobile.inserirAluno.InserirAlunoActivity;
import com.example.trabalho1progmobile.inserirCurso.InserirCursoActivity;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    public static BancoDeDados bancoDeDados;
    private ListView lstViewAlunoCursos;
    private int itemSpinnerSelecionado;
    private List<Curso> listaDeCursos;
    private List<Aluno> listaDeAlunos;

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

        lstViewAlunoCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(itemSpinnerSelecionado == 0) {
                    opcoesAluno(listaDeAlunos.get(position));
                }
                else {
                    opcoesCurso(listaDeCursos.get(position));
                }

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
            listaDeAlunos = (ArrayList<Aluno>) AlunoRepository.buscarTodosOsAlunos();

            runOnUiThread(()-> {
                ArrayAdapter arrayAdapterAlunos = new AlunoListAdapter(
                        this,
                        R.layout.list_item_aluno,
                        listaDeAlunos
                        );
                lstViewAlunoCursos.setAdapter(arrayAdapterAlunos);
            });
        });
    }

    private void listarCursos(){
        AsyncTask.execute(() -> {
            listaDeCursos = (ArrayList<Curso>) CursoRepository.buscarTodosOsCursos();

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

    private void opcoesAluno(Aluno aluno){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
            .setTitleText("Opções para o aluno" + aluno.nomeAluno)
            .setConfirmText("Info")
            .setCancelText("Deletar Aluno")
            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent intent = new Intent(
                            MainActivity.this,
                            InfoAlunoActivity.class
                    );
                    intent.putExtra("aluno", aluno);
                    sweetAlertDialog.dismissWithAnimation();
                    startActivity(intent);

                }
            })
            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    AlunoRepository.deletarAluno(aluno);
                    sweetAlertDialog.dismissWithAnimation();
                    listarAlunos();
                }
            })
            .show();
    }

    private void opcoesCurso(Curso curso){
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Opções para o aluno" + curso.nomeCurso)
                .setConfirmText("Info")
                .setCancelText("Deletar Aluno")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(
                                MainActivity.this,
                                InserirCursoActivity.class
                        );
                        intent.putExtra("curso", curso);
                        sweetAlertDialog.dismissWithAnimation();
                        startActivity(intent);

                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        CursoRepository.deletarCurso(curso);
                        sweetAlertDialog.dismissWithAnimation();
                        listarCursos();
                    }
                })
                .show();
    }
}