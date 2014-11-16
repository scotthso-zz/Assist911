package scottso.assist911.Activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import scottso.assist911.Dialogs.PromptDialDialog;
import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

public class KeypadActivity extends SimKidsActivity implements View.OnClickListener {

    private Button one, two, three, four, five, six, seven, eight, nine, zero, star, pound;
    private ImageButton delete, call;

    private TextView contactDisp;
    private TextView numbDisp;

    private final String emergency = "911";

    public int clickCount = 0;

    public static int TRIES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);

        TRIES = 0;

        one = (Button) findViewById(R.id.button1);
        two = (Button) findViewById(R.id.button2);
        three = (Button) findViewById(R.id.button3);
        four = (Button) findViewById(R.id.button4);
        five = (Button) findViewById(R.id.button5);
        six = (Button) findViewById(R.id.button6);
        seven = (Button) findViewById(R.id.button7);
        eight = (Button) findViewById(R.id.button8);
        nine = (Button) findViewById(R.id.button9);

        zero = (Button) findViewById(R.id.buttonzero);
        star = (Button) findViewById(R.id.buttonstar);
        pound = (Button) findViewById(R.id.buttonpound);

        delete = (ImageButton) findViewById(R.id.buttondelete);
        call = (ImageButton) findViewById(R.id.buttoncall);

        numbDisp = (TextView) findViewById(R.id.number_display);
        contactDisp = (TextView) findViewById(R.id.contact_display);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);

        star.setOnClickListener(this);
        pound.setOnClickListener(this);

        delete.setOnClickListener(this);

        call.setOnClickListener(this);

        if (visualPrompts()) {
            DialogFragment newFragment = new PromptDialDialog();
            newFragment.show(getFragmentManager(), "PromptDialog");
            nine.setBackgroundColor(Color.rgb(247, 202, 24));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                numbDisp.append("1");
                contactDisp.append("1");
                if(visualPrompts()) {
                    one.setBackgroundColor(Color.rgb(247, 202, 24));
                }

                String estr = contactDisp.getText().toString().trim();

                if (estr.equals(emergency)) {
                    contactDisp.append(" - Emergency");
                    one.setBackgroundColor(Color.WHITE);
                }
                break;

            case R.id.button2:
                numbDisp.append("2");
                contactDisp.append("2");
                break;

            case R.id.button3:
                numbDisp.append("3");
                contactDisp.append("3");
                break;

            case R.id.button4:
                numbDisp.append("4");
                contactDisp.append("4");
                break;

            case R.id.button5:
                numbDisp.append("5");
                contactDisp.append("5");
                break;

            case R.id.button6:
                numbDisp.append("6");
                contactDisp.append("6");
                break;

            case R.id.button7:
                numbDisp.append("7");
                contactDisp.append("7");
                break;

            case R.id.button8:
                numbDisp.append("8");
                contactDisp.append("8");
                break;

            case R.id.button9:
                numbDisp.append("9");
                contactDisp.append("9");
                if(visualPrompts()) {
                    one.setBackgroundColor(Color.rgb(247, 202, 24));
                }
                nine.setBackgroundColor(Color.WHITE);
                //one.setBackgroundColor(Color.rgb(247, 202, 24));
                // one.setBackgroundColor(Color.WHITE);
                break;

            case R.id.buttonzero:
                numbDisp.append("0");
                contactDisp.append("0");
                break;

            case R.id.buttonstar:
                numbDisp.append("*");
                contactDisp.append("*");
                break;

            case R.id.buttonpound:
                numbDisp.append("#");
                contactDisp.append("#");
                break;

            case R.id.buttondelete:
                String str = numbDisp.getText().toString().trim();
                if (str.length() != 0) {
                    str = str.substring(0, str.length() - 1);
                    numbDisp.setText(str);
                    contactDisp.setText(str);
                }
                break;

            case R.id.buttoncall:
                if (numbDisp.getText().toString().equals(emergency)) {
                    goToCall();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT);
                    toast.show();
                    TRIES++;
                    LoginActivity.EDITOR.putInt(LoginActivity.ACCOUNT_TRIES, TRIES);
                    LoginActivity.EDITOR.commit();
                    if (TRIES == 3) {
                        goToResults();
                        LoginActivity.EDITOR.putInt(LoginActivity.ACCOUNT_TRIES, TRIES);
                        LoginActivity.EDITOR.commit();
                    }
                }
                break;
        }
    }

    public boolean visualPrompts() {
        if(MainMenuActivity.TIMES_COMPLETED > 5) {
            return false;
        }
        return true;
    }
    public void goToCall() {
        LoginActivity.EDITOR.putInt(LoginActivity.ACCOUNT_TRIES, TRIES);
        Intent call = new Intent(this, CallActivity.class);
        startActivity(call);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
        LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
        LoginActivity.EDITOR.commit();
    }

    public void goToResults(){
        TRIES = 0;
        Intent i = new Intent(this, ResultsActivity.class);
        startActivity(i);
        finish();
    }
}