package scottso.assist911;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button resetButton = (Button)findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

        TextView timesOpened = (TextView) findViewById(R.id.times_opened);
        timesOpened.setText("Times Opened: " + String.valueOf(MyActivity.TIMES_OPENED));

        TextView tries = (TextView) findViewById (R.id.tries);
        tries.setText("Tries: " + String.valueOf(KeypadActivity.TRIES));

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_reset:
                MyActivity.EDITOR.clear();
                MyActivity.EDITOR.commit();
                MyActivity.TIMES_OPENED = 0;
                break;
        }
    }
}
