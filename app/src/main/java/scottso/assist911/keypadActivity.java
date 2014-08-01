package scottso.assist911;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.speech.tts.TextToSpeech.Engine;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.logging.Handler;

/**
 * Created by scottso on 2014-07-21.
 */
public class keypadActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private Button one, two, three, four, five, six, seven, eight, nine, zero, star, pound, delete, call;

    private TextView contactDisp;
    private TextView numbDisp;

    String emergency = "911";

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;

    private long startTime = 8 * 1000;
    private final long interval = 1 * 1000;

    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);

        one = (Button) findViewById(R.id.button1);
        two = (Button) findViewById(R.id.button2);
        three = (Button) findViewById(R.id.button3);
        four = (Button) findViewById(R.id.button4);
        five = (Button) findViewById(R.id.button5);
        six = (Button) findViewById(R.id.button6);
        seven = (Button) findViewById(R.id.button7);
        eight = (Button) findViewById(R.id.button8);
        nine = (Button) findViewById(R.id.button9);

        zero = (Button) findViewById(R.id.buttonzero);
        star = (Button) findViewById(R.id.buttonstar);
        pound = (Button) findViewById(R.id.buttonpound);

        delete = (Button) findViewById(R.id.buttondelete);

        call = (Button) findViewById(R.id.buttoncall);

        numbDisp = (TextView) findViewById(R.id.number_display);
        contactDisp = (TextView) findViewById(R.id.contact_display);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);

        star.setOnClickListener(this);
        pound.setOnClickListener(this);

        delete.setOnClickListener(this);

        call.setOnClickListener(this);

        tts = new TextToSpeech(this, this);

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
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut() {
        String text = "Please pick up the phone";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (millisUntilFinished/1000 == 7) {
                speakOut();
            }
            else if (millisUntilFinished/1000 == 6) {
                nine.setBackgroundColor(Color.rgb(247, 202, 24));
            } else if (millisUntilFinished/1000 == 5) {
                nine.setBackgroundColor(Color.WHITE);
                one.setBackgroundColor(Color.rgb(247, 202, 24));
            } else if (millisUntilFinished/1000 == 3) {
                one.setBackgroundColor(Color.WHITE);
            } else if (millisUntilFinished/1000 == 2) {
                one.setBackgroundColor(Color.rgb(247, 202, 24));
            }

        }

        @Override
        public void onFinish() {
            one.setBackgroundColor(Color.WHITE);

        }
    }
    @Override
    public void onClick(View view) {

            switch (view.getId()) {

                case R.id.button1:
                    numbDisp.append("1");
                    contactDisp.append("1");
                    one.setBackgroundColor(Color.rgb(238, 238, 238));

                    String estr = contactDisp.getText().toString().trim();


                    if (estr.equals(emergency)) {
                        contactDisp.append(" - Emergency");
                    }


                    break;

                case R.id.button2:
                    numbDisp.append("2");
                    contactDisp.append("2");
                    break;

                case R.id.button3:
                    numbDisp.append("3");
                    contactDisp.append("3");
                    break;

                case R.id.button4:
                    numbDisp.append("4");
                    contactDisp.append("4");
                    break;

                case R.id.button5:
                    numbDisp.append("5");
                    contactDisp.append("5");
                    break;

                case R.id.button6:
                    numbDisp.append("6");
                    contactDisp.append("6");
                    break;

                case R.id.button7:
                    numbDisp.append("7");
                    contactDisp.append("7");
                    break;

                case R.id.button8:
                    numbDisp.append("8");
                    contactDisp.append("8");
                    break;

                case R.id.button9:
                    numbDisp.append("9");
                    contactDisp.append("9");
                    break;

                case R.id.buttonzero:
                    numbDisp.append("0");
                    contactDisp.append("0");
                    break;

                case R.id.buttonstar:
                    numbDisp.append("*");
                    contactDisp.append("*");
                    break;

                case R.id.buttonpound:
                    numbDisp.append("#");
                    contactDisp.append("#");
                    break;

                case R.id.buttondelete:
                    String str = numbDisp.getText().toString().trim();
                    if (str.length() != 0) {
                        str = str.substring(0, str.length() - 1);
                        numbDisp.setText(str);
                        contactDisp.setText(str);
                    }
                    break;

                case R.id.buttoncall:

                    if (numbDisp.getText().toString().equals(emergency)) {
                        goToCall();

                    }
                    break;

            }
            one.setBackgroundColor(Color.WHITE);

        }

    public void goToCall() {

        Intent call = new Intent(this, callActivity.class);
        startActivity(call);

    }


}
