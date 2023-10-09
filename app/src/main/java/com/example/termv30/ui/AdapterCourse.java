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

public class AdapterCourse extends RecyclerView.Adapter<AdapterCourse.CoursesViewHolder> {

    private final LayoutInflater mInflater;
    private final Context context;
    public List<CourseEntity> mCourses;
    private List<AssessmentEntity> mAssessments;

    public AdapterCourse(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    class CoursesViewHolder extends RecyclerView.ViewHolder {
        private final TextView coursesItemView;
        private final TextView assessmentsItemView;

        private CoursesViewHolder(View itemView){
            super(itemView);
            coursesItemView = itemView.findViewById(R.id.item_course_textView);
            assessmentsItemView = itemView.findViewById(R.id.item_course_assessment_list_textView);
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
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_course_list, parent, false);
        return new CoursesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        if(mCourses != null) {
            final CourseEntity currentCourse = mCourses.get(position);
            holder.coursesItemView.setText((currentCourse.getCourseTitle()));

            String assignmentsPerCourse = "";
            for (AssessmentEntity assessment: mAssessments){
                if (assessment.getCourseID() == currentCourse.getCourseID())
                    assignmentsPerCourse = assignmentsPerCourse + assessment.getAssessmentTitle() + "\n";
            }
            if (assignmentsPerCourse != "")
                holder.assessmentsItemView.setText(assignmentsPerCourse);
            else
                holder.assessmentsItemView.setVisibility(View.GONE);

        } else {
            holder.coursesItemView.setText("no title");
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
