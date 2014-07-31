package scottso.assist911;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Created by scottso on 2014-07-30.
 */
public class videosActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;

    }

    }
