package com.antonybresolin.agenda.DAO;

import androidx.annotation.Nullable;

import com.antonybresolin.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorId = 1;

    public void salvar(Aluno aluno) {
        aluno.setId(contadorId);
        alunos.add(aluno);
        atualizarIds();
    }

    private void atualizarIds() {
        contadorId++;
    }

    public void editar(Aluno aluno) {
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno);
        if (alunoEncontrado != null) {
            //indexOF retorna a primeira ocorrencia do valor que estamos procurando
            int posicaoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoAluno, aluno);
        }
    }
    public void remover(Aluno aluno){
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno);
        if (alunoEncontrado != null) {
            int posicaoAluno = alunos.indexOf(alunoEncontrado);
            alunos.remove(posicaoAluno);
        }
    }

    @Nullable
    private Aluno buscaAlunoPeloId(Aluno aluno) {
        for (Aluno a :
                alunos) {
            if (a.getId() == aluno.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }
}
