package com.example.basichttprequest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    String TAG = "httprequest debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void http(View view) {
        URL u = null;

        try {
            u = new URL("https://samples.openweathermap.org/data/2.5/find?q=London&appid=b6907d289e10d714a6e88b30761fae22");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (u != null) {
            HttpRequest request = new HttpRequest();
            request.execute(u);
        }
    }

    class HttpRequest extends AsyncTask<URL, Void, String> {


        @Override
        protected String doInBackground(URL... urlssssssssssssssss) {
            URL url = null;
            String response =null;
            try {
                url = urlssssssssssssssss[0];
            } catch (ArrayIndexOutOfBoundsException exception) {
                Log.d(TAG, "doInBackground: " + "PLEASE ENTER A URL");
                return "";
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                switch (urlConnection.getResponseCode()) {
                    case 200:
                    case 201:
                        InputStream inputStream = urlConnection.getInputStream();
                        response = readStream (inputStream);

                        break;
                    default:
                        Log.d(TAG, "doInBackground: "+ "Error in request"+urlConnection.getResponseCode());

                }

                Log.d(TAG, "doInBackground: HTTP REQUEST SENT");
                Log.d(TAG, "doInBackground: " +urlConnection.getResponseMessage() );
                Log.d(TAG, "doInBackground: " +urlConnection.getResponseCode() );
            }catch (IOException e){
                e.printStackTrace();
            }
            return response;
        }

        String readStream(InputStream in) throws IOException {
            StringBuilder builder = new StringBuilder();
            if (in != null){
                InputStreamReader streamReader = new InputStreamReader(in, Charset.forName("UTF-8"));
                BufferedReader bufferedReader =new BufferedReader(streamReader);
                String output = bufferedReader.readLine();
                while (output != null){
                    builder.append(output);
                    output = bufferedReader.readLine();
                }


            }

            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            TextView textView = findViewById(R.id.response);
            textView.setText(s);



        }
    }
}
