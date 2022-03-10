package projet.projet_android_imdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText movieName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ChangePage(View v) {
        //test


        movieName = findViewById(R.id.MovieName);
        String data= movieName.getText().toString();
        Intent intent = new Intent(MainActivity.this, MovieSearch.class);
        intent.putExtra("movieName",data);
        startActivity(intent);


    }




}