package com.example.trabalho1progmobile.bancoDeDados.curso;


import java.util.List;
import static com.example.trabalho1progmobile.utils.AppUtils.bancoDeDados;

public class CursoRepository {
    public static void inserirCurso(Curso Curso){
        bancoDeDados.cursoDao().getAllCursos();
    }

    public static List<Curso> buscarTodosOsCursos(){
        return bancoDeDados.cursoDao().getAllCursos();
    }

    public static Curso buscarTodosOsCursosPorNome(String nome){
        return bancoDeDados.cursoDao().findByName(nome);
    }

    public static void deletarCurso(Curso Curso){
        bancoDeDados.cursoDao().delete(Curso);
    }

    public static void atualizarCurso(Curso Curso){
        bancoDeDados.cursoDao().updateCurso(Curso);
    }
}
