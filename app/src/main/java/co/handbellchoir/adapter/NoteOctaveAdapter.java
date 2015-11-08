package co.handbellchoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import co.handbellchoir.R;
import co.handbellchoir.models.Note;

/**
 * Created by Leonardo on 11/8/15.
 */
public class NoteOctaveAdapter extends ArrayAdapter<Note> {

    public NoteOctaveAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_octave_item, null);
        return super.getView(position, convertView, parent);
    }
}
