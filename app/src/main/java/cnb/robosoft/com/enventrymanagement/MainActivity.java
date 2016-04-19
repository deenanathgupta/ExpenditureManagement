package cnb.robosoft.com.enventrymanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cnb.robosoft.com.enventrymanagement.util.Constants;

public class MainActivity extends Activity implements SimpleGestureListener {
    private SimpleGestureFilter detector;

    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        ref = new Firebase(Constants.URI);
        // Detect touched area
        detector = new SimpleGestureFilter(this, this);

       // final Firebase ref = new Firebase(Constants.URI);

        ref.authWithPassword("jenny@example.com", "correcthorsebatterystaple",
                new Firebase.AuthResultHandler() {

                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authentication just completed successfully :)
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("provider", authData.getProvider());
                        if(authData.getProviderData().containsKey("displayName")) {
                            map.put("displayName", authData.getProviderData().get("displayName").toString());
                        }

                        ref.child("users").child(authData.getUid()).setValue(map);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        // Something went wrong :(
                    }
                });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_UP:
                ref.addAuthStateListener(new Firebase.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(AuthData authData) {
                        if (authData != null) {
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        } else {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }
                    }
                });

                break;

        }
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }
}
