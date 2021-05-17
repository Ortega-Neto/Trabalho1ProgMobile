package com.example.trabalho1progmobile.bancoDeDados.curso;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface CursoDao {
    @Insert
    long inserirCurso(Curso curso);

    @Query("SELECT * FROM curso")
    List<Curso> getAllCursos();

    @Query("SELECT nomeCurso FROM curso WHERE cursoId = (:cursosId)")
    String loadById(int cursosId);

    @Query("SELECT * FROM curso WHERE nomecurso LIKE :nomecurso LIMIT 1")
    Curso findByName(String nomecurso);

    @Update
    Integer updateCurso(Curso curso);

    @Delete
    void delete(Curso curso);
}
