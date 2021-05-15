package com.example.trabalho1progmobile.bancoDeDados.curso;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "curso")
public class Curso implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int cursoId;
    public String nomeCurso;
    public int qtdeHoras;

}
