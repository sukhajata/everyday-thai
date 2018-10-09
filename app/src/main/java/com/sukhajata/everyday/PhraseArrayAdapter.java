package com.sukhajata.everyday;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PhraseArrayAdapter extends ArrayAdapter<Phrase>
{
    private final Context context;
    private ArrayList<Phrase> phrases;
    private int subCategoryId;

    public int getSubCategoryId() {
        return subCategoryId;
    }
    public void setSubCategory(int suCatId) {
        subCategoryId = suCatId;
    }

    public PhraseArrayAdapter(Context _context, ArrayList<Phrase> _phrases) {
        super(_context, R.layout.phrase_item, _phrases);
        this.context = _context;
        this.phrases = _phrases;


    }

    /**
     * {@inheritDoc}
     *
     * @param position
     */
    @Override
    public Phrase getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.phrase_item, parent, false);

        final Phrase _phrase = phrases.get(position);

        if (_phrase != null)
        {
            TextView txtSL = (TextView)rowView.findViewById(R.id.secondLanguage);
            TextView txtFL = (TextView)rowView.findViewById(R.id.firstLanguage);
            TextView txtN = (TextView)rowView.findViewById(R.id.notes);
            //ImageView speaker = (ImageView)rowView.findViewById(R.id.speaker);
            //xspeaker.setTag(_phrase);
            /*
            speaker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SoundHelper.getInstance(context).playPhrase(_phrase);
                }
            });
            */

            txtSL.setText(_phrase.secondLanguage + "\n" + _phrase.firstLanguage);
            String secondTier = _phrase.romanisation;
            SpannableString spannableString;
            if (_phrase.literalTranslation != null && !_phrase.literalTranslation.equals(""))
            {
                int idxItalicsStart = secondTier.length() + 2; //for " ("
                int idxItalicsEnd = idxItalicsStart + _phrase.literalTranslation.length();
                secondTier = secondTier + "\n(" + _phrase.literalTranslation + ")";
                spannableString = new SpannableString(secondTier);
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC), idxItalicsStart,
                        idxItalicsEnd, 0);
            }
            else
            {
                spannableString = new SpannableString(secondTier);
            }
            txtFL.setText(spannableString);
            //put notes in italics
            if (_phrase.notes != null && !_phrase.notes.equals("")) {
                android.text.Spanned notes = Html.fromHtml("<i>" + _phrase.notes + "</i>");
                txtN.setText(notes);
            }
            else {
                txtN.setVisibility(View.GONE);
            }



            ImageView fav = (ImageView)rowView.findViewById(R.id.favourite);
            fav.setTag(_phrase);
            if (_phrase.isFavourite) {
                fav.setImageResource(R.drawable.ic_favourite_active);
            } else {
                fav.setImageResource(R.drawable.ic_favourite);
            }
            
        }

        return rowView;
    }
}
