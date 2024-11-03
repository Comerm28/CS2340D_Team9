public class NormalItem extends Item {
    public NormalItem(String name, double price, int quantity, DiscountType discountType, double discountAmount) {
        super(name, price, quantity, discountType, discountAmount);
    }

    @Override
    public double calculateDiscountedPrice() {
        return super.getPrice();
    }

    @Override
    public double getNetCost() {
        double cost = price;
        switch (discountType) {
            case PERCENTAGE:
                cost -= discountAmount * price;
                break;
            case AMOUNT:
                cost -= discountAmount;
                break;
        }

        return cost * quantity;
    }

}
