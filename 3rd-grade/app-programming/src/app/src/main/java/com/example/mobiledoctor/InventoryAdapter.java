package com.example.mobiledoctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * RecyclerView.Adapter that displays a list of inventory items (strings)
 * and supports filtering via a search query.
 */
public class InventoryAdapter
        extends RecyclerView.Adapter<InventoryAdapter.ViewHolder>
        implements Filterable {

    private static final String TAG = "InventoryAdapter";

    // 기본 텍스트 색상
    private static final int COLOR_DEFAULT   = 0xFF000000; // 검정
    private static final int COLOR_SOLD_OUT  = 0xFFFF0000; // 빨강

    // 원본 전체 데이터, 필터링 후 데이터
    private final List<String> originalItems;
    private final List<String> filteredItems;

    private final SharedPreferences sharedPreferences;

    // 실제 필터 로직을 담은 객체
    private final Filter inventoryFilter = new InventoryFilter();

    /**
     * @param context 앱의 Context
     * @param items   재고 항목 문자열 리스트
     */

    public InventoryAdapter(Context context, List<String> items) {
        this.originalItems = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
    }

    // 뷰 홀더 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    // 뷰 홀더에 데이터 바인딩
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = filteredItems.get(position);
        holder.textView.setText(item);

        Log.d(TAG, "bind pos=" + position + " → " + item);

        // “품절” 포함 시 빨간색, 아니면 검정색
        holder.textView.setTextColor(
                item.contains("품절") ? COLOR_SOLD_OUT : COLOR_DEFAULT
        );

        // 돋보기 상태 적용
        applyZoomState(holder);
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    // Filterable 구현: 외부에서 호출되는 필터 메서드
    @Override
    public Filter getFilter() {
        return inventoryFilter;
    }

    // 뷰 홀더 정의
    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    // 실제 필터링 로직은 이 내부 클래스로 분리
    private class InventoryFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint == null
                    ? ""
                    : constraint.toString().trim().toLowerCase(Locale.getDefault());

            List<String> resultList = new ArrayList<>();
            if (query.isEmpty()) {
                resultList.addAll(originalItems);
            } else {
                for (String item : originalItems) {
                    if (item.toLowerCase(Locale.getDefault()).contains(query)) {
                        resultList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = resultList;
            results.count  = resultList.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems.clear();
            filteredItems.addAll((List<String>) results.values);
            notifyDataSetChanged();
        }
    }

    // 돋보기 상태 적용 (TextView 크기 변경)
    private void applyZoomState(ViewHolder holder) {
        // SharedPreferences에서 돋보기 상태 가져오기
        boolean isZoomEnabled = sharedPreferences.getBoolean("isZoomEnabled", false);
        float zoomSize = isZoomEnabled ? 34f : 20f;

        // TextView 크기 조정
        holder.textView.setTextSize(zoomSize);
    }
}
