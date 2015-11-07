package co.handbellchoir.firebase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.orhanobut.logger.Logger;

/**
 * Created by Leonardo on 11/7/15.
 */
public class API {

    private static Firebase firebase = null;
    private static final Object lock = new Object();

    private static Firebase getInstance() {
        synchronized (lock) {
            if (firebase != null)
                return firebase;

            firebase = new Firebase("https://handbellchoir.firebaseio.com/");
            return firebase;
        }
    }

    public static void sendBell() {
        getInstance().child("message").setValue("Do you have data? You'll love Firebase.");
    }

    public static void setListener() {
        getInstance().child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Logger.e((String) snapshot.getValue());
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }
}
