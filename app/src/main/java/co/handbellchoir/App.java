package co.handbellchoir;

import android.app.Application;

import com.firebase.client.Firebase;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by Leonardo on 11/7/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);


        if (BuildConfig.DEBUG) {
            Logger.init(getString(R.string.app_name))
                    .setMethodCount(0)
                    .hideThreadInfo()
                    .setLogLevel(LogLevel.FULL)
                    .setMethodOffset(2);
        } else {
            Logger.init(getString(R.string.app_name))
                    .setLogLevel(LogLevel.NONE);
        }
    }
}
