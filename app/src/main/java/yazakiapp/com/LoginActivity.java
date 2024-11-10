package yazakiapp.com;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

 import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends   MainActivity {
    private SharedPreferences sharedPref;
    private Context context;

    private EditText editTextUsername,     editTextPassword   ;
    private   TextView textViewUsername,     textViewPassword   ;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textViewUsername = findViewById(R.id.usernameTextView);
        textViewPassword = findViewById(R.id.passwordTextView);
        editTextUsername = findViewById(R.id.usernameEditText);
        editTextPassword = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

         SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", "default value");
        String id = sharedPref.getString("id", "default value");

        if (!username.equals("default value")) {
            Intent intent = new Intent(this,Profile.class);
            startActivity(intent);

         }
        else{
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }

        });
    }

    private void userLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passworsdd = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getInt("success") == 1) {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();



                                for (int i = 0; i < jsonObject.length(); i++) {
                                    JSONObject userObject = jsonObject.getJSONObject("user");
                                     String id = userObject.getString("id");
                                    String username = userObject.getString("username");
                                    String password = userObject.getString("password");
                                    String displayName = userObject.getString("displayname");
                                    String matricule = userObject.getString("matricule");
                                    String department = userObject.getString("your_department");
                                    String lastName = userObject.getString("last_name");
                                    String caseNumber = userObject.getString("case_nameber");
                                    String parada = userObject.getString("parada");
                                    String chefDeLine = userObject.getString("chef_de_line");
                                    String chinaName = userObject.getString("la_chine_name");
                                    String post = userObject.getString("your_poste");
                                    String telephone = userObject.getString("telephone");
                                    String email = userObject.getString("email");
                                    String colum3 = userObject.getString("colum3");
                                    String colum4 = userObject.getString("colum4");
                                    String colum5 = userObject.getString("colum5");



                                    sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();


                                    editor.putString("id", id);
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.putString("displayName", displayName);
                                    editor.putString("matricule", matricule);
                                    editor.putString("department", department);
                                    editor.putString("lastName", lastName);
                                    editor.putString("caseNumber", caseNumber);
                                    editor.putString("parada", parada);
                                    editor.putString("chefDeLine", chefDeLine);
                                    editor.putString("chinaName", chinaName);
                                    editor.putString("post", post);
                                    editor.putString("telephone", telephone);
                                    editor.putString("email", email);
                                    editor.putString("colum3", colum3);
                                    editor.putString("colum4", colum4);
                                    editor.putString("colum5", colum5);
                                    editor.apply();

                                    writeToFile(username, passworsdd);


                                    Intent intent = new Intent(LoginActivity.this,Profile.class);
                                    startActivity(intent);
                                }

                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Network error occurred", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }    private void writeToFile( String username, String passworsdd  ) {
        try {
            // تحديد المسار الذي تريد كتابة الملف إليه
            File file = new File(getApplicationContext().getExternalFilesDir(null), "datalogin.txt");

            // إنشاء مخرجات لكتابة البيانات إلى الملف
            FileOutputStream fos = new FileOutputStream(file);
            String dataString =
                    "Username: " + username + "\n" +
                    "Password: " + passworsdd + "\n"  ;

            fos.write(dataString.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    public void resetlogin(View view) {
        String data = readFromFile();

        // تحديد EditText لاسم المستخدم وتعيين القيمة
         String username = extractValueFromData(data, "Username");
        editTextUsername.setText(username);

        // تحديد EditText لكلمة المرور وتعيين القيمةº¡
         String password = extractValueFromData(data, "Password");
        editTextPassword.setText(password);
    }

    // تحديد قيمة من البيانات باستخدام المفتاح (key)
    private String extractValueFromData(String data, String key) {
        String[] lines = data.split("\n");
        for (String item : lines) {
            if (item.contains(key)) {
                return item.replace(key + ": ", "");
            }
        }
        return ""; // إذا لم يتم العثور على القيمة
    }


    private String readFromFile() {
        StringBuilder userData = new StringBuilder();

        try {
            // تحديد المسار الذي يحتوي على الملف
            File file = new File(getApplicationContext().getExternalFilesDir(null), "datalogin.txt");

            // إنشاء مدخلات لقراءة البيانات من الملف
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            // قراءة البيانات من الملف
            StringBuilder data = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line).append("\n");
            }

            // إغلاق المدخلات
            br.close();
            isr.close();
            fis.close();

            // عرض الـusername والـpassword
            String[] lines = data.toString().split("\n");
            for (String item : lines) {
                if (item.contains("Username")) {
                    String username = item.replace("Username: ", "");
                    userData.append("Username: ").append(username).append("\n");
                } else if (item.contains("Password")) {
                    String password = item.replace("Password: ", "");
                    userData.append("Password: ").append(password).append("\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userData.toString();
    }



}
