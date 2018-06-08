package in.nic.kerala.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SqlActivity extends AppCompatActivity {
    TextInputLayout txtlayoutuser, txtlayoutpass;
    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText edtuser, edtpass;
    Button login;
    String username, password;
    TextView txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        edtuser = (EditText) findViewById(R.id.usertext);
        edtpass = (EditText) findViewById(R.id.passtext);
        login = (Button) findViewById(R.id.login);
        txt = (TextView) findViewById(R.id.textreg);
        txtlayoutpass = (TextInputLayout) findViewById(R.id.passlayout);
        txtlayoutuser = (TextInputLayout) findViewById(R.id.userlayout);
        View includedLayout = findViewById(R.id.head);

        TextView txttitle = (TextView) includedLayout.findViewById(R.id.txttitile);
        txttitle.setText("SqlLogin");

        // create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(SqlActivity.this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtuser.getText().toString();
                password = edtpass.getText().toString();

                if (edtuser.getText().toString().equals("")) {
                    edtuser.requestFocus();
                    edtuser.setError("Plese enter username");

                } else if (edtpass.getText().toString().equals("")) {
                    edtpass.requestFocus();
                    edtpass.setError("please enter password");

                }
                else {
                    login();
                }

            }

            private void login() {
                // fetch the Password form database for respective user name
                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(username);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword)) {
                    Toast.makeText(SqlActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(SqlActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SqlActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}

