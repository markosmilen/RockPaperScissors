package mk.codeacademy.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String ROCK = "ROCK";
    private static final String PAPER = "PAPER";
    private static final String SCISSORS = "SCISSORS";

    ImageButton rock;
    ImageButton paper;
    ImageButton scissors;
    TextView player1;
    TextView player2;
    TextView winner;
    int p1weapon;
    int p2weapon;
    Button play;
    Button replay;
    Button highscore;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        rock = (ImageButton) findViewById(R.id.rockButton);
        paper = (ImageButton) findViewById(R.id.paperButton);
        scissors = (ImageButton) findViewById(R.id.scissorsButton);
        player1 = (TextView) findViewById(R.id.Player1text);
        player2 = (TextView) findViewById(R.id.Player2text);
        winner = (TextView)findViewById(R.id.winnerText);
        play = (Button) findViewById(R.id.playButton);
        replay = (Button) findViewById(R.id.replayBut);
        highscore = (Button) findViewById(R.id.highscoreBut);
        sharedPreferences = getSharedPreferences("MY_SHARED_PREF", MODE_PRIVATE);

        if (savedInstanceState != null){
            player1.setText(savedInstanceState.getString("player1_text"));
            player2.setText(savedInstanceState.getString("computer_text"));
            winner.setText(savedInstanceState.getString("winner_text"));
        }
    }



    public void onClickButton(View view) {
        setDefaultButtonColor();
        switch (view.getId()){
            case R.id.rockButton:
                player1.setText(getResources().getString(R.string.you_selected)+ ROCK);
                rock.setBackgroundColor(getResources().getColor(R.color.orange));
                p1weapon = 1;
                break;
            case R.id.paperButton:
                player1.setText(getResources().getString(R.string.you_selected) + PAPER);
                paper.setBackgroundColor(getResources().getColor(R.color.orange));
                p1weapon = 2;
                break;
            case R.id.scissorsButton:
                player1.setText(getResources().getString(R.string.you_selected) + SCISSORS);
                scissors.setBackgroundColor(getResources().getColor(R.color.orange));
                p1weapon = 3;
                break;
        }
    }

    private void disableButtons (){
        rock.setClickable(false);
        scissors.setClickable(false);
        paper.setClickable(false);
    }

    private void enableButtons(){
        rock.setClickable(true);
        scissors.setClickable(true);
        paper.setClickable(true);
    }

    public void onClickPlayButton(View view){
        disableButtons();
        if (p1weapon == 0) {
            Toast.makeText(this, "Please select a weapon", Toast.LENGTH_LONG).show();
        } else {
            Random pl2 = new Random();
            int selectp2 = pl2.nextInt((2) + 1) + 1;
            Log.d("RANDOM_NUMBER", selectp2 + "");
            switch (selectp2) {
                case 1:
                    p2weapon = 1;
                    player2.setText(getResources().getString(R.string.computer_selected) + ROCK);
                    break;
                case 2:
                    p2weapon = 2;
                    player2.setText(getResources().getString(R.string.computer_selected) + PAPER);
                    break;
                case 3:
                    p2weapon = 3;
                    player2.setText(getResources().getString(R.string.computer_selected) + SCISSORS);
                    break;
            }
            calculateScore();
        }
    }

    private void setDefaultButtonColor(){
        rock.setBackgroundColor(getResources().getColor(R.color.bacgroundButton));
        paper.setBackgroundColor(getResources().getColor(R.color.bacgroundButton));
        scissors.setBackgroundColor(getResources().getColor(R.color.bacgroundButton));
    }

    private void calculateScore(){
        int result = 0;
        if (p1weapon == p2weapon){
            winner.setText(getResources().getString(R.string.result_draw));
        }
        if ((p1weapon == 1 && p2weapon == 3) || (p1weapon == 2 && p2weapon == 1) || (p1weapon == 3 && p2weapon == 2)) {
            winner.setText(getResources().getString(R.string.result_you_win));
            result = 1;
        }
        if ((p1weapon == 1 && p2weapon == 2) || (p1weapon == 2 && p2weapon == 3) || (p1weapon == 3 && p2weapon == 1)) {
            winner.setText(getResources().getString(R.string.result_computer_win));
            result = 2;
        }

        if (result != 0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (result==1){
                int sharedScore = sharedPreferences.getInt("player1", 0) + 1;
                editor.putInt("player1", sharedScore);
            } else {
                int sharedScore2 = sharedPreferences.getInt("player2", 0) + 1;
                editor.putInt("player2", sharedScore2);
            }
            editor.commit();
        }
        changeButtons();
    }

    private void changeButtons(){
        play.setVisibility(View.INVISIBLE);
        replay.setVisibility(View.VISIBLE);
        highscore.setVisibility(View.VISIBLE);
    }

    public void onClickReplay (View view) {
        enableButtons();
        player1.setText("");
        player2.setText("");
        winner.setText("");
        play.setVisibility(View.VISIBLE);
        replay.setVisibility(View.INVISIBLE);
        highscore.setVisibility(View.INVISIBLE);
    }

    public void onClickHighscore(View view) {
        Intent highscoreIntent = new Intent(MainActivity.this,highscoreActivity.class);
        startActivity(highscoreIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!player1.equals(null)){
            outState.putString("player_one_text", player1.getText().toString());
        }
        if (!player2.equals(null)){
            outState.putString("computer_text", player2.getText().toString());
        }
        if (!winner.equals(null)){
            outState.putString("winner_text", winner.getText().toString());
        }
        if (play.getVisibility() == View.INVISIBLE){
            outState.putBoolean("play_visibility", true);
        }
        if (replay.getVisibility() == View.VISIBLE){
            outState.putBoolean("replay_visibility", true);
        }
        if (highscore.getVisibility() == View.VISIBLE){
            outState.putBoolean("highscore_visibility", true);
        }


    }
}
