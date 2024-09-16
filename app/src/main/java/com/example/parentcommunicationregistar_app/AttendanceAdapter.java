package com.example.parentcommunicationregistar_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.parentcommunicationregistar_app.bean.AttendanceBean;
import com.example.parentcommunicationregistar_app.bean.StudentBean;
import com.example.parentcommunicationregistar_app.db.DBAdapter;

import java.util.ArrayList;

public class AttendanceAdapter extends ArrayAdapter<AttendanceBean> {
    private final Context context;
    private final ArrayList<AttendanceBean> attendanceList;
    private final DBAdapter dbAdapter;

    public AttendanceAdapter(Context context, ArrayList<AttendanceBean> attendanceList) {
        super(context, R.layout.student_card_with_call, attendanceList);
        this.context = context;
        this.attendanceList = attendanceList;
        this.dbAdapter = new DBAdapter(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.student_card_with_call, parent, false);
        }

        AttendanceBean attendance = attendanceList.get(position);
        StudentBean student = dbAdapter.getStudentById(attendance.getAttendance_student_id());

        TextView nameText = convertView.findViewById(R.id.nametext);

        // Set the student's name along with their attendance status
        String status = attendance.getAttendance_status(); // Assuming "P" for Present, "A" for Absent
        String displayText = student.getStudent_name() + " (" + (status.equals("P") ? "Present" : "Absent") + ")";
        nameText.setText(displayText);

        ImageButton callButton = convertView.findViewById(R.id.button_call);
        callButton.setOnClickListener(v -> {
            String phoneNumber = student.getStudent_mobilenumber();
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(callIntent);
            }
        });

        ImageButton smsButton = convertView.findViewById(R.id.button_sms);
        smsButton.setOnClickListener(v -> {
            String phoneNumber = student.getStudent_mobilenumber();
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
                smsIntent.putExtra("sms_body", "Your message here");
                context.startActivity(smsIntent);
            }
        });

        return convertView;
    }
}