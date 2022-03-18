package projet.projet_android_imdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MovieSearch extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        //  afficheData();
//        RequestTask rT =new RequestTask();
//        rT.execute("avengers");

    }
//
//    public void refresh(View v) {
//        Intent i = getIntent();
//        data = i.getStringExtra("data");
//
//    }


    public void afficheData() {


    }


    private class RequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String response = requete(strings[0]);
            return response;

        }
        String titleM;
        String descriptionM;
        private String requete(String name) {
            String response = "";
            try {
                HttpURLConnection connection = null;
                URL url = new
                        URL("https://imdb-api.com/en/API/SearchMovie/k_dgd1pq04/" + URLEncoder.encode(name, "utf-8"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine();
                while (ligne != null) {
                    response += ligne;
                    ligne = bufferedReader.readLine();
                }
                JSONObject toDecode = new JSONObject(response);
                response = decodeJSON(toDecode);
            } catch (UnsupportedEncodingException e) {
                response = "problème d'encodage";
            } catch (MalformedURLException e) {
                response = "problème d'URL ";
            } catch (IOException e) {
                response = "problème de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
        private String decodeJSON(JSONObject jso) throws Exception {
            String response = "";

            JSONArray jsoresult = jso.getJSONArray("results");
            for (int i = 0; i < jsoresult.length(); i++) {
                response += jsoresult.getJSONObject(i).getString("title") + " "
                        + jsoresult.getJSONObject(i).getString("description")+ "\n";
            }
            // + jsoresult.getJSONObject(i).getString("description") + " ";
//l image a metre tous seul

            //+ jsoresult.getJSONObject(i).getString("image");


            return response;
        }





        protected void onPostExecute(String result) {


        }

    }




    public void SearchButton(View v) {


        String myUrl = "avengers";
        RequestTask mh = new RequestTask();
        try {
            String result = mh.execute(myUrl).get();
            textView1  = (TextView) findViewById(R.id.textView1);

            textView1.setText(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }


    }
}