package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

/**
 * Created by scottso on 2014-08-27.
 */
public class PracticePlayVideoActivity extends Activity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_play_video);


        Button ok = (Button)findViewById(R.id.btn_ok);
        ok.setOnClickListener(this);

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

            case R.id.btn_ok:
                goToPractice();

        }
    }

    public void goToPractice() {
        Intent practice = new Intent(this, LockActivity.class);
        startActivity(practice);
    }
}
