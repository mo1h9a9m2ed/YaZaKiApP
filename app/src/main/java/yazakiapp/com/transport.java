package yazakiapp.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class dbconeActivity extends MainActivity {






        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dbcone);

            Button home = findViewById(R.id.home);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(dbconeActivity.this, MainActivity.class);
                    startActivity(intent);
                }


            });
        }  @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }
    }
