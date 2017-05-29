package www.smit.ee.smittestexercise.controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.numberprogressbar.NumberProgressBar;
import java.io.File;

import www.smit.ee.smittestexercise.MainActivity;
import www.smit.ee.smittestexercise.R;
import www.smit.ee.smittestexercise.utils.DownloadUtils;

/**
 * Created by Andreas on 22.04.2017.
 */

public class DownloadController extends AsyncTask<String, String, String> {

    private Activity activity;
    private Context context;
    private File apkStorage = null;
    private File outputFile = null;
    private Button buttonText;
    private TextView infoText;
    private DownloadUtils downloadUtils;

    private NumberProgressBar numberProgressBar;

    public DownloadController(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
        init();
        requestPermissions();
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
        );

    }

    private void init() {
        buttonText = (Button)activity.findViewById(R.id.download);
        infoText = (TextView)activity.findViewById(R.id.download_info);
        numberProgressBar = (NumberProgressBar)activity.findViewById(R.id.download_progress_bar);
        downloadUtils = new DownloadUtils(activity, context, buttonText, infoText, numberProgressBar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        buttonText.setEnabled(false);
        buttonText.setText(context.getString(R.string.download_started));
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            if (downloadUtils.outputFile != null) {
                buttonText.setEnabled(true);
                buttonText.setText(context.getString(R.string.download_finished));//If Download completed then change button text
                numberProgressBar.setProgress(0);
                outputFile = null;
                Intent i = new Intent(context, MainActivity.class);
                activity.startActivity(i);
            } else {
                buttonText.setText(context.getString(R.string.download_failed));//If download failed change button text
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonText.setEnabled(true);
                        buttonText.setText(context.getString(R.string.download_again));//Change button text again after 3sec

                    }
                }, 3000);

                Toast.makeText(context, context.getString(R.string.download_failed), Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            e.printStackTrace();

            //Change button text if exception occurs
            buttonText.setText(context.getString(R.string.download_failed));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttonText.setEnabled(true);
                    buttonText.setText(context.getString(R.string.download_again));
                }
            }, 3000);
            Log.e("Info", "Download Failed with Exception - " + e.getLocalizedMessage());
            Toast.makeText(context, context.getString(R.string.download_exception) + ": "+
                            e.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();

        }


        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        downloadUtils.downloadAndWriteFile("http://ec2-35-162-160-209.us-west-2.compute.amazonaws.com:8080/static_files/28052017/", "tiles.zip", "osmdroid");
        downloadUtils.downloadAndWriteFile("http://ec2-35-162-160-209.us-west-2.compute.amazonaws.com:8080/static_files/28052017/", "mapped_locations.json", "smit");
        return null;
    }

}
