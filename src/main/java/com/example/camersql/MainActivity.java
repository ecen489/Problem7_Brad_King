package com.example.camersql;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.UnicodeSetSpanner;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView pictureTaken;
    TextView numberIn;
    DatabaseHandler db;
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        Button cameraButt = (Button) findViewById(R.id.button);
        pictureTaken = (ImageView) findViewById(R.id.imageView);
        Button retrieveButt = (Button) findViewById(R.id.button2);
        numberIn = (TextView) findViewById(R.id.editTextNumber);
        // from youtube
        db = new DatabaseHandler(this);
        //Disable the button if the user has no camera
        if(!hasCamera()) { cameraButt.setEnabled(false); }
    }


    //Check if the user has a camera
    private boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }



    // launch camera
    public void launchCamera(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void retrieveImage(View view)
    {

        Integer num = Integer.parseInt(numberIn.getText().toString());
        if(!(numberIn.toString().equals("")))
        {
            // not null not empty


            pictureTaken.setImageBitmap(db.getimage(num));
            textView.setText(num.toString());
        }
    }
    //If you want to return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, /*@Nullable*/ Intent data)
    {
        if(requestCode == REQUEST_IMAGE_CAPTURE)
        {

            // get teh photo
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            pictureTaken = (ImageView) findViewById(R.id.imageView);
            pictureTaken.setImageBitmap(image);
            if (db.insertimage(pictureTaken))
            {
                Toast.makeText(getApplicationContext(), "Succesfull", Toast.LENGTH_SHORT).show();
            }
            else
                {
                    Toast.makeText(getApplicationContext(), "Not Succesfull", Toast.LENGTH_SHORT).show();
                }
        }
    }

    // here is how to get number from textview
    // Integer num = Integer.parseInt(number.getText().toString());

}
