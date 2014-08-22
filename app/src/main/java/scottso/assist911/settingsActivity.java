package scottso.assist911;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by scottso on 2014-08-08.
 */
public class settingsActivity extends Activity implements View.OnClickListener {


    SharedPreferences.Editor editor = MyActivity.editor;

    int timesOpened = MyActivity.timesOpened;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button resetButton = (Button)findViewById(R.id.button_reset);
        resetButton.setOnClickListener(this);

    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_reset:

        editor.clear();
        editor.commit();


                break;

        }

    }
}
