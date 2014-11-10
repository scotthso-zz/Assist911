package scottso.assist911;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends SimKidsActivity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        final TextView title = (TextView) findViewById(R.id.title);
        if(MainMenuActivity.CURRENT_TRY_SCORE == 8) {
            title.setText("Congratulations!");
        } else {
            title.setText("Game Over :(");
        }

        final TextView newHighScore = (TextView) findViewById(R.id.new_high_score);
        if (MainMenuActivity.CURRENT_TRY_SCORE > LoginActivity.PREF.getInt(LoginActivity.HIGH_SCORE, 0)) {
            LoginActivity.EDITOR.putInt(LoginActivity.HIGH_SCORE,MainMenuActivity.CURRENT_TRY_SCORE);
            LoginActivity.EDITOR.commit();
            newHighScore.setVisibility(View.VISIBLE);
        } else {
            newHighScore.setVisibility(View.GONE);
        }

        final TextView points = (TextView) findViewById(R.id.points);
        points.setText("Score: " + String.valueOf(MainMenuActivity.CURRENT_TRY_SCORE) + "/8");

        final TextView tries = (TextView) findViewById(R.id.tries);
        tries.setText("Dialpad Mistakes: " + String.valueOf(KeypadActivity.TRIES));

        final TextView removedDialog = (TextView) findViewById(R.id.removed_dialog);
        final TextView removedAudioDialog = (TextView) findViewById(R.id.removed_audio_dialog);

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
        finish();
    }
}