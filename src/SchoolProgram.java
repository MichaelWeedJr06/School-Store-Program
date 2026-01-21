import java.util.Scanner;
import java.util.Arrays;
public class SchoolProgram {
    public static void main(String[] args) {
        String[] itemNames = {"Granola", "Trail Mix", "Pizza",
                "Water", "Gatorade", "Fruit Punch",
                "Mechanical Pencils", "Pencils", "Pens"
        };
        String[][] categories = {{"Snacks", "Granola", "Trail Mix", "Pizza"},
                {"Beverages", "Water", "Gatorade", "Fruit Punch"},
                {"Writing Untencils", "Mechanical Pencils", "Pencils", "Pens"}
        };
        double[] itemPrices = {9.99, 8.99, 7.99,
                1.00, 2.00, 2.50,
                1.50, 1.00, 3.00
        };
        int[] itemStock = {5, 10, 8, 7, 5, 4, 6, 5, 6};
        Scanner scan = new Scanner(System.in);
        String keyPressed = "";
        String[] cartItems = new String[50];
        int[] cartQty = new int[50];
        int cartCount = 0;

        while (!(keyPressed.equals("Q"))) {
            System.out.println();
            System.out.println("----MENU OPTIONS----");
            System.out.println("1.  View categories and items");
            System.out.println("2. Search for an item by name");
            System.out.println("3. Add item to cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("Q. Quit");
            System.out.println("--------------------");
            System.out.print("Please choose a menu option from the drop down -> ");
            keyPressed = scan.nextLine();
            if (keyPressed.equals("1")) {

                Print_categories(categories, itemPrices);
            } else if (keyPressed.equals("2")) {
                System.out.print("Please enter an item you would like to search for -> ");
                String searchItem = scan.nextLine();
                int itemIndex = find_Inventory_Index(itemNames, searchItem);
                if (!(itemIndex == -1)) {
                    String name = itemNames[itemIndex];
                    Print_line(name, itemPrices[itemIndex], itemStock[itemIndex]);
                    System.out.print("Would you like to add this item to the cart (Y/N) -> ");
                    String choice = scan.nextLine();
                    if (choice.equalsIgnoreCase("Y")) {
                        System.out.println("How many of the item do you wish to add to the cart?");
                        int qty = scan.nextInt();
                        scan.nextLine();
                        boolean isAdded = addToCart(cartItems, cartQty, itemNames[itemIndex], qty, cartCount);
                        if (isAdded) {
                            System.out.println("Successfully added to cart!");
                        } else {
                            System.out.println("Cart is full. Please check out!");
                        }
                    } else {
                        continue;
                    }
                } else {
                    System.out.println("Item was not found");
                }
            } else if (keyPressed.equals("3")) {
                System.out.print("Please enter the item you wish to add to the cart -> ");
                String itemName = scan.nextLine();
                System.out.print("Please enter the amount of the item you wish to add to your cart -> ");
                int qty = scan.nextInt();
                scan.nextLine();
                boolean isAdded = addToCart(cartItems, cartQty, itemName, qty, cartCount);
                if (isAdded) {
                    cartCount += 1;
                    System.out.println("Successfully added to cart!");
                } else {
                    System.out.println("Cart is full. Please check out!");
                }
            } else if (keyPressed.equals("4")) {
                view_cart(itemNames, cartItems, itemPrices, cartQty, cartCount);
            } else if (keyPressed.equals("5")) {
                double[] prices = new double[cartCount];
                System.out.println("----------------------------------------");
                System.out.println("        Reciept");
                for (int j = 0; j < cartCount; j++) {
                    String item = cartItems[j];
                    int itemIndex = FindIndex(itemNames, item);
                    int stock = itemStock[itemIndex];
                    if (cartQty[j] > stock) {
                        System.out.printf("Insufficient Stock, Item: %s, Stock: %d\n", itemNames[itemIndex], stock);
                        return;
                    }
                }
                for (int i = 0; i < cartCount; i++) {
                    String item = cartItems[i];
                    int priceIndex = FindIndex(itemNames, item);
                    if (priceIndex != -1) {
                        double price = itemPrices[priceIndex];
                        prices[i] = price * cartQty[i];
                        itemStock[priceIndex] -= cartQty[i];
                        double line_total = sum(price, cartQty[i]);
                        System.out.printf("%s - %d - $%f --- TOTAL: $%f\n", item, cartQty[i], price, line_total);
                    } else {
                        System.out.printf("Item not found: %s\n", item);
                    }

                }
                double subtotal = add(prices) ;
                double tax = sum(subtotal, 0.6);
                double Total = add(subtotal, tax);
                System.out.printf("SUBTOTAL: $%f\n", subtotal);
                System.out.printf("TAX: $%f\n", tax);
                System.out.printf("TOTAL: $%f\n", Total);


            }

        }

    }

    static int FindIndex(String[] arr, String target) {

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                continue;
            }
            if (arr[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    static double add(double... values) {
        double total = 0;
        for (int i = 0; i < values.length; i++) {
            total += values[i];
        }
        return total;
}
    static double sum(double...values){
        double total = 0;
        for(int i = 0; i < values.length; i++){
            total *= values[i];
        }
        return total;
    }
    static void view_cart(String[] itemNames,String[] items, double[] itemPrice, int[] itemQty , int Count){
        for(int i = 0; i<Count; i++){
           int priceIndex =  FindIndex(itemNames, items[i]);
                System.out.printf("%s - %d -- %f\n", items[i], itemQty[i], itemPrice[priceIndex]);
        }
        System.out.printf("Item count: %d", Count);

    }
static boolean addToCart(String[] items, int[] Qty, String item, int qty, int Count){
        if(Count == 50){
            return false;
        }else {
            if (Count != 0) {
                int exists = FindIndex(items, item);
                if(exists != -1){
                    Qty[Count]+= qty;
                }else {
                    items[Count] = item;
                    Qty[Count] = qty;
                    Count += 1;
                }
            } else {
                int exists = FindIndex(items, item);
                if(exists != -1){
                    Qty[Count]+=qty;
                }else {
                    items[Count] = item;
                    Qty[Count] = qty;
                    Count += 1;
                }
            }
            return true;
        }
}
static int find_Inventory_Index(String[] items, String searchName) {
    for (int i = 0; i < items.length; i++) {
        if (items[i].equalsIgnoreCase(searchName)) {
            return i;
        }
    }
    return -1;
}
static void Print_categories(String[][] categories, double[] prices){
        int count = -1;
    for(int i = 0; i< categories.length; i++){
        for(int j = 0; j< categories[0].length; j++){

            if(j == 0){
                System.out.printf("------------%s----------------\n", categories[i][j]);
                System.out.println("-----------------------------------------------");
            }else{
                count++;
                Print_line(categories[i][j], prices[count]);
            }
        }
    }
}
static void Print_line(String name, double price){
    System.out.printf("%s - $%f\n", name, price);
}
static void Print_line(String name, double price, int stock){
    System.out.printf("%s - $%f STOCK: %d\n", name, price, stock);
}
}
