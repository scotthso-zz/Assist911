package scottso.assist911.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import scottso.assist911.AccountItem;
import scottso.assist911.FileManager;
import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

public class LoginActivity extends SimKidsActivity {
    private Button loginButton;
    private EditText usernameET;

    public static final String USERNAME = "USERNAME";
    public static final String TIMES_COMPLETED = "TIMES_COMPLETED";
    public static final String ACCOUNT_TRIES = "ACCOUNT_TRIES";
    public static final String HIGH_SCORE = "HIGH_SCORE";

    public static final String REMOVE_TEXT_PROMPT = "REMOVE_TEXT_PROMPT";
    public static final String REMOVE_AUDIO_PROMPT = "REMOVE_AUDIO_PROMPT";

    public static final String CURRENT_TRY_SCORE = "CURRENT_TRY_SCORE";

    public static Boolean IS_LOGGED_IN = false;

    public static SharedPreferences PREF;
    public static SharedPreferences.Editor EDITOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PREF = getApplicationContext().getSharedPreferences("MyPref", MODE_WORLD_READABLE);
        EDITOR = PREF.edit();

        if (!PREF.getBoolean("IS_LOGGED_IN",false)) {
            setContentView(R.layout.activity_login);

            usernameET = (EditText) findViewById(R.id.username);
            loginButton = (Button) findViewById(R.id.button_login);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });
        } else {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void login() {
        String username = usernameET.getText().toString();
        if (username.equals("")) {
            Toast.makeText(this, "Please enter an existing username or a new username.",Toast.LENGTH_LONG).show();
            IS_LOGGED_IN = false;
        } else {
            AccountItem account = FileManager.findAndReadAccount(username, this);
            if (account != null) { //load information into shared preferences
                EDITOR.putString(USERNAME, username);
                EDITOR.putInt(TIMES_COMPLETED, account.getAccountTimesOpened());
                EDITOR.putInt(ACCOUNT_TRIES, account.getAccountTries());
                EDITOR.putInt(HIGH_SCORE, account.getHighScore());
                EDITOR.commit();
                Log.d("test", "Currently " + PREF.getString(USERNAME, "") + "&" + PREF.getInt(TIMES_COMPLETED, 0)
                        + "&" + PREF.getInt(ACCOUNT_TRIES, 0) + "&" + PREF.getInt(HIGH_SCORE, 0));
            } else { //create account
                AccountItem accountItem = new AccountItem(username, 0, 0, 0);
                FileManager.saveFile(accountItem, this);
                EDITOR.putString(USERNAME, username);
                EDITOR.putInt(TIMES_COMPLETED, accountItem.getAccountTimesOpened());
                EDITOR.putInt(ACCOUNT_TRIES, accountItem.getAccountTries());
                EDITOR.putInt(HIGH_SCORE, accountItem.getHighScore());
                EDITOR.commit();
                Log.d("test", "Creating " + PREF.getString(USERNAME, "") + "&" + PREF.getInt(TIMES_COMPLETED, 0)
                        + "&" + PREF.getInt(ACCOUNT_TRIES, 0) + "&" + PREF.getInt(HIGH_SCORE, 0));
            }
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
            finish();
            IS_LOGGED_IN = true;
        }
    }
}