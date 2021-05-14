package com.example.trabalho1progmobile.bancoDeDados;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.trabalho1progmobile.bancoDeDados.aluno.Aluno;
import com.example.trabalho1progmobile.bancoDeDados.aluno.AlunoDao;
import com.example.trabalho1progmobile.bancoDeDados.curso.Curso;
import com.example.trabalho1progmobile.bancoDeDados.curso.CursoDao;

@Database(entities = {Aluno.class, Curso.class}, version = 1)
public abstract class BancoDeDados extends RoomDatabase {
    public abstract CursoDao cursoDao();
    public abstract AlunoDao alunoDao();

    private static volatile BancoDeDados instance = null;

    public static synchronized BancoDeDados getInstance(Context context){
        if (instance == null) {
            instance = buildDataBase(context);
        }
        return instance;
    }

    private static BancoDeDados buildDataBase(Context context){
        return Room.databaseBuilder(
                context,
                BancoDeDados.class, "bancoDeDados"
        ).build();
    }
}
