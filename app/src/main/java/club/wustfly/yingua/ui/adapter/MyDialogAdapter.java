package club.wustfly.yingua.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.views.MyDialog;

public class MyDialogAdapter extends RecyclerView.Adapter<MyDialogAdapter.ViewHolder> {

    Context mContext;
    List<String> data;
    MyDialog.MyDialogType type;
    private int selectedIndex;

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public MyDialogAdapter(Context mContext, MyDialog.MyDialogType type, int selectedIndex) {
        this.mContext = mContext;
        this.data = Arrays.asList(type.getItems());
        this.type = type;
        this.selectedIndex = selectedIndex;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.my_dialog_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.check_img.setImageResource(i == selectedIndex ? R.mipmap.my_dialog_item_selected : R.mipmap.my_dialog_item_unselected);
        viewHolder.item_content.setText(data.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedIndex = i;
                notifyDataSetChanged();
            }
        });

        if (type == MyDialog.MyDialogType.SELCT_BOOKBINDING) {
            LinearLayout view = viewHolder.itemView.findViewById(R.id.item_layout);
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.width = 400;
            view.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView check_img;
        TextView item_content;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check_img = itemView.findViewById(R.id.check_img);
            item_content = itemView.findViewById(R.id.item_content);
        }
    }
}
