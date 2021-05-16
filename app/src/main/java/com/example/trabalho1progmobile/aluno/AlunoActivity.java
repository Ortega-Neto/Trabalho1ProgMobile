package com.example.trabalho1progmobile.aluno;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoRepository;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoRepository;
import com.example.trabalho1progmobile.curso.CursoActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.trabalho1progmobile.utils.SpinnerCursoUtils.retornaOValorDaPosicaoDoCursoNoSpinner;
import static com.example.trabalho1progmobile.utils.SpinnerCursoUtils.transformarListaDeCursosEmArrayDeCursos;

public class AlunoActivity extends AppCompatActivity {
    private ArrayList<String> arrayDeCursos;
    private List<Curso> listaDeCursos;
    private EditText edtNomeDoAluno;
    private EditText edtCpfDoAluno;
    private EditText edtEmailDoAluno;
    private EditText edtTelefoneDoAluno;
    private int cursoSelecionado;
    private Spinner spinner;
    private boolean editarAluno = false;
    private Aluno alunoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        criarSpinnerDeCursos();

        alunoSelecionado = (Aluno) getIntent().getSerializableExtra("aluno");

        if(alunoSelecionado == null){
            setTitle(getString(R.string.inserir_aluno));
        }
        else {
            editarAluno = true;
            setTitle(alunoSelecionado.nomeAluno);
        }

        bindComOLayout();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        criarSpinnerDeCursos();
    }

    private void criarSpinnerDeCursos(){
        AsyncTask.execute(() -> {
            listaDeCursos = CursoRepository.buscarTodosOsCursos();

            runOnUiThread(()-> {
                spinner = (Spinner) findViewById(R.id.spinnerCursos);
                arrayDeCursos = transformarListaDeCursosEmArrayDeCursos(listaDeCursos);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1, arrayDeCursos
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                if(editarAluno) {
                    int idDoCursoNoSpinner = retornaOValorDaPosicaoDoCursoNoSpinner(
                            listaDeCursos, alunoSelecionado.cursoId
                    );
                    spinner.setSelection(idDoCursoNoSpinner);}

                criarListenerDoSpinner(spinner);
            });
        });
    }

    private void criarListenerDoSpinner(Spinner spinner){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cursoSelecionado = -1;
                if(arrayDeCursos.get(position).equals("Novo Curso")){
                    Intent intent = new Intent(
                            AlunoActivity.this,
                            CursoActivity.class
                            );
                    startActivity(intent);
                }
                else if(arrayDeCursos.get(position).equals("Cursos")){ }
                else {
                    cursoSelecionado = position-1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void bindComOLayout(){
        edtNomeDoAluno = findViewById(R.id.edtNomeDoAluno);
        edtCpfDoAluno = findViewById(R.id.edtCpfDoAluno);
        edtEmailDoAluno = findViewById(R.id.edtEmailDoAluno);
        edtTelefoneDoAluno = findViewById(R.id.edtTelefoneDoAluno);
        Button btnSalvarAluno = findViewById(R.id.btnSalvarAluno);
        Button btnDeletarAluno = findViewById(R.id.btnDeletarAluno);

        if(editarAluno) {
            btnDeletarAluno.setVisibility(View.VISIBLE);
            preencherDadosDoAluno(alunoSelecionado);
        }

        btnSalvarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarDadosInseridos();
            }
        });

        btnDeletarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(() -> {
                    AlunoRepository.deletarAluno(alunoSelecionado);

                    runOnUiThread(()-> {
                        finish();
                    });
                });
            }
        });
    }

    private void preencherDadosDoAluno(Aluno aluno){
        edtNomeDoAluno.setText(aluno.nomeAluno);
        edtCpfDoAluno.setText(aluno.cpf);
        edtEmailDoAluno.setText(aluno.email);
        edtTelefoneDoAluno.setText(aluno.telefone);
    }

    private void verificarDadosInseridos(){
        if(verificarSeOsCamposEstaoPreenchidos()) {
            Aluno aluno = new Aluno();
            aluno.alunoId = alunoSelecionado.alunoId;
            aluno.nomeAluno = edtNomeDoAluno.getText().toString();
            aluno.cpf = edtCpfDoAluno.getText().toString();
            aluno.email = edtEmailDoAluno.getText().toString();
            aluno.telefone = edtTelefoneDoAluno.getText().toString();
            aluno.cursoId = listaDeCursos.get(cursoSelecionado).cursoId;

            if(editarAluno){
                verificarSeOsDadosForamAlterados(aluno);
            }
            else {
                salvarAlunoNoBanco(aluno);
            }
        }
    }

    private boolean verificarSeOsCamposEstaoPreenchidos(){
        boolean retorno = true;
        if(edtNomeDoAluno.getText().toString().equals("")){
            mensagemDeCampoVazio(edtNomeDoAluno);
            retorno = false;
        }
        if(edtCpfDoAluno.getText().toString().equals("")){
            mensagemDeCampoVazio(edtCpfDoAluno);
            retorno = false;
        }
        if(edtEmailDoAluno.getText().toString().equals("")){
            mensagemDeCampoVazio(edtEmailDoAluno);
            retorno = false;
        }
        if(edtTelefoneDoAluno.getText().toString().equals("")){
            mensagemDeCampoVazio(edtTelefoneDoAluno);
            retorno = false;
        }
        if(cursoSelecionado == -1){
            mensagemDeErroSemCursoSelecionado();
            retorno = false;
        }

        return retorno;
    }

    private void verificarSeOsDadosForamAlterados(Aluno aluno){
        if(
            !alunoSelecionado.nomeAluno.equals(aluno.nomeAluno) ||
            alunoSelecionado.cursoId != aluno.cursoId ||
            !alunoSelecionado.email.equals(aluno.email) ||
            !alunoSelecionado.cpf.equals(aluno.cpf) ||
            !alunoSelecionado.telefone.equals(aluno.telefone)
        ){
            atualizarAlunoNoBanco(aluno);
        }
        else {
            Toast.makeText(this,
                    getString(R.string.dados_iguais),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void salvarAlunoNoBanco(Aluno aluno){
        AsyncTask.execute(() -> {
            long retornoBD = AlunoRepository.inserirAluno(aluno);;

            String mensagem;
            if(retornoBD == -1){
                mensagem = "Erro ao Cadastrar Aluno!";
            }
            else{
                mensagem = "Cadastro de Aluno realizado com sucesso!";
            }

            runOnUiThread(()-> {
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
            });
            finish();
        });
    }

    private void atualizarAlunoNoBanco(Aluno aluno){
        AsyncTask.execute(() -> {
            long retornoBD = AlunoRepository.atualizarAluno(aluno);;

            String mensagem;
            if(retornoBD == -1){
                mensagem = "Erro ao Atualizar Aluno!";
            }
            else{
                mensagem = "Cadastro de Aluno atualizado com sucesso!";
            }

            runOnUiThread(()-> {
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
            });
            finish();
        });
    }

    private void mensagemDeCampoVazio(EditText editText){
        editText.setError(getString(R.string.campo_vazio));
    }

    private void mensagemDeErroSemCursoSelecionado(){
        Toast.makeText(this,
                getString(R.string.curso_vazio),
                Toast.LENGTH_LONG)
                .show();
    }
}