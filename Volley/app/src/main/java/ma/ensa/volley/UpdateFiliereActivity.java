package ma.ensa.volley;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import ma.ensa.volley.Filiere;

public class UpdateFiliereActivity extends AppCompatActivity {

    private EditText editTextCode, editTextLibelle;
    private Button updateButton;
    private Filiere filiereToUpdate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_filiere);

        editTextCode = findViewById(R.id.editTextCode);
        editTextLibelle = findViewById(R.id.editTextLibelle);
        updateButton = findViewById(R.id.updateButton);

        // Récupérez la filière à mettre à jour depuis l'intent
        filiereToUpdate = (Filiere) getIntent().getSerializableExtra("filiere_to_update");

        // Pré-remplissez les champs de saisie avec les informations de la filière
        editTextCode.setText(filiereToUpdate.getCode());
        editTextLibelle.setText(filiereToUpdate.getLibelle());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFiliere();
            }
        });
    }

    private void updateFiliere() {
        String updatedCode = editTextCode.getText().toString();
        String updatedLibelle = editTextLibelle.getText().toString();

        // Créez un objet JSON pour les données à envoyer à l'API
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("code", updatedCode);
            requestObject.put("libelle", updatedLibelle);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // L'URL de mise à jour (remplacez par votre URL)
        String updateUrl = "http://192.168.1.132:8090/api/v1/filieres/" + filiereToUpdate.getId();

        // Créez une demande PUT pour mettre à jour la filière
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, updateUrl, requestObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Mise à jour réussie
                        Toast.makeText(UpdateFiliereActivity.this, "Filière mise à jour avec succès", Toast.LENGTH_SHORT).show();
                        finish(); // Fermez cette activité et retournez à l'activité précédente
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Erreur de mise à jour
                        Toast.makeText(UpdateFiliereActivity.this, "Erreur lors de la mise à jour de la filière", Toast.LENGTH_SHORT).show();
                    }
                });

        // Ajoutez la demande à la file d'attente de Volley
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}