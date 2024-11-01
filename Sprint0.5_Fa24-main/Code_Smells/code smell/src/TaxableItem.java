
public class TaxableItem extends Item {
    private double taxRate;
    
    public TaxableItem(String name, double price, int quantity, DiscountType discountType, double discountAmount, double taxRate){
        super(name, price, quantity, discountType, discountAmount);
        this.taxRate = taxRate;
    }

    public double getTaxRate(){
        return taxRate;
    }

    @Override
    public double calculateDiscountedPrice() {
        return (1 + (taxRate / 100.0)) * super.getPrice();
    }
}
