package club.wustfly.yingua.ui.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import club.wustfly.yingua.R;

public class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setTitle(String title) {
        TextView title_txt = getView().findViewById(R.id.title);
        title_txt.setText(title);
    }

    protected void setHeaderBackgroundColor(String colorStr) {
        getView().findViewById(R.id.header).setBackgroundColor(Color.parseColor(colorStr));
    }

    protected void setHeaderTopMargin() {
        View header = getView().findViewById(R.id.header);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) header.getLayoutParams();
        lp.topMargin = BaseActivity.getStatusBarHeight(getActivity());
        header.setLayoutParams(lp);
    }

    protected void setHeaderTopPadding() {
        View header = getView().findViewById(R.id.header);
        header.setPadding(0, BaseActivity.getStatusBarHeight(getActivity()), 0, 0);
    }
}
