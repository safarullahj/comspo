package com.mspo.comspo.ui.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.mspo.comspo.R;
import com.mspo.comspo.data.remote.model.responses.result_sheet.Chapter;
import com.mspo.comspo.data.remote.model.responses.result_sheet.ResultSheetResponse;

public class MyTableViewAdapter extends AbstractTableAdapter<String,String,String> {

    private Context context;
    private ResultSheetResponse sheetResponse;

    public MyTableViewAdapter(Context context, ResultSheetResponse sheetResponse) {
        super(context);
        this.context = context;
        this.sheetResponse = sheetResponse;
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.table_item,
                parent, false);
        return new MyCellViewHolder(layout);
    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {

        Log.e("tbl_:" , "coloum_pos :"+columnPosition+" value_: "+(String)cellItemModel);


        MyCellViewHolder viewHolder = (MyCellViewHolder) holder;
        if(columnPosition == 0){
            viewHolder.container.setGravity(Gravity.START);
        }
        viewHolder.cell_textview.setText((String)cellItemModel);
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout
                .table_coloum_head, parent, false);

        return new MyColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {

        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.head_textview.setText((String)columnHeaderItemModel);


    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout
                .table_row_head, parent, false);

        return new MyColumnHeaderViewHolder(layout);
    }

    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {

        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.head_textview.setText((String)rowHeaderItemModel);
    }

    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(context).inflate(R.layout.table_view_corner_layout, null);
    }

    class MyCellViewHolder extends AbstractViewHolder {

        LinearLayout container ;
        public final AppCompatTextView cell_textview;

        public MyCellViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            cell_textview = itemView.findViewById(R.id.textView_table);
        }
    }

    class MyColumnHeaderViewHolder extends AbstractViewHolder {

        public final AppCompatTextView head_textview;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            head_textview = itemView.findViewById(R.id.textView_table);
        }
    }

}
