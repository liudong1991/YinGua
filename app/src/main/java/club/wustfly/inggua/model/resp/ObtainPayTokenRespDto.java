package club.wustfly.inggua.model.resp;

import club.wustfly.inggua.model.RespDto;

public class ObtainPayTokenRespDto extends RespDto {

    private int paymode;

    private String data;

    public int getPaymode() {
        return paymode;
    }

    public void setPaymode(int paymode) {
        this.paymode = paymode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
