package com.example.imagedownloading;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
Button download;

     public class Download extends AsyncTask<String,Void, Bitmap>{
         @Override
         protected Bitmap doInBackground(String... params)
         {
           Bitmap img = null;

             HttpURLConnection connection=null;
             try
             {
                 URL url =new URL(params[0]);
                 connection=(HttpURLConnection)url.openConnection();
                 connection.connect();
                 InputStream in=connection.getInputStream();
                 img=BitmapFactory.decodeStream(in);
             }
             catch (MalformedURLException e)
             {
                 e.printStackTrace();
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }

             return  img;
         }
     }

    ImageView downloadedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download=(Button)findViewById(R.id.btndownload);
        downloadedImage=(ImageView)findViewById(R.id.imageView);
        download.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view)
           {
            Download task=new Download();
               Bitmap img;
               try {
                   img=task.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();
                   downloadedImage.setImageBitmap(img);

               } catch (ExecutionException e)
               {
                   e.printStackTrace();
                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               } catch (InterruptedException e) {
                   e.printStackTrace();
                   Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

               }


           }

       });

       }

}
