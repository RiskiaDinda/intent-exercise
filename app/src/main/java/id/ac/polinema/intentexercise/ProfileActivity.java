package id.ac.polinema.intentexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.AlphabeticIndex;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static id.ac.polinema.intentexercise.RegisterActivity.ABOUT_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.AVATARIMAGE_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.COPASS_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.EMAIL_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.FULLNAME_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.HOMEPAGE_KEY;
import static id.ac.polinema.intentexercise.RegisterActivity.PASS_KEY;

public class ProfileActivity extends AppCompatActivity {

    private ImageView avatarImage;
    private TextView fullnameText;
    private TextView emailText;
    private TextView homePageText;
    private TextView aboutText;
    private Uri uri;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        avatarImage = findViewById(R.id.image_profile);
        fullnameText = findViewById(R.id.label_fullname);
        emailText = findViewById(R.id.label_email);
        homePageText = findViewById(R.id.label_homepage);
        aboutText = findViewById(R.id.label_about);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            aboutText.setText(extras.getString(ABOUT_KEY));
            fullnameText.setText(extras.getString(FULLNAME_KEY));
            emailText.setText(extras.getString(EMAIL_KEY));
            homePageText.setText(extras.getString(HOMEPAGE_KEY));
            url = extras.getString(HOMEPAGE_KEY);
            uri = Uri.parse(extras.getString(AVATARIMAGE_KEY));
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            }catch (IOException e){
                e.printStackTrace();
            }
            avatarImage.setImageBitmap(bitmap);
        }
    }

    public void handleHomePage(View view) {
        Intent hompage = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+url));
        startActivity(hompage);
    }
}
