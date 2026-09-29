package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddArticleActivity extends AppCompatActivity {

    EditText et_title;
    EditText et_image;
    EditText et_description;

    Button btn_add;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_article);

        et_title = findViewById(R.id.et_title);
        et_image = findViewById(R.id.et_image);
        et_description = findViewById(R.id.et_description);

        btn_add = findViewById(R.id.btn_add);

        db = FirebaseFirestore.getInstance();

        btn_add.setOnClickListener(v -> {

            String title =
                    et_title.getText().toString().trim();

            String image =
                    et_image.getText().toString().trim();

            String description =
                    et_description.getText().toString().trim();

            if (title.isEmpty()
                    || image.isEmpty()
                    || description.isEmpty()) {

                Toast.makeText(
                        AddArticleActivity.this,
                        "Vui lòng nhập đầy đủ thông tin",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Article article = new Article(
                    title,
                    image,
                    description,
                    0
            );

            db.collection("articles")
                    .add(article)
                    .addOnSuccessListener(documentReference -> {

                        Toast.makeText(
                                AddArticleActivity.this,
                                "Thêm bài viết thành công",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();
                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                AddArticleActivity.this,
                                "Thêm bài viết thất bại",
                                Toast.LENGTH_SHORT
                        ).show();
                    });
        });
    }
}