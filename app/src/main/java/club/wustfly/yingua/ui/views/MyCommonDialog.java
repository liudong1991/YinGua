package club.wustfly.yingua.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Map;

public class MyCommonDialog extends MyDialog {
    public MyCommonDialog(@NonNull Context context, MyDialogType type, Map<MyDialogType, Integer> status) {
        super(context, type);
        setStatus(status);
    }
}
