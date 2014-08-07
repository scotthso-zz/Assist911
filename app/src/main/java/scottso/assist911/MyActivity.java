package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity implements View.OnClickListener{

    private Button practice;
    private Button videos;

    private int timesOpenedTemp;
    private int timesOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        practice = (Button) this.findViewById(R.id.practice_button);
        practice.setOnClickListener(this);

        videos = (Button) this.findViewById(R.id.videos_button);
        videos.setOnClickListener(this);



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
                SharedPreferences settings = getApplicationContext().getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor = settings.edit();

                timesOpenedTemp = settings.getInt("timesOpened",0);

                if (timesOpenedTemp == 0) {
                    timesOpenedTemp++;
                    timesOpenedTemp = 1;
                    editor.putInt("timesOpened", timesOpenedTemp);
                    editor.apply();
                    editor.commit();
                    timesOpened = settings.getInt("timesOpened", 0);
                    System.out.println("It equals 0");

                }else {
                    timesOpened++;
                    editor.putInt("timesOpened", timesOpened);
                    editor.apply();
                    editor.commit();
                    timesOpened = settings.getInt("timesOpened", 0);
                }

                System.out.println(timesOpened);

            break;

            case R.id.videos_button:
                goToVideos();
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
}
