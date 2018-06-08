package in.nic.kerala.training;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
    Button btnreg;
    EditText edtuser, edtpass;
    String username, password;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


// get Instance  of Database Adapter
        loginDataBaseAdapter = new LoginDataBaseAdapter(RegisterActivity.this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        edtuser = (EditText) findViewById(R.id.usertext);
        edtpass = (EditText) findViewById(R.id.passtext);
        btnreg = (Button) findViewById(R.id.reg);
        View includedLayout = findViewById(R.id.head);

        TextView txttitle = (TextView) includedLayout.findViewById(R.id.txttitile);
        txttitle.setText("Registration");

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtuser.getText().toString().equals("")) {
                    edtuser.requestFocus();
                    edtuser.setError("Plaese enter username");
                } else if (edtpass.getText().toString().equals("")) {
                    edtpass.requestFocus();
                    edtpass.setError("Please enter password");
                } else {

                    username = edtuser.getText().toString();
                    password = edtpass.getText().toString();

                    loginDataBaseAdapter.insertEntry(username, password);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}
