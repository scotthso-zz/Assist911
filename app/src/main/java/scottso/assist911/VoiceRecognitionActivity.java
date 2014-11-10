package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class VoiceRecognitionActivity extends SimKidsActivity {

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
            String str = "";
            Log.d(TAG, "onResults " + results);
            data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (Object aData : data) {
                Log.d(TAG, "result " + aData);
                str += aData;
            }

            if (CallActivity.LEVEL == CallActivity.Level.SERVICE) {
                if ((String.valueOf(data.get(0)).trim().toLowerCase().contains("ice")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("ambulance")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("fire"))) {
                    CallActivity.nameQuestion();
                    CallActivity.LEVEL = CallActivity.Level.NAME;
                    finish();
                    MainMenuActivity.CURRENT_TRY_SCORE++;
                    LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
                    LoginActivity.EDITOR.commit();
                }  else {
                    activateSpeechRecognition();
                }
            } else if (CallActivity.LEVEL == CallActivity.Level.NAME) {
                CallActivity.locationQuestion();
                CallActivity.LEVEL = CallActivity.Level.LOCATION;
                finish();
                MainMenuActivity.CURRENT_TRY_SCORE++;
                LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
                LoginActivity.EDITOR.commit();
            } else if (CallActivity.LEVEL == CallActivity.Level.LOCATION) {
                CallActivity.problemQuestion();
                CallActivity.LEVEL = CallActivity.Level.EMERGENCY;
                finish();
                MainMenuActivity.CURRENT_TRY_SCORE++;
                LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
                LoginActivity.EDITOR.commit();
            } else if (CallActivity.LEVEL == CallActivity.Level.EMERGENCY) {
                if (String.valueOf(data.get(0)).trim().toLowerCase().contains("ole")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("ing")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("assed")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("drown")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("moke")
                        || String.valueOf(data.get(0)).trim().toLowerCase().contains("ire")) {
                    CallActivity.LEVEL = CallActivity.Level.FINAL;
                    if (VideosActivity.AMBULANCE) {
                        CallActivity.ambulanceQuestion();
                        VideosActivity.AMBULANCE = false;
                        MainMenuActivity.CURRENT_TRY_SCORE++;
                    } else if (VideosActivity.FIRE) {
                        CallActivity.fireQuestion();
                        VideosActivity.FIRE = false;
                        MainMenuActivity.CURRENT_TRY_SCORE++;
                    } else if (VideosActivity.POLICE) {
                        CallActivity.policeQuestion();
                        VideosActivity.POLICE = false;
                        MainMenuActivity.CURRENT_TRY_SCORE++;
                    }
                    LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
                    LoginActivity.EDITOR.commit();
                    finish();
                } else {
                    activateSpeechRecognition();
                }
            }
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
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