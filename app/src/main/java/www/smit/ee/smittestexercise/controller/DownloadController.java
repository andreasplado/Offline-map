package www.smit.ee.smittestexercise.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.numberprogressbar.NumberProgressBar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import www.smit.ee.smittestexercise.MainActivity;
import www.smit.ee.smittestexercise.R;
import www.smit.ee.smittestexercise.utils.DownloadUtil;
import www.smit.ee.smittestexercise.utils.SDCardUtils;
import www.smit.ee.smittestexercise.utils.ProgressBarUtils;
import www.smit.ee.smittestexercise.utils.Urls;

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
    private DownloadUtil downloadUtil;

    private NumberProgressBar numberProgressBar;

    public DownloadController(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
        init();
    }

    private void init() {
        buttonText = (Button)activity.findViewById(R.id.download);
        infoText = (TextView)activity.findViewById(R.id.download_info);
        numberProgressBar = (NumberProgressBar)activity.findViewById(R.id.download_progress_bar);
        downloadUtil = new DownloadUtil(activity, context, buttonText, infoText, numberProgressBar);
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
            if (downloadUtil.outputFile != null) {
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
        downloadUtil.downloadAndWriteFile("http://aplado.5gbfree.com/", "tile.zip", "osmdroid");
        downloadUtil.downloadAndWriteFile("http://aplado.5gbfree.com/", "mapped_locations.json", "smit");
        return null;
    }

}
