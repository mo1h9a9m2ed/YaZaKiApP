// استيراد الحزم الضرورية
package yazakiapp.com;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

// تعريف الفئة UpdateActivity التي تمتد من AppCompatActivity
public class UpdateActivity extends MainActivity implements View.OnClickListener {
    // تعريف المتغيرات
    private EditText editTextUsername, editTextPassword, editTextNewPassword;
    private Button updateButton;
    private ProgressDialog progressDialog;

    // تنفيذ دالة الإنشاء
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);

        // الحصول على مراجع العناصر من تخطيط XML
        editTextUsername = findViewById(R.id.editTextTextUsername);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextNewPassword = findViewById(R.id.editTextTextNewPassword);
        updateButton = findViewById(R.id.updateButton);
        progressDialog = new ProgressDialog(this);

        // إضافة مستمع النقر
        updateButton.setOnClickListener(this);
    }

    // تنفيذ دالة تحديث المستخدم
    private void updateUser() {
        // الحصول على البيانات المدخلة من الحقول
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();

        // التحقق من ملء جميع الحقول
        if (username.isEmpty() || password.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        // عرض شريط التقدم
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        // بناء طلب الشبكة باستخدام StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_update,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Network error occurred", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override

            protected Map<String, String> getParams() {
                // إعداد البيانات المطلوبة للطلب
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("newPassword", newPassword); // تغيير الاسم إلى "newPassword"
                return params;
            }
        };

        // إضافة الطلب إلى طابور الطلبات
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // تنفيذ مستمع النقر
    @Override
    public void onClick(View v) {
        if (v == updateButton) {
            updateUser();
        }
      }     @Override
public boolean onCreateOptionsMenu(Menu menu){
    return super.onCreateOptionsMenu(menu);
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }


}
