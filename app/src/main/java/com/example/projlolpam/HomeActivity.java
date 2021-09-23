package com.example.projlolpam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import android.net.Uri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HomeActivity extends AppCompatActivity implements SensorEventListener{
    private static final String ARQUIVO_PREFERENCIAS = "ArquivoPreferencia";

    SensorManager sensorManager;
    Sensor sensor;
    Float luminosidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Para pegar Nickname do Invocador pelo SharedPreferences
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        String nickname = preferences.getString("Nick", "Forasteiro");

        if (preferences.getBoolean("Automatico", false)) {
            if (preferences.getBoolean("Dark", false)){
                ativarDarkMode();
            }
        } else if (preferences.getBoolean("Dark", false)) {
            ativarDarkMode();
        }

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        // Insere o texto
        TextView textView = (TextView) findViewById(R.id.textNickname);
        textView.setText(getString(R.string.text_olainvocador, nickname));

        if (preferences.getBoolean("Dark", false)){
            ativarDarkMode();
        }
    }
    public void abrirPorofessor(View view)
    {
        Uri uri = Uri.parse("https://porofessor.gg/pt/");
        Intent it = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(Intent.createChooser(it, "Selecione um navegador"));

    }

    public void enviarEmail(View view) throws UnsupportedEncodingException {
        String uriText =
                "mailto:leagueoflegends@support.lol" +
                        "?subject=" + URLEncoder.encode("Ticket", "utf-8") +
                        "&body=" + URLEncoder.encode("Fale%20aqui%20com%20o%20nosso%20suporte!" +
                        "%20Mande-nos%20recomendações%20e%20reporte%20falhas", "utf-8");
        Uri uri = Uri.parse(uriText);
        Intent it = new Intent(Intent.ACTION_SENDTO);
        it.setData(uri);
        startActivity(Intent.createChooser(it, "Enviar Ticket"));
    }

    public void enviarWhatsapp(View view){
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, "Olá Invocador. Você já conhece esse aplicativo " +
                "para te auxiliar a subir de ELO? Confira já! " +
                "https://play.google.com/store/apps/leagueoflegends");
        it.setType("text/plain");
        it.setPackage("com.whatsapp");
        if (it.resolveActivity(getPackageManager()) != null) {
            startActivity(it);
        }
    }

    // Navegação pelas Activities

    public void abrirHomeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirCampeoesActivity(View view) {
        Intent intent = new Intent(this, CampeoesActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirItensActivity(View view) {
        Intent intent = new Intent(this, ItensActivity.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);
        finish();
    }

    public void abrirPerfilActivity(View view) {
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        SharedPreferences.Editor editor = preferences.edit();

        if (preferences.getBoolean("Automatico", false)) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                luminosidade = event.values[0];

                if (preferences.getBoolean("Automatico", false) && luminosidade < 20000) {
                    editor.putBoolean("Dark", true).apply();

                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    this.overridePendingTransition(0, 0);
                    finish();
                } else if (preferences.getBoolean("Automatico", false) && luminosidade >= 20000) {
                    editor.putBoolean("Dark", false).apply();

                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    this.overridePendingTransition(0, 0);
                    finish();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void ativarDarkMode(){
        LinearLayout linearCabecalho = (LinearLayout) findViewById(R.id.linearCabecalho);
        ImageButton imgbtnCampeoes = (ImageButton) findViewById(R.id.imgbtnCampeoes);
        ImageButton imgbtnItens = (ImageButton) findViewById(R.id.imgbtnItens);
        ImageButton imgbtnPerfil = (ImageButton) findViewById(R.id.imgbtnPerfil);
        ImageView imgBackground = (ImageView) findViewById(R.id.imgBackground);
        ScrollView scrollContainer = (ScrollView) findViewById(R.id.scrollContainer);
        TextView textTituloCampeao = (TextView) findViewById(R.id.textTituloCampeao);
        TextView textFraseCampeao = (TextView) findViewById(R.id.textFraseCampeao);
        TextView textBuildGeral = (TextView) findViewById(R.id.textBuildGeral);
        TextView textRunaGeral = (TextView) findViewById(R.id.textRunaGeral);
        TextView textMecanica = (TextView) findViewById(R.id.textMecanica);
        TextView textSobre = (TextView) findViewById(R.id.textSobre);
        LinearLayout linearDivisao1 = (LinearLayout) findViewById(R.id.linearDivisao1);
        LinearLayout linearDivisao2 = (LinearLayout) findViewById(R.id.linearDivisao2);
        LinearLayout linearDivisao3 = (LinearLayout) findViewById(R.id.linearDivisao3);
        LinearLayout linearDivisao4 = (LinearLayout) findViewById(R.id.linearDivisao4);
        TextView textAnotacoes = (TextView) findViewById(R.id.textAnotacoes);
        EditText editAnotacoes = (EditText) findViewById(R.id.textUserNotes);
        Button buttonDeslogar = (Button) findViewById(R.id.buttonDeslogar);

        linearCabecalho.setBackgroundResource(R.color.preto_cabecalho);
        imgbtnCampeoes.setImageResource(R.drawable.imgcampeoes);
        imgbtnItens.setImageResource(R.drawable.imgitens);
        imgbtnPerfil.setImageResource(R.drawable.imgperfil);
        imgBackground.setImageResource(R.drawable.imggwen_fundo);
        imgBackground.setScrollX(-240);
        scrollContainer.setBackgroundResource(R.color.preto_container);
        textTituloCampeao.setTextColor(getResources().getColor(R.color.branco_tit_borda));
        textFraseCampeao.setTextColor(getResources().getColor(R.color.branco_texto));
        textBuildGeral.setTextColor(getResources().getColor(R.color.branco_texto));
        textRunaGeral.setTextColor(getResources().getColor(R.color.branco_tit_borda));
        textMecanica.setTextColor(getResources().getColor(R.color.branco_tit_borda));
        textSobre.setTextColor(getResources().getColor(R.color.branco_texto));
        linearDivisao1.setBackgroundResource(R.color.branco_topocontainer);
        linearDivisao2.setBackgroundResource(R.color.branco_topocontainer);
        linearDivisao3.setBackgroundResource(R.color.branco_topocontainer);
        linearDivisao4.setBackgroundResource(R.color.branco_topocontainer);
        textAnotacoes.setTextColor(getResources().getColor(R.color.branco_tit_borda));
        editAnotacoes.setTextColor(getResources().getColor(R.color.branco_texto));
        editAnotacoes.getBackground().mutate().setColorFilter(getResources().getColor(R.color.branco_tit_borda), PorterDuff.Mode.SRC_ATOP);
        buttonDeslogar.setTextColor(getResources().getColor(R.color.branco_texto));
    }
}