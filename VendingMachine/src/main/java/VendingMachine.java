import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.math.BigDecimal.ZERO;

public class VendingMachine {
    private final int MAX_STOCK = 5;
    private BigDecimal totalSales = ZERO;
    private List<Items> vendingMachineItems = new ArrayList<>();
    private BigDecimal balance = new BigDecimal(0);
    private BigDecimal addedValue;
    private LocalDateTime now = LocalDateTime.now();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
    private DateTimeFormatter salesReportFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_hh-mm-ss-a");
    private String salesFormat = now.format(salesReportFormatter);
    private String formatDateTime = now.format(format);

    private Scanner scanner = new Scanner(System.in);
    private File file = new File("log.txt");

    public VendingMachine() {}

    public List<Items> getVendingMachineItems() {
        return vendingMachineItems;
    }
    public void setVendingMachineItems(List<Items> vendingMachineItems) {
        this.vendingMachineItems = vendingMachineItems;
    }
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal setBalance) {
        this.balance = setBalance;
    }

    public void addToBalance(String addedValue) {
        this.addedValue = new BigDecimal(addedValue);
        this.balance = (balance.add(new BigDecimal(addedValue)));
        addToLog(UI.menu_state.FEED_MONEY_MENU);
    }

    public void removeFromBalance(BigDecimal balance) {
        this.balance = (this.balance.subtract(balance));
    }

    public void resetBalance() {
        addToLog(UI.menu_state.FINISH_TRANSACTION_MENU);
        System.out.println(returnChange(balance));
        this.balance = ZERO;
    }

    /*
    * Handles FEED_MONEY and FINISH_TRANSACTION log additions
    */
    public void addToLog(UI.menu_state currentMenuState) {
        try {
            String statusLogAction = "";
            FileOutputStream fos = new FileOutputStream(file, true);
            PrintWriter writer = new PrintWriter(fos);

            if(currentMenuState == UI.menu_state.FINISH_TRANSACTION_MENU) {
                statusLogAction = "GIVE CHANGE";
                writer.println(formatDateTime + " " + statusLogAction + " " + getBalance() + " 0");

            }
            else if(currentMenuState == UI.menu_state.FEED_MONEY_MENU) {
                statusLogAction = "FEED MONEY";
                writer.println(formatDateTime + " " + statusLogAction + " " + addedValue + " " + getBalance());
            }
            writer.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File does not exist");
        }
    }

    /*
    * Specific for item purchases in SELECT_PRODUCT_MENU
    */
    public void addToLog(UI.menu_state currentMenuState, Items item) {
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            PrintWriter writer = new PrintWriter(fos);
            writer.println(formatDateTime + " "  + item.getName() + " " + item.getLocation() + " " + item.getPrice() + " " + getBalance());
            writer.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File does not exist");
        }
    }

    public UI.menu_state updateInventory(String code) {
        try {
            for (Items item : getVendingMachineItems()) {
                if (code.equalsIgnoreCase(item.getLocation())) {
                    if (getBalance().subtract(item.getPrice()).compareTo(ZERO) < 0) {
                        System.out.println();
                        System.out.println("Add money before attempting to purchase");
                        return UI.menu_state.PURCHASE_MENU;
                    } else if (item.getQuantity() == 0) {
                        System.out.println();
                        System.out.println("This product is sold out");
                        return UI.menu_state.PURCHASE_MENU;
                    } else {
                        item.setQuantity(item.getQuantity() - 1);
                        removeFromBalance(item.getPrice());
                        addToLog(UI.menu_state.SELECT_PRODUCT_MENU, item);
                        System.out.println();
                        System.out.println("Here's your: " + item.getName());
                        item.soundOutput();
                        return UI.menu_state.PURCHASE_MENU;
                    }
                }
            }
            System.out.println("The code you entered is incorrect");
            return UI.menu_state.PURCHASE_MENU;
        } catch (NullPointerException npe) {
            System.out.println("Enter a code");
            return UI.menu_state.PURCHASE_MENU;
        }
    }

    public void writeSalesReport() {
        String path = "SalesReport";
        int quantitySold;

        try {
            FileOutputStream fos = new FileOutputStream(new File(path + "-" + salesFormat + ".txt"));
            PrintWriter writer = new PrintWriter(fos);
            for (Items items : vendingMachineItems) {
                quantitySold = MAX_STOCK - items.getQuantity();
                totalSales = totalSales.add(items.getPrice().multiply(BigDecimal.valueOf(quantitySold)));
                writer.println(">" + items.getName() + " | " + (MAX_STOCK - items.getQuantity()));
            }
            writer.println();
            writer.println(">**TOTAL SALES** $" + (totalSales.toString()));
            writer.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File not found");
        }
        catch (Exception ex) {
            System.out.println("Error");
        }
    }

    public String returnChange(BigDecimal bd) {
        BigDecimal returnBalance = bd;

        final BigDecimal quarter = BigDecimal.valueOf(.25);
        final BigDecimal dime = BigDecimal.valueOf(.10);
        final BigDecimal nickel = BigDecimal.valueOf(.05);
        final BigDecimal penny = BigDecimal.valueOf(.01);

        BigDecimal[] dimeReturn = new BigDecimal[0];
        BigDecimal numberOfDimesToReturn = ZERO;
        BigDecimal[] nickelReturn = new BigDecimal[0];
        BigDecimal numberOfNickelsToReturn = ZERO;
        BigDecimal[] pennyReturn = new BigDecimal[0];
        BigDecimal numberOfPenniesToReturn = ZERO;

        //Find Quarters to return.
        BigDecimal[] quarterReturn = returnBalance.divideAndRemainder(quarter);
        BigDecimal numberOfQuartersToReturn = quarterReturn[0];
        returnBalance = (quarterReturn[1]);

        // Find Dimes to return.
        if (returnBalance.compareTo(dime) >= 0) {
        dimeReturn = returnBalance.divideAndRemainder(dime);
        numberOfDimesToReturn = dimeReturn[0];
        returnBalance = (dimeReturn[1]);
    }

        // Find Nickels to return.
        if (returnBalance.compareTo(nickel) >= 0) {
        nickelReturn = returnBalance.divideAndRemainder(nickel);
        numberOfNickelsToReturn = nickelReturn[0];
        returnBalance = (nickelReturn[1]);
    }

        // Find pennies to return.
        if (returnBalance.compareTo(penny) >= 0) {
        pennyReturn = returnBalance.divideAndRemainder(penny);
        numberOfPenniesToReturn = pennyReturn[0];
    }
        String returnStatement = "";
        returnStatement = String.format("Quarters: %.0f \nDimes: %.0f \nNickels: %.0f \nPennies: %.0f", numberOfQuartersToReturn, numberOfDimesToReturn, numberOfNickelsToReturn, numberOfPenniesToReturn);
        return returnStatement;
    } // returnChange method
} // class
