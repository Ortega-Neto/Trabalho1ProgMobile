package com.example.trabalho1progmobile.utils;

import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;

import java.util.ArrayList;
import java.util.List;

public class SpinnerCursoUtils {
    public static ArrayList<String> transformarListaDeCursosEmArrayDeCursos(List<Curso> cursos){
        ArrayList<String> arrayDeCursos = new ArrayList<String>();
        arrayDeCursos.add("Cursos");
        for (Curso curso : cursos) {
            arrayDeCursos.add(curso.nomeCurso);
        }
        arrayDeCursos.add("Novo Curso");
        return arrayDeCursos;
    }
}
