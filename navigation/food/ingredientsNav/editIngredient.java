package navigation.food.ingredientsNav;

import util.inputFormatting.inputFormatterFactory;
import util.listenersFormatting.booleanWrapper;
import util.listenersFormatting.iTextFieldListener;
import util.listenersFormatting.edit.editTextFieldFListener;
import util.listenersFormatting.edit.editToggleAction;
import util.inputFormatting.iFormatter;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

import util.databaseAPIs.ingredientsAPI;
import util.databaseAPIs.menuAPI;
import util.buttonFormatters.*;
import util.databaseAPIs.allergensAPI;
import util.databaseAPIs.providerAPI;
import util.databaseAPIs.productAPI;

import java.util.ArrayList;
import java.util.Stack;
import java.awt.*;

import componentsFood.ingredient;
import componentsFood.allergen;
import componentsFood.provider;

public class editIngredient {

        private JLabel auxIngredientLabel = new JLabel();
        private JLabel theIngredientLabel = new JLabel();

        private JLabel nameLabel = new JLabel();
        private JLabel priceLabel = new JLabel();
        private JLabel quantityLabel = new JLabel();
        private JLabel providerLabel = new JLabel();
        private JLabel inventoryLabel = new JLabel();
        private JLabel allergenLabel = new JLabel();

        private JTextField nameTextField = new JTextField();
        private JTextField priceTextField = new JTextField();
        private JTextField quantityTextField = new JTextField();
        private booleanWrapper namePlaceholder = new booleanWrapper(true);
        private booleanWrapper pricePlaceholder = new booleanWrapper(true);
        private booleanWrapper quantityPlaceholder = new booleanWrapper(true);
        private JComboBox<String> providerComboBox = new JComboBox<String>();
        private JToggleButton inventoryToggle = new JToggleButton();

        private JTable tableAllergens = new JTable();
        private JTable tableSelected = new JTable();
        private DefaultTableModel modelAllergens;
        private DefaultTableModel modelSelected;

        private JButton selectButton = new JButton();
        private JButton unselectButton = new JButton();

        private JScrollPane selectedJScrollPane = new JScrollPane();
        private JScrollPane unselectedJScrollPane = new JScrollPane();

        private JLabel successLabel = new JLabel();

        private JButton editIngredientButton = new JButton();
        private JButton backButton = new JButton();
        private JButton deleteButton = new JButton();

        private JPanel jPanel1 = new JPanel();
        private JPanel jPanel2 = new JPanel();
        private JPanel jPanel3 = new JPanel();

        private ArrayList<provider> providers = new providerAPI().getAllActiveProviders();

        private ingredient theCurrentIngredient;
        private ingredientsAPI theManagerDB = new ingredientsAPI();

        public editIngredient(JPanel playground, ingredient theCurrentIngredient) {
                this.theCurrentIngredient = theCurrentIngredient;
                initComponents(playground);
                addListeners(playground);
        }

        private void initComponents(JPanel playground) {
                playground.setBackground(new Color(255, 255, 255));

                successLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                successLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                successLabel.setVerticalAlignment(SwingConstants.BOTTOM);
                successLabel.setText("                                             ");

                jPanel1.setBackground(new Color(120, 168, 252));
                jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                jPanel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

                nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
                nameLabel.setText("Name");
                nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                nameTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                nameTextField.setText(theCurrentIngredient.getName());
                nameTextField.setForeground(Color.GRAY);

                jPanel2.setBackground(new Color(0, 0, 0));

                GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 8, Short.MAX_VALUE));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 0, Short.MAX_VALUE));

                priceLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
                priceLabel.setText("Price");
                priceLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                priceTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                priceTextField.setText(Float.toString(theCurrentIngredient.getPrice()));
                priceTextField.setForeground(Color.GRAY);

                quantityLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                quantityLabel.setHorizontalAlignment(SwingConstants.LEFT);
                quantityLabel.setText("Quantity");
                quantityLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                quantityTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                quantityTextField.setText(Float.toString(theCurrentIngredient.getAmount()));
                quantityTextField.setForeground(Color.GRAY);

                inventoryLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                inventoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
                inventoryLabel.setText("Inventory");
                inventoryLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                providerLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                providerLabel.setHorizontalAlignment(SwingConstants.LEFT);
                providerLabel.setText("Provider");
                providerLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                setComboBox();
                setTables();
                selectedJScrollPane.getViewport().setBackground(new Color(245, 245, 245));
                selectedJScrollPane.setBackground(new Color(245, 245, 245));
                unselectedJScrollPane.getViewport().setBackground(new Color(245, 245, 245));
                unselectedJScrollPane.setBackground(new Color(245, 245, 245));

                inventoryToggle.setText("Yes");
                if (!theCurrentIngredient.getInInventory())
                        inventoryToggle.setText("No");
                inventoryToggle.setBackground(new Color(255, 255, 255));
                inventoryToggle.setForeground(Color.GRAY);

                allergenLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                allergenLabel.setHorizontalAlignment(SwingConstants.LEFT);
                allergenLabel.setText("Select Allergens");
                allergenLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                editIngredientButton.setText("Edit Ingredient");

                GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jPanel2,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(35, 35, 35)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.TRAILING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                                false)
                                                                                                                                .addComponent(nameLabel,
                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(priceLabel,
                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(providerLabel,
                                                                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                83,
                                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(allergenLabel))
                                                                                                .addPreferredGap(
                                                                                                                ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(nameTextField,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                497,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                                                                                false)
                                                                                                                                                .addComponent(providerComboBox,
                                                                                                                                                                0,
                                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(priceTextField,
                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                195,
                                                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addGap(12, 12, 12)
                                                                                                                                                                .addComponent(inventoryLabel,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                83,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                ComponentPlacement.UNRELATED)
                                                                                                                                                                .addComponent(quantityLabel,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                83,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                                                                                .addPreferredGap(
                                                                                                                                                ComponentPlacement.UNRELATED)
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(quantityTextField)
                                                                                                                                                .addComponent(inventoryToggle,
                                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)))
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(unselectedJScrollPane,
                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                233,
                                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                ComponentPlacement.RELATED,
                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                .addComponent(selectedJScrollPane,
                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                233,
                                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(GroupLayout.Alignment.TRAILING,
                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                .addComponent(selectButton,
                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                75,
                                                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                ComponentPlacement.RELATED,
                                                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(unselectButton))))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(318, 318, 318)
                                                                                                .addComponent(editIngredientButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                200,
                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(188, Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(16, 16, 16)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(nameLabel)
                                                                                .addComponent(nameTextField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(quantityTextField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(priceTextField,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(priceLabel)
                                                                                                .addComponent(quantityLabel)))
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(providerComboBox,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                36,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(inventoryToggle,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                33,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createParallelGroup(
                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(inventoryLabel)
                                                                                                .addComponent(providerLabel)))
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(allergenLabel)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(unselectedJScrollPane,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                210,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(selectedJScrollPane,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                210,
                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                ComponentPlacement.RELATED,
                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(selectButton)
                                                                                                                .addComponent(unselectButton))
                                                                                                .addPreferredGap(
                                                                                                                ComponentPlacement.RELATED)
                                                                                                .addComponent(editIngredientButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                55,
                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(17, 17, 17)));

                jPanel3.setBackground(new Color(71, 120, 197));

                GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 500, Short.MAX_VALUE));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 5, Short.MAX_VALUE));

                theIngredientLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                theIngredientLabel.setHorizontalAlignment(SwingConstants.CENTER);
                theIngredientLabel.setText(theCurrentIngredient.getName());
                theIngredientLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                deleteButton.setBackground(new Color(255, 102, 102));
                deleteButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                deleteButton.setText("Delete");

                auxIngredientLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
                auxIngredientLabel.setHorizontalAlignment(SwingConstants.CENTER);
                auxIngredientLabel.setText("Ingredient to edit:");
                auxIngredientLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                GroupLayout playgroundLayout = new GroupLayout(playground);
                playground.setLayout(playgroundLayout);
                playgroundLayout.setHorizontalGroup(
                                playgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(playgroundLayout.createSequentialGroup()
                                                                .addGap(185, 185, 185)
                                                                .addComponent(jPanel3,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(theIngredientLabel,
                                                                GroupLayout.Alignment.TRAILING,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                .addGroup(GroupLayout.Alignment.TRAILING, playgroundLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(backButton,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                200,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addComponent(successLabel,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addComponent(deleteButton,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                200,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap())
                                                .addComponent(auxIngredientLabel, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                playgroundLayout.setVerticalGroup(
                                playgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, playgroundLayout
                                                                .createSequentialGroup()
                                                                .addGap(8, 8, 8)
                                                                .addComponent(auxIngredientLabel)
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addComponent(theIngredientLabel)
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addComponent(jPanel3,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jPanel1,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(playgroundLayout
                                                                                .createParallelGroup(
                                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addGroup(playgroundLayout
                                                                                                .createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                ComponentPlacement.RELATED)
                                                                                                .addComponent(successLabel))
                                                                                .addGroup(playgroundLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(14, 14, 14)
                                                                                                .addGroup(playgroundLayout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(deleteButton,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                55,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(backButton,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                55,
                                                                                                                                GroupLayout.PREFERRED_SIZE))))
                                                                .addContainerGap(GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

        }

        private void setTables() {
                tableAllergens = new JTable();
                tableSelected = new JTable();
                modelAllergens = new DefaultTableModel(
                                new String[] { "id", "Name" }, 0);
                modelSelected = new DefaultTableModel(
                                new String[] { "id", "Name" }, 0);

                for (allergen tempAller : new allergensAPI().getSelectedAllergens(theCurrentIngredient)) {
                        String id = Integer.toString(tempAller.getId());
                        String name = tempAller.getName();
                        modelSelected.addRow(new String[] { id, name });
                }
                for (allergen tempAller : new allergensAPI().getNonSelectedAllergens(theCurrentIngredient)) {
                        String id = Integer.toString(tempAller.getId());
                        String name = tempAller.getName();
                        modelAllergens.addRow(new String[] { id, name });
                }
                tableAllergens.setModel(modelAllergens);
                tableSelected.setModel(modelSelected);
                unselectedJScrollPane.setViewportView(tableAllergens);
                selectedJScrollPane.setViewportView(tableSelected);
                tableLookPretty(tableAllergens);
                tableLookPretty(tableSelected);
        }

        private void tableLookPretty(JTable theTable) {
                theTable.setDefaultEditor(Object.class, null);
                theTable.removeColumn(theTable.getColumn("id"));
                theTable.setFocusable(true);
                theTable.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 9));
                theTable.getTableHeader().setBackground(new Color(120, 168, 252));
                theTable.setFillsViewportHeight(true);
                theTable.setFont(new java.awt.Font("Segoe UI", 0, 9));
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                theTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                Dimension temp = new Dimension(20, 1);
                theTable.setIntercellSpacing(temp);
                theTable.setRowHeight(theTable.getRowHeight() + 10);
        }

        private void setComboBox() {
                for (int i = 0; i < providers.size(); i++) {
                        if (providers.get(i).getId() == theCurrentIngredient.getProviderID())
                                providers.add(0, providers.remove(i));
                }
                ArrayList<String> tempNames = new ArrayList<String>();
                for (provider temp : providers)
                        tempNames.add(temp.getName());
                String[] namesArr = tempNames.toArray(new String[0]);
                providerComboBox.setModel(new DefaultComboBoxModel<String>(namesArr));
                providerComboBox.setFont(new java.awt.Font("Segoe UI", 0, 18));
                providerComboBox.setFont(new Font("Segoe UI", 0, 18));
                providerComboBox.setForeground(Color.BLACK);
                providerComboBox.setBackground(Color.WHITE);
        }

        private boolean isAllergenPlaceholder() {
                ArrayList<allergen> tempList = new allergensAPI()
                                .getSelectedAllergens(theCurrentIngredient);

                ArrayList<allergen> listSelected = new ArrayList<allergen>();
                for (int i = 0; i < modelSelected.getRowCount(); i++) {
                        int tempID = Integer.parseInt((String) modelSelected.getValueAt(i, 0));
                        String name = (String) modelSelected.getValueAt(i, 1);
                        listSelected.add(new allergen(tempID, name));
                }
                for (allergen temp : tempList) {
                        if (!listSelected.contains(temp))
                                return false;
                }
                for (allergen temp : listSelected) {
                        if (!tempList.contains(temp))
                                return false;
                }
                return true;
        }

        private void addListeners(JPanel playground) {
                deleteButton(playground);
                selectionButtons();
                backButton(playground);
                editButton(null);
                applyGenericListeners();
        }

        private void deleteButton(JPanel playground) {
                class deleteMethodHolder implements iDeleteButton {

                        public boolean thereIsError() {
                                int reply = JOptionPane.showConfirmDialog(null,
                                                "Are you sure you want to delete this Ingredient?", "Confirmation",
                                                JOptionPane.YES_NO_OPTION);
                                if (reply == JOptionPane.YES_OPTION) {
                                        return false;
                                }
                                return true;

                        }

                        public boolean askConfirmation() {
                                boolean neededToChoose = false;

                                String[] options = new String[] { "Delete Menus/Products", "Keep Menus/Products",
                                                "Cancel" };
                                int response = JOptionPane.showOptionDialog(null,
                                                "Do you want to delete every menu and product which uses this ingredient, or keep them without the ingredient?",
                                                "Choice",
                                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                                options, options[0]);
                                if (response == 0 || response == 1) {
                                        if (response == 0)
                                                deleteAllAssociatedToIngredientID(theCurrentIngredient.getId());
                                        if (response == 1)
                                                deleteIngredientsFromProducts(theCurrentIngredient.getId());
                                        playground.removeAll();
                                        new mainIngredients(playground);
                                        playground.revalidate();
                                        playground.repaint();
                                        return neededToChoose;
                                }
                                return neededToChoose;
                        }

                        public void chooseAmongOptions() {
                        }
                }
                deleteButtonFormatter.formatDeleteButton(deleteButton, new deleteMethodHolder());
        }

        private void backButton(JPanel playground) {
                class backMethodHolder extends iNavigatorButton {
                        public void createNewNavigator() {
                                new mainIngredients(playground);
                        }
                }
                navigatorButtonFormatter.formatNavigationButton(backButton, new backMethodHolder(), playground, true,
                                "Back");
        }

        private void selectionButtons() {
                class selectMethodHolder implements iSelectionButton {
                        public void doSelection() {
                                int row = tableAllergens.getSelectedRow();
                                if (row == -1)
                                        return;
                                String ID = (String) modelAllergens.getValueAt(row, 0);
                                String name = (String) modelAllergens.getValueAt(row, 1);
                                modelAllergens.removeRow(row);
                                modelSelected.addRow(new String[] { ID, name });
                        }
                }
                selectionButtonFormatter.formatSelectionButton(selectButton, new selectMethodHolder(), true);
                class unselectMethodHolder implements iSelectionButton {
                        public void doSelection() {
                                int row = tableSelected.getSelectedRow();
                                if (row == -1)
                                        return;
                                String ID = (String) modelSelected.getValueAt(row, 0);
                                String name = (String) modelSelected.getValueAt(row, 1);
                                modelSelected.removeRow(row);
                                modelAllergens.addRow(new String[] { ID, name });
                        }
                }
                selectionButtonFormatter.formatSelectionButton(unselectButton, new unselectMethodHolder(), false);
        }

        private void editButton(JPanel playground) {
                class editMethodsHolder implements iEditButton {

                        private boolean providerPlaceholder;
                        private boolean togglePlaceholder;
                        private boolean allergenPlaceholder;

                        public boolean valuesArePlaceholders() {
                                providerPlaceholder = providerComboBox.getSelectedIndex() == 0;
                                togglePlaceholder = theCurrentIngredient.getInInventory() == inventoryToggle
                                                .getText().equals("Yes");
                                allergenPlaceholder = isAllergenPlaceholder();
                                if (namePlaceholder.getValue() && pricePlaceholder.getValue()
                                                && quantityPlaceholder.getValue() && providerPlaceholder
                                                && togglePlaceholder && allergenPlaceholder) {
                                        successLabel.setText("Error. You must modify at least one field.");
                                        return true;
                                }
                                return false;
                        }

                        public boolean areInputsInvalid() {
                                if (!namePlaceholder.getValue() && theManagerDB.isNameTaken(nameTextField.getText())) {
                                        successLabel.setText("Error. The given name is already taken.");
                                        successLabel.setVisible(true);
                                        return true;
                                }
                                return false;
                        }

                        public void editFoodComponent() {
                                boolean successfulUpdate = true;

                                if (!namePlaceholder.getValue())
                                        successfulUpdate = theManagerDB.updateName(theCurrentIngredient,
                                                        nameTextField.getText());

                                if (!pricePlaceholder.getValue() && successfulUpdate) {
                                        Float price = Float.parseFloat(priceTextField.getText());
                                        successfulUpdate = theManagerDB.updatePrice(theCurrentIngredient, price);
                                }

                                if (!quantityPlaceholder.getValue() && successfulUpdate) {
                                        Float quantity = Float.parseFloat(quantityTextField.getText());
                                        successfulUpdate = theManagerDB.updateAmount(theCurrentIngredient, quantity);
                                }

                                if (!togglePlaceholder && successfulUpdate) {
                                        successfulUpdate = theManagerDB.updateInInventory(theCurrentIngredient,
                                                        !theCurrentIngredient.getInInventory());
                                }

                                if (!allergenPlaceholder && successfulUpdate) {
                                        Stack<Integer> stackAllergenIDs = getSelectedAllergensIDs();
                                        successfulUpdate = new allergensAPI().updateAlergensOfIngredient(
                                                        stackAllergenIDs,
                                                        theCurrentIngredient);
                                }

                                if (!providerPlaceholder && successfulUpdate) {
                                        int providerID = providers.get(providerComboBox.getSelectedIndex()).getId();
                                        successfulUpdate = theManagerDB.updateProvider(theCurrentIngredient,
                                                        providerID);
                                }

                                if (successfulUpdate)
                                        successLabel.setText("The category \"" + nameTextField.getText()
                                                        + "\" was successfully updated.");
                                else
                                        successLabel.setText("Something went wrong while updating the category.");
                                successLabel.setVisible(true);
                        }

                        public void updatePlaceholders() {
                                theCurrentIngredient = new ingredientsAPI().getIngredient(theCurrentIngredient.getId());
                                namePlaceholder.setValue(true);
                                pricePlaceholder.setValue(true);
                                quantityPlaceholder.setValue(true);

                                theIngredientLabel.setText(theCurrentIngredient.getName());
                                nameTextField.setForeground(Color.GRAY);
                                priceTextField.setForeground(Color.GRAY);
                                quantityTextField.setForeground(Color.GRAY);
                                inventoryToggle.setForeground(Color.GRAY);

                                providerComboBox.removeAllItems();
                                setComboBox();
                                applyGenericListeners();
                        }
                }
                editButtonFormatter.formatEditButton(editIngredientButton, new editMethodsHolder());
        }

        private Stack<Integer> getSelectedAllergensIDs() {
                Stack<Integer> stackAllergenIDs = new Stack<>();
                for (int i = 0; i < modelSelected.getRowCount(); i++)
                        stackAllergenIDs.push(Integer.parseInt((String) modelSelected.getValueAt(i, 0)));
                return stackAllergenIDs;
        }

        private void applyGenericListeners() {
                iTextFieldListener textListener = new editTextFieldFListener();
                iFormatter numericFormatter = new inputFormatterFactory().createInputFormatter("PRICE");

                textListener.applyListenerTextField(nameTextField, theCurrentIngredient.getName(), namePlaceholder,
                                false);
                textListener.applyListenerTextField(priceTextField, Float.toString(theCurrentIngredient.getPrice()),
                                pricePlaceholder, false);
                textListener.applyListenerTextField(quantityTextField,
                                Float.toString(theCurrentIngredient.getAmount()),
                                quantityPlaceholder, false);

                numericFormatter.applyFormat(priceTextField);
                numericFormatter.applyFormat(quantityTextField);
                new editToggleAction().applyActionListenerToggle(inventoryToggle, "Yes", "No",
                                theCurrentIngredient.getInInventory());
        }

        private void deleteAllAssociatedToIngredientID(int ingredientID) {
                productAPI tempAPI = new productAPI();
                ingredientsAPI theManagerDB = new ingredientsAPI();
                ArrayList<Integer> productIDs = theManagerDB.getProductsIDUsingIngredient(ingredientID);
                ArrayList<Integer> menuIDs = new ArrayList<Integer>();
                Stack<Integer> stackMenuIDs = new menuAPI().getAllActiveMenuIDs();
                while (!stackMenuIDs.isEmpty())
                        menuIDs.add(stackMenuIDs.pop());
                for (Integer tempProductID : productIDs) {
                        for (Integer tempMenuID : menuIDs)
                                tempAPI.deleteMenuWithProduct(tempMenuID, tempProductID);
                        tempAPI.updateActive(tempProductID, false);
                }
                for (Integer tempProductID : productIDs)
                        theManagerDB.deleteIngredientsInProduct(tempProductID, ingredientID);
                theManagerDB.setToUnactive(ingredientID);
        }

        private void deleteIngredientsFromProducts(int ingredientID) {
                ingredientsAPI theManagerDB = new ingredientsAPI();
                Stack<Integer> stackProductIDs = theManagerDB.getAllActiveProductIDs();
                while (!stackProductIDs.empty())
                        theManagerDB.deleteIngredientsInProduct(stackProductIDs.pop(), ingredientID);
                theManagerDB.setToUnactive(ingredientID);
        }

}