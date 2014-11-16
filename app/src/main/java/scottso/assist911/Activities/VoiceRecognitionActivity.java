package scottso.assist911.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import scottso.assist911.SimKidsActivity;

public class VoiceRecognitionActivity extends SimKidsActivity {

    private static final String TAG = "VoiceRecognitionActivity";

    public static VoiceRecognitionActivity mActivity;

    private SpeechRecognizer sr;
    private SpeechRecogListener mListener;

    ArrayList data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(getSpeechRecognizerListener());
        activateSpeechRecognition();
    }

    private SpeechRecogListener getSpeechRecognizerListener() {
        if (mListener == null)
        {
            mListener = new SpeechRecogListener();
        }
        return mListener;
    }

    private void activateSpeechRecognition() {
        sr.setRecognitionListener(getSpeechRecognizerListener());
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        sr.startListening(intent);
        Log.i("SpeechRecog", "Listening!!");
        Log.i("SpeechRecog", "TRIES: " + MainMenuActivity.NUM_TRIES);
    }

    class SpeechRecogListener implements RecognitionListener {
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
                        sr.cancel();
                        if(sr!=null) {
                            sr.destroy();
                            Log.d("SpeechRecog", "Destroyed!");
                        }
                        finish();
                    }
                }
            } else if (CallActivity.LEVEL == CallActivity.Level.NAME) {
                CallActivity.locationQuestion();
                CallActivity.LEVEL = CallActivity.Level.LOCATION;
                saveIncrementAndExit();
            } else if (CallActivity.LEVEL == CallActivity.Level.LOCATION) {
                 int size = CallActivity.ADDRESS_ARRAY[1].length();
                String substr = CallActivity.ADDRESS_ARRAY[1].substring(0,size/2).toLowerCase();
                if (said.contains(substr)) {
                    CallActivity.problemQuestion();
                    CallActivity.LEVEL = CallActivity.Level.EMERGENCY;
                    saveIncrementAndExit();
                } else {
                    if (MainMenuActivity.NUM_TRIES != 3) {
                        activateSpeechRecognition();
                    } else {
                        MainMenuActivity.NUM_TRIES = 0;
                        CallActivity.LEVEL = CallActivity.Level.FINAL;
                        CallActivity.gameOver();
                        sr.cancel();
                        if(sr!=null) {
                            sr.destroy();
                            Log.d("SpeechRecog", "Destroyed!");
                        }
                        finish();
                    }
                }
            } else if (CallActivity.LEVEL == CallActivity.Level.EMERGENCY) {
                if (VideosActivity.POLICE && (said.contains("ole") || said.contains("roke") ||
                                                said.contains("reak"))){
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
                        sr.cancel();
                        if(sr!=null) {
                            sr.destroy();
                            Log.d("SpeechRecog", "Destroyed!");
                        }
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
        sr.cancel();
        finish();
    }

    public static VoiceRecognitionActivity getInstance() {
        return mActivity;
    }

    @Override
    public void onDestroy() {
        if(sr!=null) {
            sr.destroy();
            Log.d("SpeechRecog", "Destroyed!");
        }
        super.onDestroy();
    }
}