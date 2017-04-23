package www.smit.ee.smittestexercise.utils;

import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

/**
 * Created by Andreas on 23.04.2017.
 */

public class ProgressBarUtils {

    private NumberProgressBar numberProgressBar;
    private TextView infoText;

    private int downloadedKiloBytes;
    private long fileSize;
    private int percentage;

    public ProgressBarUtils(NumberProgressBar numberProgressBar, TextView infoText,
                            int downloadedKiloBytes, long fileSize, int percentage) {
        this.numberProgressBar = numberProgressBar;
        this.infoText = infoText;
        this.downloadedKiloBytes = downloadedKiloBytes;
        this.fileSize = fileSize;
        this.percentage = percentage;
    }

    public void invoke() {
        int convertedDownloadedKiloBytes = downloadedKiloBytes;
        long convertedTotalSize = fileSize;
        String unit = "";
        String totalUnit ="";


        //sets the progressbar
        //calculate the percentage
        //TODO:


        numberProgressBar.setProgress(percentage);


        if(downloadedKiloBytes > 1000){
            convertedDownloadedKiloBytes = downloadedKiloBytes / 1000;
            unit = "MB";
        }

        if(downloadedKiloBytes > (int)Math.pow(10, 9)){
            convertedDownloadedKiloBytes = downloadedKiloBytes / (int)Math.pow(10, 9);
            unit = "GB";
        }
        if(fileSize > (int)Math.pow(10, 9)){
            convertedTotalSize = fileSize / (int)Math.pow(10, 9);
            totalUnit = "GB";

        }
        infoText.setText("Palun oota... \n" + convertedDownloadedKiloBytes + unit + "/" + convertedTotalSize + totalUnit);
    }
}
