package com.example.danie.phoebusmarvel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetalhamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhamento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_detalhamento);

        // RECEBENDO OS VALORES DO ITEM SELECIONADO
        Intent intent = getIntent();
        String description = intent.getStringExtra("description");
        final String imagem = intent.getStringExtra("imagem");
        final String preco = intent.getStringExtra("preco");
        final String titulo = intent.getStringExtra("titulo");
        final String raro = intent.getStringExtra("raro");

        ImageView capa = findViewById(R.id.item_image);
        TextView descricao = findViewById(R.id.txt_item_completo);
        TextView comicPreco = findViewById(R.id.txt_selec_preco);
        Button btComprar = findViewById(R.id.bt_item_comprar);
        final EditText edtQuantidade = findViewById(R.id.item_quantidade);

        Picasso.with(this).load(imagem).into(capa);
        descricao.setText(description);
        comicPreco.setText(preco);

        btComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // VERIFICANDO SE O CAMPO DE QUANTIDADE ESTA VAZIO OU DIGITADO 0
                if (!edtQuantidade.getText().toString().equals("0") && !edtQuantidade.getText().toString().equals("")){
                    Intent intentResult = new Intent();
                    intentResult.putExtra("preco", preco);
                    intentResult.putExtra("quantidade", edtQuantidade.getText().toString());
                    intentResult.putExtra("imagem", imagem);
                    intentResult.putExtra("titulo", titulo);
                    intentResult.putExtra("raro", raro);
                    setResult(RESULT_OK, intentResult);
                    finish();
                }else {
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
