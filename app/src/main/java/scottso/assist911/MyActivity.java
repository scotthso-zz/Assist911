package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity implements View.OnClickListener{

    private Button practice;
    private Button videos;

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
            break;

            case R.id.videos_button:
                goToVideos();
                break;
        }

    }

    public void goToPractice() {

        Intent keypad = new Intent(this, keypadActivity.class);
        startActivity(keypad);

    }

    public void goToVideos() {

        Intent video = new Intent(this, videosActivity.class);
        startActivity(video);
    }
}
