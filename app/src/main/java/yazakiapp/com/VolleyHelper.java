package yazakiapp.com;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;

import yazakiapp.com.Constant;

public class VolleyHelper {

    private static final String TAG = "VolleyHelper";

    // رابط الطلب
    private static final String REQUEST_URL = Constant.URL_newrequest;

    // طلب البيانات من الخادم
    public static void fetchRequests(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, REQUEST_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // تم استقبال البيانات بنجاح، يمكنك هنا معالجة البيانات وإرسال الإشعار
                        Log.d(TAG, "تم استقبال البيانات: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // حدث خطأ أثناء استقبال البيانات
                        Log.e(TAG, "خطأ في استقبال البيانات: " + error.toString());
                    }
                });

        // إضافة الطلب إلى طابور الطلبات
        queue.add(jsonArrayRequest);
    }
}
