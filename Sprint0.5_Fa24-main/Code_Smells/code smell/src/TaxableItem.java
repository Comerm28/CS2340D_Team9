
public class TaxableItem extends Item {
    private double taxRate;

    public TaxableItem(String name, double price, int quantity, DiscountType discountType, double discountAmount,
            double taxRate) {
        super(name, price, quantity, discountType, discountAmount);
        this.taxRate = taxRate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    @Override
    public double calculateDiscountedPrice() {
        return (1 + (taxRate / 100.0)) * super.getPrice();
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

        cost += taxRate * price;

        return cost * quantity;
    }

    // double total = 0.0;
    // for (Item item : items) {
    // double price = item.getPrice();
    // switch (item.getDiscountType()) {
    // case PERCENTAGE:
    // price -= item.getDiscountAmount() * price;
    // break;
    // case AMOUNT:
    // price -= item.getDiscountAmount();
    // break;
    // default:
    // // no discount
    // break;
    // }
    // total += price * item.getQuantity();
    // if (item instanceof TaxableItem) {
    // TaxableItem taxableItem = (TaxableItem) item;
    // double tax = taxableItem.getTaxRate() / 100.0 * item.getPrice();
    // total += tax;
    // }
    // }
}
