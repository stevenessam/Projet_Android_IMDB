package projet.projet_android_imdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SearchButton(View v) {

        EditText editText1 = findViewById(R.id.textEntree);
        Intent intent = new Intent(MainActivity.this, MovieSearch.class);
        String data= editText1.getText().toString();
        intent.putExtra("movieName",data);
        startActivity(intent);

        //test
    }
}