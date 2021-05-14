package com.example.trabalho1progmobile.bancoDeDados.curso;


import java.util.List;
import static com.example.trabalho1progmobile.utils.AppUtils.bancoDeDados;

public class CursoRepository {
    public static void inserirCurso(Curso curso){
        bancoDeDados.cursoDao().inserirCurso(curso);
    }

    public static List<Curso> buscarTodosOsCursos(){
        return bancoDeDados.cursoDao().getAllCursos();
    }

    public static Curso buscarTodosOsCursosPorNome(String nome){
        return bancoDeDados.cursoDao().findByName(nome);
    }

    public static void deletarCurso(Curso curso){
        bancoDeDados.cursoDao().delete(curso);
    }

    public static void atualizarCurso(Curso curso){
        bancoDeDados.cursoDao().updateCurso(curso);
    }
}
