package co.handbellchoir.activity;

import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.handbellchoir.R;
import co.handbellchoir.enums.Instrument;
import co.handbellchoir.enums.Note_Octave;
import co.handbellchoir.enums.Volume;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bell_tv)
    TextView bellTv;
    @Bind(R.id.volume_tv)
    TextView volumeTv;

    Instrument instrument = Instrument.DEFAULT;
    Note_Octave noteOctave = Note_Octave.DEFAULT;
    Volume volume = Volume.DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        bellTv.setText(instrument.getName() + " " + noteOctave.name());
        volumeTv.setText(volume.getName());
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
                                .items(Note_Octave.asStringList())
                                .itemsCallbackSingleChoice(noteOctave.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog materialDialog, View view, int which, CharSequence charSequence) {
                                        instrument = Instrument.fromOrdinal(selectedInstrument[0]);
                                        noteOctave = Note_Octave.fromOrdinal(which);
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

    @OnClick(R.id.volume_bt)
    public void selectVolume() {
        new MaterialDialog.Builder(MainActivity.this)
                .title("Select Volume")
                .items(Volume.asStringList())
                .itemsCallbackSingleChoice(volume.ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        volume = Volume.fromOrdinal(i);
                        volumeTv.setText(volume.getName());
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

}
