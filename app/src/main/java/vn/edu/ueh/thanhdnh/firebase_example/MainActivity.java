package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArticleAdapter adapter;
    List<Article> articles = new ArrayList<>();

    FirebaseFirestore db;

    Button btnAddArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        db = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.recyclerView);
        btnAddArticle = findViewById(R.id.btn_add_article);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // SỬA Ở ĐÂY:
        // getBaseContext() -> this
        adapter = new ArticleAdapter(
                this,
                articles
        );

        recyclerView.setAdapter(adapter);

        loadArticles();

        btnAddArticle.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddArticleActivity.class
            );

            startActivity(intent);
        });
    }

    private void loadArticles() {

        db.collection("articles")
                .addSnapshotListener((value, error) -> {

                    if (error != null || value == null) {
                        return;
                    }

                    articles.clear();

                    for (QueryDocumentSnapshot document : value) {

                        Article article =
                                document.toObject(Article.class);

                        // Lấy ID của document Firebase
                        article.setId(document.getId());

                        articles.add(article);
                    }

                    adapter.update(articles);
                    adapter.notifyDataSetChanged();
                });
    }
}