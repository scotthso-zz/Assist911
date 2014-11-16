package scottso.assist911.Activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

import scottso.assist911.Dialogs.HintProblemDialog;
import scottso.assist911.Dialogs.PromptCallDialog;
import scottso.assist911.R;

public class CallActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{

    private static final String TAG = "CallActivity";

    enum Level {
        SERVICE,
        NAME,
        LOCATION,
        EMERGENCY,
        FINAL
    }

    private static final String[] script = {"Nine one one do you need fire, ambulance or police",
                               "What's your name?",
                               "What's your address?",
                               "What's the problem?",
                               "E M S is on their way. Stay there.",
                               "Fire team is on their way. Exit the building and go to a safe place.",
                               "Police is on their way. Stay there",
                               "Try again.",
                               "Game over."};

    public static String[] ADDRESS_ARRAY = LoginActivity.PREF.getString(LoginActivity.ADDRESS,"").split(" ");

    public static Level LEVEL = Level.SERVICE;

    private TextView mText;
    private ImageView mVehicle;

    public static TextToSpeech tts;

    DialogFragment newFragment = new PromptCallDialog();
    DialogFragment hintProblemFragment = new HintProblemDialog();

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public static boolean SPOKEN = false;
    private boolean prompts = false;

    private long startTime = 9 * 1000;
    private final long interval = 1 * 1000;

    private MediaPlayer mpDialTone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        SPOKEN = false;
        LEVEL = Level.SERVICE;

        if(MainMenuActivity.TIMES_COMPLETED <= 5) {
            prompts = true;
            newFragment.show(getFragmentManager(), "PromptDialog");
            hintProblemFragment.show(getFragmentManager(), "PromptDialog");
        } else {
            prompts = false;
        }

        tts = new TextToSpeech(this, this);

        final ImageButton endButton = (ImageButton) findViewById(R.id.endButton);
        endButton.setOnClickListener(this);

        mText = (TextView) findViewById(R.id.callStatus);
        mVehicle = (ImageView) findViewById(R.id.vehicle_image);

        int soundResource = getResources().
                getIdentifier("dialtone", "raw", getPackageName());
        String uri = "android.resource://" + getPackageName() + "/" + soundResource;

        mpDialTone = MediaPlayer.create(this, Uri.parse(uri));
        mpDialTone.setLooping(true);





        tts.setOnUtteranceProgressListener(new TtsUtteranceListener());

        if (prompts) {
            countDownTimer = new MyCountDownTimer(startTime, interval);

            if (!timerHasStarted) {
                countDownTimer.start();
                timerHasStarted = true;
            } else {
                countDownTimer.cancel();
                timerHasStarted = false;
            }
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
                mpDialTone.start();
                serviceQuestion();
                startChronometer();
            }
        } else {
            Log.e("TTS", "Initialization Failed!");
        }
    }

    private static void speak(String text) {
        if(text != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, text);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
        }
    }

    private static void serviceQuestion() {
//      "911 do you need fire, ambulance or police";
        speak(script[0]);

    }

    public static void nameQuestion() {
//      "What's your name?";
        speak(script[1]);
    }

    public static void locationQuestion() {
//      "What's your address?";
        speak(script[2]);
    }

    public static void problemQuestion() {
//      "What's the problem?";
        speak(script[3]);
    }

    public static void ambulanceQuestion() {
//      "E M S is on their way. Stay there";
        speak(script[4]);
    }

    public static void fireQuestion() {
//      Fire team is on their way. Stay there";
        speak(script[5]);
    }

    public static void policeQuestion() {
//      "Police is on their way. Stay there";
        speak(script[6]);
    }

    public static void tryAgain(){
        speak(script[7]);
    }

    public static void gameOver(){
        speak(script[8]);
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

    public void onClick(View v) {
        if (v.getId() == R.id.endButton) {
            closeVoiceActivity();
            endCall();
        }
    }

    public void endCall() {
        stopChronometer();
        Intent results = new Intent(this, ResultsActivity.class);
        startActivity(results);
        finish();
        LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
        LoginActivity.EDITOR.commit();
    }

    public void goToResults() {
        Intent results = new Intent(this, ResultsActivity.class);
        startActivity(results);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
        LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
        LoginActivity.EDITOR.commit();
    }

    public void startSpeechRecognition() {
        if(Level.FINAL != LEVEL) {
            Intent i = new Intent(this, VoiceRecognitionActivity.class);
            startActivity(i);
        } else {
            LEVEL = Level.SERVICE; // reset it
            closeVoiceActivity();
            goToResults();
        }
    }

    public void closeVoiceActivity() {
        if(VoiceRecognitionActivity.getInstance()!=null) {
            VoiceRecognitionActivity.getInstance().finish();
        }
    }

    public void startChronometer() {
        ((Chronometer) findViewById(R.id.chronometer)).start();
    }

    public void stopChronometer() {
        ((Chronometer) findViewById(R.id.chronometer)).stop();
    }

    public void changeColour() {
        mText.setText("911");
        mText.setBackgroundColor(Color.GREEN);
    }

    public void displayPicture() {
        //if (LEVEL == CallActivity.Level.SERVICE) {
            if (VideosActivity.POLICE){
                mVehicle.setImageResource(R.drawable.police);
            } else if (VideosActivity.AMBULANCE){
                mVehicle.setImageResource(R.drawable.ambulance);
            } else if (VideosActivity.FIRE){
                mVehicle.setImageResource(R.drawable.fire);
            }
            mVehicle.setVisibility(View.VISIBLE);
       // }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished/1000 == 3) {
                if(!SPOKEN) {
                    displayPicture();
                }
            }
        }

        @Override
        public void onFinish() {
            if(SPOKEN) {
                mVehicle.setVisibility(View.INVISIBLE);
            }
        }
    }

    class TtsUtteranceListener extends UtteranceProgressListener{

        @Override
        public void onStart(String s) {
        }

        @Override
        public void onDone(String s) {
            startSpeechRecognition();
        }

        @Override
        public void onError(String s) {

        }
    }
}