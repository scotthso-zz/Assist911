package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by scottso on 2014-08-26.
 */
public class ResultsActivity extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);



        TextView tries = (TextView) findViewById(R.id.tries);
        tries.setText("Tries: " + String.valueOf(KeypadActivity.TRIES));

        TextView removedDialog = (TextView) findViewById(R.id.removed_dialog);
        TextView removedAudioDialog = (TextView) findViewById(R.id.removed_audio_dialog);

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
            case R.id.button_back:
                goToMenu();
                break;
        }
    }

    public void goToMenu() {
        Intent menu = new Intent(this, MyActivity.class);
        startActivity(menu);
    }
}
