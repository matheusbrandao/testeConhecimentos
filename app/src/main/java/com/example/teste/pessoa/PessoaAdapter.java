package com.example.teste.pessoa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import com.example.teste.R;

public class PessoaAdapter extends BaseAdapter {
	private Context context;
	private List<Pessoa> lista;

	public PessoaAdapter(Context context, List<Pessoa> lista){
		this.context 			= context;
		this.lista 				= lista;
	}

	public List<Pessoa> getLista() {
		return lista;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return lista.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Pessoa pessoa = lista.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.lista_item_pessoa, null);

		TextView nome = (TextView) view.findViewById(R.id.pessoa_id);
		nome.setText(pessoa.getId().toString() + " - " + pessoa.getNome());
		return view;
	}

}