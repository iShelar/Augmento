package com.example.shubham.augmento10;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

import static com.example.shubham.augmento10.MainActivity.*;

public class DetailActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    public static final String EXTRA_NAME = "name";
    private static final int PERMISSION_STORAGE_CODE = 1000;
    //Button downloadButton,playButton;
    public String modelUrl = "";
    public String name = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.model_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_TITLE);
        String description = intent.getStringExtra(EXTRA_DESC);
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        modelUrl = intent.getStringExtra(EXTRA_MODEL);

        name = intent.getStringExtra(EXTRA_NAME);

        ImageView modelImage = findViewById(R.id.modelImages);
        TextView modelTitle = findViewById(R.id.modelTitle);
        TextView modelDescription = findViewById(R.id.modelDescription);

        Picasso.get().load(imageUrl).into(modelImage);
        modelTitle.setText(title);
        modelDescription.setText(description);
        String path = Environment.DIRECTORY_DOWNLOADS +"/" +name;


        /*Toast.makeText(this,modelUrl ,
                Toast.LENGTH_LONG).show();
*/
        File file = new File("storage/emulated/0/Download/" + name);

        //BottomNavigation
        bottomNavigationView = findViewById(R.id.NavBot);
        bottomNavigationView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) item -> {

            switch (item.getItemId()) {
                case R.id.download :
                    if(!file.exists()) {
                        Toast.makeText(DetailActivity.this, "Downloading model", Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                //permission denied, request it
                                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

                                requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                            }
                            else {
                                startDownloading();
                            }
                        }
                        else {
                            startDownloading();
                        }
                    }
                    else {
                        Toast.makeText(DetailActivity.this, name + " is available to Play", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.play :
                    Intent arcoreIntent = new Intent(DetailActivity.this,ArcoreActivity.class);
                    arcoreIntent.putExtra(EXTRA_NAME,name);

                    startActivity(arcoreIntent);
                    break;
                case R.id.about :
                    Intent aboutUsIntent = new Intent(DetailActivity.this,AboutUs.class);
                    startActivity(aboutUsIntent);
                    break;
            }
            return true;
        });

        //Button
        /*downloadButton = findViewById(R.id.downloadButton);
        playButton = findViewById(R.id.playButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if OS is marshmellow or above handle runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        //permission denied, request it
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions,PERMISSION_STORAGE_CODE);
                    }
                    else {
                        startDownloading();
                    }
                }
                else {
                    startDownloading();
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent arcoreIntent = new Intent(DetailActivity.this,ArcoreActivity.class);
                arcoreIntent.putExtra(EXTRA_NAME,name);
                startActivity(arcoreIntent);
            }
        });*/

    }

    private void startDownloading() {
        //modelUrl = "https://github.com/iShelar/Augmento/blob/master/app/src/main/assets/model.sfb?raw=true";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(modelUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Augmento - " + name);
        request.setDescription("Downloading models...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,name);
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_STORAGE_CODE : {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownloading();
                }
                else {
                    Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.END);
            slide.setDuration(400);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }

}
