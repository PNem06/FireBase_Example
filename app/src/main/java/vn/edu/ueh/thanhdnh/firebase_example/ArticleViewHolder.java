package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleViewHolder
        extends RecyclerView.ViewHolder {

  private TextView txtTitle;
  private TextView txtDescription;
  private TextView txtView;
  private ImageView imgArticle;

  private ArticleAdapter adapter;

  public ArticleViewHolder(
          @NonNull View itemView,
          ArticleAdapter adapter) {

    super(itemView);

    txtTitle =
            itemView.findViewById(R.id.txt_title);

    txtDescription =
            itemView.findViewById(R.id.txt_description);

    txtView =
            itemView.findViewById(R.id.txt_view);

    imgArticle =
            itemView.findViewById(R.id.img_article);

    this.adapter = adapter;

    // Khi bấm vào một bài viết
    itemView.setOnClickListener(v -> {

      int position = getAdapterPosition();

      if (position == RecyclerView.NO_POSITION) {
        return;
      }

      Article article =
              adapter.getArticles().get(position);

      Intent intent = new Intent(
              itemView.getContext(),
              ViewArticleActivity.class
      );

      // Gửi ID bài viết sang ViewArticleActivity
      intent.putExtra(
              "article_id",
              article.getId()
      );

      itemView.getContext().startActivity(intent);
    });
  }

  public TextView getTxtTitle() {
    return txtTitle;
  }

  public void setTxtTitle(TextView txtTitle) {
    this.txtTitle = txtTitle;
  }

  public TextView getTxtDescription() {
    return txtDescription;
  }

  public void setTxtDescription(TextView txtDescription) {
    this.txtDescription = txtDescription;
  }

  public TextView getTxtView() {
    return txtView;
  }

  public void setTxtView(TextView txtView) {
    this.txtView = txtView;
  }

  public ImageView getImgArticle() {
    return imgArticle;
  }

  public void setImgArticle(ImageView imgArticle) {
    this.imgArticle = imgArticle;
  }
}