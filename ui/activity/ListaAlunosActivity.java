package com.antonybresolin.agenda.ui.activity;

import static com.antonybresolin.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;
import static com.antonybresolin.agenda.ui.activity.ConstantesActivities.TITULO_APPBAR_LISTA_DE_ALUNO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.antonybresolin.agenda.DAO.AlunoDao;
import com.antonybresolin.agenda.R;
import com.antonybresolin.agenda.model.Aluno;
import com.antonybresolin.agenda.ui.adapter.ListaAlunosAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaAlunosActivity extends AppCompatActivity {

    private final AlunoDao dao = new AlunoDao();
    private ListaAlunosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_lista_alunos);
        setTitle(TITULO_APPBAR_LISTA_DE_ALUNO);
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
            confirmarRemocaoAluno(alunoEscolhido);
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

    private void apresentarLista() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
        configurarAdapter(listaDeAlunos);
        //pegar posicao do item na tabela
        configurarLinisterDeCliquePorItem(listaDeAlunos);
        registerForContextMenu(listaDeAlunos);
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


    public void removerAluno(Aluno aluno) {
        dao.remover(aluno);
        adapter.remove(aluno);
    }

    public void confirmarRemocaoAluno(Aluno aluno) {
        new AlertDialog.Builder(this)
                .setTitle("Remover Aluno")
                .setMessage("Tem certeza que deseja remover o aluno")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removerAluno(aluno);
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }


    public void atualizarAdapter() {
        adapter.atualizar(dao.todos());
    }

    public void configurarAdapter(ListView listaDeAlunos) {
        //base adapter, para adicionar mais de 1 listView por xml
        adapter = new ListaAlunosAdapter(this);
        listaDeAlunos.setAdapter(adapter);
    }

}