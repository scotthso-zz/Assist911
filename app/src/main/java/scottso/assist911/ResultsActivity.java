package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        TextView tries = (TextView) findViewById(R.id.tries);
        tries.setText("Mistakes: " + String.valueOf(KeypadActivity.TRIES));

        TextView removedDialog = (TextView) findViewById(R.id.removed_dialog);
        TextView removedAudioDialog = (TextView) findViewById(R.id.removed_audio_dialog);

        if (MainMenuActivity.IS_REMOVE_TEXT_PROMPT == true) {
            removedDialog.setText("Removed: TRUE");
        } else {
            removedDialog.setText("Removed: FALSE");
        }

        if (MainMenuActivity.IS_REMOVE_AUDIO_PROMPT == true) {
            removedAudioDialog.setText("Removed Audio: TRUE");
        } else {
            removedAudioDialog.setText("Removed Audio: FALSE");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                goToMenu();
                break;
        }
    }

    public void goToMenu() {
        Intent menu = new Intent(this, MainMenuActivity.class);
        startActivity(menu);
    }
}