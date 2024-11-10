package yazakiapp.com;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class webvieu extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webvieu);

        webView = findViewById(R.id.wwebview);
        webView.loadUrl("https://jsonplaceholder.typicode.com/posts");
    }
}
