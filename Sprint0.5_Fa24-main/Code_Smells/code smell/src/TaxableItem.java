
public class TaxableItem extends Item implements Taxable {
    private double taxRate;

    public TaxableItem(String name, double price, int quantity, DiscountType discountType, double discountAmount,
                       double taxRate) {
        super(name, price, quantity, discountType, discountAmount);
        this.taxRate = taxRate;
    }

    @Override
    public double getNetCost() {
        double cost = getDiscountedPrice() * quantity;
        //add tax to cost
        cost += calculateTax();
        return cost;
    }

    public double calculateTax() {
        return taxRate / 100.0 * price;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }
}
