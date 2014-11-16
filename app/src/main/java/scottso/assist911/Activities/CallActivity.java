package scottso.assist911.Activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

import scottso.assist911.Dialogs.HintProblemDialog;
import scottso.assist911.Dialogs.PromptCallDialog;
import scottso.assist911.R;

public class CallActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{


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

    public TextView mText;

    public static TextToSpeech tts;

    DialogFragment newFragment = new PromptCallDialog();
    DialogFragment hintProblemFragment = new HintProblemDialog();

    private static final String TAG = "CallActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        LEVEL = Level.SERVICE;

        if(MainMenuActivity.TIMES_COMPLETED <= 5) {
            newFragment.show(getFragmentManager(), "PromptDialog");
            hintProblemFragment.show(getFragmentManager(), "PromptDialog");
        }

        tts = new TextToSpeech(this, this);

        ImageButton endButton = (ImageButton) findViewById(R.id.endButton);
        //ImageButton speakButton = (ImageButton) findViewById(R.id.btn_speak);
        //Button hintButton = (Button) findViewById(R.id.btn_hint);

        mText = (TextView) findViewById(R.id.callStatus);

        //speakButton.setOnClickListener(this);
        endButton.setOnClickListener(this);
        //hintButton.setOnClickListener(this);

        tts.setOnUtteranceProgressListener(new TtsUtteranceListener());
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                serviceQuestion();

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
//        String text = "911 do you need fire, ambulance or police";
        speak(script[0]);

    }

    public static void nameQuestion() {
//        String text = "What's your name?";
        speak(script[1]);
    }

    public static void locationQuestion() {
//        String text = "What's your address?";
        speak(script[2]);
    }

    public static void problemQuestion() {
//        String text = "What's the problem?";
        speak(script[3]);
    }

    public static void ambulanceQuestion() {
//        String text = "E M S is on their way. Stay there";
        speak(script[4]);
    }

    public static void fireQuestion() {
//        String text = "Fire team is on their way. Stay there";
        speak(script[5]);
    }

    public static void policeQuestion() {
//        String text = "Police is on their way. Stay there";
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
        //if (v.getId() == R.id.btn_speak) {
        //  startSpeechRecognition();
        if (v.getId() == R.id.endButton) {
            closeVoiceActivity();
            endCall();
            //} else if (v.getId() == R.id.btn_hint) {
            //hintProblemFragment.show(getFragmentManager(), "PromptDialog");
            //}
        }
    }

    public void endCall() {
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

    public void changeColour() {
        mText.setText("911");
        mText.setBackgroundColor(Color.GREEN);
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