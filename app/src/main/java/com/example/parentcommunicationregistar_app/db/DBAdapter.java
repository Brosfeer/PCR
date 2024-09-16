package com.example.parentcommunicationregistar_app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.parentcommunicationregistar_app.bean.AttendanceBean;
import com.example.parentcommunicationregistar_app.bean.AttendanceSessionBean;
import com.example.parentcommunicationregistar_app.bean.FacultyBean;
import com.example.parentcommunicationregistar_app.bean.StudentBean;

import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Attendance";

    // Contacts table name
    private static final String FACULTY_INFO_TABLE = "faculty_table";
    private static final String STUDENT_INFO_TABLE = "student_table";
    private static final String ATTENDANCE_SESSION_TABLE = "attendance_session_table";
    private static final String ATTENDANCE_TABLE = "attendance_table";


    // Contacts Table Columns names
    private static final String KEY_FACULTY_ID = "faculty_id";
    private static final String KEY_FACULTY_NAME = "faculty_name";
    private static final String KEY_FACULTY_Qualif = "faculty_qualif";
    private static final String KEY_FACULTY_MO_NO = "faculty_mobilenumber";
    private static final String KEY_FACULTY_ADDRESS = "faculty_address";
    private static final String KEY_FACULTY_Email = "faculty_Email";
    private static final String KEY_FACULTY_Gender = "faculty_gender";
    private static final String KEY_FACULTY_PASSWORD = "faculty_password";

    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_STUDENT_Gender = "student_gender";
    private static final String KEY_STUDENT_MO_NO = "student_mobilenumber";
    private static final String KEY_STUDENT_ADDRESS = "student_address";
    private static final String KEY_STUDENT_DOB = "student_dob";
    private static final String KEY_STUDENT_CLASS = "student_class";

    private static final String KEY_ATTENDANCE_SESSION_ID = "attendance_session_id";
    private static final String KEY_ATTENDANCE_SESSION_FACULTY_ID = "attendance_session_faculty_id";
    private static final String KEY_ATTENDANCE_SESSION_CLASS = "attendance_session_class";
    private static final String KEY_ATTENDANCE_SESSION_DATE = "attendance_session_date";

    private static final String KEY_SESSION_ID = "attendance_session_id";
    private static final String KEY_ATTENDANCE_STUDENT_ID = "attendance_student_id";
    private static final String KEY_ATTENDANCE_STATUS = "attendance_status";


    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override

    public void onCreate(SQLiteDatabase db) {
        String queryFaculty = "CREATE TABLE " + FACULTY_INFO_TABLE + " (" +
                KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FACULTY_NAME + " TEXT, " +
                KEY_FACULTY_Qualif + " TEXT, " +
                KEY_FACULTY_MO_NO + " TEXT, " +
                KEY_FACULTY_ADDRESS + " TEXT," +
                KEY_FACULTY_Gender + " TEXT," +
                KEY_FACULTY_Email + " TEXT," +
                KEY_FACULTY_PASSWORD + " TEXT " + ")";
        Log.d("queryFaculty", queryFaculty);


        String queryStudent = "CREATE TABLE " + STUDENT_INFO_TABLE + " (" +
                KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_STUDENT_NAME + " TEXT, " +
                KEY_STUDENT_Gender + " TEXT, " +
                KEY_STUDENT_MO_NO + " TEXT, " +
                KEY_STUDENT_ADDRESS + " TEXT," +
                KEY_STUDENT_DOB + " TEXT," +
                KEY_STUDENT_CLASS + " TEXT " + ")";
        Log.d("queryStudent", queryStudent);


        String queryAttendanceSession = "CREATE TABLE " + ATTENDANCE_SESSION_TABLE + " (" +
                KEY_ATTENDANCE_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ATTENDANCE_SESSION_FACULTY_ID + " INTEGER," +
                KEY_ATTENDANCE_SESSION_CLASS + " TEXT, " +
                KEY_ATTENDANCE_SESSION_DATE + " DATE" + ")";
        Log.d("queryAttendanceSession", queryAttendanceSession);


        String queryAttendance = "CREATE TABLE " + ATTENDANCE_TABLE + " (" +
                KEY_SESSION_ID + " INTEGER, " +
                KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
                KEY_ATTENDANCE_STATUS + " TEXT " + ")";
        Log.d("queryAttendance", queryAttendance);


        try {
            db.execSQL(queryFaculty);
            db.execSQL(queryStudent);
            db.execSQL(queryAttendanceSession);
            db.execSQL(queryAttendance);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        String queryFaculty = "CREATE TABLE " + FACULTY_INFO_TABLE + " (" +
                KEY_FACULTY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FACULTY_NAME + " TEXT, " +
                KEY_FACULTY_Qualif + " TEXT, " +
                KEY_FACULTY_MO_NO + " TEXT, " +
                KEY_FACULTY_ADDRESS + " TEXT," +
                KEY_FACULTY_Email + " TEXT," +
                KEY_FACULTY_Gender + " TEXT," +
                KEY_FACULTY_PASSWORD + " TEXT " + ")";
        Log.d("queryFaculty", queryFaculty);


        String queryStudent = "CREATE TABLE " + STUDENT_INFO_TABLE + " (" +
                KEY_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_STUDENT_NAME + " TEXT, " +
                KEY_STUDENT_Gender + " TEXT, " +
                KEY_STUDENT_MO_NO + " TEXT, " +
                KEY_STUDENT_ADDRESS + " TEXT," +
                KEY_STUDENT_DOB + " TEXT," +
                KEY_STUDENT_CLASS + " TEXT " + ")";
        Log.d("queryStudent", queryStudent);


        String queryAttendanceSession = "CREATE TABLE " + ATTENDANCE_SESSION_TABLE + " (" +
                KEY_ATTENDANCE_SESSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_ATTENDANCE_SESSION_FACULTY_ID + " INTEGER," +
                KEY_ATTENDANCE_SESSION_CLASS + " TEXT, " +
                KEY_ATTENDANCE_SESSION_DATE + " TEXT" + ")";
        Log.d("queryAttendanceSession", queryAttendanceSession);


        String queryAttendance = "CREATE TABLE " + ATTENDANCE_TABLE + " (" +
                KEY_SESSION_ID + " INTEGER, " +
                KEY_ATTENDANCE_STUDENT_ID + " INTEGER, " +
                KEY_ATTENDANCE_STATUS + " TEXT " + ")";
        Log.d("queryAttendance", queryAttendance);

        try {
            db.execSQL(queryFaculty);
            db.execSQL(queryStudent);
            db.execSQL(queryAttendanceSession);
            db.execSQL(queryAttendance);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
        }
    }

    //facult crud
    public void addFaculty(FacultyBean facultyBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO faculty_table(faculty_name,faculty_qualif,faculty_mobilenumber,faculty_address,faculty_Email,faculty_gender,faculty_password) values ('" +
                facultyBean.getfaculty_name() + "', '" +
                facultyBean.getfaculty_qualif() + "', '" +
                facultyBean.getFaculty_mobilenumber() + "', '" +
                facultyBean.getFaculty_address() + "', '" +
                facultyBean.getFaculty_Email() + "', '" +
                facultyBean.getFaculty_gender() + "', '" +
                facultyBean.getFaculty_password() + "')";
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    public FacultyBean validateFaculty(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM faculty_table where faculty_Email='" + userName + "' and faculty_password='" + password + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            FacultyBean facultyBean = new FacultyBean();
            facultyBean.setFaculty_id(Integer.parseInt(cursor.getString(0)));
            facultyBean.setfaculty_name(cursor.getString(1));
            facultyBean.setfaculty_qualif(cursor.getString(2));
            facultyBean.setFaculty_mobilenumber(cursor.getString(3));
            facultyBean.setFaculty_address(cursor.getString(4));
            facultyBean.setFaculty_Email(cursor.getString(5));
            facultyBean.setFaculty_gender(cursor.getString(6));
            facultyBean.setFaculty_password(cursor.getString(7));
            return facultyBean;
        }
        return null;
    }

    public ArrayList<FacultyBean> getAllFaculty() {
        Log.d("in get all", "in get all");
        ArrayList<FacultyBean> list = new ArrayList<FacultyBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM faculty_table";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                FacultyBean facultyBean = new FacultyBean();
                facultyBean.setFaculty_id(Integer.parseInt(cursor.getString(0)));
                facultyBean.setfaculty_name(cursor.getString(1));
                facultyBean.setfaculty_qualif(cursor.getString(2));
                facultyBean.setFaculty_mobilenumber(cursor.getString(3));
                facultyBean.setFaculty_address(cursor.getString(4));
                facultyBean.setFaculty_Email(cursor.getString(5));
                facultyBean.setFaculty_gender(cursor.getString(6));
                facultyBean.setFaculty_password(cursor.getString(7));
                list.add(facultyBean);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteFaculty(int facultyId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM faculty_table WHERE faculty_id=" + facultyId;

        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    //student crud
    public void addStudent(StudentBean studentBean) {

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO student_table (student_name,student_gender,student_mobilenumber,student_address,student_dob,student_class) values ('" +
                studentBean.getStudent_name() + "', '" +
                studentBean.getStudent_gender() + "','" +
                studentBean.getStudent_mobilenumber() + "', '" +
                studentBean.getStudent_address() + "', '" +
                studentBean.getStudent_dob() + "', '" +
                studentBean.getStudent_class() + "')";
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    public ArrayList<StudentBean> getAllStudent() {
        ArrayList<StudentBean> list = new ArrayList<StudentBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM student_table";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                StudentBean studentBean = new StudentBean();
                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_name(cursor.getString(1));
                studentBean.setStudent_gender(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_dob(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));
                list.add(studentBean);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<StudentBean> getAllStudentByClass(String classs) {
        ArrayList<StudentBean> list = new ArrayList<StudentBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM student_table where student_class='" + classs + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                StudentBean studentBean = new StudentBean();
                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_name(cursor.getString(1));
                studentBean.setStudent_gender(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_dob(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));
                list.add(studentBean);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public StudentBean getStudentById(int studentId) {
        StudentBean studentBean = new StudentBean();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM student_table where student_id=" + studentId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                studentBean.setStudent_id(Integer.parseInt(cursor.getString(0)));
                studentBean.setStudent_name(cursor.getString(1));
                studentBean.setStudent_gender(cursor.getString(2));
                studentBean.setStudent_mobilenumber(cursor.getString(3));
                studentBean.setStudent_address(cursor.getString(4));
                studentBean.setStudent_dob(cursor.getString(5));
                studentBean.setStudent_class(cursor.getString(6));

            } while (cursor.moveToNext());
        }
        return studentBean;
    }

    public void deleteStudent(int studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String query = "DELETE FROM student_table WHERE student_id=" + studentId;

            Log.d("query", query);
            db.execSQL(query);
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //attendance session Table crud
    public int addAttendanceSession(AttendanceSessionBean attendanceSessionBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO attendance_session_table (attendance_session_faculty_id,attendance_session_class,attendance_session_date) values ('" +
                attendanceSessionBean.getAttendance_session_faculty_id() + "', '" +
                attendanceSessionBean.getAttendance_session_class() + "', '" +
                attendanceSessionBean.getAttendance_session_date() + "')";
        Log.d("query", query);
        db.execSQL(query);

        String query1 = "select max(attendance_session_id) from attendance_session_table";
        Cursor cursor = db.rawQuery(query1, null);

        if (cursor.moveToFirst()) {
            int sessionId = Integer.parseInt(cursor.getString(0));

            return sessionId;
        }


        db.close();
        return 0;
    }

    public ArrayList<AttendanceSessionBean> getAllAttendanceSession() {
        ArrayList<AttendanceSessionBean> list = new ArrayList<AttendanceSessionBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM attendance_session_table";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AttendanceSessionBean attendanceSessionBean = new AttendanceSessionBean();
                attendanceSessionBean.setAttendance_session_id(Integer.parseInt(cursor.getString(0)));
                attendanceSessionBean.setAttendance_session_faculty_id(Integer.parseInt(cursor.getString(1)));
                attendanceSessionBean.setAttendance_session_class(cursor.getString(2));
                attendanceSessionBean.setAttendance_session_date(cursor.getString(3));
                list.add(attendanceSessionBean);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteAttendanceSession(int attendanceSessionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM attendance_session_table WHERE attendance_session_id=" + attendanceSessionId;

        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }

    //attendance crud
    public void addNewAttendance(AttendanceBean attendanceBean) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "INSERT INTO attendance_table values (" +
                attendanceBean.getAttendance_session_id() + ", " +
                attendanceBean.getAttendance_student_id() + ", '" +
                attendanceBean.getAttendance_status() + "')";
        Log.d("query", query);
        db.execSQL(query);
        db.close();
    }


    public ArrayList<AttendanceBean> getAttendanceBySessionID(AttendanceSessionBean attendanceSessionBean) {
        int attendanceSessionId = 0;
        ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM attendance_session_table where attendance_session_faculty_id=" + attendanceSessionBean.getAttendance_session_faculty_id() + ""
                + "AND attendance_session_class='" + attendanceSessionBean.getAttendance_session_class() + "'" +
                " AND attendance_session_date='" + attendanceSessionBean.getAttendance_session_date() + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                attendanceSessionId = (Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        String query1 = "SELECT * FROM attendance_table where attendance_session_id=" + attendanceSessionId + " order by attendance_student_id";
        Cursor cursor1 = db.rawQuery(query1, null);
        if (cursor1.moveToFirst()) {
            do {
                AttendanceBean attendanceBean = new AttendanceBean();
                attendanceBean.setAttendance_session_id(Integer.parseInt(cursor1.getString(0)));
                attendanceBean.setAttendance_student_id(Integer.parseInt(cursor1.getString(1)));
                attendanceBean.setAttendance_status(cursor1.getString(2));
                list.add(attendanceBean);

            } while (cursor1.moveToNext());
        }
        return list;
    }

    public ArrayList<AttendanceBean> getTotalAttendanceBySessionID(AttendanceSessionBean attendanceSessionBean) {
        int attendanceSessionId = 0;
        ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM attendance_session_table where attendance_session_faculty_id=" + attendanceSessionBean.getAttendance_session_faculty_id() + ""
                + "AND attendance_session_class='" + attendanceSessionBean.getAttendance_session_class() + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                attendanceSessionId = (Integer.parseInt(cursor.getString(0)));

                String query1 = "SELECT * FROM attendance_table where attendance_session_id=" + attendanceSessionId + " order by attendance_student_id";
                Cursor cursor1 = db.rawQuery(query1, null);
                if (cursor1.moveToFirst()) {
                    do {
                        AttendanceBean attendanceBean = new AttendanceBean();
                        attendanceBean.setAttendance_session_id(Integer.parseInt(cursor1.getString(0)));
                        attendanceBean.setAttendance_student_id(Integer.parseInt(cursor1.getString(1)));
                        attendanceBean.setAttendance_status(cursor1.getString(2));
                        list.add(attendanceBean);

                    } while (cursor1.moveToNext());
                }

                AttendanceBean attendanceBean = new AttendanceBean();
                attendanceBean.setAttendance_session_id(0);
                attendanceBean.setAttendance_status("Date : " + cursor.getString(4));
                list.add(attendanceBean);

            } while (cursor.moveToNext());
        }


        return list;
    }

    public ArrayList<AttendanceBean> getAllAttendanceByStudent() {
        ArrayList<AttendanceBean> list = new ArrayList<AttendanceBean>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM attendance_table";
        //String query = "SELECT attendance_student_id,count(*) FROM attendance_table where attendance_status='P' group by attendance_student_id";

        Log.d("query", query);

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                //Log.d("studentId","studentId:"+cursor.getString(0)+", Count:"+cursor.getString(1));
                AttendanceBean attendanceBean = new AttendanceBean();
                attendanceBean.setAttendance_session_id(Integer.parseInt(cursor.getString(0)));
                attendanceBean.setAttendance_student_id(Integer.parseInt(cursor.getString(1)));
                attendanceBean.setAttendance_status(cursor.getString(2));
                list.add(attendanceBean);

            } while (cursor.moveToNext());
        }
        return list;
    }
}