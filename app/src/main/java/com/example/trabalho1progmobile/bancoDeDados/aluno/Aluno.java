package com.example.trabalho1progmobile.bancoDeDados.aluno;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "aluno")
public class Aluno {
    @PrimaryKey(autoGenerate = true)
    public int alunoId;
    public int cursoId;
    public String nomeAluno;
    public String cpf;
    public String email;
    public String telefone;
}
