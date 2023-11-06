package ma.ensa.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnGestionFiliere = findViewById(R.id.btnGestionFiliere);
        Button btnGestionEtudiant = findViewById(R.id.btnGestionEtudiant);
        Button btnGestionRole = findViewById(R.id.btnGestionRole);

        btnGestionFiliere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListFiliereActivity.class);
                startActivity(intent);
            }
        });

        btnGestionEtudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListEtudiantActivity.class);
                startActivity(intent);
            }
        });

        btnGestionRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RoleActivity.class);
                startActivity(intent);
            }
        });
    }

}