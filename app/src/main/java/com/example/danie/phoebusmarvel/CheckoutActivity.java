package com.example.danie.phoebusmarvel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    DecimalFormat decimalFormat = new DecimalFormat("0.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.title_activity_carrinho);

        // RECEBENDO A LISTA DO CARRINHO
        final Intent intent = getIntent();
        final ArrayList<Comic> carrinho = (ArrayList<Comic>) intent.getSerializableExtra("carrinho");
        final String carrinhoValor = intent.getStringExtra("valor");

        // CRIANDO A LISTA
        ListView listView = findViewById(R.id.lv_checkout);
        ArrayAdapter adapter = new ChekoutAdapter(CheckoutActivity.this, carrinho);
        listView.setAdapter(adapter);

        final TextView valor = findViewById(R.id.txt_checkout_valor);
        final EditText edtCupom = findViewById(R.id.edt_checkout_cupom);
        final TextView valorCupom = findViewById(R.id.txt_checkout_cupom);
        final TextView valorTotal = findViewById(R.id.txt_checkout_valor_total);
        Button btFinalizar = findViewById(R.id.bt_checkout_finalizar);
        Button btAplicarCupom = findViewById(R.id.bt_checkout_cupom);

        // PREENCHENDO OS VALORES FINAIS DE COMPRA
        valor.setText(carrinhoValor);
        valorCupom.setText("0.0");
        valorTotal.setText(carrinhoValor);

        // VERIFICANDO SE O CUPOM É VALIDO
        btAplicarCupom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cupom = 0;
                double calculo;

                if (edtCupom.getText().toString().equals("Raro")){
                    cupom = Double.parseDouble(carrinhoValor) * 0.25;

                }else if (edtCupom.getText().toString().equals("Comum")){
                    //VERIFICA UM A UM SE É COMUM E ADICIONA O DESCONTO DOS PRODUTOS
                    for (int i = 0; i<carrinho.size(); i++){
                        if (carrinho.get(i).getRaro().equals("NAO")){
                            cupom = cupom + (carrinho.get(i).getPrice() * 0.1);
                        }
                    }
                }

                calculo = Double.parseDouble(carrinhoValor) - cupom;

                String valorCupomAjustado = decimalFormat.format(cupom);
                String valorTotalAjustado = decimalFormat.format(calculo);

                valorCupom.setText(String.valueOf(valorCupomAjustado));
                valorTotal.setText(String.valueOf(valorTotalAjustado));
            }
        });

        // FINALIZAR COMPRA E ZERAR O CARRINHO
        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CheckoutActivity.this, "Compra realizada com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                intent.putExtra("carrinho", "0.0");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}
