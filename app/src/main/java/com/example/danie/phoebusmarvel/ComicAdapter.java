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

public class ComicAdapter extends ArrayAdapter<Comic> {

    private final Context context;
    private final ArrayList<Comic> elementos;

    public ComicAdapter(Context context, ArrayList<Comic> elementos){
        super(context, R.layout.item, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    // CRIANDO O ITEM DA LIST VIEW DA PAGINA INICIAL
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item, parent, false);

        TextView comicTitulo = rowView.findViewById(R.id.item_title);
        TextView comicDescricao = rowView.findViewById(R.id.item_description);
        TextView comicPreco = rowView.findViewById(R.id.item_price);
        TextView comicRaridade = rowView.findViewById(R.id.item_raridade);
        ImageView thumb = rowView.findViewById(R.id.item_thumb);
        Picasso.with(context).load(elementos.get(position).getThumbnail()).into(thumb);

        comicTitulo.setText(elementos.get(position).getTitle());
        comicDescricao.setText(elementos.get(position).getPreDescription());
        comicPreco.setText(String.valueOf(elementos.get(position).getPrice()));
        comicRaridade.setText(elementos.get(position).getRaro());

        return rowView;
    }
}
