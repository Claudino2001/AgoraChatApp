package br.com.application.agoraproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTuplaMsg extends ArrayAdapter<Usuario> {
    private final Context context;
    private final ArrayList<Usuario> users;

    public AdapterTuplaMsg(Context context, ArrayList<Usuario> users){
        super(context, R.layout.tupla_msg, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tupla_msg, parent, false);

        TextView txtNome = (TextView) rowView.findViewById(R.id.txtNome);
        TextView txtMsg = (TextView) rowView.findViewById(R.id.txtMsg);
        txtNome.setText(users.get(position).getNome());
        txtMsg.setText(users.get(position).getMsg());

        return rowView;
    }
}
