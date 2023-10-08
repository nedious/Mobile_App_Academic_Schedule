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
import com.example.termv30.entities.CourseEntity;

import java.util.List;

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.CourseViewHolder> {

    private final LayoutInflater mInflater;
    private final Context context;
    public List<CourseEntity> mCourses;
    private List<AssessmentEntity> mAssessments;

    public AdapterCourse(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView assessmentItemView;

        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.item_course_textView);
            assessmentItemView = itemView.findViewById(R.id.item_course_assessment_list_textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    int position = getAdapterPosition();
                    final CourseEntity currentCourse = mCourses.get(position);
                    Intent intent = new Intent(context, ActivityCourseDetail.class);
                    intent.putExtra("courseID", currentCourse.getCourseID());
                    intent.putExtra("termID", currentCourse.getTermID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_course_list, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses != null) {
            final CourseEntity currentCourse = mCourses.get(position);
            holder.courseItemView.setText((currentCourse.getCourseTitle()));

            String filteredAssessmentList = "";
            for (AssessmentEntity assessment: mAssessments){
                if (assessment.getCourseID() == currentCourse.getCourseID())
                    filteredAssessmentList = filteredAssessmentList + assessment.getAssessmentTitle() + "\n";
            }
            if (filteredAssessmentList != "")
                holder.assessmentItemView.setText(filteredAssessmentList);
            else
                holder.assessmentItemView.setVisibility(View.GONE);

        } else {
            holder.courseItemView.setText("no title");
        }
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

    public void setCourses(List<CourseEntity> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
    public CourseEntity getCourseAt(int position) {
        return mCourses.get(position);
    }
}
