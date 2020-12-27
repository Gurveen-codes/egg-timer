package gurveen.com.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar seekBar;
    Button goButton;
    CountDownTimer countDownTimer;
    boolean isCounterActive = false;


    public void buttonClicked(View view){

        if (isCounterActive){
            timerTextView.setText("00:30");
            seekBar.setEnabled(true);
            seekBar.setProgress(30);
            goButton.setText("GO!");
            isCounterActive = false;
            countDownTimer.cancel();
        }
        else {
            isCounterActive = true;
            goButton.setText("STOP!");
            seekBar.setEnabled(false);

         countDownTimer = new CountDownTimer
                (seekBar.getProgress() * 1000 + 100,
                1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer =  MediaPlayer.create(getApplicationContext(),
                        R.raw.alarm_horn);
                mediaPlayer.start();
                timerTextView.setText("00:30");
                seekBar.setEnabled(true);
                seekBar.setProgress(30);
                goButton.setText("GO!");
                isCounterActive = false;
                countDownTimer.cancel();

            }
        }.start();}
    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes*60);

        String secondsString = Integer.toString(seconds);
        if (seconds <= 9){
            secondsString = "0"+ secondsString;
        }
        timerTextView.setText(Integer.toString(minutes)+":"+secondsString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         timerTextView  = findViewById(R.id.timer_TextView);
         seekBar = findViewById(R.id.seekBar);
         goButton = findViewById(R.id.go_button);

        int max = 600;
        int intialValue = 30;
        seekBar.setMax(max);
        seekBar.setProgress(intialValue);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
