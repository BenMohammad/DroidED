package com.benmohammad.droided;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter adapter = new GitHubRepoAdapter();
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = findViewById(R.id.list_view_repos);
        listView.setAdapter(adapter);
        final EditText editTextTextName = findViewById(R.id.edit_text_username);
        final Button buttonSearch = findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(v -> {
            final String userName = editTextTextName.getText().toString().trim();
            if(!TextUtils.isEmpty(userName)) {
                getStarredRepos(userName);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(subscription != null) {
            subscription.cancel();
        }

        super.onDestroy();
    }

    private void getStarredRepos(String userName) {
        GithubClient.getInstance()
                .getStarredRepos(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GitHubRepo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GitHubRepo> gitHubRepos) {
                        adapter.setGitHubRepos(gitHubRepos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
