package www.smit.ee.smittestexercise.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import www.smit.ee.smittestexercise.data.JSONData;

/**
 * Created by Andreas on 22.04.2017.
 */

public class SDCardUtils {
    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public void readLocationData(String fileName){
        File sdcard = Environment.getExternalStorageDirectory();

        //Get the text file
        File file = new File(sdcard.getAbsolutePath() +"/smit/", fileName);

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            //BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF8"));

            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            JSONData.setJsonData(text.toString());
            Log.e("JSON data:", JSONData.getJsonData());
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

    }
}
