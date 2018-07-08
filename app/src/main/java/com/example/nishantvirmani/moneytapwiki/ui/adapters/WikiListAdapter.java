package com.example.nishantvirmani.moneytapwiki.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.nishantvirmani.moneytapwiki.R;
import com.example.nishantvirmani.moneytapwiki.listener.WikiItemListener;
import com.example.nishantvirmani.moneytapwiki.models.Page;
import com.example.nishantvirmani.moneytapwiki.ui.viewholders.WikiViewHolder;

import java.util.ArrayList;


public class WikiListAdapter extends RecyclerView.Adapter<WikiViewHolder> {
    private ArrayList<Page> newsList;
    private WikiItemListener wikiItemListener;


    public WikiListAdapter(ArrayList<Page> newsList, WikiItemListener eventItemListener) {
        super();
        this.newsList = newsList;
        this.wikiItemListener = eventItemListener;
    }

    @Override
    public WikiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wiki_item, parent, false);

        return new WikiViewHolder(itemView, wikiItemListener);
    }

    @Override
    public void onBindViewHolder(WikiViewHolder holder, int position) {
        holder.bindData(newsList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}


