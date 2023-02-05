package windows.allergensWindow;

import javax.swing.table.DefaultTableModel;
import util.abstractEdit_CheckWindow;
import componentsFood.ingredient;
import util.abstractUpdater;
import windows.ingredientsWindow.ingredientsAPI;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.*;

public class editIngr_aWindow extends abstractEdit_CheckWindow {

    private JLabel instruction = new JLabel("Double-click on Ingredient to change its Allergens");

    public editIngr_aWindow(abstractUpdater previousWindow) {
        super(previousWindow, "Choose Ingredients to change its allergens", "Ingredients");
    }

    @Override
    public void addActionListeners() {
        abstractUpdater temp = this;
        myTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    int ID = Integer.parseInt((String) model.getValueAt(myTable.getSelectedRow(), 0));
                    new assist_editIngr_aWindow(temp, new ingredientsAPI().getIngredient(ID))
                            .updateToThisMenu();
                }
            }
        });
    }

    @Override
    public void addRowsToModel() {
        ArrayList<ingredient> tempList = new ingredientsAPI().getAllCurrentIngredients();
        myTable = new JTable();
        model = new DefaultTableModel(
                new String[] { "ID", "Prov_ID", "Active Since", "Name", "Price", "Amount", "in_inventory", "active" },
                0);
        for (ingredient temp : tempList) {
            String id = Integer.toString(temp.getId());
            String prov_id = Integer.toString(temp.getProviderID());
            String price = Float.toString(temp.getPrice());
            String amount = Float.toString(temp.getAmount());
            String in_inventory;
            String active;
            if (temp.getInInventory())
                in_inventory = "In inventory";
            else
                in_inventory = "Not in inventory";
            if (temp.getActive())
                active = "Active";
            else
                active = "Not active";
            model.addRow(
                    new String[] { id, prov_id, temp.getDate(), temp.getName(), price, amount, in_inventory, active });
        }
    }

    @Override
    public void adjustTable() {
        setScrollPane(new JScrollPane(myTable));
        getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        myTable.setDefaultEditor(Object.class, null);
        myTable.setFocusable(true);
        myTable.setModel(model);
        myTable.removeColumn(myTable.getColumn("ID"));
        myTable.removeColumn(myTable.getColumn("Prov_ID"));

    }

    @Override
    public void setBounds() {
        getScrollPane().setBounds(45, 60, 500, 300);
        getSummaryTXT().setBounds(200, 20, 250, 25);
        getBackButton().setBounds(400, 400, 120, 80);
        instruction.setBounds(150, 370, 300, 25);
    }

    @Override
    public void addToFrame() {
        theFrame.add(getSummaryTXT());
        theFrame.add(getBackButton());
        theFrame.add(getScrollPane());
        theFrame.add(instruction);
    }

}
