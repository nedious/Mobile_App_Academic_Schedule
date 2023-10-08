package com.example.termv30.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termv30.R;
import com.example.termv30.entities.AssessmentEntity;

import java.util.List;

public class AdapterAssessment extends RecyclerView.Adapter<AdapterAssessment.AssessmentViewHolder> {

    private final LayoutInflater mInflater;
    private final Context context;
    public List<AssessmentEntity> mAssessments;

    public AdapterAssessment(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.item_assessment_list_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity currentAssessment = mAssessments.get(position);
                    Intent intent = new Intent(context, ActivityAssessmentDetail.class);
                    intent.putExtra("assessmentID", currentAssessment.getAssessmentID());
                    intent.putExtra("courseID", currentAssessment.getCourseID());
                    intent.putExtra("termID", ActivityCourseDetail.termID);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_assessment_list, parent, false);
        return new AssessmentViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            final AssessmentEntity currentAssessment = mAssessments.get(position);
            holder.assessmentItemView.setText(currentAssessment.getAssessmentTitle());
        }
        else {
            holder.assessmentItemView.setText("No title");
        }
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null){
            return mAssessments.size();
        }
        else return 0;
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }

    public AssessmentEntity getAssessmentAt(int position) {
        return mAssessments.get(position);
    }
}
