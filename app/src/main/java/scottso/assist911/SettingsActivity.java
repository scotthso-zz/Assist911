package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button resetButton = (Button)findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        TextView timesOpened = (TextView) findViewById(R.id.times_opened);
        timesOpened.setText("Times Opened: " + String.valueOf(MyActivity.TIMES_OPENED));

        TextView tries = (TextView) findViewById (R.id.tries);
        tries.setText("Tries: " + String.valueOf(KeypadActivity.TRIES));

        TextView removedDialog = (TextView)findViewById(R.id.removed_dialog);
        TextView removedAudioDialog = (TextView)findViewById(R.id.removed_audio_dialog);

        Button tutorialButton = (Button) findViewById(R.id.button_showtutorial);
        tutorialButton.setOnClickListener(this);

        if (MyActivity.REMOVED_TEXT_PROMPT == true) {

            removedDialog.setText("Removed: TRUE");

        } else {

            removedDialog.setText("Removed: FALSE");
        }

        if (MyActivity.REMOVED_AUDIO_PROMPT == true) {

            removedAudioDialog.setText("Removed Audio: TRUE");

        } else {

            removedAudioDialog.setText("Removed Audio: FALSE");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_reset:
                MyActivity.EDITOR.clear();
                MyActivity.EDITOR.commit();
                MyActivity.TIMES_OPENED = 0;

                MyActivity.REMOVED_TEXT_PROMPT = false;
                MyActivity.REMOVED_AUDIO_PROMPT = false;

                finish();
                startActivity(getIntent());

                break;
            case R.id.button_showtutorial:
                Intent tutorialIntent = new Intent(this, TutorialActivity.class);
                startActivity(tutorialIntent);
                break;
        }
    }
}
