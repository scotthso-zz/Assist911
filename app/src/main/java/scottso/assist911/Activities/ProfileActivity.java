package scottso.assist911.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import scottso.assist911.AccountItem;
import scottso.assist911.FileManager;
import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

public class ProfileActivity extends SimKidsActivity implements View.OnClickListener {

    private TextView usernameTV;
    private TextView highScoreTV;
    private TextView timesOpened;
    private TextView tries;
    private TextView removedDialog;
    private TextView removedAudioDialog;
    private Button returnButton;
    private Button resetButton;
    private Button logoutButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        returnButton = (Button) findViewById(R.id.button_return);
        returnButton.setOnClickListener(this);

        resetButton = (Button) findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        logoutButton = (Button) findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(this);

        usernameTV = (TextView) findViewById(R.id.display_username);
        usernameTV.setText("Username: " + LoginActivity.PREF.getString(LoginActivity.USERNAME,""));

        highScoreTV = (TextView) findViewById(R.id.high_score);
        highScoreTV.setText("High Score: " + LoginActivity.PREF.getInt(LoginActivity.HIGH_SCORE,0));

        timesOpened = (TextView) findViewById(R.id.times_opened);
        timesOpened.setText("Times Completed: " + LoginActivity.PREF.getInt(LoginActivity.TIMES_COMPLETED,0));

        tries = (TextView) findViewById (R.id.tries);
        tries.setText("Dial Tries: " + String.valueOf(KeypadActivity.TRIES));

        removedDialog = (TextView) findViewById(R.id.removed_dialog_prompt);
        removedAudioDialog = (TextView) findViewById(R.id.removed_audio_prompt);

        if (MainMenuActivity.IS_REMOVE_TEXT_PROMPT) {
            removedDialog.setText("Removed Text: TRUE");
        } else {
            removedDialog.setText("Removed Text: FALSE");
        }

        if (MainMenuActivity.IS_REMOVE_AUDIO_PROMPT) {
            removedAudioDialog.setText("Removed Audio: TRUE");
        } else {
            removedAudioDialog.setText("Removed Audio: FALSE");
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_return:
                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.button_reset:
                MainMenuActivity.TIMES_COMPLETED = 0;
                MainMenuActivity.IS_REMOVE_TEXT_PROMPT = false;
                MainMenuActivity.IS_REMOVE_AUDIO_PROMPT = false;

                LoginActivity.EDITOR.putInt(LoginActivity.TIMES_COMPLETED, MainMenuActivity.TIMES_COMPLETED);
                LoginActivity.EDITOR.putBoolean(LoginActivity.REMOVE_TEXT_PROMPT, MainMenuActivity.IS_REMOVE_TEXT_PROMPT);
                LoginActivity.EDITOR.putBoolean(LoginActivity.REMOVE_AUDIO_PROMPT, MainMenuActivity.IS_REMOVE_AUDIO_PROMPT);
                LoginActivity.EDITOR.commit();

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
                        LoginActivity.PREF.getInt(LoginActivity.TIMES_COMPLETED, 0),
                        LoginActivity.PREF.getInt(LoginActivity.HIGH_SCORE, 0));
                FileManager.saveToAccount(account, this);
                ////// clearing data
                LoginActivity.EDITOR.clear();
                LoginActivity.EDITOR.commit();
                MainMenuActivity.TIMES_COMPLETED = 0;

                MainMenuActivity.IS_REMOVE_TEXT_PROMPT = false;
                MainMenuActivity.IS_REMOVE_AUDIO_PROMPT = false;
                LoginActivity.IS_LOGGED_IN = false;

                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();

                startActivity(loginIntent);
                break;
        }
    }
}
