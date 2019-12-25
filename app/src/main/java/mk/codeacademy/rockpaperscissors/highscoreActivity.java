package mk.codeacademy.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class highscoreActivity extends AppCompatActivity {


    TextView score1;
    TextView score2;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        score1 = (TextView) findViewById(R.id.scoreP1);
        score2 = (TextView) findViewById(R.id.scorep2);
        sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);

        score1.setText("" + sharedPreferences.getInt("player1", 0));
        score2.setText("" + sharedPreferences.getInt("player2", 0));
    }

    public void onClickClearButton(View view) {
        score1.setText("0");
        score2.setText("0");
        sharedPreferences.edit().clear().commit();
    }
}
