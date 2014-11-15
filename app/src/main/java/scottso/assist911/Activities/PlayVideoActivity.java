package scottso.assist911.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

public class PlayVideoActivity extends SimKidsActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        int videoResource = getResources().
                getIdentifier(VideosActivity.VIDEO_NAME, "raw", getPackageName());
        String uri = "android.resource://" + getPackageName() + "/" + videoResource;

        VideoView videoView = (VideoView) findViewById(R.id.VideoView);
        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();

       
        Button back = (Button) findViewById(R.id.btn_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                goToVideoPlayerMenu();
                break;
        }
    }

    public void goToVideoPlayerMenu() {
        Intent videoPlayer = new Intent(this, VideosActivity.class);
        startActivity(videoPlayer);
        finish();
    }
}
