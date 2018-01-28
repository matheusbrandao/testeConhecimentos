package com.example.teste.pessoa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.teste.MainActivity;
import com.example.teste.R;
import com.example.teste.configuracao.Configuracao;
import com.example.teste.configuracao.ConfiguracaoDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PessoaActivity extends AppCompatActivity {

    PessoaDAO pessoaDAO;
    Pessoa pessoa;
    EditText edtNome, edtCpf, edtDataCadastro, edtDataNascimento, edtEstado, edtRG;
    ConfiguracaoDAO configuracaoDAO;
    Configuracao configuracao;
    LinearLayout layoutRG;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pessoaDAO = new PessoaDAO(this);
        configuracaoDAO = new ConfiguracaoDAO(this);
        configuracao = configuracaoDAO.buscar(1);

        long id = 0;
        Intent it = this.getIntent();
        if (it != null) {
            id = it.getLongExtra(MainActivity.PESSOA_ID, 0);

            pessoa = pessoaDAO.buscar(id);
            if (!(pessoa instanceof Pessoa)){
                pessoa = new Pessoa();
                pessoa.setDataCadastro(new Date());
            }
        }

        edtDataNascimento   = (EditText) findViewById(R.id.edtDataNascimento);
        edtDataCadastro     = (EditText) findViewById(R.id.edtDataCadastro);
        edtCpf              = (EditText) findViewById(R.id.edtCpf);
        edtNome             = (EditText) findViewById(R.id.edtNome);
        edtEstado           = (EditText) findViewById(R.id.edtEstado);
        edtRG               = (EditText) findViewById(R.id.edtRG);
        edtEstado.setEnabled(true);

        layoutRG            = (LinearLayout) findViewById(R.id.layoutRG);
        if (configuracao.getEstado().getUf().equals("SC"))
            layoutRG.setVisibility(View.VISIBLE);
        else
            layoutRG.setVisibility(View.INVISIBLE);

        long currentdate = System.currentTimeMillis();
        //String dateString = sdf.format(currentdate);
        //edtDataNascimento.setText(dateString);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                pessoa.setDataNascimento(myCalendar.getTime());
                atualizarCampoData();
            }
        };

        edtDataNascimento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PessoaActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                salvarDados(view);
            }
        });

        atualizarCampos();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void atualizarCampos() {
        if (pessoa.getDataNascimento() instanceof Date)
            edtDataNascimento.setText(sdf.format(pessoa.getDataNascimento()));

        if (pessoa.getDataCadastro() instanceof Date)
            edtDataCadastro.setText(sdf.format(pessoa.getDataCadastro()));

        edtNome.setText(pessoa.getNome());
        edtCpf.setText(pessoa.getCpf());
        edtEstado.setText(configuracao.getEstado().toString());
        edtRG.setText(pessoa.getRg());
    }

    private void atualizarCampoData(){
        if (pessoa.getDataNascimento() instanceof Date)
            edtDataNascimento.setText(sdf.format(pessoa.getDataNascimento()));
    }

    private void salvarDados(View view){
        boolean finalizaCadastro = true;
        if (configuracao.getEstado().getUf().equals("PR")){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.YEAR, -18);
            Date newDate = c.getTime();

            if(pessoa.getDataNascimento().after(newDate)) {
                Snackbar.make(view, "Cliente precisa ser maior que 18 anos!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                finalizaCadastro = false;
            }
        }

        if (finalizaCadastro) {
            pessoa.setNome(edtNome.getText().toString());
            pessoa.setCpf(edtCpf.getText().toString());
            pessoa.setRg(edtRG.getText().toString());
            pessoaDAO.salvar(pessoa);

            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
