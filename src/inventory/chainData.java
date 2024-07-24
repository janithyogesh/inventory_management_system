package inventory;

import java.sql.Date;


public class chainData {
    private String productId;
    private String category;
    private String weight;
    private Double net_weight;
    private String length;
    private String karat;
    private Double gold_rate;
    private String supplier;
    private String status;
    private String image;
    private Date date;


    public chainData(String productId, String category, String weight, Double net_weight, String length, String karat, Double gold_rate, String supplier, String status, String image, Date date) {
        //this.product_id = product_id;
        this.productId = productId;
        this.category = category;
        this.weight = weight;
        this.net_weight = net_weight;
        this.length = length;
        this.karat = karat;
        this.gold_rate = gold_rate;
        this.supplier = supplier;
        this.status = status;
        this.image = image;
        this.date = date;
    }

    public String getProductId() {
        return productId;
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

    public String getSupplier() {
        return supplier;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }


}
