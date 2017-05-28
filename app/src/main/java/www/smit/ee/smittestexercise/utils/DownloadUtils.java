package www.smit.ee.smittestexercise.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
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

/**
 * Created by Andreas on 23.04.2017.
 */

public class DownloadUtils {


    private Activity activity;
    private Context context;
    private File apkStorage = null;
    public File outputFile = null;
    private Button buttonText;
    private TextView infoText;
    private NumberProgressBar numberProgressBar;

    public DownloadUtils(Activity activity, Context context, Button buttonText, TextView infoText, NumberProgressBar numberProgressBar){
        this.activity = activity;
        this.context = context;
        this.buttonText = buttonText;
        this.infoText = infoText;
        this.numberProgressBar = numberProgressBar;
    }

    public void downloadAndWriteFile(String urlName, String fileName, String downloadDirectory){
        try {
            URL url = new URL(urlName + fileName);//Create Download URl
            HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
            c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
            c.connect();//connect the URL Connection

            //If Connection response is not OK then show Logs
            if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("Info", "Server returned HTTP " + c.getResponseCode()
                        + " " + c.getResponseMessage());

            }


            //Get File if SD card is present
            if (new SDCardUtils().isSDCardPresent()) {

                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/"
                                + downloadDirectory);
            } else
                Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

            //If File is not present create directory
            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e("Info", "Directory Created.");
            }
            outputFile = new File(apkStorage, fileName);//Create Output file in Main File

            //Create New File if not present
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                Log.e("Info", "File Created");
            }

            FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

            InputStream is = c.getInputStream();//Get InputStream for connection

            byte[] buffer = new byte[1024];//Set buffer type
            int data = 0;//init length
            int total = 0;
            final long fileSize = c.getContentLength();
            while ((data = is.read(buffer)) != -1) {
                total += data;
                fos.write(buffer, 0, data);//Write new file
                final int downloadedKiloBytes = total / 1024;
                final long percentage = (Long.valueOf(total) * 100) / fileSize;

                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        new ProgressBarUtils(numberProgressBar, infoText,downloadedKiloBytes, fileSize, (int) percentage).invoke();
                    }
                });


            }

            fos.flush();
            //Close all connection after doing task
            fos.close();
            is.close();

        } catch (final Exception e) {

            //Read exception if something went wrong
            e.printStackTrace();
            outputFile = null;
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Allalaadimise veateade: \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
