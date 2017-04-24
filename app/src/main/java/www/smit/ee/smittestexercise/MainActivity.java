package www.smit.ee.smittestexercise;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.views.MapView;

import www.smit.ee.smittestexercise.controller.MainController;

import static www.smit.ee.smittestexercise.utils.Constants.PERMISSION_REQUEST_CODE;

public class MainActivity extends Activity {

    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainController = new MainController(this, this);
        mainController.requestPermissonCheck();
        mainController.initialCheck();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, getString(R.string.permission_granted_now_you), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, getString(R.string.permission_denied_now_you), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 5:
                Toast.makeText(this, "See töötab!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
