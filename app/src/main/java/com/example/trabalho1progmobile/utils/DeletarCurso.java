package com.example.trabalho1progmobile.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.trabalho1progmobile.R;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoRepository;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoRepository;

import java.util.List;

public class DeletarCurso {
    public static void deletarCursoComSeguranca(Context context, Curso curso){
        Activity activity = (Activity) context;
        AsyncTask.execute(() -> {
            List<Integer> listaDeCursos = AlunoRepository.buscarTodosCursosDosAlunos();

            if(cursoParaDeletarEstaNaLista(curso.cursoId, listaDeCursos)){
                CursoRepository.deletarCurso(curso);
            }
            else {
                activity.runOnUiThread(()-> {
                    Toast.makeText(
                            context,
                            context.getText(R.string.erro_deletar_curso),
                            Toast.LENGTH_LONG
                    ).show();
                });
            }
        });
    }

    private static boolean cursoParaDeletarEstaNaLista(Integer curdoId, List<Integer> listaDeCursos){
        for (Integer idDoCurso: listaDeCursos) {
            if(idDoCurso.equals(curdoId)){
                return false;
            }
        }

        return true;
    }
}
