package intersoft.com.androidfs.Const;

/**
 * Created by user on 8/29/2017.
 */

public class ApparelItems {

    public static final String FIELD_TITLE = "title";
    public static final String FIELD_PRICE = "price";
    public static final String FIELD_STOCK = "stock";
    public static final String FIELD_THUMBNAIL = "thumbnail";
    public static final String FIELD_BRAND = "brand";
    public static final String FIELD_RATING = "ratings";
    public static final String FIELD_SELLER = "seller";

    private String name;
    private int price;
    private String stock;
    private String thumbnail;
    private String brand;
    private String ratings;
    private String seller;

    public ApparelItems(){

    }
    public ApparelItems(String name, int price, String stock,String thumbnail, String ratings,String brand,String seller){
        this.brand = brand;
        this.ratings = ratings;
        this.thumbnail = thumbnail;
        this.stock = stock;
        this.price = price;
        this.name = name;
        this.seller = seller;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }


}
