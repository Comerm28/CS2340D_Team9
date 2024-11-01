public class NormalItem extends Item {
    public NormalItem(String name, double price, int quantity, DiscountType discountType, double discountAmount){
        super(name, price, quantity, discountType, discountAmount);
    }

    @Override
    public double calculateDiscountedPrice() {
        return super.getPrice();
    }
}
