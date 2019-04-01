package club.wustfly.inggua.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;

import club.wustfly.inggua.model.bean.Address;

public class MyAddressDialog extends MyDialog {

    public MyAddressDialog(@NonNull Context context, MyDialogType type, Address address) {
        super(context, type);
        setAddress(address);
    }
}
