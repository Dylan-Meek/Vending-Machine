import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineTests{

    VendingMachine vendingMachine = new VendingMachine();

    @Test
    public void addToBalanceTest_add_100_to_0_returns_100() {
        String addedValue = "100";
        vendingMachine.addToBalance(addedValue);
        Assert.assertEquals(new BigDecimal(100), vendingMachine.getBalance());
    }

    @Test
    public void removeFromBalanceTest_remove_100_from_0_returns_negative_100() {
        BigDecimal remove = new BigDecimal(100);
        vendingMachine.removeFromBalance(remove);
        Assert.assertEquals(new BigDecimal(-100), vendingMachine.getBalance());
    }

    @Test
    public void resetBalanceTest_resets_100_to_0() {
        vendingMachine.setBalance(new BigDecimal(100));
        vendingMachine.resetBalance();
        Assert.assertEquals(new BigDecimal(0), vendingMachine.getBalance());
    }

    /*
    * updateInventory() tests
    */

    List<Items> updateInventoryTestList = new ArrayList<>();
    UI.menu_state updateInventoryMenuStateTest;

    @Test
    public void updateInventoryTest_correct_item_locationand_quantity_provided_returns_PURCHASE_MENU_and_updates_balance_and_quantity() {
        updateInventoryTestList.add(new Items("Queso Chips", "A2", "Chips", new BigDecimal(1.50)));
        String code = "A2";
        vendingMachine.setVendingMachineItems(updateInventoryTestList);
        updateInventoryMenuStateTest = vendingMachine.updateInventory(code);
        Assert.assertEquals(UI.menu_state.PURCHASE_MENU, updateInventoryMenuStateTest);
    }

    @Test
    public void updateInventoryTest_quantity_zero_returns_sold_out_and_purchase_menu() {
        vendingMachine.setBalance(new BigDecimal(100));
        updateInventoryTestList.add(new Items("Queso Chips", "A2", "Chips", new BigDecimal(1.50)));
        vendingMachine.setVendingMachineItems(updateInventoryTestList);
        vendingMachine.getVendingMachineItems().get(0).setQuantity(0);
        String code = "A2";
        vendingMachine.setVendingMachineItems(updateInventoryTestList);
        updateInventoryMenuStateTest = vendingMachine.updateInventory(code);
        Assert.assertEquals(UI.menu_state.PURCHASE_MENU, updateInventoryMenuStateTest);
    }

    @Test
    public void updateInventoryTest_wrong_code_returns_incorrect_code_given() {
        updateInventoryTestList.add(new Items("Queso Chips", "A2", "Chips", new BigDecimal(1.50)));
        String code = "A1";
        vendingMachine.setVendingMachineItems(updateInventoryTestList);
        updateInventoryMenuStateTest = vendingMachine.updateInventory(code);
        Assert.assertEquals(UI.menu_state.PURCHASE_MENU, updateInventoryMenuStateTest);
    }

    @Test
    public void updateInventoryTest_null_code_returns_enter_code_and_PURCHASE_MENU() {
        updateInventoryTestList.add(new Items("Queso Chips", "A2", "Chips", new BigDecimal(1.50)));
        String code = null;
        vendingMachine.setVendingMachineItems(updateInventoryTestList);
        updateInventoryMenuStateTest = vendingMachine.updateInventory(code);
        Assert.assertEquals(UI.menu_state.PURCHASE_MENU, updateInventoryMenuStateTest);
    }

    @Test
    public void updateInventoryTest_quantity_sufficient_and_location_correct_returns_item_and_PURCHASE_MENU() {
        vendingMachine.setBalance(new BigDecimal(100));
        updateInventoryTestList.add(new Items("Queso Chips", "A2", "Chips", new BigDecimal(1.50)));
        vendingMachine.setVendingMachineItems(updateInventoryTestList);
        String code = "A2";
        vendingMachine.setVendingMachineItems(updateInventoryTestList);
        updateInventoryMenuStateTest = vendingMachine.updateInventory(code);
        Assert.assertEquals(UI.menu_state.PURCHASE_MENU, updateInventoryMenuStateTest);
    }

    /*
    * returnChange() tests
    * Returning no money
    * Returning one of each
    */

    @Test
    public void returnChangeTests_zero_balance_returns_zero_coins() {
        String expected = "Quarters: 0 \nDimes: 0 \nNickels: 0 \nPennies: 0";
        String actual = vendingMachine.returnChange(new BigDecimal(.00));
        Assert.assertTrue(expected.equals(actual));
    }

    @Test
    public void returnChangeTests_odd_balance_returns_all_coins() {
        String expected = "Quarters: 4 \nDimes: 1 \nNickels: 1 \nPennies: 1";
        String actual = vendingMachine.returnChange(new BigDecimal("1.16"));
        Assert.assertEquals(expected, actual);
    }
}
