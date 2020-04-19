package com.example.shubham.augmento10;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        ImageView saurabhLinkedin = findViewById(R.id.saurabhLinkedin);
        ImageView pranavLinkedin = findViewById(R.id.pranavLinkedin);
        ImageView suryaLinkedin = findViewById(R.id.suryaLinkedin);
        ImageView rohitLinkedin = findViewById(R.id.rohitLinkedin);

        ImageView saurabhWebButton = findViewById(R.id.saurabhWebButton);
        ImageView pranavWebButton = findViewById(R.id.pranavWebButton);
        ImageView suryaWebButton = findViewById(R.id.suryaWebButton);
        ImageView rohitWebButton = findViewById(R.id.rohitWebButton);

        ImageView[] webButton = {saurabhWebButton,pranavWebButton,suryaWebButton,rohitWebButton};
        String[] websites = {"https://ishelar.github.io/","https://ishelar.github.io/","https://ishelar.github.io/","https://ishelar.github.io/"};
        ImageView[] linkedinButton = {saurabhLinkedin,pranavLinkedin,suryaLinkedin,rohitLinkedin};
        String[] linkedin = {"https://www.linkedin.com/in/shelarsaurabh/","https://www.linkedin.com/in/pranav-d-0b0202178/","https://www.linkedin.com/in/suryadeep-jaykar-63a2b6158/","https://www.linkedin.com/in/shelarsaurabh/"};

        for(int i = 0; i < webButton.length; i++) {
            int finalI = i;
            webButton[i].setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websites[finalI]));
                startActivity(browserIntent);
            });
        }

        for(int i = 0; i < linkedinButton.length; i++) {
            int finalI = i;
            linkedinButton[i].setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedin[finalI]));
                startActivity(browserIntent);
            });
        }
    }
}
