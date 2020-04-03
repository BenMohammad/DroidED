package com.benmohammad.droided;

import androidx.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {

    private static final String GITHUB_BASE_URL = "https://api.github.com/";

    private GithubService service;
    private static GithubClient client;

    private GithubClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(GithubService.class);

    }

    public static GithubClient getInstance() {
        if(client == null) {
            client = new GithubClient();
        }
        return client;
    }

    public Observable<List<GitHubRepo>> getStarredRepos(@NonNull String userName) {
            return service.getStarredRepositories(userName);
    }
}
