package com.example.nishantvirmani.moneytapwiki.ui.viewholders;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.nishantvirmani.moneytapwiki.R;
import com.example.nishantvirmani.moneytapwiki.listener.WikiItemListener;
import com.example.nishantvirmani.moneytapwiki.models.Page;

public class WikiViewHolder extends RecyclerView.ViewHolder {

    protected WikiItemListener wikiItemListener;
    private TextView titleText;
    private TextView descriptionText;
    private ImageView imageView;


    public WikiViewHolder(View itemView, final WikiItemListener wikiItemListener) {
        super(itemView);
        this.wikiItemListener = wikiItemListener;
        initViews(itemView);
    }

    private void initViews(View itemView) {

        titleText = itemView.findViewById(R.id.news_title);
        descriptionText = itemView.findViewById(R.id.description);
        imageView = itemView.findViewById(R.id.news_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wikiItemListener.onItemClick(getAdapterPosition());
            }
        });
    }

    public void bindData(final Page wikiItem) {
        if (wikiItem != null) {
            titleText.setText(wikiItem.getTitle());
            if (wikiItem.getTerms() != null)
                descriptionText.setText(wikiItem.getTerms().getDescriptionText());

            if (wikiItem.getThumbnail() != null) {

                Glide.with(itemView.getContext())
                        .load(Uri.parse(wikiItem.getThumbnail().getSource()))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .thumbnail(.5f)
                        .into(imageView);
            } else {
                Glide.with(itemView.getContext())
                        .load(R.drawable.wiki_image)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .thumbnail(.5f)
                        .into(imageView);
            }
        }

    }

}