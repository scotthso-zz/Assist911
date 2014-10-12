package scottso.assist911;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by scottso on 2014-10-12.
 */
public class SimKidsApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault("fonts/Montserrat-Regular.ttf");
    }
}
