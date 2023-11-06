package ma.ensa.volley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
import ma.ensa.volley.Role;
import ma.ensa.volley.adapter.RoleAdapter;
import ma.ensa.volley.UpdateRoleActivity;

public class RoleActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Role> roles;
    private String loadUrl = "http://192.168.1.132:8090/api/v1/roles";
    private RequestQueue requestQueue;
    Button btnAjouterRole;
    private RoleAdapter roleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        listView = findViewById(R.id.listrole); // Assurez-vous que l'ID de votre ListView correspond à celui dans le fichier XML
        roles = new ArrayList<>();

        // Utilisation de Volley pour récupérer les données de l'API
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, loadUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject roleObject;
                                try {
                                    roleObject = response.getJSONObject(i);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                int id = roleObject.getInt("id");
                                String name = roleObject.getString("name");


                                Role role = new Role(id, name);
                                role.setName(name);


                                // Ajoutez l'étudiant à la liste
                                roles.add(role);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Créez un adaptateur personnalisé pour votre ListView
                        roleAdapter = new RoleAdapter(RoleActivity.this, roles);
                        listView.setAdapter(roleAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gérez les erreurs ici
                        Toast.makeText(RoleActivity.this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show();
                    }
                });

        // Ajoutez la requête à la file d'attente de requêtes
        requestQueue.add(jsonArrayRequest);

        // Configurer la gestion des clics sur la liste
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Role roleSelectionne = roles.get(position);
                showOptionsDialog(roleSelectionne);
            }
        });
        btnAjouterRole = findViewById(R.id.btnAjouterRole);
        btnAjouterRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lorsque le bouton est cliqué, démarrez l'activité "AddRole"
                Intent intent = new Intent(RoleActivity.this, AddRoleActivity.class);
                startActivity(intent);
            }
        });
    }
    private void deleteRole(final int position) {
        Role roleToDelete = roles.get(position);
        int roleId = roleToDelete.getId(); // Assurez-vous d'avoir une méthode getId() dans votre classe Role pour obtenir l'identifiant de la filière à supprimer
        String url = "http://192.168.1.132:8090/api/v1/roles/" + roleId; // Remplacez ceci par l'URL de votre API pour supprimer une filière

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        roles.remove(position);
                        roleAdapter.notifyDataSetChanged();
                        Toast.makeText(RoleActivity.this, "Role supprimé avec succès", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", "Erreur de suppression du role : " + error.toString());
                        Toast.makeText(RoleActivity.this, "Erreur lors de la suppression du role", Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Ajoutez la requête à la file d'attente de requêtes
        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void showOptionsDialog(final Role role) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");
        builder.setMessage("Que voulez-vous faire avec " + role.getName() + "?");

        builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = roles.indexOf(role);
                deleteRole(position);
            }
        });

        builder.setNeutralButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Créez un nouvel Intent pour l'activité de mise à jour
                Intent updateIntent = new Intent(RoleActivity.this, UpdateRoleActivity.class);
                // Passez l'objet Role en utilisant putExtra
                updateIntent.putExtra("role_to_update", (Serializable) role);
                // Démarrez l'activité de mise à jour
                startActivity(updateIntent);
            }
        });

        builder.show();
    }


}