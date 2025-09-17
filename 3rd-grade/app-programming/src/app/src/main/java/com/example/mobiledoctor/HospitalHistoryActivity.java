package com.example.mobiledoctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class HospitalHistoryActivity extends BaseActivity {

    // 1) 모델 정의: 한 번만 선언
    static class HospitalRecord {
        long id;
        String patientName, dob, diagnosis, visitDate, symptom, medication;
        int cost;
        HospitalRecord(long i, String n, String d, String diag,
                       String vd, String s, int c, String m) {
            id = i; patientName = n; dob = d;
            diagnosis = diag; visitDate = vd;
            symptom = s; cost = c; medication = m;
        }
    }

    // 2) 어댑터 정의: SharedPreferences 기반 돋보기 적용
    static class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.VH> {
        private final List<HospitalRecord> items;
        private final SharedPreferences    prefs;

        // ViewHolder: 레이블 + 값 모두 참조
        static class VH extends RecyclerView.ViewHolder {
            TextView tvVisitDate, tvCost;
            TextView tvLabelDiagnosis, tvDiagnosis;
            TextView tvLabelSymptom,   tvSymptom;
            TextView tvLabelMedication, tvMedication;

            VH(View v) {
                super(v);
                tvVisitDate       = v.findViewById(R.id.tv_visit_date);
                tvCost             = v.findViewById(R.id.tv_cost);
                tvLabelDiagnosis   = v.findViewById(R.id.tv_label_diagnosis);
                tvDiagnosis        = v.findViewById(R.id.tv_diagnosis);
                tvLabelSymptom     = v.findViewById(R.id.tv_label_symptom);
                tvSymptom          = v.findViewById(R.id.tv_symptom);
                tvLabelMedication  = v.findViewById(R.id.tv_label_medication);
                tvMedication       = v.findViewById(R.id.tv_medication);
            }
        }

        RecordAdapter(Context context, List<HospitalRecord> l) {
            items = l;
            prefs = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        }

        void updateData(List<HospitalRecord> l) {
            items.clear();
            items.addAll(l);
            notifyDataSetChanged();
        }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_record, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH h, int pos) {
            HospitalRecord r = items.get(pos);
            h.tvVisitDate .setText(r.visitDate);
            h.tvDiagnosis .setText(r.diagnosis);
            h.tvSymptom   .setText(r.symptom);
            h.tvMedication.setText(r.medication);
            h.tvCost       .setText(r.cost + "원");

            // 돋보기 적용
            boolean zoom = prefs.getBoolean("isZoomEnabled", false);
            float size = zoom ? 30f : 16f;
            h.tvVisitDate       .setTextSize(size);
            h.tvCost            .setTextSize(size);
            h.tvLabelDiagnosis  .setTextSize(size);
            h.tvDiagnosis       .setTextSize(size);
            h.tvLabelSymptom    .setTextSize(size);
            h.tvSymptom         .setTextSize(size);
            h.tvLabelMedication .setTextSize(size);
            h.tvMedication      .setTextSize(size);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    // 액티비티 멤버 변수
    private String searchName, searchDob;
    private TextInputEditText etName, etDob;
    private MaterialButton     btnSearch;
    private RecyclerView       rvRecords;
    private RecordAdapter      adapter;
    private HospitalDbHelper   dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_history);

        // 돋보기 상태 적용 (BaseActivity)
        applyZoomState();

        // 툴바 세팅
        MaterialToolbar toolbar = findViewById(R.id.toolbar_history);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 뷰 초기화
        etName    = findViewById(R.id.et_name);
        etDob     = findViewById(R.id.et_dob);
        btnSearch = findViewById(R.id.btn_search);
        rvRecords = findViewById(R.id.rv_records);

        // DB & 어댑터 세팅
        dbHelper = new HospitalDbHelper(this);
        adapter  = new RecordAdapter(this, new ArrayList<>());
        rvRecords.setLayoutManager(new LinearLayoutManager(this));
        rvRecords.setAdapter(adapter);

        // 조회 버튼
        btnSearch.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String dob  = etDob.getText().toString().trim();
            if (name.isEmpty() || dob.isEmpty()) {
                Toast.makeText(this, "이름과 생년월일을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            List<HospitalRecord> list = dbHelper.queryByPatient(name, dob);
            adapter.updateData(list);
            if (list.isEmpty())
                Toast.makeText(this, "조회된 기록이 없습니다.", Toast.LENGTH_SHORT).show();

            // 결과 전달용 데이터 저장
            searchName = name;
            searchDob  = dob;
            Intent result = new Intent()
                    .putExtra("patient_name", searchName)
                    .putExtra("dob", searchDob);
            setResult(RESULT_OK, result);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // 돌아가기
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();  // 결과(이미 setResult)와 함께 종료
    }

    // 3) DB 헬퍼
    static class HospitalDbHelper extends SQLiteOpenHelper {
        private static final String DB_NAME    = "hospital.db";
        private static final int    DB_VERSION = 2;
        private static final String TABLE_NAME = "hospital_history";

        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "patient_name TEXT NOT NULL, " +
                        "dob TEXT NOT NULL, " +
                        "diagnosis TEXT, " +
                        "visit_date TEXT, " +
                        "symptom TEXT, " +
                        "cost INTEGER, " +
                        "medication TEXT" +
                        ");";

        HospitalDbHelper(Context ctx) {
            super(ctx, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);

            db.execSQL(
                    "INSERT INTO " + TABLE_NAME +
                            " (patient_name, dob, diagnosis, visit_date, symptom, cost, medication) VALUES " +
                            // 박재현 (dob=2002-12-04)
                            "('박재현','2002-12-04','급성 상기도 감염','2025-04-01','감기',6000,'감기약')," +
                            "('박재현','2002-12-04','긴장성 두통','2025-04-03','두통',7000,'진통제')," +
                            "('박재현','2002-12-04','제1형 과민반응','2025-04-06','알레르기',5000,'항히스타민')," +
                            "('박재현','2002-12-04','기능성 소화불량','2025-04-09','소화불량',4000,'소화제')," +
                            "('박재현','2002-12-04','근육통','2025-04-12','근육통',7500,'근육이완제')," +
                            "('박재현','2002-12-04','급성 위염','2025-04-15','위염',8000,'위장약')," +
                            "('박재현','2002-12-04','결막염','2025-04-18','눈병',5000,'안약')," +
                            "('박재현','2002-12-04','알레르기 비염','2025-04-21','비염',6500,'비염약')," +
                            "('박재현','2002-12-04','요추염좌','2025-04-24','허리통증',7200,'진통제')," +
                            "('박재현','2002-12-04','접촉성 피부염','2025-04-27','피부염',6600,'피부약')," +
                            "('박재현','2002-12-04','급성 기관지염','2025-05-01','기침',5500,'기침약')," +
                            "('박재현','2002-12-04','불면증','2025-05-04','불면증',9000,'수면제')," +
                            "('박재현','2002-12-04','요추통증','2025-05-07','요통',7800,'근육이완제')," +
                            "('박재현','2002-12-04','급성 위장관염','2025-05-10','설사',4300,'지사제')," +
                            "('박재현','2002-12-04','급성 스트레스 반응','2025-05-13','스트레스',9500,'신경안정제')," +
                            "('박재현','2002-12-04','고열','2025-05-16','발열',6100,'해열제')," +
                            "('박재현','2002-12-04','특발성 두드러기','2025-05-19','두드러기',5200,'항히스타민')," +
                            "('박재현','2002-12-04','현훈','2025-05-22','현기증',5600,'어지럼증약')," +
                            "('박재현','2002-12-04','급성 기관지염','2025-05-25','기관지염',8800,'항생제')," +
                            "('박재현','2002-12-04','위장통증','2025-05-28','위통',6900,'위장약')," +

                            // 문지훈 (dob=2002-07-11)
                            "('문지훈','2002-07-11','급성 상기도 감염','2025-04-01','감기',6000,'감기약')," +
                            "('문지훈','2002-07-11','긴장성 두통','2025-04-03','두통',7000,'진통제')," +
                            "('문지훈','2002-07-11','위장통증','2025-05-28','위통',6900,'위장약');"
            );
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        List<HospitalRecord> queryAllDescending() {
            SQLiteDatabase db = getReadableDatabase();
            List<HospitalRecord> list = new ArrayList<>();
            Cursor c = db.query(
                    TABLE_NAME, null, null, null, null, null, "visit_date DESC"
            );
            while (c.moveToNext()) {
                list.add(new HospitalRecord(
                        c.getLong(c.getColumnIndexOrThrow("_id")),
                        c.getString(c.getColumnIndexOrThrow("patient_name")),
                        c.getString(c.getColumnIndexOrThrow("dob")),
                        c.getString(c.getColumnIndexOrThrow("diagnosis")),
                        c.getString(c.getColumnIndexOrThrow("visit_date")),
                        c.getString(c.getColumnIndexOrThrow("symptom")),
                        c.getInt(c.getColumnIndexOrThrow("cost")),
                        c.getString(c.getColumnIndexOrThrow("medication"))
                ));
            }
            c.close();
            return list;
        }

        List<HospitalRecord> queryByPatient(String name, String dob) {
            SQLiteDatabase db = getReadableDatabase();
            List<HospitalRecord> list = new ArrayList<>();
            Cursor c = db.query(
                    TABLE_NAME,
                    null,
                    "patient_name = ? AND dob = ?",
                    new String[]{ name, dob },
                    null, null,
                    "visit_date DESC"
            );
            while (c.moveToNext()) {
                list.add(new HospitalRecord(
                        c.getLong(c.getColumnIndexOrThrow("_id")),
                        c.getString(c.getColumnIndexOrThrow("patient_name")),
                        c.getString(c.getColumnIndexOrThrow("dob")),
                        c.getString(c.getColumnIndexOrThrow("diagnosis")),
                        c.getString(c.getColumnIndexOrThrow("visit_date")),
                        c.getString(c.getColumnIndexOrThrow("symptom")),
                        c.getInt(c.getColumnIndexOrThrow("cost")),
                        c.getString(c.getColumnIndexOrThrow("medication"))
                ));
            }
            c.close();
            return list;
        }
    }
}
