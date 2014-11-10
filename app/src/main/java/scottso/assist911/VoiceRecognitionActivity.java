package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceRecognitionActivity extends Activity {

    private static final String TAG = "VoiceRecognitionActivity";
    private SpeechRecognizer sr;

    ArrayList data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new speechRecogListener());
        activateSpeechRecognition();
    }

    private void activateSpeechRecognition() {
        sr.setRecognitionListener(new speechRecogListener());
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        sr.startListening(intent);
        Log.i("SpeechRecog", "Listening!!");
    }

    class speechRecogListener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
//            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            Log.d(TAG, "error " + error);

            Toast toast = Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT);
            toast.show();

            activateSpeechRecognition();
        }

        public void onResults(Bundle results) {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }

            if (CallActivity.LEVEL == CallActivity.Level.SERVICE) {
                if ((String.valueOf(data.get(0)).trim().toLowerCase().contains("ice")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("ambulance")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("fire"))) {
                    CallActivity.nameQuestion();
                    CallActivity.LEVEL = CallActivity.Level.NAME;
                    finish();
                    MainMenuActivity.CURRENT_TRY_SCORE++;
                }  else {
                    activateSpeechRecognition();
                }
            } else if (CallActivity.LEVEL == CallActivity.Level.NAME) {
                CallActivity.locationQuestion();
                CallActivity.LEVEL = CallActivity.Level.LOCATION;
                finish();
                MainMenuActivity.CURRENT_TRY_SCORE++;
            } else if (CallActivity.LEVEL == CallActivity.Level.LOCATION) {
                CallActivity.problemQuestion();
                CallActivity.LEVEL = CallActivity.Level.EMERGENCY;
                finish();
                MainMenuActivity.CURRENT_TRY_SCORE++;
            } else if (CallActivity.LEVEL == CallActivity.Level.EMERGENCY) {
                if (String.valueOf(data.get(0)).trim().toLowerCase().contains("ole")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("ing")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("assed")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("drown")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("moke")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("ire")) {
                    CallActivity.LEVEL = CallActivity.Level.FINAL;
                    CallActivity.ambulanceQuestion();
//                policeQuestion();
//                fireQuestion();
                    finish();
                    MainMenuActivity.CURRENT_TRY_SCORE++;
                } else {
                    activateSpeechRecognition();
                }
            } else if (CallActivity.LEVEL == CallActivity.Level.FINAL) {
                CallActivity.LEVEL = CallActivity.Level.SERVICE;
                goToResults();
            }
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }

    public void goToResults() {
        Intent results = new Intent(this, ResultsActivity.class);
        startActivity(results);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
    }

    @Override
    protected void onDestroy() {
        if(sr!=null)
        {
            sr.destroy();
        }
        super.onDestroy();
    }
}
