package inventory;

import java.sql.Date;

public class salesData {
    private Integer customer_id;
    private String product_id;
    private String category;
    private String weight;
    private Double net_weight;
    private String length;
    private String karat;
    private Double gold_rate;
    private Double price;
    private Double return_value;
    private Date date;

    public salesData(Integer customer_id, String product_id, String category, String weight, Double net_weight, String length, String karat, Double gold_rate, Double price, Double return_value, Date date) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.category = category;
        this.weight = weight;
        this.net_weight = net_weight;
        this.length = length;
        this.karat = karat;
        this.gold_rate = gold_rate;
        this.price = price;
        this.return_value = return_value;
        this.date = date;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getCategory() {
        return category;
    }

    public String getWeight() {
        return weight;
    }

    public Double getNet_weight() {
        return net_weight;
    }

    public String getLength() {
        return length;
    }

    public String getKarat() {
        return karat;
    }

    public Double getGold_rate() {
        return gold_rate;
    }

    public Double getPrice() {
        return price;
    }

    public Double getReturn_value() {
        return return_value;
    }


    public Date getDate() {
        return date;
    }
}
