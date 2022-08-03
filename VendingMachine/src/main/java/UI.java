import java.util.Scanner;

public class UI {

    public static enum  menu_state{
        MAIN_MENU,
        DISPLAY_ITEMS,
        PURCHASE_MENU,
        FEED_MONEY_MENU,
        SELECT_PRODUCT_MENU,
        FINISH_TRANSACTION_MENU,
        EXIT
    }

    private String nextMenu;
    private menu_state currentMenuState = menu_state.MAIN_MENU;
    private Scanner scanner = new Scanner(System.in);

    public void setCurrentMenuState(menu_state currentMenuState) {
        this.currentMenuState = currentMenuState;
    }

    public menu_state getCurrentMenuState() {return currentMenuState;}

    public UI() {}

        public int displayMainMenu () {
            System.out.println("===================================");
            System.out.println("            MAIN MENU");
            System.out.println("===================================");
            System.out.println("(1) Display Vending Machine Items");
            System.out.println("(2) Purchase");
            System.out.println("(3) Exit");
            nextMenu = scanner.nextLine();
            return Integer.parseInt(nextMenu);
    }
        public void displayItemsMenu (VendingMachine vendingMachine) {
            System.out.println("===================================");
            System.out.println("          INVENTORY MENU");
            System.out.println("===================================");
            for(Items category : vendingMachine.getVendingMachineItems()) {
                System.out.print("PRODUCT: " + category.getName()
                        + ", PRICE: " + category.getPrice()
                        + ", CODE: " + category.getLocation()
                        + ", QUANTITY: ");
                if(category.getQuantity() == 0) {
                    System.out.println("SOLD OUT");
                }
                else {
                    System.out.println(category.getQuantity());
                }
            }
            setCurrentMenuState(menu_state.MAIN_MENU);
    }
        public int purchaseMenu (VendingMachine vendingMachine) {
            System.out.println("===================================");
            System.out.println("           PURCHASE MENU");
            System.out.println("===================================");
            System.out.println("Current Money Provided: " + vendingMachine.getBalance());
            System.out.println();
            System.out.println("(1) Feed Money");
            System.out.println("(2) Select Product");
            System.out.println("(3) Finish Transaction");
            nextMenu = scanner.nextLine();
            return Integer.parseInt(nextMenu);
    }
        public void feedMoneyMenu (VendingMachine vendingMachine) {
            System.out.println("===================================");
            System.out.println("          FEED MONEY MENU");
            System.out.println("===================================");
            System.out.print("How much money would you like to feed? ");
            vendingMachine.addToBalance((scanner.nextLine()));
    }
        public String selectProductMenu (VendingMachine vendingMachine) {
            System.out.println("===================================");
            System.out.println("      PRODUCT SELECTION MENU");
            System.out.println("===================================");
            for (Items category : vendingMachine.getVendingMachineItems()) {
                System.out.println("Product: " + category.getName() + ", Code: " + category.getLocation());
            }
            System.out.print("Enter product code: ");
            String code = scanner.nextLine();
            return code;
        }
        public menu_state finishTransactionMenu (VendingMachine vendingMachine) {
            vendingMachine.resetBalance();
            return menu_state.MAIN_MENU;
    }
} // class
