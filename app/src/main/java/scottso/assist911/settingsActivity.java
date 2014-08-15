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

    Button resetButton;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MyActivity myactivity = new MyActivity();
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_reset:

        editor.clear();
        editor.commit();

        }

    }
}
