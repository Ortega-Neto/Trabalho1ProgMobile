package com.example.trabalho1progmobile.bancoDeDados.aluno;

import java.util.List;
import static com.example.trabalho1progmobile.utils.AppUtils.bancoDeDados;

public class AlunoRepository{

    public static void inserirAluno(Aluno aluno){
        bancoDeDados.alunoDao().getAllAlunos();
    }

    public static List<Aluno> buscarTodosOsAlunos(){
        return bancoDeDados.alunoDao().getAllAlunos();
    }

    public static Aluno buscarTodosOsAlunosPorNome(String nome){
        return bancoDeDados.alunoDao().findByName(nome);
    }

    public static void deletarAluno(Aluno aluno){
        bancoDeDados.alunoDao().delete(aluno);
    }

    public static void atualizarAluno(Aluno aluno){
        bancoDeDados.alunoDao().updateAluno(aluno);
    }

}
