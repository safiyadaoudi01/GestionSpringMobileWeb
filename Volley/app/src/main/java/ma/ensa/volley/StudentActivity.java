package ma.ensa.volley;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, password, name, phone, email;
    private Spinner roleSpinner, filiereSpinner;
    private Button bnAdd;
    RequestQueue requestQueue;
    private List<JSONObject> roleList;
    private List<JSONObject> filiereList;
    private List<String> roleNames;
    private List<String> filiereNames;
    String insertUrl = "http://192.168.1.132:8090/api/student";
    String roleURL = "http://192.168.1.132:8090/api/v1/roles";
    String filiereURL = "http://192.168.1.132:8090/api/v1/filieres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        roleSpinner = findViewById(R.id.roleSpinner);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        filiereSpinner = findViewById(R.id.filiereSpinner);
        bnAdd = findViewById(R.id.bnAdd);
        roleNames = new ArrayList<>();
        roleList = new ArrayList<>();
        filiereNames = new ArrayList<>();
        filiereList = new ArrayList<>();
        loadRoles();
        loadFilieres();

        bnAdd.setOnClickListener(this);
    }

    private void loadRoles() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, roleURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            roleList.clear();
                            roleNames.clear(); // Clear the role names list
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject role = response.getJSONObject(i);
                                roleList.add(role);
                                roleNames.add(role.getString("name")); // Add the role name to the list
                            }
                            ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(StudentActivity.this,
                                    android.R.layout.simple_spinner_item, roleNames);
                            roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            roleSpinner.setAdapter(roleAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(StudentActivity.this, "Erreur lors du chargement des rôles", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void loadFilieres() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, filiereURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            filiereList.clear();
                            filiereNames.clear(); // Clear the filiere names list
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject filiere = response.getJSONObject(i);
                                filiereList.add(filiere);
                                filiereNames.add(filiere.getString("libelle")); // Add the filiere name to the list
                            }
                            ArrayAdapter<String> filiereAdapter = new ArrayAdapter<>(StudentActivity.this,
                                    android.R.layout.simple_spinner_item, filiereNames);
                            filiereAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            filiereSpinner.setAdapter(filiereAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(StudentActivity.this, "Erreur lors du chargement des filières", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View view) {
        // Create the JSON structure
        JSONObject jsonBody = new JSONObject();
        try {
            // Create the "roles" JSON array
            JSONArray rolesArray = new JSONArray();
            JSONObject selectedRole = roleList.get(roleSpinner.getSelectedItemPosition());
            rolesArray.put(selectedRole);

            // Create the "filiere" JSON object
            JSONObject selectedFiliere = filiereList.get(filiereSpinner.getSelectedItemPosition());

            // Populate the main JSON object with all the fields
            jsonBody.put("roles", rolesArray);
            jsonBody.put("username", username.getText().toString());
            jsonBody.put("password", password.getText().toString());
            jsonBody.put("name", name.getText().toString());
            jsonBody.put("phone", Integer.parseInt(phone.getText().toString()));
            jsonBody.put("email", email.getText().toString());
            jsonBody.put("filiere", selectedFiliere);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("resultat", response.toString());
                Toast.makeText(StudentActivity.this, "Étudiant créé avec succès", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Erreur", error.toString());
                Toast.makeText(StudentActivity.this, "Erreur lors de la création de l'étudiant", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
}