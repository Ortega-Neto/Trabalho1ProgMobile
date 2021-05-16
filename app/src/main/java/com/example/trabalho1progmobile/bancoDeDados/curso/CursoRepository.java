package com.example.trabalho1progmobile.bancoDeDados.curso;
import java.util.List;

import static com.example.trabalho1progmobile.main.view.MainActivity.bancoDeDados;

public class CursoRepository {
    public static long inserirCurso(Curso curso){
        return bancoDeDados.cursoDao().inserirCurso(curso);
    }

    public static List<Curso> buscarTodosOsCursos(){
        return bancoDeDados.cursoDao().getAllCursos();
    }

    public static Curso buscarCursoPorNome(String nome){
        return bancoDeDados.cursoDao().findByName(nome);
    }

    public static void deletarCurso(Curso curso){
        bancoDeDados.cursoDao().delete(curso);
    }

    public static Integer atualizarCurso(Curso curso){
        return bancoDeDados.cursoDao().updateCurso(curso);
    }
}
