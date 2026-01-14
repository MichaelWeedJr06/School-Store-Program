import java.util.Scanner;

public class SchoolProgram {
    static void main() {
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

        while(!(keyPressed.equals("Q"))){
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
            if(keyPressed.equals("1")){
                Print_categories(categories);
            }else if (keyPressed.equals("2")){
                System.out.print("Please enter an item you would like to search for -> ");
                String searchItem = scan.nextLine();
                int itemIndex = -1;
                for(int i = 0; i < itemNames.length; i++){
                    if(itemNames[i].equalsIgnoreCase(searchItem)){
                        itemIndex = i;
                    }
                }
                if(!(itemIndex == -1)){
                    System.out.print("Would you like to add this item to the cart (Y/N) -> ");
                    String choice = scan.nextLine();
                    if(choice.equalsIgnoreCase("Y")){
                        System.out.println("How many of the item do you wish to add to the cart?");
                        int qty = scan.nextInt();
                        scan.nextLine();
                        boolean isAdded = addToCart(cartItems, cartQty, itemNames[itemIndex], qty, cartCount);
                        if(isAdded){
                            System.out.println("Successfully added to cart!");
                        }else{
                            System.out.println("Cart is full. Please check out!");
                        }
                    }else{
                        continue;
                    }
                }else{
                    System.out.println("Item was not found");
                }
            }else if (keyPressed.equals("3")){
                System.out.print("Please enter the item you wish to add to the cart -> ");
                String itemName = scan.nextLine();
                System.out.print("Please enter the amount of the item you wish to add to your cart -> ");
                int qty = scan.nextInt();
                scan.nextLine();
                boolean isAdded = addToCart(cartItems, cartQty, itemName, qty, cartCount);
                if(isAdded){
                    System.out.println("Successfully added to cart!");
                }else{
                    System.out.println("Cart is full. Please check out!");
                }
            } else if (keyPressed.equals("4")){

            }

        }

    }
static boolean addToCart(String[] items, int[] Qty, String item, int qty, int cartCount){
        if(cartCount == 50){
            return false;
        }else {
            if (cartCount != 0) {
                items[cartCount + 1] = item;
                Qty[cartCount + 1] = qty;
            } else {
                items[cartCount] = item;
                Qty[cartCount] = qty;
            }
            return true;
        }
}
static void Print_categories(String[][] categories){
    for(int i = 0; i< categories.length; i++){
        for(int j = 0; j< categories[0].length; j++){
            if(j == 0){
                System.out.printf("------------%s----------------\n", categories[i][j]);
                System.out.println("-----------------------------------------------");
            }else{
                System.out.printf("%d). %s\n", j, categories[i][j]);
            }
        }
    }
}
}
