public abstract class Item {
    protected String name;
    protected double price;
    protected int quantity;
    protected DiscountType discountType;
    protected double discountAmount;

    public Item(String name, double price, int quantity, DiscountType discountType, double discountAmount) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
    }

    public abstract double getNetCost();

    public double getDiscountedPrice() {
        //apply discount
        return applyDiscount();
    }

    public double applyDiscount() {
        if (discountType == DiscountType.PERCENTAGE) {
            return price - discountAmount * price;
        } else if (discountType == DiscountType.AMOUNT) {
            return price - discountAmount;
        } else {
            return price;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
}
