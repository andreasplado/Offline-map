package www.smit.ee.smittestexercise;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import www.smit.ee.smittestexercise.controller.DownloadController;


/**
 * Created by Andreas on 22.04.2017.
 */

public class DownloadActivity extends Activity {

    private Button downloadBtn;
    private TextView downloadInfo;
    private DownloadController downloadController;
    private NumberProgressBar downloadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        downloadBtn = (Button)findViewById(R.id.download);
        downloadProgressBar = (NumberProgressBar)findViewById(R.id.download_progress_bar);
        downloadController = new DownloadController(this, this);
        addOnClickListeners();

    }

    protected void addOnClickListeners(){
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadProgressBar.setVisibility(View.VISIBLE);
                downloadController.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }




}
