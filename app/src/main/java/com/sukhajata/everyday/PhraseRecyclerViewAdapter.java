package com.sukhajata.everyday;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Tim on 7/05/2017.
 */

public class PhraseRecyclerViewAdapter extends RecyclerView.Adapter<PhraseRecyclerViewAdapter.ViewHolder> {

    private final List<Phrase> mPhrases;
    private Context mContext;
    private int selectedPos;


    public PhraseRecyclerViewAdapter(Context context, List<Phrase> phrases) {
        mContext = context;
        mPhrases = phrases;
        selectedPos = -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phrase_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mPhrases.get(position);
        holder.txtSL.setText(holder.mItem.secondLanguage + "\n" + holder.mItem.firstLanguage);
        String secondTier = holder.mItem.romanisation;
        SpannableString spannableString;
        if (holder.mItem.literalTranslation != null && !holder.mItem.literalTranslation.equals(""))
        {
            int idxItalicsStart = secondTier.length() + 2; //for " ("
            int idxItalicsEnd = idxItalicsStart + holder.mItem.literalTranslation.length();
            secondTier = secondTier + "\n(" + holder.mItem.literalTranslation + ")";
            spannableString = new SpannableString(secondTier);
            spannableString.setSpan(new StyleSpan(Typeface.ITALIC), idxItalicsStart,
                    idxItalicsEnd, 0);
        }
        else
        {
            spannableString = new SpannableString(secondTier);
        }
        holder.txtFL.setText(spannableString);
        //put notes in italics
        if (holder.mItem.notes != null && !holder.mItem.notes.equals("")) {
            android.text.Spanned notes = Html.fromHtml("<i>" + holder.mItem.notes + "</i>");
            holder.txtN.setText(notes);
        }
        else {
            holder.txtN.setVisibility(View.GONE);
        }

        holder.imgFav.setTag(holder.mItem);

        holder.imgFav.setTag(holder.mItem);
        if (holder.mItem.isFavourite) {
            holder.imgFav.setImageResource(R.drawable.ic_favourite_active);
        } else {
            holder.imgFav.setImageResource(R.drawable.ic_favourite);
        }

        if (selectedPos == position) {
            holder.itemView.setBackgroundColor(ResourcesCompat.getColor(holder.itemView.getResources(), R.color.accent, null));
        } else {
            holder.itemView.setBackgroundColor(ResourcesCompat.getColor(holder.itemView.getResources(), R.color.white, null));
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPos);
                selectedPos = position;
                notifyItemChanged(selectedPos);
                //Toast.makeText(mContext, "Item clicked", Toast.LENGTH_SHORT).show();
                SoundHelper.getInstance(mContext).playPhrase(holder.mItem);
            }
        });
    }
    
    
    @Override
    public int getItemCount() {
        return mPhrases.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtSL;
        public final TextView txtFL;
        public final TextView txtN;
        public final ImageView imgFav;
        public Phrase mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtSL = (TextView)view.findViewById(R.id.secondLanguage);
            txtFL = (TextView)view.findViewById(R.id.firstLanguage);
            txtN = (TextView)view.findViewById(R.id.notes);
            imgFav = (ImageView)view.findViewById(R.id.favourite);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtFL.getText() + "'";
        }
    }
}
