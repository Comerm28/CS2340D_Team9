import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Item item1 = new NormalItem("Book", 20, 1, DiscountType.AMOUNT, 5);
        Item item2 = new TaxableItem("Laptop", 1000, 1, DiscountType.PERCENTAGE, 0.1, 7);
        Item item3 = new NormalItem("Gift Card", 10, 1, DiscountType.AMOUNT, 10);

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Order order = new Order(items, "John Doe", "johndoe@example.com");

        System.out.println("Total Price: " + order.calculateTotalPrice());

        //Refactored for coupling issue
        EmailSender.sendConfirmationEmail(order);

        order.printOrder();
    }
}