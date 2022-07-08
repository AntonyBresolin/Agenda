package com.antonybresolin.agenda.ui.activity;

import static com.antonybresolin.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.antonybresolin.agenda.DAO.AlunoDao;
import com.antonybresolin.agenda.R;
import com.antonybresolin.agenda.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String NOME_APPBAR = "Lista de alunos";
    private final AlunoDao dao = new AlunoDao();
    private ArrayAdapter<Aluno> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_lista_alunos);
        setTitle(NOME_APPBAR);
        configurarFabNovoAluno();
        apresentarLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lisa_alunos_menu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
            removerAluno(alunoEscolhido);
        }

        return super.onContextItemSelected(item);
    }

    private void configurarFabNovoAluno() {
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_lista_alunos_fab_novo_aluno);
        botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFormularioModoInserirAluno();
            }
        });
    }

    private void abrirFormularioModoInserirAluno() {
        startActivity(new Intent(ListaAlunosActivity.this,
                FormularioAlunoActivity.class));
    }

    //pesquisar ciclo de vida de uma activity no docs
    @Override
    protected void onResume() {
        super.onResume();
        atualizarAdapter();
    }

    private void atualizarAdapter() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    private void apresentarLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        configurarAdapter(listaDeAlunos);
        //pegar posicao do item na tabela
        configurarLinisterDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
    }

    private void configurarLinisterDeCliqueLongoPorItem(ListView listaDeAlunos) {

    }

    private void removerAluno(Aluno aluno) {
        dao.remover(aluno);
        adapter.remove(aluno);
    }

    private void configurarLinisterDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);
                //Passa para outra tela editando o aluno
                abrirFormularioModoEditarAluno(alunoEscolhido);
            }
        });
    }

    private void abrirFormularioModoEditarAluno(Aluno aluno) {
        Intent telaFormularioActivity = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
        telaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
        startActivity(telaFormularioActivity);
    }

    private void configurarAdapter(ListView listaDeAlunos) {
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);
        listaDeAlunos.setAdapter(adapter);
    }
}