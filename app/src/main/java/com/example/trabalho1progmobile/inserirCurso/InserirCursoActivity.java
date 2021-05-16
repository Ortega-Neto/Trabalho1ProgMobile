package com.example.trabalho1progmobile.inserirCurso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoRepository;

public class InserirCursoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.inserir_curso));
        setContentView(R.layout.activity_curso);

        bindComOLayout();
    }

    private void bindComOLayout(){
        EditText edtNomeDoCurso = findViewById(R.id.edtNomeDoCurso);
        EditText edtHorasDoCurso = findViewById(R.id.edtHorasDoCurso);
        Button btnSalvarCurso = findViewById(R.id.btnSalvarCurso);

        btnSalvarCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Curso curso = new Curso();
                curso.nomeCurso = edtNomeDoCurso.getText().toString();
                curso.qtdeHoras = Integer.parseInt(edtHorasDoCurso.getText().toString());
                salvarCursoNoBanco(curso);
            }
        });
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
}