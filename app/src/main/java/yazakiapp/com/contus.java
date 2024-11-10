package yazakiapp.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class contus extends AppCompatActivity {

    private TextView textViewData;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contus);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contus.this,MainActivity.class);
                startActivity(intent);

                // Initialize WebView
                webView = findViewById(R.id.wwebview);

                // Configure WebView settings
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true); // تمكين تنفيذ الجافا سكريبت في الصفحة

                // Load the URL
                webView.loadUrl("https://jsonplaceholder.typicode.com/posts");
            }
        });
    }

    public void home(View view) {
        Intent intent = new Intent(contus.this, MainActivity.class);
        startActivity(intent);
    }
}
