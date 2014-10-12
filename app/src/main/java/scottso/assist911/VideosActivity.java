package scottso.assist911;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by scottso on 2014-07-30.
 */
public class VideosActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static String VIDEO_NAME;
    public static Boolean EMERGENCY;

    private Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        spinner = (Spinner) findViewById(R.id.videos_spinner);
        spinner.setOnItemSelectedListener(this);

        Button watch = (Button) findViewById(R.id.btn_watch);
        watch.setOnClickListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.videos_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {


        System.out.println(spinner.getSelectedItem());


    }





    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View v) {

     switch(v.getId()) {

         case R.id.btn_watch:

             if (String.valueOf(spinner.getSelectedItem()).equals("Flames")) {
                 VIDEO_NAME = "flame";
             } else if (String.valueOf(spinner.getSelectedItem()).equals("Smoke")) {
                 VIDEO_NAME = "smoke";
             } else if (String.valueOf(spinner.getSelectedItem()).equals("Car Thief")) {
                 VIDEO_NAME = "car";
             } else if (String.valueOf(spinner.getSelectedItem()).equals("Passing Out")) {
                 VIDEO_NAME = "passed";
             }

//             System.out.println(VIDEO_NAME);
             goToVideoPlayer();

     }
    }

    public void goToVideoPlayer() {

        Intent videoPlayer = new Intent(this, PlayVideoActivity.class);
        startActivity(videoPlayer);

    }


}
