package yazakiapp.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String list1="RH";
        String list2="Transport";
        String list3="la contune";
        String list4="la line";
        String list5="LQC";
        String list6="activite";
        String list7="--- --- --- ---";
        String list8="--- --- --- ---";
        String list9="--- --- --- ---";


        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, dbconeActivity.class);
                startActivity(intent);
            }  });


        Button contuscontus = findViewById(R.id.contuss);
        contuscontus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, contus.class);
                startActivity(intent);
            }});


        Button Profile = findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, yazakiapp.com.Profile.class);
                startActivity(intent);
            }});


// البيانات التي ستعرض في ListView
        String[] data = {list1,list2,list3,list4,list5,list6,list7,list8,list9,};


// الربط بين ListView ومصفوفة البيانات
        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);

// إضافة مستمع للنقر على عناصر ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // استجابة للنقر على العنصر المحدد
                String selectedItem = data[position]; // احصل على العنصر المحدد
                if (list1.equals(selectedItem)) {
                    // انتقل إلى الصفحة الأخرى هنا (استبدلها بالانتقال إلى الصفحة المناسبة)
                    Intent intent = new Intent(MainActivity.this, yazakiapp.com.Profile.class);
                    startActivity(intent);
                }
                if (list2.equals(selectedItem)) {
                    // انتقل إلى الصفحة الأخرى هنا (استبدلها بالانتقال إلى الصفحة المناسبة)
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }                if (list6.equals(selectedItem)) {
                    // انتقل إلى الصفحة الأخرى هنا (استبدلها بالانتقال إلى الصفحة المناسبة)
                    Intent intent = new Intent(MainActivity.this,activity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
