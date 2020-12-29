package sa.videolinks.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import sa.videolinks.R;

/**
 * Created by ament on 04/04/2020.
 */

public class VideoPlayer extends Activity {
    ProgressDialog pDialog;
    VideoView videoview;
    String vid_url ="https://www.youtube.com/watch?v=QnOcXQL2wDA&t=18s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Intent intent = getIntent();
        // String vid_url = intent.getStringExtra("video_url");
        videoview = (VideoView) findViewById(R.id.videoView);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Video Stream");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
        try {
            MediaController mediacontroller = new MediaController(this);
            mediacontroller.setAnchorView(videoview);
            Uri video = Uri.parse(vid_url);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                //mp.start();

                VideoPlayer.this.pDialog.dismiss();
                VideoPlayer.this.videoview.start();
                

            }
        });
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });


    }
}
