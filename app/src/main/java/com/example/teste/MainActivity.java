package com.example.teste;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.teste.configuracao.ConfiguracaoActivity;
import com.example.teste.pessoa.Pessoa;
import com.example.teste.pessoa.PessoaActivity;
import com.example.teste.pessoa.PessoaAdapter;
import com.example.teste.pessoa.PessoaDAO;
import com.example.teste.pessoa.PessoaFilterActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PessoaDAO pessoaDAO;
    PessoaAdapter adapter;
    Context context;

    String filtroNome;
    Date filtroDataNascimento, filtroDataCadastro;

    public static final String PESSOA_ID 			    = "ID_PESSOA";
    public static final int ADICIONAPESSOA 				= 1;
    public static final int FILTRARPESSOA 				= 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = MainActivity.this;

        pessoaDAO = new PessoaDAO(context);

        List<Pessoa> pessoas = pessoaDAO.buscar();
        adapter = new PessoaAdapter(context, pessoas);

        ListView listPessoa = (ListView) findViewById(R.id.lista_pessoa);
        listPessoa.setAdapter(adapter);
        listPessoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                Intent it = new Intent(context, PessoaActivity.class);
                it.putExtra(PESSOA_ID, id);
                startActivityForResult(it, 0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, PessoaActivity.class);
                startActivityForResult(it, ADICIONAPESSOA);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            abrirTelaConfiguracao();
            return true;
        }
        if (id == R.id.action_filter) {
            abrirTelaFiltro();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ADICIONAPESSOA:
                    atualizar();
                    break;
                case FILTRARPESSOA:
                    filtroNome = data.getStringExtra(PessoaFilterActivity.FILTRONOME);
                    Long longDate = data.getLongExtra(PessoaFilterActivity.FILTRODATACADASTRO, -1);
                    if (longDate != -1)
                        filtroDataCadastro = new Date(longDate);

                    longDate = data.getLongExtra(PessoaFilterActivity.FILTRODATANASCIMENTO, -1);
                    if (longDate != -1)
                        filtroDataNascimento = new Date(longDate);
                    atualizarDados();
                    break;
            }
        }
    }

    private void atualizar(){
        adapter.setLista(pessoaDAO.buscar());
        adapter.notifyDataSetChanged();
    }

    private void atualizarDados(){
        adapter.setLista(pessoaDAO.buscar(filtroNome, filtroDataCadastro, filtroDataNascimento));
        adapter.notifyDataSetChanged();
    }

    private void abrirTelaConfiguracao(){
        Intent it = new Intent(context, ConfiguracaoActivity.class);
        startActivity(it);
    }

    private void abrirTelaFiltro(){
        Intent it = new Intent(context, PessoaFilterActivity.class);
        it.putExtra(PessoaFilterActivity.FILTRONOME, filtroNome);
        if (filtroDataCadastro instanceof Date)
            it.putExtra(PessoaFilterActivity.FILTRODATACADASTRO, filtroDataCadastro.getTime());
        if (filtroDataNascimento instanceof Date)
            it.putExtra(PessoaFilterActivity.FILTRODATANASCIMENTO, filtroDataNascimento.getTime());
        startActivityForResult(it, FILTRARPESSOA);
    }
}
