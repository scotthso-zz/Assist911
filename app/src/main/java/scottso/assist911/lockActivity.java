package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.Locale;

/**
 * Created by scottso on 2014-08-01.
 */
public class lockActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts;

    private Button unlockButton;

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;

    private long startTime = 10 * 1000;
    private final long interval = 1 * 1000;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        tts = new TextToSpeech(this, this);

        unlockButton = (Button) findViewById(R.id.lock_button);

        unlockButton.setOnClickListener(this);

        countDownTimer = new MyCountDownTimer(startTime, interval);

        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;

        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
        }
    }

    public void onClick(View view) {

       switch (view.getId()) {

           case R.id.lock_button:
               goToDialpad();
               break;
       }

    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakPickUp();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakPickUp() {
        String text = "Please pick up the phone";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speakUnlock() {
        String text = "Unlock the phone";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (millisUntilFinished/1000 == 8) {
                speakPickUp();
            } else if (millisUntilFinished/1000 == 4) {
                speakUnlock();
            }

        }

        @Override
        public void onFinish() {

        }

    }

    public void goToDialpad() {

        Intent dial = new Intent(this, keypadActivity.class);
        startActivity(dial);

        }

    }

