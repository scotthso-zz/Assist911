package scottso.assist911;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SimKidsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault("fonts/Montserrat-Regular.ttf", R.attr.fontPath);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    @Override
    protected void onPause() {
        checkLoggedIn();
        super.onPause();
    }

    @Override
    protected void onStop() {
        checkLoggedIn();
        super.onStop();
    }

    public void checkLoggedIn(){
        if(!LoginActivity.PREF.getString(LoginActivity.USERNAME,"").equals("") &&
                MainMenuActivity.TIMES_COMPLETED != 0){
            LoginActivity.IS_LOGGED_IN = true;
            LoginActivity.EDITOR.putBoolean("IS_LOGGED_IN",LoginActivity.IS_LOGGED_IN);
            LoginActivity.EDITOR.putInt(LoginActivity.TIMES_COMPLETED,MainMenuActivity.TIMES_COMPLETED);
            LoginActivity.EDITOR.commit();
        }
    }
}
