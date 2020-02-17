package hu.bme.aut.jatekido;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordActivity extends AppCompatActivity {
    SharedPreferences pref;
    TextView first;
    EditText etPassword;
    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        first = findViewById(R.id.first);
        etPassword = findViewById(R.id.etPassword);
        btnEnter = findViewById(R.id.btnEnter);

        pref = getApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        final Editor editor = pref.edit();
        if(pref.contains("password")) {
            first.setVisibility(View.INVISIBLE);
        }

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String pw = etPassword.getText().toString();
                if (pw.isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError(getString(R.string.passwordempty));
                    return;
                }

                if(!pref.contains("password")) {
                    editor.putString("password", etPassword.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(PasswordActivity.this, StatActivity.class);
                    startActivity(intent);
                    return;
                }
                else if(!pw.equals(pref.getString("password", ""))) {
                    etPassword.requestFocus();
                    etPassword.setError(getString(R.string.wrongpassword));
                    return;
                }
                Intent intent = new Intent(PasswordActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
