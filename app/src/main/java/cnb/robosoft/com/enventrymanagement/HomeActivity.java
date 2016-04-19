package cnb.robosoft.com.enventrymanagement;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cnb.robosoft.com.enventrymanagement.adapter.FragementPagerAdapter;
import cnb.robosoft.com.enventrymanagement.model.Data;
import cnb.robosoft.com.enventrymanagement.util.Constants;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    LayoutInflater mLayoutInflater;
    private TabLayout tabLayout;
    ViewPager viewPager;
    FragementPagerAdapter pagerAdapter;
    private FloatingActionButton mFloatingActionButton;
    AlertDialog.Builder builder = null;
    Firebase ref;
    ArrayList<String> mCateogry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Firebase.setAndroidContext(this);
        ref = new Firebase(Constants.URI + "/users");

        mCateogry = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("test", "Childern Count " + dataSnapshot.getChildrenCount());
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    //Log.i("test", "Childern :  " + dataSnapshot1.getChildren().toString());
                    Data data=new Data();
                    data.setExpendAmount(dataSnapshot1.getValue(Data.class).getExpendAmount());

                }
                while (dataSnapshot.hasChildren()){
                    Log.i("test","Children "+dataSnapshot.getValue());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        mLayoutInflater = LayoutInflater.from(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager(ViewPager viewPager) {
        pagerAdapter = new FragementPagerAdapter(getSupportFragmentManager(), viewPager);
        pagerAdapter.addFragment(new DetailsFragment(), "Medicals");
        pagerAdapter.addFragment(new DetailsFragment(), "Electricty");
        pagerAdapter.addFragment(new DetailsFragment(), "Education");
        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:

                break;
            case R.id.addnew:
                final View view = mLayoutInflater.inflate(R.layout.addnewtab, null);
                final EditText editText = (EditText) view.findViewById(R.id.addnewtab);
                builder = new AlertDialog.Builder(this);
                builder.setView(view);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HomeActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                        DetailsFragment detailsFragment = new DetailsFragment();
                        pagerAdapter.addFragment(detailsFragment, editText.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putString("Data", editText.getText().toString());
                        detailsFragment.setArguments(bundle);
                        viewPager.setAdapter(pagerAdapter);
                        pagerAdapter.notifyDataSetChanged();
                        viewPager.invalidate();
                        AuthData authData = ref.getAuth();
                        Firebase firebase = ref.child(authData.getUid()).child(editText.getText().toString());

                        firebase.push().setValue(new Data("null", "null", "null"));

                    }
                }).setCancelable(true).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.logout:
                Firebase ref = new Firebase(Constants.URI);
                ref.unauth();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                final Dialog inputView = new Dialog(this);
                inputView.setTitle(R.string.Custom_Title);
                inputView.setContentView(R.layout.input_dialog);


                final EditText expenditureDes = (EditText) inputView.findViewById(R.id.editText3);
                final EditText expendAmount = (EditText) inputView.findViewById(R.id.editText4);
                final EditText expenddate = (EditText) inputView.findViewById(R.id.editText5);
                Button btnSave = (Button) inputView.findViewById(R.id.btnsubmit);
                Button btnCancel = (Button) inputView.findViewById(R.id.btncancel);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputView.dismiss();
                    }
                });
                inputView.show();

                break;
        }
    }

}
