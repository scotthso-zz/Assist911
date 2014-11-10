package scottso.assist911;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

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
                if(VideosActivity.EMERGENCY == true) {
                    goToPractice();
                } else {
                    goToResults();
                }
                break;

            case R.id.btn_not_emergency:
                if (!VideosActivity.EMERGENCY) {
                    MainMenuActivity.CURRENT_TRY_SCORE = 8; // success!
                    LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
                    LoginActivity.EDITOR.commit();
                    goToResults();
                } else {
                    goToResults();
                }
                break;
        }
    }

    public void goToResults() {
        Intent results = new Intent(this, ResultsActivity.class);
        startActivity(results);
        finish();
    }

    public void goToPractice() {
        Intent practice = new Intent(this, LockActivity.class);
        startActivity(practice);
        finish();
        MainMenuActivity.CURRENT_TRY_SCORE++;
        LoginActivity.EDITOR.putInt(LoginActivity.CURRENT_TRY_SCORE, MainMenuActivity.CURRENT_TRY_SCORE);
        LoginActivity.EDITOR.commit();
    }
}
