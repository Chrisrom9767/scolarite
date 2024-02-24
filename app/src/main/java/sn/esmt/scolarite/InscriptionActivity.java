package sn.esmt.scolarite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sn.esmt.scolarite.http.Api;
import sn.esmt.scolarite.http.EtudiantResponce;

public class InscriptionActivity extends AppCompatActivity {
    private Button retourbt;
    private EditText mattxt;
    private EditText nomtext;
    private EditText prenomtext;
    private EditText adressetext;
    private EditText teltxt;
    private EditText frtext;
    private Button inscrirebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        retourbt = (Button) findViewById(R.id.retourbt);
        mattxt= (EditText) findViewById(R.id.mattxt);
        nomtext = (EditText) findViewById(R.id.nomtext);
        prenomtext = (EditText) findViewById(R.id.prenomtext);
        adressetext = (EditText) findViewById(R.id.adressetext);
        teltxt = (EditText) findViewById(R.id.teltxt);
        frtext = (EditText) findViewById(R.id.frtext);
        inscrirebt = (Button) findViewById(R.id.inscrirebt);
        inscrirebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8082/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api = retrofit.create(Api.class);
                //Creation d'un objet etudiant a ajouter dans la base
                EtudiantResponce e = new EtudiantResponce();
                e.setMat(mattxt.getText().toString());
                e.setNom(nomtext.getText().toString());
                e.setPrenom(prenomtext.getText().toString());
                e.setTel(Integer.parseInt(teltxt.getText().toString()));
                e.setAdr(adressetext.getText().toString());
                e.setFrais(Double.parseDouble(frtext.getText().toString()));
                //Appel de l'API
                Call<EtudiantResponce> callSave = api.saveEtudiant(e);
                callSave.enqueue(new Callback<EtudiantResponce>() {
                    @Override
                    public void onResponse(Call<EtudiantResponce> call, Response<EtudiantResponce> response) {
                        Toast.makeText(InscriptionActivity.this,
                                "Etudiant inscrit avec succe",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<EtudiantResponce> call, Throwable t) {
                        Toast.makeText(InscriptionActivity.this,
                                "Impossible d'acceder au server",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}