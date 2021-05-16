package com.example.trabalho1progmobile.inserirAluno;

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
import com.example.trabalho1progmobile.inserirCurso.InserirCursoActivity;
import com.example.trabalho1progmobile.main.view.CursoListAdapter;
import java.util.ArrayList;
import java.util.List;

import static com.example.trabalho1progmobile.utils.SpinnerCursoUtils.transformarListaDeCursosEmArrayDeCursos;

public class InserirAlunoActivity extends AppCompatActivity {
    private ArrayList<String> arrayDeCursos;
    private List<Curso> listaDeCursos;
    private EditText edtNomeDoAluno;
    private EditText edtCpfDoAluno;
    private EditText edtEmailDoAluno;
    private EditText edtTelefoneDoAluno;
    private int cursoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.inserir_aluno));
        setContentView(R.layout.activity_inserir_aluno);

        criarSpinnerDeCursos();
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
                Spinner spinner = (Spinner) findViewById(R.id.spinnerCursos);
                arrayDeCursos = transformarListaDeCursosEmArrayDeCursos(listaDeCursos);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        this, android.R.layout.simple_list_item_1, arrayDeCursos
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

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
                            InserirAlunoActivity.this,
                            InserirCursoActivity.class
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
        edtNomeDoAluno = findViewById(R.id.txtViewNomeDoAluno);
        edtCpfDoAluno = findViewById(R.id.edtCpfDoAluno);
        edtEmailDoAluno = findViewById(R.id.edtEmailDoAluno);
        edtTelefoneDoAluno = findViewById(R.id.edtTelefoneDoAluno);
        Button btnSalvarAluno = findViewById(R.id.btnSalvarAluno);

        btnSalvarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarDadosInseridos();
            }
        });
    }

    private void verificarDadosInseridos(){
        if(verificarSeOsCamposEstaoPreenchidos()) {
            Aluno aluno = new Aluno();
            aluno.nomeAluno = edtNomeDoAluno.getText().toString();
            aluno.cpf = edtCpfDoAluno.getText().toString();
            aluno.email = edtEmailDoAluno.getText().toString();
            aluno.telefone = edtTelefoneDoAluno.getText().toString();
            aluno.cursoId = listaDeCursos.get(cursoSelecionado).cursoId;

            salvarAlunoNoBanco(aluno);
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