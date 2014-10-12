package scottso.assist911;

import android.app.Activity;
import android.os.Bundle;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by scottso on 2014-10-12.
 */
public class SimKidsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/Montserrat-Regular.ttf", R.attr.fontPath);
    }
}
