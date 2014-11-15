package scottso.assist911.Activities;

import android.os.Bundle;
import android.widget.TextView;

import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

public class ReportActivity extends SimKidsActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView username = (TextView) findViewById(R.id.username);
        username.setText(String.valueOf(LoginActivity.PREF.getString(LoginActivity.USERNAME,"")));

        TextView highScore = (TextView) findViewById(R.id.rp_high_score);
        highScore.setText("High Score: " + LoginActivity.PREF.getInt(LoginActivity.HIGH_SCORE,0));

    }

}
