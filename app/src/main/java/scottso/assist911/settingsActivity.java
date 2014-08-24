package scottso.assist911;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button resetButton = (Button)findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

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
