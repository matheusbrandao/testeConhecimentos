package com.example.teste.configuracao;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.teste.R;
import com.example.teste.estado.Estado;
import com.example.teste.estado.EstadoDAO;

public class ConfiguracaoActivity extends AppCompatActivity {

    Spinner spinnerEstado;
    EstadoDAO estadoDAO;
    ConfiguracaoDAO configuracaoDAO;
    Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        estadoDAO = new EstadoDAO(this);
        configuracaoDAO = new ConfiguracaoDAO(this);

        configuracao = configuracaoDAO.buscar(1);

        spinnerEstado = (Spinner) findViewById(R.id.spinnerEstado);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, estadoDAO.buscar());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEstado.setAdapter(adapter);

        for(int i = 0; i < adapter.getCount(); i++) {
            Estado e = (Estado) adapter.getItem(i);
            if(e.getId().equals(configuracao.getEstado().getId())){
                spinnerEstado.setSelection(i);
                break;
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarDados();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void salvarDados(){
        configuracao.setEstado((Estado) spinnerEstado.getSelectedItem());
        configuracaoDAO.salvar(configuracao);

        finish();
    }
}
