package com.example.teste.pessoa;

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

import com.example.teste.MainActivity;
import com.example.teste.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PessoaFilterActivity extends AppCompatActivity {

    public static final String FILTRONOME = "FILTRONOME";
    public static final String FILTRODATANASCIMENTO = "FILTRODATANASCIMENTO";
    public static final String FILTRODATACADASTRO = "FILTRODATACADASTRO";

    EditText edtFiltroNome, edtFiltroDataCadastro, edtFiltroDataNascimento;
    Date filtroDataCadastro, filtroDataNascimento;

    Calendar myCalendar = Calendar.getInstance();
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener dataCadastro, dataNascimento;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtFiltroNome           = (EditText) findViewById(R.id.edtNome);
        edtFiltroDataCadastro   = (EditText) findViewById(R.id.edtDataCadastro);
        edtFiltroDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);

        long id = 0;
        Intent it = this.getIntent();
        if (it != null) {
            edtFiltroNome.setText(it.getStringExtra(FILTRONOME));

            Long longDate = it.getLongExtra(FILTRODATACADASTRO, -1);
            if (longDate != -1)
                filtroDataCadastro = new Date(longDate);

            longDate = it.getLongExtra(FILTRODATANASCIMENTO, -1);
            if (longDate != -1)
                filtroDataNascimento = new Date(longDate);
        }

        dataCadastro = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                filtroDataCadastro = myCalendar.getTime();
                atualizarCampoData();
            }
        };

        edtFiltroDataCadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PessoaFilterActivity.this, dataCadastro,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dataNascimento = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                filtroDataNascimento = myCalendar.getTime();
                atualizarCampoData();
            }
        };

        edtFiltroDataNascimento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PessoaFilterActivity.this, dataNascimento, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                filtrar();
            }
        });

        atualizarCampoData();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void atualizarCampoData(){
        if (filtroDataCadastro instanceof Date)
            edtFiltroDataCadastro.setText(sdf.format(filtroDataCadastro));

        if (filtroDataNascimento instanceof Date)
            edtFiltroDataNascimento.setText(sdf.format(filtroDataNascimento));
    }

    private void filtrar(){
        Intent it = new Intent();
        it.putExtra(FILTRONOME, edtFiltroNome.getText().toString());
        if (filtroDataCadastro instanceof Date)
            it.putExtra(FILTRODATACADASTRO, filtroDataCadastro.getTime());
        if (filtroDataNascimento instanceof Date)
            it.putExtra(FILTRODATANASCIMENTO, filtroDataNascimento.getTime());
        setResult(RESULT_OK, it);
        finish();
    }

}
