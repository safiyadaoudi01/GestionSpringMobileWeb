package ma.ensa.volley.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import ma.ensa.volley.R;
import ma.ensa.volley.Filiere;

public class FiliereAdapter extends ArrayAdapter<Filiere> {

    private ArrayList<Filiere> FiliereList;
    private Context context;

    public FiliereAdapter(Context context, ArrayList<Filiere> etudiantList) {
        super(context, 0, etudiantList);
        this.context = context;
        this.FiliereList = etudiantList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtenir l'objet Etudiant pour cette position
        Filiere filiere = getItem(position);

        // Vérifier si la vue est réutilisée, sinon l'inflater
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_filiere, parent, false);
        }

        // Récupérer les vues à remplir à partir de l'élément Etudiant
        TextView codeTextView = convertView.findViewById(R.id.code);
        TextView libelleTextView = convertView.findViewById(R.id.libelle);


        // Remplir les vues avec les données de l'Etudiant
        if (filiere != null) {
            codeTextView.setText(filiere.getCode());
            libelleTextView.setText(filiere.getLibelle());


        }

        return convertView;
    }
}
