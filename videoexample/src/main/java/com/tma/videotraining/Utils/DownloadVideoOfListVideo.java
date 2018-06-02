package com.tma.videotraining.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tma.videotraining.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

/**
 * Created by nbhung on 7/12/2017.
 */

public class DownloadVideoOfListVideo extends AsyncTask<String, String, String> {
    private Context mContext;
    private ProgressBar mProgressBar;
    private TextView tvcountFile;
    private boolean statusStop = false;
    private Button down, pause, resume, btncancel;
    private String nameofFile;

    public DownloadVideoOfListVideo(Context mContext, ProgressBar mProgressBar, TextView tvcountFile, Button down, Button pause, Button resume, Button btncancel) {
        this.mContext = mContext;
        this.mProgressBar = mProgressBar;
        this.tvcountFile = tvcountFile;
        this.down = down;
        this.pause = pause;
        this.resume = resume;
        this.btncancel = btncancel;
    }

    public void pauseVideo() {
        statusStop = true;
    }

    public void resumeVideo() {
        statusStop = false;
    }

    private void deleteFile() {
        File delete = new File(nameofFile);
        delete.delete();

    }


    @Override
    protected String doInBackground(String... strings) {
        int count = 0;
        String path = "";

        URL url;
        try {
            url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            int fileLength = urlConnection.getContentLength();


            File ff1 = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                File tam2[] = mContext.getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS);
                ff1 = tam2[tam2.length - 1];
                Log.e("filedownloads", ff1.getAbsolutePath());
                if (!ff1.exists()) {
                    ff1.createNewFile();
                }
                path = ff1.getAbsolutePath();
            }
            Random rand = new Random();
            int n = rand.nextInt(5000) + 1;

            nameofFile = ff1 + "/videonew" + n + ".mp4";
            OutputStream output = new FileOutputStream(nameofFile);
            InputStream input = urlConnection.getInputStream();

            byte data[] = new byte[2014];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    deleteFile();
                    break;
                }
                while (statusStop) {
                    if (isCancelled()) {
                        deleteFile();
                        break;
                    }
                }
                total += count;
                publishProgress("" + (int) ((total * 100) / fileLength));
                output.write(data, 0, count);

            }
            output.close();
            input.close();


        } catch (IOException e) {
            e.printStackTrace();
        }


//                File xmlFile = new File(Environment
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                        + "/videonew.mp4");
//                if (!xmlFile.exists()) {
//                    xmlFile.createNewFile();
//                }
//                Log.e("xmlfile", xmlFile.getPath());
        return path;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        String tam=values[0]+"%";
        tvcountFile.setText(tam);
        mProgressBar.setProgress(Integer.parseInt(values[0]));

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        tvcountFile.setText(R.string.done);
        tvcountFile.setTextColor(ContextCompat.getColor(mContext, R.color.textDone));
        mProgressBar.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.INVISIBLE);
        down.setVisibility(View.VISIBLE);
        resume.setVisibility(View.INVISIBLE);
        btncancel.setEnabled(false);

    }


}
