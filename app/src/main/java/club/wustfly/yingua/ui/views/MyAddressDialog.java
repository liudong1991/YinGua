package club.wustfly.yingua.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;

import club.wustfly.yingua.model.bean.Address;

public class MyAddressDialog extends MyDialog {

    public MyAddressDialog(@NonNull Context context, MyDialogType type, Address address) {
        super(context, type);
        setAddress(address);
    }
}
