package scottso.assist911;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyActivity extends SimKidsActivity implements View.OnClickListener{

    private Button practice;
    private Button videos;
    private Button settings;
    private Button report;

    String[] videoArray = {"flame","smoke","passed","car", "drowning", "a", "b"};

    public static int TIMES_OPENED;
    public static boolean REMOVED_TEXT_PROMPT = false;
    public static boolean REMOVED_AUDIO_PROMPT = false;

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                setContentView(R.layout.activity_my);

                practice = (Button) this.findViewById(R.id.practice_button);
                practice.setOnClickListener(this);

                videos = (Button) this.findViewById(R.id.videos_button);
                videos.setOnClickListener(this);

                settings = (Button) this.findViewById(R.id.profile_button);
                settings.setOnClickListener(this);

                report = (Button) this.findViewById(R.id.report_button);
                report.setOnClickListener(this);

                TIMES_OPENED = LoginActivity.PREF.getInt(LoginActivity.TIMES_OPENED, 0);
                REMOVED_TEXT_PROMPT = LoginActivity.PREF.getBoolean("REMOVED_TEXT_PROMPT", false);
                REMOVED_AUDIO_PROMPT = LoginActivity.PREF.getBoolean("REMOVED_AUDIO_PROMPT", false);
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

                    TIMES_OPENED++;
                    LoginActivity.EDITOR.putInt(LoginActivity.TIMES_OPENED, TIMES_OPENED);
                    LoginActivity.EDITOR.commit();

                    System.out.println(LoginActivity.PREF.getInt(LoginActivity.TIMES_OPENED, 0));

                    int index = new Random().nextInt(videoArray.length);
                    VideosActivity.VIDEO_NAME = (videoArray[index]);

                    if (VideosActivity.VIDEO_NAME.equals("flame") || VideosActivity.VIDEO_NAME.equals("car") ||
                            VideosActivity.VIDEO_NAME.equals("smoke") || VideosActivity.VIDEO_NAME.equals("passed") || VideosActivity.VIDEO_NAME.equals("drowning"))  {
                        VideosActivity.EMERGENCY = true;
                    } else if (VideosActivity.VIDEO_NAME.equals("a") || VideosActivity.VIDEO_NAME.equals("b")){
                        VideosActivity.EMERGENCY = false;
                    }
                    //goToPractice();
                    goToVideoPlayer();
            break;

            case R.id.videos_button:
                goToVideos();
                System.out.println(LoginActivity.PREF.getInt(LoginActivity.TIMES_OPENED, 0));
                break;

            case R.id.profile_button:
                goToSettings();
                break;

            case R.id.report_button:
                goToReport();
                break;
        }

    }

    public void goToVideos() {
        Intent video = new Intent(this, VideosActivity.class);
        startActivity(video);
    }

    public void goToSettings() {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    public void goToReport() {
        Intent report = new Intent(this, ReportActivity.class);
        startActivity(report);
    }

    public void goToVideoPlayer() {
        Intent videoPlayer = new Intent(this, PracticePlayVideoActivity.class);
        startActivity(videoPlayer);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }
}


