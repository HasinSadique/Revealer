package com.example.revealer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Bitmap imageBitmap;
    EditText fullname, age, gender;
    ImageView imageViewPicture;
    private Bundle extras;
    Detector detector;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewPicture = findViewById(R.id.ImageView_Picture);
        fullname = findViewById(R.id.EditText_FullName);
        age = findViewById(R.id.EditText_Age);
        gender = findViewById(R.id.EditText_Gender);

        imageViewPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (captureImage.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(captureImage, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageViewPicture.setImageBitmap(imageBitmap);
            //Run detector
            detector = new Detector(getApplicationContext());
            detector.runFaceDetector(imageBitmap);
        }
    }

    public void Proceed(View view) {
        String FullName, Age, Gender;
        FullName = fullname.getText().toString();
        Age = age.getText().toString();
        Gender = gender.getText().toString();

        Intent intent = new Intent(this, ViewCapturedImage.class);
        intent.putExtras(extras);
        intent.putExtra("name", FullName);
        intent.putExtra("age", Age);
        intent.putExtra("gender", Gender);
        double smileProbability=detector.getSmileProbability();
        Toast.makeText(this, "On"+smileProbability, Toast.LENGTH_SHORT).show();
        intent.putExtra("smile_probability",smileProbability);
        startActivity(intent);
    }
}