package yazakiapp.com.showdata;

import static yazakiapp.com.Constant.showdata;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import yazakiapp.com.Constant;
import yazakiapp.com.R;
import yazakiapp.com.showdata.Complaint;
import yazakiapp.com.showdata.ComplaintsAdapter;

public class ComplaintsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ComplaintsAdapter adapter;
    private List<Complaint> complaintList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        recyclerView = findViewById(R.id.recycler_view_complaints);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        complaintList = new ArrayList<>();
        adapter = new ComplaintsAdapter(this, complaintList);
        recyclerView.setAdapter(adapter);

        // استدعاء الطريقة لجلب البيانات من الخادم
        fetchComplaints();
    }

    private void fetchComplaints() {
        String url = showdata;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String fileName = jsonObject.getString("file_name");
                                String reason = jsonObject.getString("reason");
                                String message = jsonObject.getString("message");

                                Complaint complaint = new Complaint(fileName, reason, message);
                                complaintList.add(complaint);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ComplaintsActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(ComplaintsActivity.this, "Error fetching complaints", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
