package com.example.mobiledoctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MedicineAdapter extends ArrayAdapter<Medicine> {

    private final LayoutInflater inflater;
    private final SharedPreferences sharedPreferences;

    // 생성자에서 Context를 받아 SharedPreferences를 초기화합니다.
    public MedicineAdapter(@NonNull Context context, @NonNull List<Medicine> meds) {
        super(context, 0, meds);
        this.inflater = LayoutInflater.from(context);
        this.sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_medicine, parent, false);
        }

        Medicine m = getItem(pos);  // 현재 아이템(약) 객체 가져오기

        // 각 TextView 연결
        TextView tvName = convertView.findViewById(R.id.tvMedName);
        TextView tvEfficacy = convertView.findViewById(R.id.tvMedEfficacy);
        TextView tvUsage = convertView.findViewById(R.id.tvMedUsage);
        TextView tvPrice = convertView.findViewById(R.id.tvMedPrice);

        if (m != null) {
            // 약 정보 설정
            tvName.setText(m.getName());
            tvEfficacy.setText("효능: " + m.getEfficacy());
            tvUsage.setText("복용법: " + m.getUsage());
            tvPrice.setText("가격: " + m.getPrice());
        }

        // 돋보기 상태 적용
        applyZoomState(tvName, tvEfficacy, tvUsage, tvPrice);

        return convertView;
    }

    private void applyZoomState(TextView... textViews) {
        // SharedPreferences에서 돋보기 상태 가져오기
        boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);
        float zoomSize = isZoomEnabled ? 30f : 16f;

        // 각 TextView의 크기를 돋보기 상태에 맞게 설정
        for (TextView textView : textViews) {
            textView.setTextSize(zoomSize);
        }
    }
}
