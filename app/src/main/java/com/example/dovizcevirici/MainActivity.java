package com.example.dovizcevirici;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnGetRates;
    TextView chfText ;
    TextView cadText ;
    TextView tryText ;
    TextView usdText ;
    TextView jpyText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        addListener ();
    }

    private void addListener() {
        btnGetRates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadData downloadData = new DownloadData();

                try {

                    String url = "http://data.fixer.io/api/latest?access_key=76a86c1324f721b28879c4980359ce9b&format=1";

                    downloadData.execute(url);

                }catch (Exception e){

                }

            }
        });
    }


    private  void initialize (){
     btnGetRates = findViewById(R.id.btnGetRates);
     usdText = findViewById(R.id.usdText);
     tryText = findViewById(R.id.tryText);
     cadText = findViewById(R.id.cadText);
     jpyText = findViewById(R.id.jpyText);
     chfText = findViewById(R.id.chfText);


}

private class DownloadData extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... strings) {

        String result = "";
        URL url;
        HttpURLConnection httpURLConnection;

        try {

            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();

            while (data>0) {

                char character = (char) data ;
                result += character;

                data = inputStream.read();
            }


            return result;

        } catch (Exception e) {
            return null;
        }

    }


        @Override
    protected void onPostExecute(String s) {  //bu metod yukardaki işlem bittikten sonra ne olacağını yazdığımız metoddur.
        super.onPostExecute(s);

        //System.out.println("alınan data : "+ s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                System.out.println("base :" + base);

                String rates = jsonObject.getString("rate");
                System.out.println("rates :" + rates);

                String turkishLira = jsonObject.getString("TRY");
                tryText.setText("TRY :"+turkishLira);


            }catch (Exception e){

            }

    }
}


}
