package navigation.administration.employeesNav;

import componentsFood.employee;
import componentsFood.role;
import util.buttonFormatters.*;
import util.databaseAPIs.employeesAPI;
import util.databaseAPIs.rolesAPI;
import util.inputFormatting.inputFormatterFactory;
import util.listenersFormatting.booleanWrapper;
import util.listenersFormatting.iTextFieldListener;
import util.listenersFormatting.edit.editTextFieldFListener;

import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class editEmployee {

        private JPanel fillingPanel = new JPanel();
        private JPanel jPanel2 = new JPanel();
        private JPanel jPanel3 = new JPanel();

        private JLabel auxEmployeeLabel = new JLabel();
        private JLabel theEmployeeLabel = new JLabel();
        private JLabel nameLabel = new JLabel();
        private JLabel salaryLabel = new JLabel();
        private JLabel hoursWeekLabel = new JLabel();
        private JLabel roleLabel = new JLabel();

        private JTextField nameTextField = new JTextField();
        private JTextField salaryTextField = new JTextField();
        private JTextField hoursWeekTextField = new JTextField();
        private JComboBox<String> roleComboBox = new JComboBox<>();

        private JButton editEmployeeButton = new JButton();
        private JButton deleteButton = new JButton();
        private JButton backButton = new JButton();

        private booleanWrapper namePlaceholder = new booleanWrapper(true);
        private booleanWrapper salaryPlaceholder = new booleanWrapper(true);
        private booleanWrapper hoursWeekPlaceholder = new booleanWrapper(true);

        private ArrayList<role> roles = new rolesAPI().getAllRoles();
        private JLabel successLabel = new JLabel();

        private employee theEmployee;

        private employeesAPI theManagerDB = new employeesAPI();

        public editEmployee(
                        JPanel playground, employee theEmployee) {
                this.theEmployee = theEmployee;
                initComponents(playground);
                addActionListeners(playground);
        }

        private void initComponents(JPanel playground) {
                playground.setBackground(new Color(255, 255, 255));

                successLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
                successLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                successLabel.setVisible(false);
                successLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                fillingPanel.setBackground(new Color(120, 168, 252));
                fillingPanel.setCursor(new Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                fillingPanel.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);

                nameLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
                nameLabel.setText("Name");
                nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                nameTextField.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
                nameTextField.setText(theEmployee.getName());
                nameTextField.setForeground(Color.GRAY);

                editEmployeeButton.setText("Edit Employee");

                jPanel2.setBackground(new Color(0, 0, 0));

                GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 8, Short.MAX_VALUE));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 0, Short.MAX_VALUE));

                salaryLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                salaryLabel.setHorizontalAlignment(SwingConstants.LEFT);
                salaryLabel.setText("Salary");
                salaryLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                salaryTextField.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
                salaryTextField.setText(theEmployee.getSalary() + "");
                salaryTextField.setForeground(Color.GRAY);

                hoursWeekLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                hoursWeekLabel.setText("Hours per Week");
                hoursWeekLabel.setHorizontalAlignment(SwingConstants.LEFT);
                hoursWeekLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                roleLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                roleLabel.setHorizontalAlignment(SwingConstants.LEFT);
                roleLabel.setText("Role");
                roleLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                setComboBox();

                hoursWeekTextField.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
                hoursWeekTextField.setText(theEmployee.getHoursWeek().substring(0, 5));
                hoursWeekTextField.setForeground(Color.GRAY);

                GroupLayout fillingPanelLayout = new GroupLayout(fillingPanel);
                fillingPanel.setLayout(fillingPanelLayout);
                fillingPanelLayout.setHorizontalGroup(
                                fillingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(fillingPanelLayout.createSequentialGroup()
                                                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(fillingPanelLayout
                                                                                .createParallelGroup(
                                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addGroup(fillingPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(56, 56, 56)
                                                                                                .addGroup(fillingPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(salaryLabel,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(fillingPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                                .addComponent(nameLabel,
                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                165,
                                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(roleLabel,
                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(hoursWeekLabel,
                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(fillingPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(salaryTextField)
                                                                                                                .addComponent(nameTextField)
                                                                                                                .addComponent(hoursWeekTextField,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                434,
                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(roleComboBox,
                                                                                                                                0,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE))
                                                                                                .addGap(201, 201, 201))
                                                                                .addGroup(fillingPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(331, 331, 331)
                                                                                                .addComponent(editEmployeeButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                200,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addContainerGap(
                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)))));
                fillingPanelLayout.setVerticalGroup(
                                fillingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, fillingPanelLayout
                                                                .createSequentialGroup()
                                                                .addGap(23, 23, 23)
                                                                .addGroup(
                                                                                fillingPanelLayout
                                                                                                .createParallelGroup(
                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(nameLabel)
                                                                                                .addComponent(nameTextField,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(
                                                                                fillingPanelLayout
                                                                                                .createParallelGroup(
                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(salaryTextField,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                35,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(salaryLabel))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(fillingPanelLayout
                                                                                .createParallelGroup(
                                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(hoursWeekLabel)
                                                                                .addComponent(hoursWeekTextField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(
                                                                                fillingPanelLayout
                                                                                                .createParallelGroup(
                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(roleComboBox,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                31,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(roleLabel))
                                                                .addGap(37, 37, 37)
                                                                .addComponent(editEmployeeButton,
                                                                                GroupLayout.PREFERRED_SIZE, 55,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(42, Short.MAX_VALUE))
                                                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                jPanel3.setBackground(new Color(71, 120, 197));

                GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 500, Short.MAX_VALUE));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 5, Short.MAX_VALUE));

                theEmployeeLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                theEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                theEmployeeLabel.setText(theEmployee.getName());
                theEmployeeLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                auxEmployeeLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
                auxEmployeeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                auxEmployeeLabel.setText("Employe to edit:");
                auxEmployeeLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                deleteButton.setBackground(new Color(71, 120, 197));
                deleteButton.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
                deleteButton.setText("Delete");
                deleteButton.setBackground(new Color(255, 102, 102));
                deleteButton.setForeground(new Color(255, 255, 255));

                GroupLayout playgroundLayout = new GroupLayout(playground);
                playground.setLayout(playgroundLayout);
                playgroundLayout.setHorizontalGroup(
                                playgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(fillingPanel, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(playgroundLayout.createSequentialGroup()
                                                                .addGap(185, 185, 185)
                                                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(theEmployeeLabel, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(playgroundLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(playgroundLayout
                                                                                .createParallelGroup(
                                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(auxEmployeeLabel,
                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(playgroundLayout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(backButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                200,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(deleteButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                200,
                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                .addComponent(successLabel,
                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));
                playgroundLayout.setVerticalGroup(
                                playgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, playgroundLayout
                                                                .createSequentialGroup()
                                                                .addGap(8, 8, 8)
                                                                .addComponent(auxEmployeeLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(theEmployeeLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(fillingPanel, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(successLabel)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                                                138,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(
                                                                                playgroundLayout.createParallelGroup(
                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                .addComponent(backButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                55,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addComponent(deleteButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                55,
                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));
        }

        private void addActionListeners(JPanel playground) {
                deleteButton.addMouseListener(new MouseListener() {
                        public void mouseClicked(MouseEvent e) {
                                int reply = JOptionPane.showConfirmDialog(null,
                                                "Are you sure you want to delete " + theEmployee.getName()
                                                                + " from employees?",
                                                "Confirmation", JOptionPane.YES_NO_OPTION);
                                if (reply == JOptionPane.YES_OPTION) {
                                        if (new employeesAPI().hasEmployeeFutureShifts(theEmployee)) {
                                                int response = JOptionPane.showOptionDialog(null,
                                                                "An employee with future or current shifts assigned cannot be deleted.\nYou can delete all their future shifts or keep the employee.",
                                                                "Choice",
                                                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                new String[] { "Delete Shifts and Employee",
                                                                                "Keep Employee" },
                                                                null);
                                                if (response == 0) {
                                                        new employeesAPI().setEmployeeUnactive(theEmployee);
                                                        playground.removeAll();
                                                        new mainEmployees(playground);
                                                        playground.revalidate();
                                                        playground.repaint();
                                                        return;
                                                }
                                        }
                                }
                        }

                        public void mousePressed(MouseEvent e) {
                        }

                        public void mouseReleased(MouseEvent e) {
                        }

                        public void mouseEntered(MouseEvent e) {
                                deleteButton.setBackground(new Color(255, 255, 255));
                                deleteButton.setForeground(new Color(255, 102, 102));
                        }

                        public void mouseExited(MouseEvent e) {
                                deleteButton.setBackground(new Color(255, 102, 102));
                                deleteButton.setForeground(new Color(255, 255, 255));
                        }
                });
                backButton(playground);
                editButton(null);
                applyGenericListeners();
        }

        private void backButton(JPanel playground) {
                class backMethodHolder extends iBackButton {
                        public void createNewNavigator() {
                                new mainEmployees(playground);
                        }
                }
                backButtonFormatter.formatBackButton(backButton, new backMethodHolder(), playground);
        }

        private void editButton(JPanel playground) {
                class editMethodsHolder implements iEditButton {

                        private boolean rolePlaceholder;

                        public boolean valuesArePlaceholders() {

                                rolePlaceholder = (roles.get(roleComboBox.getSelectedIndex()).getId() == theEmployee
                                                .getRoleID());
                                if (namePlaceholder.getValue() && salaryPlaceholder.getValue()
                                                && hoursWeekPlaceholder.getValue() && rolePlaceholder) {
                                        successLabel.setText("Error. At least one field must be diffeernt.");
                                        successLabel.setVisible(true);
                                        return true;
                                }
                                return false;
                        }

                        public boolean areInputsInvalid() {
                                return false;
                        }

                        public void editFoodComponent() {
                                boolean successfulUpdate = true;

                                if (!namePlaceholder.getValue())
                                        successfulUpdate = theManagerDB.updateEmployeeName(theEmployee,
                                                        nameTextField.getText());

                                if (!salaryPlaceholder.getValue()) {
                                        Float salary = Float.parseFloat(salaryTextField.getText());
                                        successfulUpdate = theManagerDB.updateEmployeeSalary(theEmployee, salary);
                                }

                                if (!hoursWeekPlaceholder.getValue()) {
                                        String hoursWeek = hoursWeekTextField.getText();
                                        successfulUpdate = theManagerDB.updateEmployeeHoursWeek(theEmployee, hoursWeek);
                                }

                                if (!rolePlaceholder)
                                        successfulUpdate = theManagerDB.updateEmployeeRole(theEmployee,
                                                        roles.get(roleComboBox.getSelectedIndex()).getId());

                                if (successfulUpdate)
                                        successLabel.setText("The employee \"" + nameTextField.getText()
                                                        + "\" was successfully updated.");
                                else
                                        successLabel.setText("Something went wrong while updating the employee.");
                                successLabel.setVisible(true);
                        }

                        public void updatePlaceholders() {
                                theEmployee = new employeesAPI().getEmployee(theEmployee.getId());

                                namePlaceholder.setValue(true);
                                salaryPlaceholder.setValue(true);
                                hoursWeekPlaceholder.setValue(true);

                                theEmployeeLabel.setText(theEmployee.getName());
                                nameTextField.setForeground(Color.GRAY);
                                salaryTextField.setForeground(Color.GRAY);
                                hoursWeekTextField.setForeground(Color.GRAY);
                                for (int i = 0; i < roles.size(); i++) {
                                        if (roles.get(i).getId() == theEmployee.getRoleID()) {
                                                role temp = roles.remove(i);
                                                roles.add(0, temp);
                                        }
                                }
                                ArrayList<String> tempNames = new ArrayList<>();
                                for (role temp : roles)
                                        tempNames.add(temp.getName());

                                String[] namesArr = tempNames.toArray(new String[0]);
                                roleComboBox.setModel(new DefaultComboBoxModel<String>(namesArr));

                                applyGenericListeners();
                        }
                }
                editButtonFormatter.formatEditButton(editEmployeeButton, new editMethodsHolder());
        }

        private void applyGenericListeners() {
                iTextFieldListener textListener = new editTextFieldFListener();
                textListener.applyListenerTextField(nameTextField, theEmployee.getName(), namePlaceholder);

                textListener.applyListenerTextField(nameTextField, theEmployee.getName(), namePlaceholder);
                textListener.applyListenerTextField(salaryTextField, Float.toString(theEmployee.getSalary()),
                                salaryPlaceholder);
                textListener.applyListenerTextField(hoursWeekTextField, theEmployee.getHoursWeek().substring(0, 5),
                                hoursWeekPlaceholder);

                new inputFormatterFactory().createInputFormatter("PRICE").applyFormat(salaryTextField);
                new inputFormatterFactory().createInputFormatter("TIME").applyFormat(hoursWeekTextField);
        }

        private void setComboBox() {
                for (int i = 0; i < roles.size(); i++) {
                        if (roles.get(i).getId() == theEmployee.getRoleID()) {
                                role temp = roles.remove(i);
                                roles.add(0, temp);
                        }
                }
                roleComboBox.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
                ArrayList<String> tempNames = new ArrayList<>();
                for (role temp : roles)
                        tempNames.add(temp.getName());

                String[] namesArr = tempNames.toArray(new String[0]);
                roleComboBox.setModel(new DefaultComboBoxModel<String>(namesArr));
                roleComboBox.setFont(new Font("Segoe UI", 0, 18));
                roleComboBox.setForeground(Color.BLACK);
                roleComboBox.setBackground(Color.WHITE);
        }

}
