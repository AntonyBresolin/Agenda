package com.antonybresolin.agenda;

import android.app.Application;

import com.antonybresolin.agenda.DAO.AlunoDao;
import com.antonybresolin.agenda.model.Aluno;

public class AgendaApplication extends Application {

    //A inserção de uma application para a adição de alunos exemplos, se torna uma boa prática, pois desta forma
    //não assumimos problemas com essa manipulação, pois o metodo onCreate é executado apenas uma vez, já a acctivity
    //possui maleabilidade em sua execução.

    //contudo, não se deve colocar muitos comandos nessa aplicação, pois em aparelhos mais basicos (com menor poder de processamento)
    //Pode ocorrer demora ao fazer o loading da pagina da activity.

    @Override
    public void onCreate() {
        super.onCreate();
        criarAlunosDeExemplo();
    }

    private void criarAlunosDeExemplo() {
        AlunoDao dao = new AlunoDao();
        dao.salvar(new Aluno("Dalton", "11233334444", "dalon@askjfnsa.com"));
        dao.salvar(new Aluno("Juremias", "11233334444", "dalon@askjfa.com"));
    }
}
