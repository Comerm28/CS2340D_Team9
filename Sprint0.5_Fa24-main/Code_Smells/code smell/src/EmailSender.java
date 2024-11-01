import java.util.List;

public class EmailSender {
    public static void sendEmail(String customerEmail, String subject, String message){
        System.out.println("Email to: " + customerEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + message);
    }

    public static void sendConfirmationEmail(Order order) {
       List<Item> items = order.getItems();
       String message = "Thank you for your order, " + order.getCustomerName() + "!\n\n" +
               "Your order details:\n";
       for(int i = 0; i < items.size(); i++) {
           message += items.get(i).getName()+ " - " + items.get(i).getPrice() + "\n";
       }
       message += "Total: " + order.calculateTotalPrice();
       EmailSender.sendEmail(order.getCustomerEmail(), "Order Confirmation", message);
   }

}
