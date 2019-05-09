package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class GoodItem extends BaseModel {


    /**
     * id : 3
     * size : A4
     * issingle : 单页
     * color : 黑白
     * layout : 每版2页
     * price : 25
     * binding : 右上角装订
     * packfree : 5
     */

    private Integer id;
    private String size;
    private String issingle;
    private String color;
    private String layout;
    private String price;
    private String binding;
    private String packfree;

    public GoodItem() {
    }

    public GoodItem(GoodItem item) {
        this.size = item.getSize();
        this.issingle = item.getIssingle();
        this.color = item.getColor();
        this.layout = item.getLayout();
        this.binding = item.getBinding();
    }

    public GoodItem(String size, String issingle, String color, String layout, String binding) {
        this.size = size;
        this.issingle = issingle;
        this.color = color;
        this.layout = layout;
        this.binding = binding;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIssingle() {
        return issingle;
    }

    public void setIssingle(String issingle) {
        this.issingle = issingle;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getPackfree() {
        return packfree;
    }

    public void setPackfree(String packfree) {
        this.packfree = packfree;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GoodItem) {
            GoodItem item = (GoodItem) obj;
            return this.size.equals(item.getSize())
                    && this.issingle.equals(item.getIssingle())
                    && this.color.equals(item.getColor())
                    && this.layout.equals(item.getLayout())
                    && this.binding.equals(item.getBinding());
        }
        return super.equals(obj);
    }
}
