package scottso.assist911;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import net.frakbot.glowpadbackport.GlowPadView;

import java.util.Locale;

public class LockActivity extends Activity implements TextToSpeech.OnInitListener {

    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts;

    private GlowPadView mGlowPadView;

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;

    private long startTime = 10 * 1000;
    private final long interval = 1 * 1000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        if(MainMenuActivity.TIMES_OPENED > 5 && MainMenuActivity.IS_REMOVE_TEXT_PROMPT == false) {
            DialogFragment newFragment = new PromptRemovedDialog();
            newFragment.show(getFragmentManager(), "PromptDialog");

            MainMenuActivity.IS_REMOVE_TEXT_PROMPT = true;
            LoginActivity.EDITOR.putBoolean(LoginActivity.REMOVE_TEXT_PROMPT, true);
            LoginActivity.EDITOR.commit();

        } else if (MainMenuActivity.TIMES_OPENED > 10 && MainMenuActivity.IS_REMOVE_AUDIO_PROMPT == false) {
            DialogFragment newFragment = new AudioPromptRemovedDialog();
            newFragment.show(getFragmentManager(), "PromptDialog");

            MainMenuActivity.IS_REMOVE_AUDIO_PROMPT = true;
            LoginActivity.EDITOR.putBoolean(LoginActivity.REMOVE_AUDIO_PROMPT, true);
            LoginActivity.EDITOR.commit();
        }

        if (MainMenuActivity.TIMES_OPENED <= 5) {
            DialogFragment newFragment = new PromptUnlockDialog();
            newFragment.show(getFragmentManager(), "PromptDialog");
        }

        tts = new TextToSpeech(this, this);

        mGlowPadView = (GlowPadView) findViewById(R.id.glow_pad_view);
        mGlowPadView.setOnTriggerListener(new GlowPadView.OnTriggerListener() {
            @Override
            public void onGrabbed(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onReleased(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onTrigger(View v, int target) {
                goToDialpad();
                //Toast.makeText(LockActivity.this, "Target triggered! ID=" + target, Toast.LENGTH_SHORT).show();
               // mGlowPadView.reset(true);
            }

            @Override
            public void onGrabbedStateChange(View v, int handle) {
                // Do nothing
            }

            @Override
            public void onFinishFinalAnimation() {
                // Do nothing
            }
        });

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
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                if(MainMenuActivity.TIMES_OPENED < 10) {
                    speakUnlock();
                }
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void speakUnlock() {
        String text = "Unlock the phone";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished/1000 == 4 && MainMenuActivity.TIMES_OPENED < 10) {
//                speakUnlock();
            } else {
            }
        }

        @Override
        public void onFinish() {
        }
    }

    public void goToDialpad() {
        Intent dial = new Intent(this, KeypadActivity.class);
        startActivity(dial);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
    }
}