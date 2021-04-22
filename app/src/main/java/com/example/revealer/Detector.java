package com.example.revealer;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class Detector {

    double SP=0;
    private double SmileProbability;

    public double getSmileProbability() {
        return SmileProbability;
    }
    public void setSmileProbability(double smileProbability) {
        SmileProbability = smileProbability;
    }

    Context ApplicationContext;
    public Detector(Context applicationContext) {
        ApplicationContext=applicationContext;
    }

    void runFaceDetector(Bitmap imageBitmap) {
        //create firebaseVisionFaceDetectorOption object
        FirebaseVisionFaceDetectorOptions options= new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setMinFaceSize(0.1f).build();
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        //add options
        FirebaseVisionFaceDetector detector= FirebaseVision.getInstance().getVisionFaceDetector(options);
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            double proba;
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                proba=checkSmiling(firebaseVisionFaces);
                setSmileProbability(proba);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ApplicationContext, "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private double checkSmiling(List<FirebaseVisionFace> firebaseVisionFaces) {
        double x=0;
        for(FirebaseVisionFace face:firebaseVisionFaces){
            if(face.getSmilingProbability()!=FirebaseVisionFace.UNCOMPUTED_PROBABILITY){
                SP=face.getSmilingProbability();
                x=SP;
            }
        }
        return x;
    }
}
