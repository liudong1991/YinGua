package club.wustfly.yingua.model.resp;

import club.wustfly.yingua.model.RespDto;
import club.wustfly.yingua.model.bean.OrderItem;

public class SelectPayRespDto extends RespDto {

    private OrderItem orderItem;

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
