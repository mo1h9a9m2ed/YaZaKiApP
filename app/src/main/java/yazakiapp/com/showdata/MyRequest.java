package yazakiapp.com.showdata;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
 import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyRequest {

    private RequestQueue requestQueue;
    private String baseUrl = "http://your_server_address/"; // تغيير "your_server_address" بعنوان الخادم الخاص بك

    public MyRequest(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public interface VolleyCallback {
        void onSuccessResponse(JSONArray result);
        void onErrorResponse(VolleyError error);
    }

    public void getComplaints(String matricule, final VolleyCallback callback) {
        String url = baseUrl + "complaints.php?matricule=" + matricule;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callback.onSuccessResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    public void deleteComplaint(String id, final VolleyCallback callback) {
        String url = baseUrl + "delete.php?id=" + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // لا يوجد استجابة للحذف، يمكنك تحديد رسالة نجاح أو فشل هنا
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
