package scottso.assist911.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.grantland.widget.AutofitHelper;
import scottso.assist911.AccountItem;
import scottso.assist911.FileManager;
import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

public class ProfileActivity extends SimKidsActivity implements View.OnClickListener {

    private TextView addressTV;

    private String newAddress = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Button editAddressButton = (Button) findViewById(R.id.button_edit_address);
        editAddressButton.setOnClickListener(this);

        final Button returnButton = (Button) findViewById(R.id.button_return);
        returnButton.setOnClickListener(this);

        final Button resetButton = (Button) findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        final Button logoutButton = (Button) findViewById(R.id.button_logout);
        logoutButton.setOnClickListener(this);

        final TextView usernameTV = (TextView) findViewById(R.id.display_username);
        usernameTV.setText("Username: " + LoginActivity.PREF.getString(LoginActivity.USERNAME,""));

        addressTV = (TextView) findViewById(R.id.current_address);
        AutofitHelper.create(addressTV);
        addressTV.setText("Address: " + LoginActivity.PREF.getString(LoginActivity.ADDRESS, ""));




        final TextView timesOpened = (TextView) findViewById(R.id.times_opened);
        timesOpened.setText("Times Completed: " + LoginActivity.PREF.getInt(LoginActivity.TIMES_COMPLETED,0));

        final TextView tries = (TextView) findViewById (R.id.tries);
        tries.setText("Dial Tries: " + String.valueOf(KeypadActivity.TRIES));

        final TextView removedDialog = (TextView) findViewById(R.id.removed_dialog_prompt);
        final TextView removedAudioDialog = (TextView) findViewById(R.id.removed_audio_prompt);

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
            case R.id.button_edit_address:
                showDialog();
                break;

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
                saveAccount();
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

    private void saveAccount() {
        AccountItem account = new AccountItem(LoginActivity.PREF.getString(LoginActivity.USERNAME,""),
                LoginActivity.PREF.getInt(LoginActivity.ACCOUNT_TRIES, 0),
                LoginActivity.PREF.getInt(LoginActivity.TIMES_COMPLETED, 0),
                LoginActivity.PREF.getInt(LoginActivity.HIGH_SCORE, 0),
                LoginActivity.PREF.getString(LoginActivity.ADDRESS,""));
        FileManager.saveToAccount(account, this);
    }

    private void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(ProfileActivity.this);
        final View yourCustomView = inflater.inflate(R.layout.dialog_edit_address, null);

        final TextView etName = (EditText) yourCustomView.findViewById(R.id.edit_address);
        AlertDialog dialog = new AlertDialog.Builder(ProfileActivity.this)
                .setTitle("Enter the new address")
                .setView(yourCustomView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String address = etName.getText().toString();
                        refreshAddress(address);
                    }
                })
                .setNegativeButton("Cancel", null).create();
        dialog.show();
    }

    private void refreshAddress(String address) {
        if (!address.equals("")) {
            address = address.trim();
            newAddress = address;
            addressTV.setText("Address: " + newAddress);
            LoginActivity.EDITOR.putString(LoginActivity.ADDRESS, newAddress);
            LoginActivity.EDITOR.commit();
        } else {
            Toast.makeText(this, "Address was not changed.",Toast.LENGTH_LONG).show();
        }
    }
}
