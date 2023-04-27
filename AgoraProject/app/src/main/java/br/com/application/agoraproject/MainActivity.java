package br.com.application.agoraproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public Button btnLogin;
    public ImageView imageView;
    public EditText inputName, inputRoomkey;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference users = reference.child("Usuario");
    private DatabaseReference chat = reference.child("Conversas");
    private DatabaseReference id_user = reference.child("id_user").child("valor");

    public int id_lido_no_banco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        inputName = (EditText) findViewById(R.id.inputName);
        inputRoomkey = (EditText) findViewById(R.id.inputRoomkey);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(inputName.getText().toString())){
                    Toast.makeText(MainActivity.this, "Insira seu nome e Chave da sala.", Toast.LENGTH_SHORT).show();
                }else{
                    id_lido_no_banco = id_lido_no_banco + 1;
                    Usuario u = new Usuario();
                    u.setNome(inputName.getText().toString());
                    users.child(String.valueOf(id_lido_no_banco)).setValue(u);
                    id_user.setValue(String.valueOf(id_lido_no_banco));
                    System.out.println(">>ID BUTTON >>" + id_lido_no_banco);
                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                    intent.putExtra("chave_da_sala", inputRoomkey.getText().toString());
                    intent.putExtra("user_name", inputName.getText().toString());
                    startActivity(intent);
                    inputName.setText("");
                    inputRoomkey.setText("");
                }
            }
        });

        id_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id_lido_no_banco = Integer.parseInt(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}