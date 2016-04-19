package cnb.robosoft.com.enventrymanagement;

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

import cnb.robosoft.com.enventrymanagement.model.Data;
import cnb.robosoft.com.enventrymanagement.util.Constants;

public class UserRegistration extends AppCompatActivity implements View.OnClickListener {
    private EditText username, emailid, password;
    private Button btnRegister, btnCancel;
    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        ref = new Firebase(Constants.URI);
        username = (EditText) findViewById(R.id.username);
        emailid = (EditText) findViewById(R.id.useremail);
        password = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btn_Register);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Register:
                ref.createUser(emailid.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        Toast.makeText(UserRegistration.this, "Registration Completed Successfully..!!!", Toast.LENGTH_SHORT).show();
                        ref.authWithPassword(emailid.getText().toString(), password.getText().toString(), new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                Firebase ref = new Firebase(Constants.URI);
                                Firebase postRef = ref.child("users").child(authData.getUid()).child("Hospital");
                                postRef.push().setValue(new Data("Announcing COBOL, a New Programming Language", "2000", "10-02-2016"));
                                postRef = ref.child("users").child(authData.getUid()).child("Education");
                                postRef.push().setValue(new Data("Announcing COBOL, a New Programming Language", "2000", "10-02-2016"));
                                postRef = ref.child("users").child(authData.getUid()).child("Other");
                                postRef.push().setValue(new Data("Announcing COBOL, a New Programming Language", "2000", "10-02-2016"));
                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {

                            }
                        });
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Log.i("Test", "Errorrrr" + firebaseError);
                        Toast.makeText(UserRegistration.this, "Error..!!!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_cancel:
                break;
        }
    }
}
