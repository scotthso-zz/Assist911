package scottso.assist911;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Handler;

/**
 * Created by scottso on 2014-07-21.
 */
public class keypadActivity extends Activity implements View.OnClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, star, pound;
    TextView numbDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);

        one = (Button)findViewById(R.id.button1);
        two = (Button)findViewById(R.id.button2);
        three = (Button)findViewById(R.id.button3);
        four = (Button)findViewById(R.id.button4);
        five = (Button)findViewById(R.id.button5);
        six = (Button)findViewById(R.id.button6);
        seven = (Button)findViewById(R.id.button7);
        eight = (Button)findViewById(R.id.button8);
        nine = (Button)findViewById(R.id.button9);

        zero = (Button)findViewById(R.id.buttonzero);
        star = (Button)findViewById(R.id.buttonstar);
        pound = (Button)findViewById(R.id.buttonpound);

        numbDisp = (TextView)findViewById(R.id.number_display);

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


    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.button1:
                numbDisp.append("1");
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
        }
    }
}
