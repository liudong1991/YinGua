package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class ObtainPayTokenParam extends BaseModel {

    private int paymode;
    private int oid;

    public int getPaymode() {
        return paymode;
    }

    public void setPaymode(int paymode) {
        this.paymode = paymode;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }
}
