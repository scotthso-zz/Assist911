package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends SimKidsActivity {
    private Button loginButton;
    private EditText usernameET;

    public static String USERNAME = "USERNAME";
    public static String TIMES_OPENED = "TIMES_OPENED";
    public static String ACCOUNT_TRIES = "ACCOUNT_TRIES";

    public static Boolean IS_LOGGED_IN = false;

    public static SharedPreferences PREF;
    public static SharedPreferences.Editor EDITOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PREF = getApplicationContext().getSharedPreferences("MyPref", MODE_WORLD_READABLE);
        EDITOR = PREF.edit();

        if (!IS_LOGGED_IN) {
            setContentView(R.layout.activity_login);

            usernameET = (EditText) findViewById(R.id.username);
            loginButton = (Button) findViewById(R.id.button_login);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    login();
                }
            });

            IS_LOGGED_IN = true;
        } else {
            Intent intent = new Intent(this, MyActivity.class);
            startActivity(intent);
        }
    }

    void login() {
        String username = usernameET.getText().toString();
        AccountItem account = FileManager.findAndReadAccount(username, this);
        if(account != null) { //load information into shared preferences
            EDITOR.putString(USERNAME, username);
            EDITOR.putInt(TIMES_OPENED, account.getAccountTimesOpened());
            EDITOR.putInt(ACCOUNT_TRIES, account.getAccountTries());
            EDITOR.commit();
            Log.d("test", "Currently " + PREF.getString(USERNAME, "") + PREF.getInt(TIMES_OPENED,0) + PREF.getInt(ACCOUNT_TRIES,0) );
        } else { //create account
            AccountItem accountItem = new AccountItem(username,0, 0);
            FileManager.saveFile(accountItem, this);
            EDITOR.putString(USERNAME, username);
            EDITOR.putInt(TIMES_OPENED, accountItem.getAccountTimesOpened());
            EDITOR.putInt(ACCOUNT_TRIES, accountItem.getAccountTries());
            EDITOR.commit();
            Log.d("test", "Creating " + PREF.getString(USERNAME, "") + PREF.getInt(TIMES_OPENED,0) + PREF.getInt(ACCOUNT_TRIES,0) );
        }

        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }


}
