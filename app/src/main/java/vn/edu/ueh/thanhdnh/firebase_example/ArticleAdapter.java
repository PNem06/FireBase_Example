package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter
        extends RecyclerView.Adapter<ArticleViewHolder> {

    private LayoutInflater mInflater;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.mInflater = LayoutInflater.from(context);
        this.articles = articles;
    }

    public void update(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View customView = mInflater.inflate(
                R.layout.article_list,
                parent,
                false
        );

        return new ArticleViewHolder(customView, this);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ArticleViewHolder holder,
            int position) {

        Article currentArticle = articles.get(position);

        holder.getTxtTitle().setText(
                currentArticle.getTitle()
        );

        String description = currentArticle.getDescription();

        if (description != null && description.length() > 80) {
            description = description.substring(0, 80) + "...";
        }

        holder.getTxtDescription().setText(description);

        holder.getTxtView().setText(
                "View: " + currentArticle.getView()
        );

        Picasso.get()
                .load(currentArticle.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.getImgArticle());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public List<Article> getArticles() {
        return articles;
    }
}