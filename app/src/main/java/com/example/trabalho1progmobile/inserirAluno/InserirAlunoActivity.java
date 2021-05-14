package com.example.trabalho1progmobile.inserirAluno;

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

public class InserirAlunoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.inserir_aluno));
        setContentView(R.layout.activity_inserir_aluno);

        bindComOLayout();
    }

    private void bindComOLayout(){
        EditText edtNomeDoAluno = findViewById(R.id.txtViewNomeDoAluno);
        EditText edtCpfDoAluno = findViewById(R.id.edtCpfDoAluno);
        EditText edtEmailDoAluno = findViewById(R.id.edtEmailDoAluno);
        EditText edtTelefoneDoAluno = findViewById(R.id.edtTelefoneDoAluno);
        Button btnSalvarAluno = findViewById(R.id.btnSalvarAluno);

        btnSalvarAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno = new Aluno();
                aluno.nomeAluno = edtNomeDoAluno.getText().toString();
                aluno.cpf = edtCpfDoAluno.getText().toString();
                aluno.email = edtEmailDoAluno.getText().toString();
                aluno.telefone = edtTelefoneDoAluno.getText().toString();

                salvarAlunoNoBanco(aluno);
            }
        });
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
}