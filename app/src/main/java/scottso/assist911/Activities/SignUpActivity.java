package scottso.assist911.Activities;

import android.content.Intent;
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

public class SignUpActivity extends SimKidsActivity {

    private EditText usernameET;
    private EditText addressET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameET = (EditText) findViewById(R.id.new_username);
        addressET = (EditText) findViewById(R.id.address);

        final Button createAccount = (Button) findViewById(R.id.button_create_account);
        final Button back = (Button) findViewById(R.id.button_back_to_login);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = String.valueOf(usernameET.getText());
                String address = String.valueOf(addressET.getText());
                signUp(username, address);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    private void signUp(String username, String address) {
        if (!username.equals("") && !address.equals("")) {
            if(!FileManager.accountNames.contains(username)) {
                username = username.trim();
                address = address.trim();
                AccountItem accountItem = new AccountItem(username, 0, 0, 0, address);
                FileManager.saveFile(accountItem, this);
                LoginActivity.EDITOR.putString(LoginActivity.USERNAME, username);
                LoginActivity.EDITOR.putInt(LoginActivity.TIMES_COMPLETED, accountItem.getAccountTimesOpened());
                LoginActivity.EDITOR.putInt(LoginActivity.ACCOUNT_TRIES, accountItem.getAccountTries());
                LoginActivity.EDITOR.putInt(LoginActivity.HIGH_SCORE, accountItem.getHighScore());
                LoginActivity.EDITOR.putString(LoginActivity.ADDRESS, accountItem.getAddress());
                LoginActivity.EDITOR.commit();
                Log.d("test", "Creating " + LoginActivity.PREF.getString(LoginActivity.USERNAME, "") + "&"
                        + LoginActivity.PREF.getInt(LoginActivity.TIMES_COMPLETED, 0) + "&"
                        + LoginActivity.PREF.getInt(LoginActivity.ACCOUNT_TRIES, 0) + "&"
                        + LoginActivity.PREF.getInt(LoginActivity.HIGH_SCORE, 0) + "&"
                        + LoginActivity.PREF.getString(LoginActivity.ADDRESS, ""));

                Intent intent = new Intent(this, MainMenuActivity.class);
                startActivity(intent);
                finish();
                LoginActivity.IS_LOGGED_IN = true;
            } else {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_LONG).show();
                //usernameET.setHint("Username already exists!");
                //usernameET.setHintTextColor("#324242");
            }
        } else {
            //usernameET.setHint("Please enter a username.");
            //addressET.setHint("Please enter an address.");
            Toast.makeText(this, "Username and/or address cannot be left blank!", Toast.LENGTH_LONG).show();
        }
    }

    private void goBack() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
