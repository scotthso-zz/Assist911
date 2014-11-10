package scottso.assist911;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CallActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener{

    enum Level {
        SERVICE,
        NAME,
        LOCATION,
        EMERGENCY,
        FINAL
    }

    private static final String[] script = {"911 do you need fire, ambulance or police",
                               "What's your name?",
                               "What's your address?",
                               "What's the problem?",
                               "E M S is on their way. Stay there.",
                               "Fire team is on their way. Stay there",
                               "Police is on their way. Stay there",};

    public static Level LEVEL = Level.SERVICE;

    private TextView mText;

    public static TextToSpeech tts;

    DialogFragment newFragment = new PromptCallDialog();
    DialogFragment hintProblemFragment = new HintProblemDialog();

    private static final String TAG = "CallActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        if(MainMenuActivity.TIMES_OPENED <= 5) {
            newFragment.show(getFragmentManager(), "PromptDialog");
            hintProblemFragment.show(getFragmentManager(), "PromptDialog");
        }

        tts = new TextToSpeech(this, this);

        ImageButton endButton = (ImageButton) findViewById(R.id.endButton);
        ImageButton speakButton = (ImageButton) findViewById(R.id.btn_speak);
        Button hintButton = (Button) findViewById(R.id.btn_hint);

        mText = (TextView) findViewById(R.id.textView1);

        speakButton.setOnClickListener(this);
        endButton.setOnClickListener(this);
        hintButton.setOnClickListener(this);


        tts.setOnUtteranceProgressListener(new ttsUtteranceListener());
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
        if (v.getId() == R.id.btn_speak) {
            startSpeechRecognition();
        } else if (v.getId() == R.id.endButton) {
            goToResults();
        } else if (v.getId() == R.id.btn_hint) {
            hintProblemFragment.show(getFragmentManager(), "PromptDialog");
        }
    }

    public void goToResults() {
        Intent results = new Intent(this, ResultsActivity.class);
        startActivity(results);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
    }

    public void startSpeechRecognition() {
        Intent i = new Intent(this, VoiceRecognitionActivity.class);
        startActivity(i);
    }
    class ttsUtteranceListener extends UtteranceProgressListener{

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