package com.sukhajata.everyday;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SoundHelper  {
    private static SoundHelper mInstance;

    private Context context;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundIDs;
    private static MediaPlayer mediaPlayer;
    boolean plays = false;
    boolean loaded = false;
   // float actVolume, maxVolume;//, volume;
    AudioManager audioManager;
    int counter;

            /** Called when the activity is first created. */
    private  SoundHelper(Context _context) {
        context = _context;

        //SoundPool.Builder soundPoolBuilder = new SoundPool.Builder();
        //soundPoolBuilder.setMaxStreams(1);
        //soundPoolBuilder.setAudioAttributes(audioAttributes);
        //soundPool = soundPoolBuilder.build();

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //volume = actVolume / maxVolume;
        soundIDs = new HashMap<>();

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createSoundPoolWithBuilder();
        } else{
            createSoundPoolWithConstructor();
        }
        */

        //Hardware buttons setting to adjust the media sound
//        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // the counter will help us recognize the stream id of the sound played  now
  //      counter = 0;

        // Load the sounds
        /*
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0)
                {
                  //  Log.d("Loaded", String.valueOf(sampleId));
                }
            }
        });
        */

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createSoundPoolWithBuilder(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(5).build();
    }

    @SuppressWarnings("deprecation")
    protected void createSoundPoolWithConstructor(){
        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    public static SoundHelper getInstance(Context _context) {
        if (mInstance == null) {
            mInstance = new SoundHelper(_context);
            //_context.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        }

        return mInstance;
    }

    public void LoadAudioFiles(ArrayList<Phrase> phrases,
                               LoadAudioFilesCompletedListener listener){

        Phrase[] phraseArray = phrases.toArray(new Phrase[phrases.size()]);
        new LoadAudioFilesTask(listener).execute(phraseArray);

        /*
        soundIDs = new HashMap<Integer, Integer>();


        for(Phrase phrase : phrases) {
            String path = "raw/" + String.valueOf(phrase.fileName);

            try {
                int resId = context.getResources().getIdentifier(path, "raw",
                        context.getPackageName());

              //  Log.d("respath", path);
                Integer soundId = soundPool.load(context, resId, 5);
                soundIDs.put(new Integer(phrase.pid), new Integer(soundId));
            } catch (Exception ex) {
                //no audio file found
            }

        }
        */
    }

    public void LoadSearchAudioFiles(ArrayList<SearchResult> searchResults,
                                     LoadAudioFilesCompletedListener listener){
        SearchResult[] searchResultsArray = searchResults.toArray(
                new SearchResult[searchResults.size()]);
        new LoadSearchAudioFilesTask(listener).execute(searchResultsArray);


        /*
        soundIDs = new HashMap<Integer, Integer>();

        for(SearchResult searchResult : searchResults) {
           // if (searchResult.fileName != null && searchResult.fileName != "") {
            String path = "raw/" + String.valueOf(searchResult.fileName);

            try {
                int resId = context.getResources().getIdentifier(path, "raw", context.getPackageName());
              //  Log.d("respathsearch", path);
                Integer soundId = soundPool.load(context, resId, 5);
                soundIDs.put(new Integer(searchResult.pid), new Integer(soundId));
            } catch (Exception ex) {
                //could not find audio file
            }
          //  }
        }
           */
        /*
        Iterator iterator = soundIDs.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>) iterator.next();
            Log.d("soundid", pair.getKey().toString() + ", " + pair.getValue().toString());
        }
        */
    }


    public void playPhrase(Phrase phrase) {
      //  Log.d("play", phrase.firstLanguage);
        String path = "raw/" + String.valueOf(phrase.fileName);
        int resId = context
                .getResources()
                .getIdentifier(path, "raw", context.getPackageName());
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, resId);
        mediaPlayer.start();

        /*
        if (soundIDs.containsKey(phrase.pid)) {

            int soundId = soundIDs.get(phrase.pid);
            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int streamId = soundPool.play(soundId, volume, volume, 1, 0, 1f);

           // Toast.makeText(context, "streamId = " + String.valueOf(streamId), Toast.LENGTH_SHORT).show();

        } else {
            //Toast.makeText(context, phrase.secondLanguage, Toast.LENGTH_SHORT).show();
        }
        */

    }

    public void playSearchResult(SearchResult searchResult) {
       // Log.d("select", searchResult.firstLanguage);
        //Toast.makeText(context, searchResult.firstLanguage, Toast.LENGTH_SHORT);
       // if (searchResult.fileName != null){

        String path = "raw/" + String.valueOf(searchResult.fileName);
        int resId = context
                .getResources()
                .getIdentifier(path, "raw", context.getPackageName());
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = MediaPlayer.create(context, resId);
        mediaPlayer.start();

        /*
            if (soundIDs.containsKey(searchResult.pid))
            {
                //audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                //Log.d("soundid retrieve", "contains key " + String.valueOf(phrase.pid));
                //int soundId = soundIDs.get(searchResult.pid);
                //float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                //soundPool.play(soundId, volume, volume, 1, 0, 1f);

            }
            */



    }

    private class LoadSearchAudioFilesTask extends AsyncTask<SearchResult, Void, Long>{
        private LoadAudioFilesCompletedListener mListener;

        public LoadSearchAudioFilesTask(LoadAudioFilesCompletedListener listener) {
            mListener = listener;
        }

        protected Long doInBackground(SearchResult... searchResults) {
            int count = searchResults.length;
            long totalSize = 0;
            soundIDs = new HashMap<>();

            if (soundPool != null) {
                soundPool.release();
                soundPool = null;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                createSoundPoolWithBuilder();
            } else{
                createSoundPoolWithConstructor();
            }

            for (int i = 0; i < count; i++) {
                String path = "raw/" + String.valueOf(searchResults[i].fileName);

                try {
                    int resId = context
                            .getResources()
                            .getIdentifier(path, "raw", context.getPackageName());

                    Integer soundId = soundPool.load(context, resId, 5);
                    soundIDs.put(new Integer(searchResults[i].pid), new Integer(soundId));
                } catch (Exception ex) {
                    //no audio file found
                }
                totalSize++;
            }

            return totalSize;
        }

        protected void onPostExecute(Long result) {
            mListener.onLoadCompleted();
        }

    }

    private class LoadAudioFilesTask extends AsyncTask<Phrase, Void, Long> {
        private LoadAudioFilesCompletedListener mListener;

        public LoadAudioFilesTask(LoadAudioFilesCompletedListener listener) {
            mListener = listener;
        }

        protected Long doInBackground(Phrase... phrases) {
            int count = phrases.length;
            long totalSize = 0;
            soundIDs = new HashMap<Integer, Integer>();
            if (soundPool != null) {
                soundPool.release();
                soundPool = null;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                createSoundPoolWithBuilder();
            } else{
                createSoundPoolWithConstructor();
            }

            for (int i = 0; i < count; i++) {
                String path = "raw/" + String.valueOf(phrases[i].fileName);

                try {
                    int resId = context
                            .getResources()
                            .getIdentifier(path, "raw", context.getPackageName());

                    Integer soundId = soundPool.load(context, resId, 5);
                    soundIDs.put(new Integer(phrases[i].pid), new Integer(soundId));
                } catch (Exception ex) {
                    //no audio file found
                }
                totalSize++;
            }

            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            mListener.onLoadCompleted();
        }
    }


    public interface LoadAudioFilesCompletedListener {
        void onLoadCompleted();
    }


    public void pauseSound(View v) {
        /*
        if (plays) {
            soundPool.pause(soundID);
            //soundID = soundPool.load(this, R.raw.beep, counter);
            Toast.makeText(context, "Pause sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
        */
    }

    public void stopSound(View v) {
        /*
        if (plays) {
            soundPool.stop(soundID);
            //soundID = soundPool.load(this, R.raw.beep, counter);
            Toast.makeText(context, "Stop sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
        */
    }
}

