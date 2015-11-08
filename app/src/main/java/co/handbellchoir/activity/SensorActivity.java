package co.handbellchoir.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

import co.handbellchoir.enums.Shake;
import co.handbellchoir.events.OnShaked;
import co.handbellchoir.sensors.OrientationTracker;
import de.greenrobot.event.EventBus;

/**
 * Created by Leonardo on 11/7/15.
 */
public class SensorActivity extends AppCompatActivity implements OrientationTracker.OnOrientationChangedListener {

    OrientationTracker orientationTracker;
    Shake shake = Shake.DEFAULT;

    float pitch;
    STATUS status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientationTracker = new OrientationTracker(this);
        orientationTracker.addListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orientationTracker.removeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pitch = Float.MAX_VALUE;
        orientationTracker.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        orientationTracker.stop();
        super.onStop();
    }

    @Override
    public void onOrientationChanged(float azimuth, float pitch, float roll) {
        if (pitch != Float.MAX_VALUE) {
            if ((this.status == STATUS.MIDDLE
                    && (getStatusFromPitch(pitch) == STATUS.DOWN || getStatusFromPitch(pitch) == STATUS.UP))
                    || this.status == STATUS.DOWN && getStatusFromPitch(pitch) == STATUS.UP
                    || this.status == STATUS.UP && getStatusFromPitch(pitch) == STATUS.DOWN) {
                EventBus.getDefault().post(new OnShaked());
                Logger.e("Shaked");
            }
        }

        this.pitch = pitch;
        switch (getStatusFromPitch(pitch)) {
            case DOWN:
                status = STATUS.DOWN;
                break;
            case DOWN_MIDDLE:
            case MIDDLE:
            case UP_MIDDLE:
                status = STATUS.MIDDLE;
                break;
            case UP:
                status = STATUS.UP;
                break;
        }
    }

    private STATUS getStatusFromPitch(float pitch) {
        if (pitch > -30)
            return STATUS.DOWN;
        else if (pitch > -35)
            return STATUS.DOWN_MIDDLE;
        else if (pitch > -40)
            return STATUS.MIDDLE;
        else if (pitch > -45)
            return STATUS.UP_MIDDLE;
        else
            return STATUS.UP;
    }

    enum STATUS {
        DOWN, DOWN_MIDDLE, MIDDLE, UP_MIDDLE, UP
    }
}
