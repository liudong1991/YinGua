package club.wustfly.inggua.model.resp;

import java.util.List;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.DocumentItem;

public class ObtainMyDocumentRespDto extends RespDto {


    private List<DocumentItem> data;

    public List<DocumentItem> getData() {
        return data;
    }

    public void setData(List<DocumentItem> data) {
        this.data = data;
    }
}
