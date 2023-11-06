package ma.ensa.volley.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import ma.ensa.volley.R;
import ma.ensa.volley.Role;

public class RoleAdapter extends ArrayAdapter<Role> {

    private ArrayList<Role> RoleList;
    private Context context;

    public RoleAdapter(Context context, ArrayList<Role> etudiantList) {
        super(context, 0, etudiantList);
        this.context = context;
        this.RoleList = etudiantList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtenir l'objet Etudiant pour cette position
        Role role = getItem(position);

        // Vérifier si la vue est réutilisée, sinon l'inflater
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_role, parent, false);
        }

        // Récupérer les vues à remplir à partir de l'élément Etudiant
        TextView nameTextView = convertView.findViewById(R.id.name);



        // Remplir les vues avec les données de l'Etudiant
        if (role != null) {
            nameTextView.setText(role.getName());


        }

        return convertView;
    }
}
