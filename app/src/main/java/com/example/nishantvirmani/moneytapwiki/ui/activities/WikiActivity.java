package com.example.nishantvirmani.moneytapwiki.ui.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.nishantvirmani.moneytapwiki.R;
import com.example.nishantvirmani.moneytapwiki.apiservices.WikiService;
import com.example.nishantvirmani.moneytapwiki.listener.WikiItemListener;
import com.example.nishantvirmani.moneytapwiki.models.Page;
import com.example.nishantvirmani.moneytapwiki.models.Search;
import com.example.nishantvirmani.moneytapwiki.network.ApiCallHandler;
import com.example.nishantvirmani.moneytapwiki.ui.adapters.WikiListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WikiActivity extends AppCompatActivity implements WikiItemListener {
    private ApiCallHandler apiCallHandler;
    private RecyclerView recyclerView;
    private WikiListAdapter adapter;
    private ArrayList<Page> articleList;
    private EditText searchedArticle;
    private TextView search;
    private ProgressDialog progressDialog;
    private static final String WIKI_DETAIL_PAGE_BASE_URL = "https://en.wikipedia.org/wiki/";
    private String searchStr;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        initData();
        initViews();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        searchedArticle = findViewById(R.id.search_article);
        recyclerView = findViewById(R.id.recyclerview);
        search = findViewById(R.id.search);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WikiListAdapter(articleList, this);
        recyclerView.setAdapter(adapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchSearchedNews(searchedArticle.getText().toString());
            }
        });
        initTextWatcher();
    }

    private void initTextWatcher() {
        searchedArticle = findViewById(R.id.search_article);
        searchedArticle.addTextChangedListener(searchTextWatcher);
    }


    private TextWatcher searchTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {

            searchStr = arg0.toString().trim();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(0);
                }
            }, 800);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nothing to do here
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (timer != null) {
                timer.cancel();
            }
        }
    };


    private void initData() {
        apiCallHandler = ApiCallHandler.newInstance();
        articleList = new ArrayList<>();
    }

    private void fetchSearchedNews(String keyword) {
        if(keyword!=null && keyword.isEmpty()){
            clearResults();
        }
        hideKeyboard(searchedArticle);
        progressDialog.setMessage("Loading Wiki ...");
        progressDialog.show();
        WikiService service = apiCallHandler.createService(WikiService.class);

        Call<Search> call = service.fetchNews(getParams(keyword));
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null && response.body().getQuery() != null &&
                        response.body().getQuery().getPages() != null) {
                    updateNewsList(response.body().getQuery().getPages());
                } else {
                    showErrorMessage(response.message());
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                progressDialog.dismiss();
                showErrorMessage(t.getMessage());

            }
        });

    }

    private void clearResults() {
        articleList.clear();
        adapter.notifyDataSetChanged();
    }

    private void updateNewsList(List<Page> value) {
        articleList.clear();
        articleList.addAll(value);
        adapter.notifyDataSetChanged();
        TextView noResultText = findViewById(R.id.no_result_text);
        if (articleList.isEmpty()) {
            noResultText.setVisibility(View.VISIBLE);
        } else
            noResultText.setVisibility(View.GONE);
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT);
    }

    private HashMap<String, Object> getParams(String keyword) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("action", "query");
        params.put("format", "json");
        params.put("prop", "pageimages|pageterms");
        params.put("generator", "prefixsearch");
        params.put("redirects", "");
        params.put("formatversion", 2);
        params.put("piprop", "thumbnail");
        params.put("pithumbsize", 50);
        params.put("pilimit", 10);
        params.put("wbptterms", "description");
        params.put("gpssearch", keyword);
        params.put("gpslimit", 10);

        return params;
    }

    @Override
    public void onItemClick(int position) {

        Page newsItem = articleList.get(position);
        Intent myIntent = new Intent(WikiActivity.this, WikiDetailView.class);
        myIntent.putExtra("url", WIKI_DETAIL_PAGE_BASE_URL + articleList.get(position).getTitleUrl());
        startActivity(myIntent);
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            fetchSearchedNews(searchStr);
        }
    };
}
