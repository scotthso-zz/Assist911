package scottso.assist911;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import net.frakbot.glowpadbackport.GlowPadView;

import java.util.Locale;

public class LockActivity extends Activity implements TextToSpeech.OnInitListener {

    private static int TTS_DATA_CHECK = 1;
    private TextToSpeech tts;

    private GlowPadView mGlowPadView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

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
                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                }
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

    public void goToDialpad() {
        Intent dial = new Intent(this, KeypadActivity.class);
        startActivity(dial);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
        LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
        LoginActivity.EDITOR.commit();
    }
}