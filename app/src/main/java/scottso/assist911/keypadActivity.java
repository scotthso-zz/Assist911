package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by scottso on 2014-07-21.
 */
public class KeypadActivity extends Activity implements View.OnClickListener {

    private Button one, two, three, four, five, six, seven, eight, nine, zero, star, pound, delete, call;

    private TextView contactDisp;
    private TextView numbDisp;

    String emergency = "911";

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;

    private long startTime = 8 * 1000;
    private final long interval = 1 * 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);

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

        delete = (Button) findViewById(R.id.buttondelete);

        call = (Button) findViewById(R.id.buttoncall);

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



        countDownTimer = new MyCountDownTimer(startTime, interval);

        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;

        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
        }

    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

               if (millisUntilFinished/1000 == 6) {
                nine.setBackgroundColor(Color.rgb(247, 202, 24));
            } else if (millisUntilFinished/1000 == 5) {
                nine.setBackgroundColor(Color.WHITE);
                one.setBackgroundColor(Color.rgb(247, 202, 24));
            } else if (millisUntilFinished/1000 == 3) {
                one.setBackgroundColor(Color.WHITE);
            } else if (millisUntilFinished/1000 == 2) {
                one.setBackgroundColor(Color.rgb(247, 202, 24));
            }

        }

        @Override
        public void onFinish() {
            one.setBackgroundColor(Color.WHITE);

        }
    }
    @Override
    public void onClick(View view) {

            switch (view.getId()) {

                case R.id.button1:
                    numbDisp.append("1");
                    contactDisp.append("1");
                    one.setBackgroundColor(Color.rgb(238, 238, 238));

                    String estr = contactDisp.getText().toString().trim();


                    if (estr.equals(emergency)) {
                        contactDisp.append(" - Emergency");
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

                    }
                    break;

            }
            one.setBackgroundColor(Color.WHITE);

        }

    public void goToCall() {

        Intent call = new Intent(this, CallActivity.class);
        startActivity(call);

    }


}
