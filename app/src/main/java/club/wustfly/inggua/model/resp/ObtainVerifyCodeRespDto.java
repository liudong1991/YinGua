package club.wustfly.inggua.model.resp;

import club.wustfly.inggua.model.RespDto;

public class ObtainVerifyCodeRespDto extends RespDto {


    /**
     * Message : OK
     * RequestId : D372026A-805B-4D04-9286-4D95B4799540
     * BizId : 256315453829843833^0
     * Code : OK
     */

    private String Message;
    private String Code;
    private String RequestId;
    private String BizId;
    private String from;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        super.setMsg(message);
        Message = message;
    }

    @Override
    public String getCode() {
        return Code;
    }

    @Override
    public void setCode(String code) {
        super.setCode(code);
        Code = code;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String RequestId) {
        this.RequestId = RequestId;
    }

    public String getBizId() {
        return BizId;
    }

    public void setBizId(String BizId) {
        this.BizId = BizId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
