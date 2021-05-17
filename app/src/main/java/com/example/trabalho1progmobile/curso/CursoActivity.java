package com.example.trabalho1progmobile.curso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoRepository;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoRepository;

import static com.example.trabalho1progmobile.utils.DeletarCurso.deletarCursoComSeguranca;

public class CursoActivity extends AppCompatActivity {
    private Curso cursoSelecionado;
    private EditText edtNomeDoCurso;
    private EditText edtHorasDoCurso;
    private Button btnSalvarCurso;
    private Button btnDeletarCurso;
    private boolean editarCurso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        cursoSelecionado = (Curso) getIntent().getSerializableExtra("curso");

        if(cursoSelecionado == null){
            setTitle(getString(R.string.inserir_curso));
        }
        else {
            editarCurso = true;
            setTitle(cursoSelecionado.nomeCurso);
        }

        bindComOLayout();
    }

    private void bindComOLayout(){
        edtNomeDoCurso = findViewById(R.id.edtNomeDoCurso);
        edtHorasDoCurso = findViewById(R.id.edtHorasDoCurso);
        btnSalvarCurso = findViewById(R.id.btnSalvarCurso);
        btnDeletarCurso = findViewById(R.id.btnDeletarCurso);

        if(editarCurso) {
            btnDeletarCurso.setVisibility(View.VISIBLE);
            preencherDadosDoCurso(cursoSelecionado);
        }

        btnSalvarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarDadosInseridos();
            }
        });

        btnDeletarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(() -> {
                    deletarCursoComSeguranca(CursoActivity.this, cursoSelecionado);

                    runOnUiThread(()-> {
                        finish();
                    });
                });
            }
        });
    }

    private void preencherDadosDoCurso(Curso curso){
        Integer qtdeHoras = (Integer) (curso.qtdeHoras);
        edtNomeDoCurso.setText(curso.nomeCurso);
        edtHorasDoCurso.setText(qtdeHoras.toString());
    }

    private void salvarCursoNoBanco(Curso curso){
        AsyncTask.execute(() -> {
            long retornoBD = CursoRepository.inserirCurso(curso);;

            String mensagem;
            if(retornoBD == -1){
                mensagem = "Erro ao Cadastrar Curso!";
            }
            else{
                mensagem = "Cadastro de Curso realizado com sucesso!";
            }

            runOnUiThread(()-> {
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
            });
            finish();
        });
    }

    private void verificarDadosInseridos(){
        if(verificarSeOsCamposEstaoPreenchidos()) {
            Curso curso = new Curso();
            curso.nomeCurso = edtNomeDoCurso.getText().toString();
            curso.qtdeHoras = Integer.parseInt(edtHorasDoCurso.getText().toString());

            if(editarCurso){
                curso.cursoId = cursoSelecionado.cursoId;
                verificarSeOsDadosForamAlterados(curso);
            }
            else {
                salvarCursoNoBanco(curso);
            }
        }
    }

    private boolean verificarSeOsCamposEstaoPreenchidos(){
        boolean retorno = true;
        if(edtNomeDoCurso.getText().toString().equals("")){
            mensagemDeCampoVazio(edtNomeDoCurso);
            retorno = false;
        }
        if(edtHorasDoCurso.getText().toString().equals("")){
            mensagemDeCampoVazio(edtHorasDoCurso);
            retorno = false;
        }

        return retorno;
    }

    private void verificarSeOsDadosForamAlterados(Curso curso){
        if(
            !cursoSelecionado.nomeCurso.equals(curso.nomeCurso) ||
            cursoSelecionado.qtdeHoras != curso.qtdeHoras
        ){
            atualizarCursoNoBanco(curso);
        }
        else {
            Toast.makeText(this,
                    getString(R.string.dados_iguais),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void atualizarCursoNoBanco(Curso curso){
        AsyncTask.execute(() -> {
            long retornoBD = CursoRepository.atualizarCurso(curso);

            String mensagem;
            if(retornoBD == -1){
                mensagem = "Erro ao Atualizar Curso!";
            }
            else{
                mensagem = "Cadastro de Curso atualizado com sucesso!";
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
}