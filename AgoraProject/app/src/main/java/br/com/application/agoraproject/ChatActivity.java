package br.com.application.agoraproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference chat = reference.child("Conversas");
    private DatabaseReference id_mensagem = chat.child("id_mensagem");
    public EditText inputMensagem;
    public Button btnEnviarMensagem;
    public ListView listView;
    public static String chave_da_sala = "SUPER_SALA";
    public String user_name;
    Integer id;
    //public ArrayList<DataSnapshot> lista_de_chaves_de_sala;

    public ArrayList<Usuario> usuarios= new ArrayList();
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputMensagem = (EditText) findViewById(R.id.inputMensagem);
        btnEnviarMensagem = (Button) findViewById(R.id.btnEnviarmensagem);
        listView = (ListView) findViewById(R.id.listView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //chave_da_sala = extras.getString("chave_da_sala");
            System.out.println("CHAVE DA SALA: " + chave_da_sala);
            user_name = extras.getString("user_name");
            System.out.println("user_name: " + user_name);

        }

        //chat.child(chave_da_sala).child("id").setValue("0");

        id_mensagem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = Integer.parseInt(snapshot.getValue().toString());
                System.out.println("ID DA SALA::: " + id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(inputMensagem.getText().toString())){

                }else{
                    id = id + 1;
                    chat.child(chave_da_sala).child(String.valueOf(id)).child("name").setValue(user_name);
                    chat.child(chave_da_sala).child(String.valueOf(id)).child("msg").setValue(inputMensagem.getText().toString());
                    inputMensagem.setText("");
                    id_mensagem.setValue(String.valueOf(id));
                }
            }
        });



        chat.child("SUPER_SALA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuarios.clear();
                
                for(DataSnapshot users : snapshot.getChildren()){
                    System.out.println(users.child("name").getValue().toString() + " disse: ");
                    System.out.println("-> " + users.child("msg").getValue().toString());

                    Usuario u = new Usuario();

                    u.setNome(users.child("name").getValue().toString());
                    u.setMsg(users.child("msg").getValue().toString());

                    usuarios.add(u);
                }
                updateList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateList(){
        ArrayAdapter adapter = new AdapterTuplaMsg(this,usuarios);
        listView.setAdapter(adapter);
    }

}