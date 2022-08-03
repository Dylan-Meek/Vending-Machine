import java.math.BigDecimal;

public class Items {

    private String name;
    private String location;
    // types: chip, candy, drink, gum
    private String type;
    private BigDecimal price;
    private int quantity = 5;

    public Items(String name, String location, String type, BigDecimal price) {
        this.name = name;
        this.location = location;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void soundOutput () {
        /*
         * Displays sound relevant to item type
         * Might go in selectProductMenu()*/
        if(this.type.equalsIgnoreCase("candy")) {
            System.out.println("Munch Munch, Yum!");
        }
        else if(this.type.equalsIgnoreCase("chip")) {
            System.out.println("Crunch Crunch, Yum!");
        }
        else if(this.type.equalsIgnoreCase("drink")) {
            System.out.println("Glug Glug, Yum!");
        }
        else if(this.type.equalsIgnoreCase("gum")) {
            System.out.println("Chew Chew, Yum!");
        }
    }

} // class
