package co.handbellchoir.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.handbellchoir.R;
import co.handbellchoir.audio.MidiPlayer;
import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.NoteOctave;
import co.handbellchoir.enums.Score;
import co.handbellchoir.enums.Shake;
import co.handbellchoir.enums.Sound;
import co.handbellchoir.events.OnShaked;
import co.handbellchoir.firebase.API;
import co.handbellchoir.utils.AudioUtil;

public class MainActivity extends HeroActivity implements API.OnPlayListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.play_bt)
    Button playBt;
    @Bind(R.id.instrument_tv)
    TextView instrumentTv;
    @Bind(R.id.sound_tv)
    TextView soundTv;
    @Bind(R.id.note_octave_tv)
    TextView noteOctaveTv;
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

        instrumentTv.setText(instrument.getName());
        soundTv.setText(sound.getName());
        noteOctaveTv.setText(noteOctave.name());
        shakeTv.setText(shake.name());
        playBt.setSoundEffectsEnabled(false);

        API.setListener(this);
//        API.findEmptyNoteOctave(new API.OnFoundEmptyNoteOctaveListener() {
//            @Override
//            public void onFound(NoteOctave noteOctave) {
//                MainActivity.this.noteOctave = noteOctave;
//                API.assignNoteOctave(noteOctave, true);
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        API.assignNoteOctave(noteOctave, false);
    }

    @OnClick(R.id.instrument_bt)
    public void selectInstrument() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("Select Instrument")
                .items(Instrument.asStringList())
                .itemsCallbackSingleChoice(instrument.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int which, CharSequence charSequence) {
                        instrument = Instrument.fromOrdinal(which);
                        instrumentTv.setText(instrument.getName());
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

    @OnClick(R.id.note_octave_bt)
    public void selectNoteOctave() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("Select Note & Octave")
                .items(NoteOctave.asStringList())
                .itemsCallbackSingleChoice(noteOctave.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int which, CharSequence charSequence) {
                        noteOctave = NoteOctave.fromOrdinal(which);
                        noteOctaveTv.setText(noteOctave.name());
                        return true;
                    }
                })
//                .adapter(new NoteOctaveAdapter(this),
//                        new MaterialDialog.ListCallback() {
//                            @Override
//                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
//                                API.assignNoteOctave(noteOctave, false);
//
//                                noteOctave = NoteOctave.fromOrdinal(which);
//                                noteOctaveTv.setText(noteOctave.name());
//
//                                API.assignNoteOctave(noteOctave, true);
//                            }
//                        })
                .positiveText(getString(R.string.choose))
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
        showEffect(Score.PERFECT);
    }

    public void onEvent(OnShaked event) {
        switch (shake) {
            case On:
                playSelf();
                break;
            case Off:
                break;
        }
    }

    private void playSelf() {
        API.play(instrument, noteOctave);
        switch (sound) {
            case SILENT:
                break;
            case MY_SELF:
                MidiPlayer.play(this, instrument, noteOctave);
                break;
            case ALL:
                break;
        }
    }

    @Override
    public void onPlay(Instrument instrument, NoteOctave noteOctave) {
        if (instrument == null)
            instrument = this.instrument;
//        Logger.e("onPlay: " + instrument.getName() + ", NoteOctave: " + noteOctave.name());
        switch (sound) {
            case SILENT:
            case MY_SELF:
                break;
            case ALL:
                MidiPlayer.play(this, instrument, noteOctave);
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
