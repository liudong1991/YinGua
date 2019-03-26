package club.wustfly.yingua.ui.adapter;

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
import java.util.List;

import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.EditPrintActivity;

public class MyDocumentAdapter extends RecyclerView.Adapter<MyDocumentAdapter.ViewHolder> {

    Context mContext;
    List<String> list;

    List<Boolean> status;

    TextView mPrintBtn;

    boolean isPatch = false;

    public MyDocumentAdapter(Context context, List<String> list, TextView printBtn) {
        this.mContext = context;
        this.list = list;
        this.mPrintBtn = printBtn;
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
            viewHolder.doc_select_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewHolder.doc_select_logo.setImageResource(!status.get(i) ? R.mipmap.doc_selected_logo : R.mipmap.doc_unselect_logo);
                    status.set(i, !status.get(i));

                    printBtnStatus();
                }
            });

            viewHolder.itemView.setOnClickListener(null);

        } else {
            viewHolder.doc_select_logo.setVisibility(View.GONE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, EditPrintActivity.class));
                }
            });
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
                    mContext.startActivity(new Intent(mContext, EditPrintActivity.class));
                }
            });
        } else {
            mPrintBtn.setBackgroundResource(R.drawable.my_doc_print_btn_bg_disable);
            mPrintBtn.setOnClickListener(null);
        }
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
            fileNameTxt = itemView.findViewById(R.id.file_time);
            doc_select_logo = itemView.findViewById(R.id.doc_select_logo);
        }
    }
}
