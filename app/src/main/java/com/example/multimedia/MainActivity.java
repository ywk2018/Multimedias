package com.example.multimedia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.video_view)
    VideoView videoView;
    private MediaPlayer mMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMediaPlayer = new MediaPlayer();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initPlayMedia();
            initVideoPath();
        }
    }

    private void initVideoPath() {
        File file = new File(Environment.getExternalStorageDirectory(), "movie.mp4");
        videoView.setVideoPath(file.getPath());
    }

    @OnClick({R.id.btn_start_play, R.id.btn_pause, R.id.btn_stop, R.id.btn_start_video, R.id.btn_pause_video, R.id.btn_stop_video})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_play:
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();         //开始播放
                }
                break;
            case R.id.btn_pause:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
            case R.id.btn_stop:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                break;
            case R.id.btn_start_video:
                if (!videoView.isPlaying()) {
                    videoView.start();         //开始播放
                }
                break;
            case R.id.btn_pause_video:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.btn_stop_video:
                if (videoView.isPlaying()) {
                    videoView.resume();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPlayMedia();
                initVideoPath();
            } else {
                Toast.makeText(this, "我们需要读取内存卡的权限，否则程序无法正常运行", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initPlayMedia() {
        File file = new File(Environment.getExternalStorageDirectory(), "misic.mp3");
        try {
            mMediaPlayer.setDataSource(file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        if (videoView != null) {
            videoView.suspend();
        }
    }

}
