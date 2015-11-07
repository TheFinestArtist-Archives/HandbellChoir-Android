package co.handbellchoir.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheFinestArtist on 3/8/15.
 */
public class OrientationTracker implements SensorEventListener {

    private SensorManager sensorManager;

    float[] lastAccels = new float[3];
    float[] lastMagFields = new float[3];
    private float[] rotationMatrix = new float[16];
    private float[] orientation = new float[4];

    LowPassFilter filterAzimuth = new LowPassFilter(0.03f);
    LowPassFilter filterPitch = new LowPassFilter(0.03f);
    LowPassFilter filterRoll = new LowPassFilter(0.03f);

    public interface OnOrientationChangedListener {
        void onOrientationChanged(float azimuth, float pitch, float roll);
    }

    private List<OnOrientationChangedListener> listeners = new ArrayList<>();

    public void addListener(OnOrientationChangedListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OnOrientationChangedListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                System.arraycopy(event.values, 0, lastAccels, 0, 3);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(event.values, 0, lastMagFields, 0, 3);
                break;
            default:
                return;
        }

        computeOrientation();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void computeOrientation() {
        if (SensorManager.getRotationMatrix(rotationMatrix, null, lastAccels, lastMagFields)) {
            SensorManager.getOrientation(rotationMatrix, orientation);

            float aizmuth = (float) Math.toDegrees(orientation[0]);
            float pitch = (float) Math.toDegrees(orientation[1]);
            float roll = (float) Math.toDegrees(orientation[2]);

            aizmuth = filterAzimuth.lowPass(aizmuth);
            pitch = filterPitch.lowPass(pitch);
            roll = filterRoll.lowPass(roll);

            for (OnOrientationChangedListener listener : listeners)
                if (listener != null)
                    listener.onOrientationChanged(aizmuth, pitch, roll);
        }
    }

    public class LowPassFilter {
        /*
         * time smoothing constant for low-pass filter 0 ≤ alpha ≤ 1 ; a smaller
         * value basically means more smoothing See: http://en.wikipedia.org/wiki
         * /Low-pass_filter#Discrete-time_realization
         */
        float ALPHA = 0f;
        float lastOutput = 0;

        public LowPassFilter(float ALPHA) {
            this.ALPHA = ALPHA;
        }

        public float lowPass(float input) {
            if (Math.abs(input - lastOutput) > 170) {
                lastOutput = input;
                return lastOutput;
            }
            lastOutput = lastOutput + ALPHA * (input - lastOutput);
            return lastOutput;
        }
    }

    public OrientationTracker(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void start() {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }
}
