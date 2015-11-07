package co.handbellchoir.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import co.handbellchoir.enums.Shake;
import co.handbellchoir.sensors.OrientationTracker;

/**
 * Created by Leonardo on 11/7/15.
 */
public class SensorActivity extends AppCompatActivity implements OrientationTracker.OnOrientationChangedListener {

    OrientationTracker orientationTracker;
    Shake shake = Shake.DEFAULT;
    float pitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientationTracker = new OrientationTracker(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        orientationTracker.addListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        orientationTracker.removeListener(this);
    }

    @Override
    public void onOrientationChanged(float azimuth, float pitch, float roll) {

    }
}
