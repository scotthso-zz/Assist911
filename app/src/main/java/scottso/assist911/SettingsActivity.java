package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends Activity implements View.OnClickListener {

    private TextView usernameTV;
    private TextView timesOpened;
    private TextView tries;
    private TextView removedDialog;
    private TextView removedAudioDialog;
    private Button resetButton;
    private Button logoutButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        resetButton = (Button) findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        logoutButton = (Button) findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(this);

        usernameTV = (TextView) findViewById(R.id.display_username);
        usernameTV.setText("Username: " + LoginActivity.PREF.getString(LoginActivity.USERNAME,""));

        timesOpened = (TextView) findViewById(R.id.times_opened);
        timesOpened.setText("Times Opened: " + String.valueOf(MainMenuActivity.TIMES_OPENED));

        tries = (TextView) findViewById (R.id.tries);
        tries.setText("Tries: " + String.valueOf(KeypadActivity.TRIES));

        removedDialog = (TextView) findViewById(R.id.removed_dialog);
        removedAudioDialog = (TextView) findViewById(R.id.removed_audio_dialog);

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
            case R.id.button_reset:
                LoginActivity.EDITOR.clear();//TODO: what is this
                LoginActivity.EDITOR.commit();
                MainMenuActivity.TIMES_OPENED = 0;

                MainMenuActivity.IS_REMOVE_TEXT_PROMPT = false;
                MainMenuActivity.IS_REMOVE_AUDIO_PROMPT = false;

                finish();
                startActivity(getIntent());

                break;
//            case R.id.button_showtutorial:
//                Intent tutorialIntent = new Intent(this, TutorialActivity.class);
//                startActivity(tutorialIntent);
//                finish();
//                break;
            case R.id.button_logout:
                AccountItem account = new AccountItem(LoginActivity.PREF.getString(LoginActivity.USERNAME,""),
                        LoginActivity.PREF.getInt(LoginActivity.ACCOUNT_TRIES, 0),
                        LoginActivity.PREF.getInt(LoginActivity.TIMES_OPENED, 0),
                        LoginActivity.PREF.getInt(LoginActivity.HIGH_SCORE, 0));
                FileManager.saveToAccount(account, this);
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                LoginActivity.IS_LOGGED_IN = false;
                finish();
                startActivity(loginIntent);
                break;
        }
    }
}
