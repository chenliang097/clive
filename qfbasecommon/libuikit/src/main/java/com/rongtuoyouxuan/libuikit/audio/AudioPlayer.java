/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rongtuoyouxuan.libuikit.audio;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RawRes;

import com.blankj.utilcode.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AudioPlayer implements PlayerContract.Player, MediaPlayer.OnPreparedListener {

    private List<PlayerContract.PlayerCallback> actionsListeners = new ArrayList<>();

    private MediaPlayer mediaPlayer;
    private Timer timerProgress;
    private boolean isPrepared = false;
    private boolean isPause = false;
    private long seekPos = 0;
    private long pausePos = 0;
    private String dataSource = null;

    public static final String ASSET = "asset://";
    public static final String RAW = "raw://";

    private static AudioPlayer singleton = new AudioPlayer();

    public static AudioPlayer getInstance() {
        return singleton;
    }

    public static AudioPlayer create() {
        return new AudioPlayer();
    }

    private AudioPlayer() {
    }

    @Override
    public void addPlayerCallback(PlayerContract.PlayerCallback callback) {
        if (callback != null) {
            actionsListeners.add(callback);
        }
    }

    @Override
    public boolean removePlayerCallback(PlayerContract.PlayerCallback callback) {
        if (callback != null) {
            return actionsListeners.remove(callback);
        }
        return false;
    }

    @Override
    public void setData(String data) {
        if (mediaPlayer != null && dataSource != null && dataSource.equals(data)) {
            //Do nothing
        } else {
            dataSource = data;
            restartPlayer();
        }
    }

    @RawRes
    int rawResId;

    public void setRawData( @RawRes int rawResId) {
        this.rawResId = rawResId;
        setData(RAW);
    }

    public void setAssetData(String data) {
        setData(ASSET + data);
    }

    private void restartPlayer() {
        if (dataSource != null) {
            try {
                isPrepared = false;
                mediaPlayer = new MediaPlayer();
                Context context = Utils.getApp().getApplicationContext();
                if (context != null && dataSource.startsWith(RAW)) {
                    AssetFileDescriptor file = context.getResources().openRawResourceFd(rawResId);
                    mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(),
                            file.getLength());
                    file.close();
                } else if (context != null && dataSource.startsWith(ASSET)) {
                    AssetManager am = context.getResources().getAssets();
                    dataSource = dataSource.replace(ASSET, "");
                    AssetFileDescriptor file = am.openFd(dataSource);
                    mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                    file.close();
                } else {
                    mediaPlayer.setDataSource(dataSource);
                }
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            } catch (IOException | IllegalArgumentException | IllegalStateException | SecurityException e) {
                if (e.getMessage().contains("Permission denied")) {
                    onError(new Exception("PermissionDenied"));
                } else {
                    onError(new Exception("PlayerDataSource"));
                }
            }
        }
    }

    @Override
    public void play() {
        try {
            if (mediaPlayer != null) {
                isPause = false;
                if (!isPrepared) {
                    try {
                        mediaPlayer.setOnPreparedListener(this);
                        mediaPlayer.prepareAsync();
                    } catch (IllegalStateException ex) {
                        ex.printStackTrace();
                        restartPlayer();
                        mediaPlayer.setOnPreparedListener(this);
                        try {
                            mediaPlayer.prepareAsync();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            restartPlayer();
                        }
                    }
                } else {
//                    mediaPlayer.start();
                    start();
                    mediaPlayer.seekTo((int) pausePos);
                    onStartPlay();
                    mediaPlayer.setOnCompletionListener(mp -> {
                        stop();
                        onCompletionFinish();
                    });
                    if (timerProgress != null) {
                        timerProgress.cancel();
                        timerProgress = null;
                    }
                    timerCancel();
                    timerNew();

                }
                pausePos = 0;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void timerCancel() {
        if (timerProgress != null) {
            timerProgress.cancel();
            timerProgress.purge();
            timerProgress = null;
        }
    }

    private void timerNew() {
        timerProgress = new Timer();
        timerProgress.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        int curPos = mediaPlayer.getCurrentPosition();
                        onPlayProgress(curPos);
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }, 0, AudioConfig.VISUALIZATION_INTERVAL);

    }

    @Override
    public void playOrPause() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    pause();
                } else {
                    play();
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    private void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            mediaPlayer.setVolume(1f, 1f);
        }
    }

    @Override
    public void onPrepared(final MediaPlayer mp) {
        if (mediaPlayer != mp) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = mp;
        }
        onPreparePlay();
        isPrepared = true;
        start();
        mediaPlayer.seekTo((int) seekPos);
        onStartPlay();
        mediaPlayer.setOnCompletionListener(mp1 -> {
            stop();
            onCompletionFinish();
        });

        timerCancel();
        timerNew();
    }

    @Override
    public void seek(long mills) {
        seekPos = mills;
        if (isPause) {
            pausePos = mills;
        }
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo((int) seekPos);
                onSeek((int) seekPos);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
//        if (timerProgress != null) {
//            timerProgress.cancel();
//            timerProgress.purge();
//        }

        timerCancel();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                onPausePlay();
                seekPos = mediaPlayer.getCurrentPosition();
                isPause = true;
                pausePos = seekPos;
            }
        }
    }

    @Override
    public void stop() {
//        if (timerProgress != null) {
//            timerProgress.cancel();
//            timerProgress.purge();
//        }
        timerCancel();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.setOnCompletionListener(null);
            isPrepared = false;
            onStopPlay();
            mediaPlayer.getCurrentPosition();
            seekPos = 0;
        }
        isPause = false;
        pausePos = 0;

    }

    @Override
    public boolean isPlaying() {
        try {
            return mediaPlayer != null && mediaPlayer.isPlaying();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isPause() {
        return isPause;
    }

    @Override
    public long getPauseTime() {
        return seekPos;
    }

    @Override
    public void release() {
        stop();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPrepared = false;
        isPause = false;
        dataSource = null;
        actionsListeners.clear();
    }

    private void onPreparePlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onPreparePlay();
            }
        }
    }

    public void setSpeed(float speed) {
        try {
            if (mediaPlayer == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // API 23 （6.0）以上 ，通过设置Speed改变音乐的播放速率
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
                if (!mediaPlayer.isPlaying()) {
                    // 判断是否正在播放，未播放时，要在设置Speed后，暂停音乐播放
                    mediaPlayer.pause();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setLooping(Boolean looping){
        if (mediaPlayer != null)
            mediaPlayer.setLooping(looping);
    }

    public void setVolume(int leftVolume,int rightVolume){
        if (mediaPlayer != null){
            mediaPlayer.setVolume(leftVolume,rightVolume);
        }
    }

    private void onStartPlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onStartPlay();
            }
        }
    }

    private void onCompletionFinish() {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onCompletion();
            }
        }
    }

    private void onPlayProgress(long mills) {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onPlayProgress(mills);
            }
        }
    }

    private void onStopPlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = actionsListeners.size() - 1; i >= 0; i--) {
                actionsListeners.get(i).onStopPlay();
            }
        }
    }

    private void onPausePlay() {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onPausePlay();
            }
        }
    }

    private void onSeek(long mills) {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onSeek(mills);
            }
        }
    }

    private void onError(Exception throwable) {
        if (!actionsListeners.isEmpty()) {
            for (int i = 0; i < actionsListeners.size(); i++) {
                actionsListeners.get(i).onError(throwable);
            }
        }
    }
}
