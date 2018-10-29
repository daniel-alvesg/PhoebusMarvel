package com.example.danie.phoebusmarvel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChekoutAdapter extends ArrayAdapter<Comic> {

    private final Context context;
    private final ArrayList<Comic> elementos;

    public ChekoutAdapter(Context context, ArrayList<Comic> elementos){
        super(context, R.layout.item, elementos);
        this.context = context;
        this.elementos = elementos;
    }


    // CRIANDO OS ITENS DA LIST VIEW DE CHECKOUT
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_chekout, parent, false);

        TextView comicTitulo = rowView.findViewById(R.id.txt_item_chekout_titulo);
        TextView comicQuantidade = rowView.findViewById(R.id.txt_item_chekout_quantidade);
        TextView comicPreco = rowView.findViewById(R.id.txt_item_chekout_valor);
        ImageView thumb = rowView.findViewById(R.id.item_chekout_thumb);

        Picasso.with(context).load(elementos.get(position).getThumbnail()).into(thumb);
        comicTitulo.setText(elementos.get(position).getTitle());
        comicPreco.setText(String.valueOf(elementos.get(position).getPrice()));
        comicQuantidade.setText(String.valueOf(elementos.get(position).getQuantidade()));

        return rowView;
    }

}
