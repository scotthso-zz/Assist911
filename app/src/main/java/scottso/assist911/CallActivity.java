package scottso.assist911;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by scottso on 2014-07-31.
 */

public class CallActivity extends Activity implements View.OnClickListener, TextToSpeech.OnInitListener {


    private TextView mText;
    private SpeechRecognizer sr;

    private TextToSpeech tts;

    DialogFragment newFragment = new PromptCallDialog();
    DialogFragment hintProblemFragment = new HintProblemDialog();
    DialogFragment hintLocationFragment = new HintLocationDialog();

    String location;
    String service;
    int level = 1;

    ArrayList data;
    private static final String TAG = "MyStt3Activity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        if(MyActivity.TIMES_OPENED <= 5) {

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

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {

                firstQuestion();

            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void firstQuestion() {
        String text = "911 do you need police, fire or ambulance";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void locationQuestion() {
        String text = "Where are you located?";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void locationConfirmationQuestion() {
        String text = "You are at" + location;
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void fireQuestion() {
        String text = "Fire team is on their way. Where are you located?";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        hintLocationFragment.show(getFragmentManager(), "PromptDialog");
    }

    public void ambulanceQuestion() {
        String text = "E M S is on their way. Where are you located?";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        hintLocationFragment.show(getFragmentManager(), "PromptDialog");
    }

    public void policeQuestion() {
        String text = "Police is on their way. Where are you located?";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        hintLocationFragment.show(getFragmentManager(), "PromptDialog");
    }

    public void finalQuestion() {
        String text = "We will be right there, stay calm.";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        hintLocationFragment.show(getFragmentManager(), "PromptDialog");
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

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            Log.d(TAG, "error " + error);
            mText.setText("error " + error);

            Toast toast = Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT);
            toast.show();
        }

        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }

            if (String.valueOf(data.get(0)).trim().toLowerCase().contains("police")) {
                locationQuestion();
                level = 2;
            } else if (String.valueOf(data.get(0)).trim().toLowerCase().contains("ing") || String.valueOf(data.get(0)).trim().toLowerCase().contains("assed")) {
                ambulanceQuestion();
                level = 2;
            } else if (String.valueOf(data.get(0)).trim().toLowerCase().contains("roke") || String.valueOf(data.get(0)).trim().toLowerCase().contains("ole")) {
                policeQuestion();
                level = 2;
            }

            mText.setText("results: " + String.valueOf(data.get(0)));
            //mText.setText("results: " + String.valueOf(data.size()));
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }


    public void onClick(View v) {
        if (v.getId() == R.id.btn_speak) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
            sr.startListening(intent);
            Log.i("111111", "11111111");
        } else if (v.getId() == R.id.endButton) {

            goToResults();

        } else if (v.getId() == R.id.btn_hint) {

            if (level == 1) {
                hintProblemFragment.show(getFragmentManager(), "PromptDialog");
            } else {
                hintLocationFragment.show(getFragmentManager(), "PromptDialog");
            }
        }

    }

    public void goToResults() {
        Intent results = new Intent(this, ResultsActivity.class);
        startActivity(results);

        }





    }





