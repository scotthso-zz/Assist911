package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity implements View.OnClickListener{


    private Button practice;
    private Button videos;
    private Button settings;
    private Button report;

    static int timesOpened;

    static SharedPreferences pref;
    static Editor editor;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_my);

                practice = (Button) this.findViewById(R.id.practice_button);
                practice.setOnClickListener(this);

                videos = (Button) this.findViewById(R.id.videos_button);
                videos.setOnClickListener(this);

                settings = (Button) this.findViewById(R.id.settings_button);
                settings.setOnClickListener(this);

                report = (Button) this.findViewById(R.id.report_button);
                report.setOnClickListener(this);

                     pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    editor = pref.edit();

                    timesOpened = pref.getInt("timesOpened", 2);

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

        public void onClick(View v) {

            switch(v.getId()) {

                case R.id.practice_button:
                    goToPractice();

                        timesOpened++;
                        editor.putInt("timesOpened", timesOpened);
                        editor.commit();


                System.out.println(pref.getInt("timesOpened",2));

            break;

            case R.id.videos_button:
                goToVideos();
                break;

            case R.id.settings_button:
                goToSettings();
                break;

            case R.id.report_button:
                goToReport();
                break;
        }

    }

    public void goToPractice() {
        Intent lock = new Intent(this, lockActivity.class);
        startActivity(lock);
    }

    public void goToVideos() {
        Intent video = new Intent(this, videosActivity.class);
        startActivity(video);
    }

    public void goToSettings() {
        Intent settings = new Intent(this, settingsActivity.class);
        startActivity(settings);
    }

    public void goToReport() {
        Intent report = new Intent(this, reportActivity.class);
        startActivity(report);
    }

}


