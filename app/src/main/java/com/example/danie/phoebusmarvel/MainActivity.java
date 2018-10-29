package com.example.danie.phoebusmarvel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    ArrayList<Comic> carrinhoComic = new ArrayList<>();
    Double contador = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.app_name);

        Button btCheckout = findViewById(R.id.bt_main_checkout);

        obterDados();

        // IR PARA O CHEKOUT LEVANDO DADOS
        btCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);
                intent.putExtra("carrinho", (Serializable) carrinhoComic);
                intent.putExtra("valor", String.valueOf(contador));
                startActivityForResult(intent, 300);
            }
        });
    }

    private void obterDados() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "http://gateway.marvel.com/v1/public/comics?ts=1&apikey=120f6668a63b6f97c1a7b2ff6cee79c8&hash=c28657d38c8715f353aec096601220bd";

        // METODO GET
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    final ArrayList<Comic> comics = new ArrayList<>();
                    JSONObject resposta = new JSONObject(response);
                    JSONArray quadrinhos = new JSONArray(resposta.getJSONObject("data").get("results").toString());
                    double sorteio = quadrinhos.length() * 0.12;
                    int arrendodado = (int)Math.round(sorteio);

                    // ARMAZENANDO OS DADOS DO JSON
                    for(int i=0; i< quadrinhos.length(); i++){
                        Comic comic = new Comic();
                        comic.setTitle(quadrinhos.getJSONObject(i).getString("title"));
                        comic.setPrice(quadrinhos.getJSONObject(i).getJSONArray("prices").getJSONObject(0).getDouble("price"));
                        comic.setThumbnail(quadrinhos.getJSONObject(i).getJSONObject("thumbnail").getString("path") + "." + quadrinhos.getJSONObject(i).getJSONObject("thumbnail").getString("extension"));

                        // REDUZINDO O TAMANHO DA DESCRIÇÃO
                        if (quadrinhos.getJSONObject(i).get("description").equals(null)){
                            comic.setDescription("Sem descrição");
                            comic.setPreDescription("Sem Descrição");
                        }else{
                            comic.setDescription(quadrinhos.getJSONObject(i).getString("description"));

                            if (comic.getDescription().length() > 120){
                                comic.setPreDescription(quadrinhos.getJSONObject(i).getString("description").substring(0, 120) + "...");
                            }else{
                                comic.setPreDescription(quadrinhos.getJSONObject(i).getString("description"));
                            }
                        }

                        // SORTEIO SE O ITEM É RARO OU NÃO
                        Random random = new Random();
                        int num = random.nextInt(2);

                        if (num == 1 && arrendodado>=0){
                            comic.setRaro("SIM");
                            arrendodado --;
                        }else {
                            comic.setRaro("NAO");
                        }

                        comics.add(comic);
                    }

                    // LISTA DE QUADRINHOS DISPONIVEIS
                    ListView listView = findViewById(R.id.list_comic);
                    ArrayAdapter adapter = new ComicAdapter(MainActivity.this, comics);
                    listView.setAdapter(adapter);

                    // CASO UM ITEM SEJA SELECIONADO
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, DetalhamentoActivity.class);
                            intent.putExtra("description", comics.get(position).getDescription());
                            intent.putExtra("preco", String.valueOf(comics.get(position).getPrice()));
                            intent.putExtra("imagem", comics.get(position).getThumbnail());
                            intent.putExtra("titulo", comics.get(position).getTitle());
                            intent.putExtra("raro", comics.get(position).getRaro());
                            startActivityForResult(intent, 200); // ABRIR A TELA DE DESCRIÇÃO ESPERANDO UMA RESPOSTA DE VAI PRO CARRINHO OU NÃO
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Erro: " + error.toString());

            }
        });
        requestQueue.add(stringRequest);
    }

    // RESPOSTA VINDA DAS TELAS DE DETALHES E DE CHECKOUT
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView carrinho = findViewById(R.id.txt_main_valor);
        DecimalFormat decimalFormat = new DecimalFormat("0.##");

        if (requestCode == 200 && resultCode==RESULT_OK){
            Comic comic = new Comic();
            comic.setTitle(data.getStringExtra("titulo"));
            comic.setPrice(Double.parseDouble(data.getStringExtra("preco")) * Double.parseDouble(data.getStringExtra("quantidade")));
            comic.setThumbnail(data.getStringExtra("imagem"));
            comic.setQuantidade(Integer.parseInt(data.getStringExtra("quantidade")));
            comic.setRaro(data.getStringExtra("raro"));

            // ATUALIZAR A LISTA DO CARRINHO
            carrinhoComic.add(comic);
            contador = contador + comic.getPrice();
            String valorContador = decimalFormat.format(contador);
            carrinho.setText(valorContador);

        }else if (requestCode == 300 && resultCode==RESULT_OK){

            // QUANDO A VENDA FOI CONCLUIDA
            carrinho.setText("0.0");
            contador = 0.0;
            carrinhoComic.clear();
        }

    }
}
