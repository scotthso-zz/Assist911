package scottso.assist911.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import scottso.assist911.R;
import scottso.assist911.SimKidsActivity;

/**
 * Created by scottso on 14-11-16.
 */
public class InstructionActivity extends SimKidsActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        int videoResource = getResources().
                getIdentifier("instructional", "raw", getPackageName());
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
                goToMainMenu();
                break;
        }
    }

    public void goToMainMenu() {
        Intent mainMenu = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenu);
        finish();
    }

}
