package com.benmohammad.droided;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GitHubRepoAdapter extends BaseAdapter {

    private List<GitHubRepo> gitHubRepos = new ArrayList<>();


    @Override
    public int getCount() {
        return gitHubRepos.size();
    }

    @Override
    public GitHubRepo getItem(int position) {
        if(position < 0 || position>= gitHubRepos.size()) {
            return null;
        } else {
            return gitHubRepos.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View v = (convertView != null) ? convertView : createView(parent);
        final ViewHolder viewHolder = (ViewHolder) v.getTag();
        viewHolder.setGithubRepo(getItem(position));
        return v;
    }

    public void setGitHubRepos(@Nullable List<GitHubRepo> repos) {
        if(repos == null) {
            return;
        }
        gitHubRepos.clear();
        gitHubRepos.addAll(repos);
        notifyDataSetChanged();

    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_github_repo, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class ViewHolder {
        private TextView textRepoName;
        private TextView textRepoDescription;
        private TextView textRepoLanguage;
        private TextView textRepoStars;

        public ViewHolder(View view) {
            textRepoName = view.findViewById(R.id.text_repo_name);
            textRepoDescription = view.findViewById(R.id.text_repo_description);
            textRepoLanguage = view.findViewById(R.id.text_language);
            textRepoStars = view.findViewById(R.id.text_stars);
        }

        public void setGithubRepo(GitHubRepo repo) {
            textRepoName.setText(repo.name);
            textRepoDescription.setText(repo.description);
            textRepoLanguage.setText("Language : " +repo.language);
            textRepoStars.setText("Stars" + repo.stargazersCount);
        }
    }

}
