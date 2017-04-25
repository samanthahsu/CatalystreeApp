package com.example.catalystreeapp.Users;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.catalystreeapp.Main.MainActivity;
        import com.example.catalystreeapp.R;

public class SignUpActivity extends Activity
{
    EditText editTextUserName,editTextEmail,editTextPassword,editTextConfirmPassword;
    Button btnCreateAccount;
    SessionManagement session;
    UserDbAdapter userDbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        session = new SessionManagement(getApplicationContext());

        // get Instance  of Database Adapter
        userDbAdapter =new UserDbAdapter(this);
        userDbAdapter = userDbAdapter.open();

        // Get References of Views
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String userName=editTextUserName.getText().toString();
                String email=editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();

                // check if any of the fields are vacant
                if(userName.equals("")||email.equals("")||password.equals("")||confirmPassword.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                }
                else
                {
                    session.createLoginSession(userName, email);
                    // Save the Data in Database
                    UserDbAdapter.insertEntry(userName, email, password);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);

                    intent.putExtra("caller", "Home");
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
//        add stuff here
        super.onDestroy();

        userDbAdapter.close();
    }
}