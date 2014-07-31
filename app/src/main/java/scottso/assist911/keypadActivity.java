package scottso.assist911;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.logging.Handler;

/**
 * Created by scottso on 2014-07-21.
 */
public class keypadActivity extends Activity implements View.OnClickListener {

    private Button one, two, three, four, five, six, seven, eight, nine, zero, star, pound, delete;
    private TextView numbDisp;

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

        numbDisp = (TextView) findViewById(R.id.number_display);

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


        countDownTimer = new MyCountDownTimer(startTime, interval);

        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;

        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (millisUntilFinished/1000 == 6) {
                nine.setBackgroundColor(Color.rgb(247, 202, 24));
            } else if (millisUntilFinished/1000 == 4) {
                nine.setBackgroundColor(Color.WHITE);
                one.setBackgroundColor(Color.rgb(247, 202, 24));
            } else if (millisUntilFinished/1000 == 2) {
                one.setBackgroundColor(Color.WHITE);
            } else if (millisUntilFinished/1000 == 1) {
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
                    one.setBackgroundColor(Color.rgb(238, 238, 238));
                    break;

                case R.id.button2:
                    numbDisp.append("2");
                    break;

                case R.id.button3:
                    numbDisp.append("3");
                    break;

                case R.id.button4:
                    numbDisp.append("4");
                    break;

                case R.id.button5:
                    numbDisp.append("5");
                    break;

                case R.id.button6:
                    numbDisp.append("6");
                    break;

                case R.id.button7:
                    numbDisp.append("7");
                    break;

                case R.id.button8:
                    numbDisp.append("8");
                    break;

                case R.id.button9:
                    numbDisp.append("9");
                    break;

                case R.id.buttonzero:
                    numbDisp.append("0");
                    break;

                case R.id.buttonstar:
                    numbDisp.append("*");
                    break;

                case R.id.buttonpound:
                    numbDisp.append("#");
                    break;

                case R.id.buttondelete:
                    String str = numbDisp.getText().toString().trim();
                    if (str.length() != 0) {
                        str = str.substring(0, str.length() - 1);
                        numbDisp.setText(str);
                    }
                    break;


            }
            one.setBackgroundColor(Color.WHITE);



        }


}
