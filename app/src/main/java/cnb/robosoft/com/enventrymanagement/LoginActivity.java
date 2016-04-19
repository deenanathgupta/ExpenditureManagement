package cnb.robosoft.com.enventrymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import cnb.robosoft.com.enventrymanagement.util.Constants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText loginid, passwd;
    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        ref = new Firebase(Constants.URI);
        loginid = (EditText) findViewById(R.id.editText);
        passwd = (EditText) findViewById(R.id.editText2);
        Button signIn = (Button) findViewById(R.id.signin);
        Button signUp = (Button) findViewById(R.id.signup);
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                ref.authWithPassword(loginid.getText().toString(), passwd.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        ref.child("Hai").push() .setValue("Hellllo");
                        Log.i("test", "onAuthenticated()" + authData.getUid());
                        if (authData != null)
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        switch (firebaseError.getCode()) {
                            case FirebaseError.INVALID_EMAIL:
                                Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                break;
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                loginid.setError("UserId doesn't exist.");
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                passwd.setError("Invalid Password.");
                                break;
                        }
                        Log.i("test", "onAuthenticated()" + firebaseError);
                    }
                });
                break;
            case R.id.signup:
                startActivity(new Intent(LoginActivity.this, UserRegistration.class));
                break;
        }
    }
}
