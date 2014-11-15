package scottso.assist911;

import android.content.Intent;
import android.os.Bundle;
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
        Log.i("SpeechRecog", "TRIES: " + MainMenuActivity.NUM_TRIES);
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
            MainMenuActivity.NUM_TRIES++;
            String str = "";
            Log.d(TAG, "onResults " + results);
            data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (Object aData : data) {
                Log.d(TAG, "result " + aData);
                str += aData;
            }

            String said = String.valueOf(data.get(0)).trim().toLowerCase();

            if (CallActivity.LEVEL == CallActivity.Level.SERVICE) {
                if ((VideosActivity.POLICE && said.contains("ice"))
                       || (VideosActivity.AMBULANCE && said.contains("ambulance"))
                       ||  (VideosActivity.FIRE && said.contains("fire"))) {
                    CallActivity.nameQuestion();
                    CallActivity.LEVEL = CallActivity.Level.NAME;
                    saveIncrementAndExit();
                } else {
                    if (MainMenuActivity.NUM_TRIES != 3) {
                        activateSpeechRecognition();
                    } else {
                        MainMenuActivity.NUM_TRIES = 0;
                        CallActivity.LEVEL = CallActivity.Level.FINAL;
                        CallActivity.gameOver();
                        finish();
                    }
                }
            } else if (CallActivity.LEVEL == CallActivity.Level.NAME) {
                CallActivity.locationQuestion();
                CallActivity.LEVEL = CallActivity.Level.LOCATION;
                saveIncrementAndExit();
            } else if (CallActivity.LEVEL == CallActivity.Level.LOCATION) {
                CallActivity.problemQuestion();
                CallActivity.LEVEL = CallActivity.Level.EMERGENCY;
                saveIncrementAndExit();
            } else if (CallActivity.LEVEL == CallActivity.Level.EMERGENCY) {
                if (VideosActivity.POLICE && said.contains("ole")){
                    CallActivity.LEVEL = CallActivity.Level.FINAL;
                    CallActivity.policeQuestion();
                    VideosActivity.POLICE = false;
                    saveIncrementAndExit();
                } else if (VideosActivity.AMBULANCE && (said.contains("drown") ||
                                                        said.contains("assed") ||
                                                        said.contains("hok") )){
                    CallActivity.LEVEL = CallActivity.Level.FINAL;
                    CallActivity.ambulanceQuestion();
                    VideosActivity.AMBULANCE = false;
                    saveIncrementAndExit();
                } else if (VideosActivity.FIRE && (said.contains("ire") ||
                                                   said.contains("moke"))){
                    CallActivity.LEVEL = CallActivity.Level.FINAL;
                    CallActivity.fireQuestion();
                    VideosActivity.FIRE = false;
                    saveIncrementAndExit();
                } else {
                    if (MainMenuActivity.NUM_TRIES != 3) {
                        activateSpeechRecognition();
                    } else {
                        VideosActivity.POLICE = false;
                        VideosActivity.AMBULANCE = false;
                        VideosActivity.FIRE = false;
                        MainMenuActivity.NUM_TRIES = 0;
                        CallActivity.LEVEL = CallActivity.Level.FINAL;
                        CallActivity.gameOver();
                        finish();
                    }
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

    public void saveIncrementAndExit(){
        MainMenuActivity.CURRENT_TRY_SCORE++;
        LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
        LoginActivity.EDITOR.commit();
        MainMenuActivity.NUM_TRIES = 0;
        finish();

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