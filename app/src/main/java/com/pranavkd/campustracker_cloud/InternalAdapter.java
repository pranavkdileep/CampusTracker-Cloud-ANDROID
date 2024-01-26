package com.pranavkd.campustracker_cloud;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavkd.campustracker_cloud.data.InternallistData;
import com.pranavkd.campustracker_cloud.interfaces.onEditAtt;

import java.util.List;

public class InternalAdapter extends RecyclerView.Adapter<InternalAdapter.InternalViewHolder> {
    List<InternallistData> internallistDataList;
    onEditAtt onEditAttt;

    public InternalAdapter(List<InternallistData> internallistDataList, onEditAtt onEditAttt) {
        if(internallistDataList!=null){
            this.internallistDataList = internallistDataList;
            this.onEditAttt = onEditAttt;
        }
    }

    @NonNull
    @Override
    public InternalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.internallist, parent, false);
        return new InternalViewHolder(view,onEditAttt,internallistDataList);
    }

    @Override
    public void onBindViewHolder(@NonNull InternalViewHolder holder, int position) {
        InternallistData data = internallistDataList.get(position);
        holder.tvId.setText(String.valueOf(data.getStudent_id()));
        holder.tvName.setText(data.getStudent_name());
        holder.internalid.setText(String.valueOf(data.getInternal_id()));
        holder.max_marks.setText(String.valueOf(data.getMax_marks()));
        holder.marks_obtained.setText(String.valueOf(data.getMarks_obtained()));
    }

    @Override
    public int getItemCount() {
        return internallistDataList.size();
    }

    public static class InternalViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvName, internalid, max_marks, marks_obtained;
        ImageView Edit;

        public InternalViewHolder(@NonNull View itemView, onEditAtt onEditAtt, List<InternallistData> internallistDataList) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvName = itemView.findViewById(R.id.tvName);
            internalid = itemView.findViewById(R.id.internalid);
            max_marks = itemView.findViewById(R.id.max_marks);
            marks_obtained = itemView.findViewById(R.id.marks_obtained);
            Edit = itemView.findViewById(R.id.EditInternals);
            Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditAtt.onEdit(internallistDataList.get(getAdapterPosition()).getInternal_id());
                }
            });
        }
    }
}