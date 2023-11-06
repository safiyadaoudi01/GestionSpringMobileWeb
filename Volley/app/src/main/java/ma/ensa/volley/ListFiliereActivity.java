package ma.ensa.volley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import ma.ensa.volley.adapter.FiliereAdapter;

public class ListFiliereActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Filiere> filieres;
    private String loadUrl = "http://192.168.1.132:8090/api/v1/filieres";
    private RequestQueue requestQueue;
    private FiliereAdapter filiereAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filiere);

        listView = findViewById(R.id.listfiliere); // Assurez-vous que l'ID de votre ListView correspond à celui dans le fichier XML
        filieres = new ArrayList<>();

        // Utilisation de Volley pour récupérer les données de l'API
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, loadUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject filiereObject;
                                try {
                                    filiereObject = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                int id = filiereObject.getInt("id");
                                String code = filiereObject.getString("code");
                                String libelle = filiereObject.getString("libelle");


                                Filiere filiere = new Filiere(id, code, libelle);
                                filiere.setCode(code);
                                filiere.setLibelle(libelle);


                                // Ajoutez l'étudiant à la liste
                                filieres.add(filiere);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Créez un adaptateur personnalisé pour votre ListView
                        filiereAdapter = new FiliereAdapter(ListFiliereActivity.this, filieres);
                        listView.setAdapter(filiereAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gérez les erreurs ici
                        Toast.makeText(ListFiliereActivity.this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show();
                    }
                });

        // Ajoutez la requête à la file d'attente de requêtes
        requestQueue.add(jsonArrayRequest);

        // Configurer la gestion des clics sur la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Filiere filiereSelectionne = filieres.get(position);
                showOptionsDialog(filiereSelectionne);
            }
        });
        Button btnAjouterEtudiant = findViewById(R.id.btnAjouterFiliere);
        btnAjouterEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lorsque le bouton est cliqué, démarrez l'activité "AddEtudiant"
                Intent intent = new Intent(ListFiliereActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void deleteFiliere(final int position) {
        Filiere filiereToDelete = filieres.get(position);
        int filiereId = filiereToDelete.getId(); // Assurez-vous d'avoir une méthode getId() dans votre classe Filiere pour obtenir l'identifiant de la filière à supprimer
        String url = "http://192.168.1.132:8090/api/v1/filieres/" + filiereId; // Remplacez ceci par l'URL de votre API pour supprimer une filière

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        filieres.remove(position);
                        filiereAdapter.notifyDataSetChanged();
                        Toast.makeText(ListFiliereActivity.this, "Filière supprimée avec succès", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Erreur de suppression de la filière : " + error.toString());
                        Toast.makeText(ListFiliereActivity.this, "Erreur lors de la suppression de la filière", Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Ajoutez la requête à la file d'attente de requêtes
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void showOptionsDialog(final Filiere filiere) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setMessage("Que voulez-vous faire avec " + filiere.getLibelle() + "?");

        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = filieres.indexOf(filiere);
                deleteFiliere(position);
            }
        });

        builder.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Créez un nouvel Intent pour l'activité de mise à jour
                Intent updateIntent = new Intent(ListFiliereActivity.this, UpdateFiliereActivity.class);
                // Passez l'objet Filiere en utilisant putExtra
                updateIntent.putExtra("filiere_to_update", (Serializable) filiere);
                // Démarrez l'activité de mise à jour
                startActivity(updateIntent);
            }
        });

        builder.show();
    }


}
