package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by scottso on 2014-08-27.
 */
public class PracticePlayVideoActivity extends SimKidsActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_play_video);


        Button ok = (Button)findViewById(R.id.btn_emergency);
        ok.setOnClickListener(this);

        Button no = (Button)findViewById(R.id.btn_not_emergency);
        no.setOnClickListener(this);

        int videoResource = getResources().
                getIdentifier(VideosActivity.VIDEO_NAME, "raw", getPackageName());
        String uri = "android.resource://" + getPackageName() + "/" + videoResource;

        VideoView videoView = (VideoView) findViewById(R.id.VideoView);
        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_emergency:
                goToPractice();
                break;

            case R.id.btn_not_emergency:
               goToMainMenu();
                break;

        }

    }


    public void goToPractice() {
        Intent practice = new Intent(this, LockActivity.class);
        startActivity(practice);
    }

    public void goToMainMenu() {
        Toast toast = Toast.makeText(getApplicationContext(), "You are correct!", Toast.LENGTH_SHORT);
        toast.show();
        Intent mainMenu = new Intent(this, MyActivity.class);
        startActivity(mainMenu);

    }
}
