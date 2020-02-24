package id.ac.polinema.intentexercise;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getCanonicalName();
    private static final int GALLERY_REQUEST_CODE = 1;

    public static final String AVATARIMAGE_KEY = "image";
    public static final String FULLNAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASS_KEY = "password";
    public static final String COPASS_KEY = "conf_password";
    public static final String HOMEPAGE_KEY = "homepage";
    public static final String ABOUT_KEY = "about";

    private ImageView avatarImage;
    private EditText fullnameInput;
    private EditText emailInput;
    private EditText passInput;
    private EditText coPassInput;
    private EditText homePageInput;
    private EditText aboutInput;

    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        avatarImage = findViewById(R.id.image_profile);
        fullnameInput = findViewById(R.id.text_fullname);
        emailInput = findViewById(R.id.text_email);
        passInput = findViewById(R.id.text_password);
        coPassInput = findViewById(R.id.text_confirm_password);
        homePageInput = findViewById(R.id.text_homepage);
        aboutInput = findViewById(R.id.text_about);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            return;
        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (data != null){
                try{
                    imageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    avatarImage.setImageBitmap(bitmap);
                }catch (IOException e){
                    Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void handleChangeAvatar(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_REQUEST_CODE);
    }

    public void handleOk(View view) {
        String fullname = fullnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passInput.getText().toString();
        String conf_password = coPassInput.getText().toString();
        String homepage = homePageInput.getText().toString();
        String about = aboutInput.getText().toString();

         if (fullname.isEmpty()){
             fullnameInput.setError("Isi nama terlebih dahulu");
         }else if (password.isEmpty()){
             passInput.setError("Isi password terlebih dahulu");
         }else if (conf_password.isEmpty()){
             coPassInput.setError("Confirm password terlebih dahulu");
         }else if (homepage.isEmpty()){
             homePageInput.setError("Isi homepage terlebih dahulu");
         }else if (about.isEmpty()){
             aboutInput.setError("Isi about you terlebih dahulu");
         }else if(!password.equals(conf_password)){
             coPassInput.setError("Confirm password terlebih dahulu");
         }else {
             Intent i = new Intent(this, ProfileActivity.class);
             i.putExtra(FULLNAME_KEY, fullname);
             i.putExtra(EMAIL_KEY, email);
             i.putExtra(HOMEPAGE_KEY, homepage);
             i.putExtra(ABOUT_KEY, about);
             //startActivity(i);
             if (imageUri!=null){
                 i.putExtra(AVATARIMAGE_KEY, imageUri.toString());
                 try{
                     startActivity(i);
                 }catch (Exception ex){
                     i.putExtra(AVATARIMAGE_KEY,"");
                     Toast.makeText(this, "Your image to big", Toast.LENGTH_SHORT).show();
                     startActivity(i);
                 }
             }else {
                 Toast.makeText(this,"Please insert your image", Toast.LENGTH_SHORT).show();
                 handleChangeAvatar(view);
             }
         }



    }

}
