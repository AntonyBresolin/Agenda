package com.antonybresolin.agenda.ui.activity;

import static com.antonybresolin.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;
import static com.antonybresolin.agenda.ui.activity.ConstantesActivities.TITULO_APPBAR_EDITA_ALUNO;
import static com.antonybresolin.agenda.ui.activity.ConstantesActivities.TITULO_APPBAR_NOVO_ALUNO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.antonybresolin.agenda.DAO.AlunoDao;
import com.antonybresolin.agenda.R;
import com.antonybresolin.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDao dao = new AlunoDao();
    private Aluno aluno;
    private int contadorDeClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializarCampos();
        carregarAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
            if (contadorDeClicks == 0) {
                finalizarFormulario();
            }
            contadorDeClicks++;
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregarAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            //capta a chave Extra "aluno"
            setTitle(TITULO_APPBAR_EDITA_ALUNO);

            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);

            campoNome.setText(aluno.getNome());
            campoEmail.setText(aluno.getEmail());
            campoTelefone.setText(aluno.getTelefone());
        } else {
            aluno = new Aluno();
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
        }
    }


    private void finalizarFormulario() {
        preencherAluno();
        cadastrarEditar();
    }

    private void cadastrarEditar() {
        Intent dados = getIntent();
        //verifica se possui aluno, preferi assim do que o validador por int, pois caso aconteça algum problema
        //ele simplesmente vai salvar um novo
        if (dados.hasExtra(CHAVE_ALUNO)) {
            dao.editar(aluno);
        } else {
            dao.salvar(aluno);
        }
        finish();
    }

    private void inicializarCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }


    private void preencherAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        preencherCamposAlunoCadastrado(nome, telefone, email);
    }

    private void preencherCamposAlunoCadastrado(String nome, String telefone, String email) {
        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }

}
//aparecer mensagem na tela
//   Toast.makeText(FormularioAlunoActivity.this,
//         "Cliquei no botão Salvar",
//       Toast.LENGTH_SHORT).show();


//iniciar uma activity
// startActivity(new Intent(FormularioAlunoActivity.this,
//         ListaAlunosActivity.class));