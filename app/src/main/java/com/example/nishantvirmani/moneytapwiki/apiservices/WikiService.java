package com.example.nishantvirmani.moneytapwiki.apiservices;

import com.example.nishantvirmani.moneytapwiki.models.Query;
import com.example.nishantvirmani.moneytapwiki.models.Search;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface WikiService {
    @GET("api.php")
    Call<Search> fetchNews(
            @QueryMap HashMap<String, Object> params);
}
