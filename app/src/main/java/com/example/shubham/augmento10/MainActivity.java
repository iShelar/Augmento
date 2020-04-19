package com.example.shubham.augmento10;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ModelAdapter.onItemClickListener {


    public static final String EXTRA_DESC = "description";
    public static final  String EXTRA_TITLE = "title";
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_MODEL = "modelUrl";
    public static final String EXTRA_NAME = "name";
    private static final int PERMISSION_STORAGE_CODE = 1000;


    RecyclerView recyclerView;
    ModelAdapter adapter;

    List<Model> modelList;

    DatabaseReference databaseModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        //Permission for read and write to storage device
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions,PERMISSION_STORAGE_CODE);

        //Connect to firebase with databaseModels reference
        databaseModels = FirebaseDatabase.getInstance().getReference("Models");

        modelList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ShimmerFrameLayout container;

        //Reflect list after data added or removed from firebase
        databaseModels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Model m = ds.getValue(Model.class); // POJO class

                    modelList.add(m);

                }
                adapter = new ModelAdapter(MainActivity.this, modelList);
                recyclerView.setAdapter(adapter);
                adapter.setItemClickListener(MainActivity.this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onItemClick(View v, int position) {
        Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
        Model model = modelList.get(position);

        detailIntent.putExtra(EXTRA_TITLE, model.getTitle());
        detailIntent.putExtra(EXTRA_DESC,model.getDescription());
        detailIntent.putExtra(EXTRA_URL,model.getImage());
        detailIntent.putExtra(EXTRA_MODEL,model.getModelLink());
        detailIntent.putExtra(EXTRA_NAME,model.getName());



        if(Build.VERSION.SDK_INT>20){
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this);
            startActivity(detailIntent,options.toBundle());
        }else {
            startActivity(detailIntent);
        }

    }
}
