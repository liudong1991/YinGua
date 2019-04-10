package club.wustfly.inggua.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import club.wustfly.inggua.R;
import club.wustfly.inggua.model.bean.DocumentItem;
import club.wustfly.inggua.ui.EditPrintActivity;

public class MyDocumentAdapter extends RecyclerView.Adapter<MyDocumentAdapter.ViewHolder> {

    Context mContext;
    List<DocumentItem> list;

    List<Boolean> status;

    TextView mPrintBtn;

    boolean isPatch = false;

    int type;

    public MyDocumentAdapter(Context context, List<DocumentItem> list, TextView printBtn, int type) {
        this.mContext = context;
        this.list = list;
        this.mPrintBtn = printBtn;
        this.type = type;
        initStatus();
    }

    void initStatus() {
        if (status == null) {
            status = new ArrayList<>();
        } else {
            status.clear();
        }
        for (int i = 0; i < list.size(); i++) {
            status.add(false);
        }
    }

    public void printByBatch() {
        isPatch = true;
        notifyDataSetChanged();
    }

    public void cancelPrintByBatch() {
        initStatus();
        isPatch = false;
        notifyDataSetChanged();
        printBtnStatus();
    }

    public void selectAll() {
        for (int i = 0; i < status.size(); i++) {
            status.set(i, true);
        }
        notifyDataSetChanged();
        printBtnStatus();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_document_recycler_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        if (isPatch) {
            viewHolder.doc_select_logo.setVisibility(View.VISIBLE);
            viewHolder.doc_select_logo.setImageResource(status.get(i) ? R.mipmap.doc_selected_logo : R.mipmap.doc_unselect_logo);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.doc_select_logo.setImageResource(!status.get(i) ? R.mipmap.doc_selected_logo : R.mipmap.doc_unselect_logo);
                    status.set(i, !status.get(i));
                    printBtnStatus();
                }
            });
        } else {
            viewHolder.doc_select_logo.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EditPrintActivity.class);
                    intent.putExtra("fid", list.get(i).getId() + "");
                    mContext.startActivity(intent);
                }
            });
        }

        viewHolder.fileNameTxt.setText(list.get(i).getFilename());
        viewHolder.fileTimeTxt.setText(list.get(i).getAddtime());
        viewHolder.pagesTxt.setText(list.get(i).getPage() + "页");
        switch (type) {
            case 1:
                viewHolder.logo.setImageResource(R.mipmap.doc_logo);
                break;
            case 2:
                viewHolder.logo.setImageResource(R.mipmap.pdf_logo);
                break;
            case 3:
                viewHolder.logo.setImageResource(R.mipmap.ppt_logo);
                break;
        }

    }

    private void printBtnStatus() {
        boolean flag = false;
        for (boolean b : status) {
            if (b) {
                flag = true;
                break;
            }
        }
        if (flag) {
            mPrintBtn.setBackgroundResource(R.drawable.my_doc_print_btn_bg_enable);
            mPrintBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, EditPrintActivity.class);
                    intent.putExtra("fid", assembleFid());
                    mContext.startActivity(intent);
                }
            });
        } else {
            mPrintBtn.setBackgroundResource(R.drawable.my_doc_print_btn_bg_disable);
            mPrintBtn.setOnClickListener(null);
        }


    }

    public String assembleFid() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (status.get(i)) {
                sb.append(list.get(i).getId());
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView logo;
        TextView fileNameTxt;
        TextView pagesTxt;
        TextView fileTimeTxt;
        ImageView doc_select_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            fileNameTxt = itemView.findViewById(R.id.file_name);
            pagesTxt = itemView.findViewById(R.id.pages);
            fileTimeTxt = itemView.findViewById(R.id.file_time);
            doc_select_logo = itemView.findViewById(R.id.doc_select_logo);
        }
    }

    public void addDocumentItem(List<DocumentItem> dataList) {
        if (dataList == null) return;
        list.clear();
        list.addAll(dataList);
        initStatus();
        notifyDataSetChanged();
    }

    public void delete() {
        Iterator<DocumentItem> listIter = list.iterator();
        Iterator<Boolean> statusIter = status.iterator();
        while (statusIter.hasNext()) {
            listIter.next();
            if (statusIter.next()) {
                statusIter.remove();
                listIter.remove();
            }
        }

        notifyDataSetChanged();
    }
}
