package yazakiapp.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class RHActivity extends MainActivity {

    private EditText editTextFileName, editTextMessage;
    private Button buttonSend,homee;
    private Spinner spinnerReason;

    private static final String URL = Constant.URL_rh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rh);

        editTextFileName = findViewById(R.id.edit_text_file_name);
        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.button_send);
        spinnerReason = findViewById(R.id.spinner_reason);
        homee = findViewById(R.id.homee);
        homee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RHActivity.this, MainActivity.class);
                startActivity(intent);
            }});
        // استرجاع اسم المستخدم المحفوظ مسبقاً
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "USER");
        editTextFileName.setText(username);

        // Populate the Spinner with reason options
        String[] reasonOptions = {"Low", "Medium", "High", "Reason 4"}; // Example options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reasonOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReason.setAdapter(adapter);

        // تعيين مستمع لزر الإرسال
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer();
            }
        });
    }

    // إرسال البيانات إلى الخادم
    private void sendDataToServer() {
        String fileName = editTextFileName.getText().toString();
        String reason = spinnerReason.getSelectedItem().toString().trim(); // Get selected reason from Spinner
        String message = editTextMessage.getText().toString().trim();

        // التحقق من فارغة الحقول
        if (fileName.isEmpty() || reason.isEmpty() || message.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return; // عدم إرسال البيانات إذا كان أحد الحقول فارغًا
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int success = jsonObject.getInt("success");
                            String responseMessage = jsonObject.getString("message");

                            if (success == 1) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                // Clear EditText fields after successful submission
                                editTextFileName.setText("");
                                editTextMessage.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), " success", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Network error occurred", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("file_name", fileName);
                params.put("reason", reason);
                params.put("message", message);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
