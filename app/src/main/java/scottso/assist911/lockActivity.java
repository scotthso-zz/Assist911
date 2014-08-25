package scottso.assist911;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

/**
 * Created by scottso on 2014-08-01.
 */
public class LockActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts;


    private Button unlockButton;

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;

    private long startTime = 10 * 1000;
    private final long interval = 1 * 1000;

    private GestureDetectorCompat gDetect;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        gDetect = new GestureDetectorCompat(this, new GestureListener());

        DialogFragment newFragment = new PromptUnlockDialog();
        newFragment.show(getFragmentManager(), "PromptDialog");

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


    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
//class content

        private float flingMin = 100;
        private float velocityMin = 100;

        //user will move right through messages on fling up or left
        boolean right = false;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            //determine what happens on fling events
            //calculate the change in X position within the fling gesture
            float horizontalDiff = event2.getX() - event1.getX();
//calculate the change in Y position within the fling gesture
            float verticalDiff = event2.getY() - event1.getY();

            float absHDiff = Math.abs(horizontalDiff);
            float absVDiff = Math.abs(verticalDiff);
            float absVelocityX = Math.abs(velocityX);
            float absVelocityY = Math.abs(velocityY);

            if(absHDiff>absVDiff && absHDiff>flingMin && absVelocityX>velocityMin){
//move right or backward
                if(horizontalDiff<0)
                 right =true;
            } else if(absVDiff>flingMin && absVelocityY>velocityMin){
                if(verticalDiff<0)
                 right =true;
            }

            if(right){
                goToDialpad();
               System.out.println("FORWARD");
            }
//user is cycling backwards through messages


            return true;
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
        String text = "Please pick up the phone. Stay calm.";
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

        Intent dial = new Intent(this, KeypadActivity.class);
        startActivity(dial);

        }

    }

