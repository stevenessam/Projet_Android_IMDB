package projet.projet_android_imdb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MovieSearch extends AppCompatActivity {

TextView textView1;
TextView textView2;
EditText editTextMovieName;
ArrayList<MovieModel> movieList = new ArrayList<MovieModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
//
//        RequestTask rT =new RequestTask();
//        rT.execute("avengers");
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
                response = decodeJSON(toDecode).toString();
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

        private ArrayList<MovieModel> decodeJSON(JSONObject jso) throws Exception {
            //String response = "";


            JSONArray jsoresult = jso.getJSONArray("results");
            for (int i = 0; i < jsoresult.length(); i++) {
                MovieModel movieModel = new MovieModel();
                JSONObject tmp = jsoresult.getJSONObject(i);
                String title = tmp.getString("title");
                String description = tmp.getString("description");
                String image = tmp.getString("image");

                movieModel.setTitle(title);
                movieModel.setDescription(description);
                movieModel.setImg(image);

                movieList.add(movieModel);

            }
            return movieList;
        }


        protected void onPostExecute(ArrayList<String> result) {
//
//            LinearLayout ll = (LinearLayout) findViewById(R.id.ll1);
//
//            for (int i = 0 ; i <result.size();i++){
//                TextView titleView = new TextView(getApplicationContext());
//                TextView descriptionView = new TextView(getApplicationContext());
//                ll.addView(titleView);
//                ll.addView(descriptionView);
//                titleView.setText(result.get(i));
//                descriptionView.setText(result.get(i));
////                titleView.setId(i);
////                descriptionView.setId(++i);
//            }

        }

    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



    public void SearchButton(View v) {

        editTextMovieName = findViewById(R.id.editTextMovieName);
        String data= editTextMovieName.getText().toString();
        String myUrl = data;
        RequestTask mh = new RequestTask();
        try { String result = mh.execute(myUrl).get();
            LinearLayout ll = (LinearLayout) findViewById(R.id.ll1);
            for (int i = 0; i < movieList.size(); i++) {
                int a,b,c,d;
                d =0;
                a = 0;
                a = d + i;

                LinearLayout ln = new LinearLayout(getApplicationContext());
                TextView titleView = new TextView(getApplicationContext());
                TextView descriptionView = new TextView(getApplicationContext());
                ImageView imageView = new ImageView(getApplicationContext());

                titleView.setId(a);
                b= a+1;
                descriptionView.setId(b);
                c = b +1;
                imageView.setId(c);
                d=c+1;
                ln.setId(d);

                ln.addView(titleView);
                ln.addView(descriptionView);
                ln.addView(imageView);

                titleView.setText(movieList.get(i).getTitle());
                descriptionView.setText(movieList.get(i).getDescription());

                new DownloadImageTask((ImageView) findViewById(c)).execute(movieList.get(i).getImg());


                ll.addView(ln);



            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }


    }
}