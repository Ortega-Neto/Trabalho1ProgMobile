package com.example.trabalho1progmobile.bancoDeDados.aluno;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AlunoDao {
    @Insert
    long inserirAluno(Aluno aluno);

    @Query("SELECT * FROM aluno")
    List<Aluno> getAllAlunos();

    @Query("SELECT cursoId FROM aluno")
    List<Integer> getAllCursos();

    @Query("SELECT * FROM aluno WHERE nomeAluno LIKE '%' || :nomeAluno || '%'")
    List<Aluno> findByName(String nomeAluno);

    @Update
    int updateAluno(Aluno aluno);

    @Delete
    void delete(Aluno aluno);
}
