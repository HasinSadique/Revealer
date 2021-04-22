package com.example.revealer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.revealer.MainActivity.REQUEST_IMAGE_CAPTURE;

public class ViewCapturedImage extends AppCompatActivity {

    ImageView imageView;
    TextView name,age,gender,description;
    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_captured_image);

        imageView = findViewById(R.id.ImageView_Pic);
        name = findViewById(R.id.TextView_Name);
        age = findViewById(R.id.TextView_age);
        gender = findViewById(R.id.TextView_gender);
        description = findViewById(R.id.TextView_descrip);

        String Name = (String)getIntent().getExtras().get("name");
        String Age = (String)getIntent().getExtras().get("age");
        String Gender = (String)getIntent().getExtras().get("gender");
        double smileProbability = (double)getIntent().getExtras().get("smile_probability");
        Bitmap image1 = (Bitmap) getIntent().getExtras().get("data");
        if(smileProbability>0.5){
            description.setText("smile probability: "+smileProbability+"\nHence Smiling");
        }else{
            description.setText("smile probability: "+smileProbability+"\nHence not Smiling");
        }
        imageView.setImageBitmap(image1);
        name.setText("Name: "+Name);
        age.setText("Age: "+Age);
        gender.setText("Gender: "+Gender);


//        imageView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //bring up camera
//                Intent captureImage=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if(captureImage.resolveActivity(getPackageManager())!=null){
//                    startActivityForResult(captureImage, REQUEST_IMAGE_CAPTURE);
//                }
//                //click image
//                //save in imageview
//            }
//        });
    }
}
