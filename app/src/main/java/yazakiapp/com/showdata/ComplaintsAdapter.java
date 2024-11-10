package yazakiapp.com.showdata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import yazakiapp.com.R;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder> {

    private Context context;
    private List<Complaint> complaintList;

    public ComplaintsAdapter(Context context, List<Complaint> complaintList) {
        this.context = context;
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.textViewFileName.setText("File Name: " + complaint.getFileName());
        holder.textViewReason.setText("Reason: " + complaint.getReason());
        holder.textViewMessage.setText("Message: " + complaint.getMessage());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFileName, textViewReason, textViewMessage;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFileName = itemView.findViewById(R.id.text_view_file_name);
            textViewReason = itemView.findViewById(R.id.text_view_reason);
            textViewMessage = itemView.findViewById(R.id.text_view_message);
        }
    }
}
