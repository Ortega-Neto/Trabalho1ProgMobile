package com.example.trabalho1progmobile.bancoDeDados.aluno;
import java.util.List;

import static com.example.trabalho1progmobile.main.view.MainActivity.bancoDeDados;

public class AlunoRepository{
    public static long inserirAluno(Aluno aluno){
        return bancoDeDados.alunoDao().inserirAluno(aluno);
    }

    public static List<Aluno> buscarTodosOsAlunos(){
        return bancoDeDados.alunoDao().getAllAlunos();
    }

    public static List<Integer> buscarTodosCursosDosAlunos(){
        return bancoDeDados.alunoDao().getAllCursos();
    }

    public static void deletarAluno(Aluno aluno){
        bancoDeDados.alunoDao().delete(aluno);
    }

    public static int atualizarAluno(Aluno aluno){
        return bancoDeDados.alunoDao().updateAluno(aluno);
    }

}
