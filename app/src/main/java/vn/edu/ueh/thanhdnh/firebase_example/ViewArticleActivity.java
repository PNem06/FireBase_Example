package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ViewArticleActivity extends AppCompatActivity {

    ImageView iv_detail;

    TextView tv_detail_title;
    TextView tv_detail_view;
    TextView tv_detail_description;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_article);

        iv_detail = findViewById(R.id.iv_detail);

        tv_detail_title =
                findViewById(R.id.tv_detail_title);

        tv_detail_view =
                findViewById(R.id.tv_detail_view);

        tv_detail_description =
                findViewById(R.id.tv_detail_description);

        db = FirebaseFirestore.getInstance();

        String articleId =
                getIntent().getStringExtra("article_id");

        // Kiểm tra ID
        if (articleId == null || articleId.isEmpty()) {

            Toast.makeText(
                    this,
                    "Không tìm thấy ID bài viết",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        db.collection("articles")
                .document(articleId)
                .update(
                        "view",
                        FieldValue.increment(1)
                )
                .addOnSuccessListener(unused -> {

                    // Sau khi tăng View thì lấy lại dữ liệu
                    db.collection("articles")
                            .document(articleId)
                            .get()
                            .addOnSuccessListener(
                                    documentSnapshot -> {

                                        if (!documentSnapshot.exists()) {

                                            Toast.makeText(
                                                    this,
                                                    "Không tìm thấy bài viết",
                                                    Toast.LENGTH_SHORT
                                            ).show();

                                            finish();
                                            return;
                                        }

                                        Article article =
                                                documentSnapshot
                                                        .toObject(Article.class);

                                        if (article != null) {

                                            tv_detail_title.setText(
                                                    article.getTitle()
                                            );

                                            tv_detail_view.setText(
                                                    "View: "
                                                            + article.getView()
                                            );

                                            tv_detail_description.setText(
                                                    article.getDescription()
                                            );

                                            Picasso.get()
                                                    .load(article.getImage())
                                                    .into(iv_detail);
                                        }
                                    }
                            )
                            .addOnFailureListener(e -> {

                                Toast.makeText(
                                        this,
                                        "Lỗi tải bài viết: "
                                                + e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            });
                })
                .addOnFailureListener(e -> {

                    Toast.makeText(
                            this,
                            "Lỗi tăng View: "
                                    + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                });
    }
}