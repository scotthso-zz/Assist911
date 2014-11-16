package scottso.assist911.Activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import scottso.assist911.Dialogs.AudioPromptRemovedDialog;
import scottso.assist911.Dialogs.PromptDialDialog;
import scottso.assist911.Dialogs.PromptRemovedDialog;
import scottso.assist911.Dialogs.PromptUnlockDialog;
import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

/**
 * Created by scottso on 14-11-16.
 */
public class PasswordActivity extends SimKidsActivity implements View.OnClickListener, TextToSpeech.OnInitListener {

    private Button one, two, three, four, five, six, seven, eight, nine, zero, enter, emergencyCall;
    private ImageButton delete;

    private TextView time;
    private TextView date;
    private TextView numbDisp;

    private TextToSpeech tts;

//    private CountDownTimer countDownTimer;
//    private boolean timerHasStarted = false;

//    private long startTime = 8 * 1000;
//    private final long interval = 1 * 1000;

    public int clickCount = 0;

    public static int TRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        if(MainMenuActivity.TIMES_COMPLETED > 5 && MainMenuActivity.IS_REMOVE_TEXT_PROMPT == false) {
            DialogFragment newFragment = new PromptRemovedDialog();
            newFragment.show(getFragmentManager(), "PromptDialog");

            MainMenuActivity.IS_REMOVE_TEXT_PROMPT = true;
            LoginActivity.EDITOR.putBoolean(LoginActivity.REMOVE_TEXT_PROMPT, true);
            LoginActivity.EDITOR.commit();

        } else if (MainMenuActivity.TIMES_COMPLETED > 10 && MainMenuActivity.IS_REMOVE_AUDIO_PROMPT == false) {
            DialogFragment newFragment = new AudioPromptRemovedDialog();
            newFragment.show(getFragmentManager(), "PromptDialog");

            MainMenuActivity.IS_REMOVE_AUDIO_PROMPT = true;
            LoginActivity.EDITOR.putBoolean(LoginActivity.REMOVE_AUDIO_PROMPT, true);
            LoginActivity.EDITOR.commit();
        }

        if (MainMenuActivity.TIMES_COMPLETED <= 5) {
            DialogFragment newFragment = new PromptUnlockDialog();
            newFragment.show(getFragmentManager(), "PromptDialog");
        }

        tts = new TextToSpeech(this, this);

        TRIES = 0;

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
        enter = (Button) findViewById(R.id.buttonEnter);

        delete = (ImageButton) findViewById(R.id.buttondelete);
        emergencyCall = (Button) findViewById(R.id.emergencyCall);

        numbDisp = (TextView) findViewById(R.id.number_display);

        time = (TextView) findViewById(R.id.time);
        date = (TextView) findViewById(R.id.date);

        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int ds = c.get(Calendar.AM_PM);
        String AM_PM;

        if(ds==0)
            AM_PM="AM";
        else
            AM_PM="PM";

        time.setText(""+hour+":"+min+ " " + AM_PM);

        date.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(yy).append(" ").append("-").append(mm + 1).append("-")
                .append(dd));

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

        enter.setOnClickListener(this);

        delete.setOnClickListener(this);

        emergencyCall.setOnClickListener(this);


//        countDownTimer = new MyCountDownTimer(startTime, interval);
//
//        if (!timerHasStarted) {
//            countDownTimer.start();
//            timerHasStarted = true;
//        } else {
//            countDownTimer.cancel();
//            timerHasStarted = false;
//        }
    }

//    public class MyCountDownTimer extends CountDownTimer {
//        public MyCountDownTimer(long startTime, long interval) {
//            super(startTime, interval);
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//
//            if (millisUntilFinished/1000 == 6) {
//               // nine.setBackgroundColor(Color.rgb(247, 202, 24));
//            } else if (millisUntilFinished/1000 == 5) {
//               // nine.setBackgroundColor(Color.WHITE);
//               // one.setBackgroundColor(Color.rgb(247, 202, 24));
//            } else if (millisUntilFinished/1000 == 3) {
//               // one.setBackgroundColor(Color.WHITE);
//            } else if (millisUntilFinished/1000 == 2) {
//               // one.setBackgroundColor(Color.rgb(247, 202, 24));
//            }
//        }
//
//        @Override
//        public void onFinish() {
//            one.setBackgroundColor(Color.WHITE);
//        }
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                numbDisp.append("1");
                break;

            case R.id.button2:
                numbDisp.append("2");
                break;

            case R.id.button3:
                numbDisp.append("3");
                break;

            case R.id.button4:
                numbDisp.append("4");
                break;

            case R.id.button5:
                numbDisp.append("5");
                break;

            case R.id.button6:
                numbDisp.append("6");
                break;

            case R.id.button7:
                numbDisp.append("7");
                break;

            case R.id.button8:
                numbDisp.append("8");
                break;

            case R.id.button9:
                numbDisp.append("9");
                break;

            case R.id.buttonzero:
                numbDisp.append("0");
                break;

            case R.id.buttondelete:
                String str = numbDisp.getText().toString().trim();
                if (str.length() != 0) {
                    str = str.substring(0, str.length() - 1);
                    numbDisp.setText(str);
                }
                break;

            case R.id.emergencyCall:
                  goToDialpad();
                break;
        }
    }

    public void goToDialpad() {
        Intent dial = new Intent(this, KeypadActivity.class);
        startActivity(dial);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
        LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
        LoginActivity.EDITOR.commit();
    }

    public void goToResults() {
        TRIES = 0;
        Intent i = new Intent(this, ResultsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                if(MainMenuActivity.TIMES_COMPLETED < 10) {
                    speakUnlock();
                }
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
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

    public void speakUnlock() {
        String text = "Press the Emergency call button to unlock the phone";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


}
