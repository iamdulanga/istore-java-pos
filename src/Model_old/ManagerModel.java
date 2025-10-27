package Model;

public abstract class ManagerModel{

    private int itemid;
    private String name;
    private String cat;
    private int qty;
    private int price;

public  ManagerModel(int itemid, String name, int qty, int price, String cat) {
        this.itemid = itemid;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.cat = cat;
}

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}