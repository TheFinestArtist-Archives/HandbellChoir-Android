package co.handbellchoir.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.handbellchoir.R;
import co.handbellchoir.audio.AudioPlayer;
import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.NoteOctave;
import co.handbellchoir.enums.Shake;
import co.handbellchoir.enums.Sound;
import co.handbellchoir.events.OnShaked;
import co.handbellchoir.firebase.API;
import co.handbellchoir.utils.AudioUtil;

public class MainActivity extends SensorActivity implements API.OnPlayListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.play_bt)
    ImageButton playBt;
    @Bind(R.id.bell_tv)
    TextView bellTv;
    @Bind(R.id.sound_tv)
    TextView soundTv;
    @Bind(R.id.shake_tv)
    TextView shakeTv;

    Instrument instrument = Instrument.DEFAULT;
    NoteOctave noteOctave = NoteOctave.DEFAULT;
    Sound sound = Sound.DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        bellTv.setText(instrument.getName() + " " + noteOctave.name());
        soundTv.setText(sound.getName());
        shakeTv.setText(shake.name());
        playBt.setSoundEffectsEnabled(false);

        API.setListener(this);
    }

    @OnClick(R.id.bell_bt)
    public void selectBell() {
        final int[] selectedInstrument = new int[1];
        new MaterialDialog.Builder(MainActivity.this)
                .title("Select Instrument")
                .items(Instrument.asStringList())
                .itemsCallbackSingleChoice(instrument.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int which, CharSequence charSequence) {
                        selectedInstrument[0] = which;

                        new MaterialDialog.Builder(MainActivity.this)
                                .title("Select Note & Octave")
                                .items(NoteOctave.asStringList())
                                .itemsCallbackSingleChoice(noteOctave.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog materialDialog, View view, int which, CharSequence charSequence) {
                                        instrument = Instrument.fromOrdinal(selectedInstrument[0]);
                                        noteOctave = NoteOctave.fromOrdinal(which);
                                        bellTv.setText(instrument.getName() + " " + noteOctave.name());
                                        return true;
                                    }
                                })
                                .positiveText(getString(R.string.choose))
                                .show();

                        return true;
                    }
                })
                .positiveText(getString(R.string.choose))
                .show();
    }

    @OnClick(R.id.sound_bt)
    public void selectSound() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("Select Sound")
                .items(Sound.asStringList())
                .itemsCallbackSingleChoice(sound.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        sound = Sound.fromOrdinal(i);
                        soundTv.setText(sound.getName());
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    @OnClick(R.id.shake_bt)
    public void shakeOn() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("Select Shake")
                .items(Shake.asStringList())
                .itemsCallbackSingleChoice(shake.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        shake = Shake.fromOrdinal(i);
                        shakeTv.setText(shake.name());
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    @OnClick(R.id.play_bt)
    public void playClicked() {
        playSelf();
    }

    public void onEvent(OnShaked event) {
        switch (shake){
            case ON:
                playSelf();
                break;
            case OFF:
                break;
        }
    }

    private void playSelf() {
        API.play(instrument, noteOctave);
        switch (sound) {
            case SILENT:
                break;
            case MY_SELF:
                AudioPlayer.play(this, instrument, noteOctave);
                break;
            case ALL:
                break;
        }
    }

    @Override
    public void onPlay(Instrument instrument, NoteOctave noteOctave) {
        Logger.e("onPlay: " + instrument.getName() + ", NoteOctave: " + noteOctave.name());
        switch (sound) {
            case SILENT:
            case MY_SELF:
                break;
            case ALL:
                AudioPlayer.play(this, instrument, noteOctave);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            AudioUtil.adjustMusicVolume(getApplicationContext(), true, true);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            AudioUtil.adjustMusicVolume(getApplicationContext(), false, true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
