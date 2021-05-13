package com.example.trabalho1progmobile.bancoDeDados.curso;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface CrusoDao {
    @Query("SELECT * FROM curso")
    List<Curso> getAllCursos();

    @Query("SELECT * FROM curso WHERE cursoId IN (:cursosIds)")
    List<Curso> loadAllByIds(int[] cursosIds);

    @Query("SELECT * FROM curso WHERE nomecurso LIKE :nomecurso LIMIT 1")
    Curso findByName(String nomecurso);

    @Update
    public void updateUsers(Curso... curso);

    @Insert
    void insertAll(Curso... curso);

    @Delete
    void delete(Curso curso);
}
