package yazakiapp.com;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

 import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class activity extends MainActivity implements View.OnClickListener {

    // تعريف المتغيرات
    private EditText editTextEmail, editTextUsername, editTextPassword, editTextDisplayName, editTextMatricule, editTextYourDepartment,
            editTextLastName, editTextCaseNumber, editTextParada, editTextChefDeLine, editTextLaChineName, editTextYourPoste,
            editTextTelephone;

    private Button register;
    private ProgressDialog progressDialog;

    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        // الاتصال بعناصر واجهة المستخدم
        editTextEmail = findViewById(R.id.editTextTextEmail);
        editTextTelephone = findViewById(R.id.editexttelephone);
        editTextUsername = findViewById(R.id.editextusername);
        editTextPassword = findViewById(R.id.editextpassword);
        editTextDisplayName = findViewById(R.id.editextdisplayname);
        editTextMatricule = findViewById(R.id.editextmatricule);
        editTextYourDepartment = findViewById(R.id.editextyour_department);
        editTextLastName = findViewById(R.id.editextlast_name);
        editTextCaseNumber = findViewById(R.id.editextcase_nameber);
        editTextParada = findViewById(R.id.editextparada);
        editTextChefDeLine = findViewById(R.id.editextchef_de_line);
        editTextLaChineName = findViewById(R.id.editextla_chine_name);
        editTextYourPoste = findViewById(R.id.editextyour_poste);

        register = findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(this);
    }

    // دالة تسجيل المستخدم
    private void registerUser() {
        // استرداد القيم من عناصر واجهة المستخدم
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String displayName = editTextDisplayName.getText().toString().trim();
        String matricule = editTextMatricule.getText().toString().trim();
        String yourDepartment = editTextYourDepartment.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String caseNumber = editTextCaseNumber.getText().toString().trim();
        String telephone = editTextTelephone.getText().toString().trim();
        String parada = editTextParada.getText().toString().trim();
        String chefDeLine = editTextChefDeLine.getText().toString().trim();
        String laChineName = editTextLaChineName.getText().toString().trim();
        String yourPoste = editTextYourPoste.getText().toString().trim();

        // التحقق من ملء جميع الحقول المطلوبة
        if (email.isEmpty() || matricule.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG).show();
            return;
        }

        // عرض شاشة التحميل
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        // إجراء طلب الشبكة
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.URL_register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            // إذا كان التسجيل ناجحًا، قم بكتابة البيانات إلى ملف خارجي
                            if (jsonObject.getInt("success") == 1) {
                                // كتابة البيانات إلى ملف خارجي
                                if (ContextCompat.checkSelfPermission(activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                                } else {
                                    writeToFile(email, username, password, displayName, matricule, yourDepartment, lastName, caseNumber, telephone, parada, chefDeLine, laChineName, yourPoste);
                                }
                            }

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
            // إضافة البيانات كباراميتر للطلب
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("displayname", displayName);
                params.put("email", email);
                params.put("matricule", matricule);
                params.put("your_department", yourDepartment);
                params.put("last_name", lastName);
                params.put("case_nameber", caseNumber);
                params.put("parada", parada);
                params.put("chef_de_line", chefDeLine);
                params.put("la_chine_name", laChineName);
                params.put("your_poste", yourPoste);
                params.put("telephone", telephone);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // دالة كتابة البيانات إلى ملف خارجي
    private void writeToFile(String email, String username, String password, String displayName, String matricule, String yourDepartment, String lastName, String caseNumber, String telephone, String parada, String chefDeLine, String laChineName, String yourPoste) {
        try {
            // تحديد المسار الذي تريد كتابة الملف إليه
            File file = new File(getApplicationContext().getExternalFilesDir(null), "data.txt");

            // إنشاء مخرجات لكتابة البيانات إلى الملف
            FileOutputStream fos = new FileOutputStream(file);
            String dataString = "Email: " + email + "\n" +
                    "Username: " + username + "\n" +
                    "Password: " + password + "\n" +
                    "Display Name: " + displayName + "\n" +
                    "Matricule: " + matricule + "\n" +
                    "Your Department: " + yourDepartment + "\n" +
                    "Last Name: " + lastName + "\n" +
                    "Case Number: " + caseNumber + "\n" +
                    "Telephone: " + telephone + "\n" +
                    "Parada: " + parada + "\n" +
                    "Chef de Line: " + chefDeLine + "\n" +
                    "La Chine Name: " + laChineName + "\n" +
                    "Your Poste: " + yourPoste + "\n";

            fos.write(dataString.getBytes());
            fos.close();
            Toast.makeText(getApplicationContext(), "تمت كتابة البيانات بنجاح", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // دالة تنفيذ الأحداث على النقرة
    @Override
    public void onClick(View view) {
        if (view == register) {
            registerUser();
        }
    }




}
