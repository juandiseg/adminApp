package navigation.administration.shiftsNav;

import javax.swing.table.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.*;

import componentsFood.shift;
import util.buttonFormatters.*;
import util.databaseAPIs.employeesAPI;
import util.databaseAPIs.shiftsAPI;
import util.inputFormatting.iFormatter;
import util.inputFormatting.inputFormatterFactory;
import util.listenersFormatting.booleanWrapper;
import util.listenersFormatting.iTextFieldListener;
import util.listenersFormatting.edit.editDateTFFListener;

public class editShifts {

        private JLabel dateLabel = new JLabel();
        private JLabel startShiftLabel = new JLabel();
        private JLabel endShiftLabel = new JLabel();

        private JTextField dateTextField = new JTextField();
        private JTextField startShiftTextField = new JTextField();
        private JTextField endShiftTextField = new JTextField();

        private booleanWrapper datePlaceholder = new booleanWrapper(true);
        private booleanWrapper startPlaceholder = new booleanWrapper(true);
        private booleanWrapper endPlaceholder = new booleanWrapper(true);

        private JButton backButton = new JButton();
        private JButton deleteButton = new JButton();

        private JButton selectButton = new JButton();
        private JButton unselectButton = new JButton();
        private JButton editShiftsButton = new JButton();

        private JLabel selectShiftsLabel = new JLabel();
        private JLabel editShiftLabel = new JLabel();

        private JScrollPane selectedJScrollPane = new JScrollPane();
        private JScrollPane unselectedJScrollPane = new JScrollPane();

        private JLabel successLabel = new JLabel("Shift(s) successfully added !");

        private JPanel jPanel1 = new JPanel();
        private JPanel jPanel2 = new JPanel();
        private JPanel jPanel3 = new JPanel();

        private JTable tableEmployees;
        private JTable tableSelected;

        private DefaultTableModel modelEmployees;
        private DefaultTableModel modelSelected;

        private boolean shiftDate;

        private shift theShift;
        private String from;
        private String to;

        public editShifts(JPanel playground, String from, String to, boolean shiftDate, shift theShift)
                        throws ParseException {
                this.theShift = theShift;
                this.shiftDate = shiftDate;
                this.from = from;
                this.to = to;
                setTables(from, to, shiftDate);
                initComponents(playground);
                addListeners(playground);
        }

        private void initComponents(JPanel playground) {

                playground.setBackground(new Color(255, 255, 255));

                successLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
                successLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                successLabel.setVisible(false);
                successLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                jPanel1.setBackground(new Color(120, 168, 252));
                jPanel1.setCursor(new Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                jPanel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);

                dateLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                dateLabel.setHorizontalAlignment(SwingConstants.LEFT);
                dateLabel.setText("Date");
                dateLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                editShiftsButton.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
                editShiftsButton.setText("Edit Shift(s)");
                editShiftsButton.setBackground(new Color(255, 255, 255));
                editShiftsButton.setForeground(new Color(23, 35, 51));

                jPanel2.setBackground(new Color(0, 0, 0));

                GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 8, Short.MAX_VALUE));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGap(0, 0, Short.MAX_VALUE));

                startShiftLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                startShiftLabel.setHorizontalAlignment(SwingConstants.LEFT);
                startShiftLabel.setText("Start Shift");
                startShiftLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                endShiftLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                endShiftLabel.setHorizontalAlignment(SwingConstants.LEFT);
                endShiftLabel.setText("End Shift");
                endShiftLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                selectShiftsLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                selectShiftsLabel.setHorizontalAlignment(SwingConstants.LEFT);
                selectShiftsLabel.setText("Select Shifts");
                selectShiftsLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
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
                                                                                                .addGap(125, 125, 125)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(endShiftLabel,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(startShiftLabel,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(dateLabel,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                86,
                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(GroupLayout.Alignment.TRAILING,
                                                                                                                                jPanel1Layout.createParallelGroup(
                                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                                false)
                                                                                                                                                .addComponent(startShiftTextField,
                                                                                                                                                                GroupLayout.Alignment.TRAILING)
                                                                                                                                                .addComponent(endShiftTextField,
                                                                                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                434,
                                                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(dateTextField,
                                                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                434,
                                                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                                                .addContainerGap(
                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(GroupLayout.Alignment.TRAILING,
                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                .addPreferredGap(
                                                                                                                                ComponentPlacement.RELATED,
                                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                GroupLayout.Alignment.LEADING,
                                                                                                                                                false)
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
                                                                                                                                                                .addComponent(editShiftsButton,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                200,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addGap(163, 163,
                                                                                                                                                                                163)
                                                                                                                                                                .addComponent(unselectButton))
                                                                                                                                .addGroup(GroupLayout.Alignment.TRAILING,
                                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                                .addComponent(unselectedJScrollPane,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                333,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                ComponentPlacement.UNRELATED)
                                                                                                                                                                .addComponent(selectedJScrollPane,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                333,
                                                                                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                                                                .addGap(99, 99, 99)))));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(dateLabel)
                                                                                .addComponent(dateTextField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(startShiftTextField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(startShiftLabel))
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(endShiftLabel)
                                                                                .addComponent(endShiftTextField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                35,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(selectedJScrollPane,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                190,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(unselectedJScrollPane,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                190,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addGroup(GroupLayout.Alignment.TRAILING,
                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                                                                .addComponent(unselectButton)
                                                                                                                                .addComponent(selectButton))
                                                                                                                .addGap(52, 52, 52))
                                                                                .addComponent(editShiftsButton,
                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                55,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addGap(10, 10, 10))
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

                deleteButton.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
                deleteButton.setText("Delete");
                deleteButton.setBackground(new Color(255, 102, 102));
                deleteButton.setForeground(new Color(255, 255, 255));

                editShiftLabel.setFont(new Font("Segoe UI", 1, 18)); // NOI18N
                editShiftLabel.setHorizontalAlignment(SwingConstants.CENTER);
                editShiftLabel.setText("Edit Shifts");
                editShiftLabel.setToolTipText("");
                editShiftLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                GroupLayout playgroundLayout = new GroupLayout(playground);
                playground.setLayout(playgroundLayout);
                playgroundLayout.setHorizontalGroup(
                                playgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(editShiftLabel,
                                                                GroupLayout.Alignment.TRAILING,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(playgroundLayout.createSequentialGroup()
                                                                .addGap(185, 185, 185)
                                                                .addComponent(jPanel3,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 185, Short.MAX_VALUE))
                                                .addGroup(GroupLayout.Alignment.TRAILING, playgroundLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(playgroundLayout.createParallelGroup(
                                                                                GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(successLabel,
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
                                                                                                                ComponentPlacement.RELATED,
                                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(deleteButton,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                200,
                                                                                                                GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap()));
                playgroundLayout.setVerticalGroup(
                                playgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, playgroundLayout
                                                                .createSequentialGroup()
                                                                .addGap(39, 39, 39)
                                                                .addComponent(editShiftLabel)
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
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED)
                                                                .addComponent(successLabel)
                                                                .addPreferredGap(
                                                                                ComponentPlacement.RELATED,
                                                                                23, Short.MAX_VALUE)
                                                                .addGroup(playgroundLayout.createParallelGroup(
                                                                                GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(deleteButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                55,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(backButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                55,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap()));
        }

        private void addListeners(JPanel playground) {
                // Call to the method(s) which format each JButton / JTextField accordingly.
                deleteButton(playground);
                selectionButtons();
                backButton(playground);
                editButton(playground);
                applyGenericListeners();
        }

        private void deleteButton(JPanel playground) {
                class deleteMethodHolder implements iDeleteButton {

                        public boolean thereIsError() {
                                return modelSelected.getRowCount() == 0;
                        }

                        public boolean askConfirmation() {
                                boolean neededToChoose = false;

                                int reply = JOptionPane.showConfirmDialog(playground,
                                                "Are you sure you want to delete the selected shifts?", "Confirmation",
                                                JOptionPane.YES_NO_OPTION);

                                if (reply == JOptionPane.YES_OPTION) {
                                        ArrayList<shift> listShifts = new ArrayList<shift>();
                                        for (int i = 0; i < modelSelected.getRowCount(); i++) {
                                                int id = Integer.parseInt((String) modelSelected.getValueAt(i, 0));
                                                String date = (String) modelSelected.getValueAt(i, 3);
                                                String startTime = (String) modelSelected.getValueAt(i, 4);
                                                String endTime = (String) modelSelected.getValueAt(i, 5);
                                                listShifts.add(new shift(id, date, startTime, endTime));
                                        }
                                        for (int i = modelSelected.getRowCount() - 1; i > -1; i--) {
                                                modelSelected.removeRow(i);
                                                shiftsAPI.deleteShift(listShifts.get(i));
                                        }
                                }

                                return neededToChoose;
                        }

                        public void chooseAmongOptions() {
                        }

                }
                deleteButtonFormatter.formatDeleteButton(deleteButton, new deleteMethodHolder());
        }

        private void selectionButtons() {
                class selectMethodHolder implements iSelectionButton {
                        public void doSelection() {
                                int row = tableEmployees.getSelectedRow();
                                if (row == -1)
                                        return;
                                String ID = (String) modelEmployees.getValueAt(row, 0);
                                String name = (String) modelEmployees.getValueAt(row, 1);
                                String salary = (String) modelEmployees.getValueAt(row, 2);
                                String hoursWeek = (String) modelEmployees.getValueAt(row, 3);
                                String role = (String) modelEmployees.getValueAt(row, 4);
                                String roleID = (String) modelEmployees.getValueAt(row, 5);
                                modelEmployees.removeRow(row);
                                modelSelected.addRow(new String[] { ID, name, salary, hoursWeek, role, roleID });
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
                                String salary = (String) modelSelected.getValueAt(row, 2);
                                String hoursWeek = (String) modelSelected.getValueAt(row, 3);
                                String role = (String) modelSelected.getValueAt(row, 4);
                                String roleID = (String) modelSelected.getValueAt(row, 5);
                                modelSelected.removeRow(row);
                                modelEmployees.addRow(new String[] { ID, name, salary, hoursWeek, role, roleID });
                        }
                }
                selectionButtonFormatter.formatSelectionButton(unselectButton, new unselectMethodHolder(), false);
        }

        private void backButton(JPanel playground) {
                class backMethodHolder extends iNavigatorButton {
                        public void createNewNavigator() {
                                new mainShifts(playground, from, to, shiftDate);
                        }
                }
                navigatorButtonFormatter.formatNavigationButton(backButton, new backMethodHolder(), playground, true,
                                "Back");
        }

        private void editButton(JPanel playground) {
                class editMethodsHolder implements iEditButton {

                        private String newStart;
                        private String newEnd;
                        private String newDate;

                        public boolean valuesArePlaceholders() {
                                return false;
                        }

                        public boolean areInputsInvalid() {
                                for (int i = 0; i < modelSelected.getRowCount(); i++) {
                                        for (int innerI = 0; innerI < modelSelected.getRowCount(); innerI++) {
                                                String i_ID = (String) modelSelected.getValueAt(i, 0);
                                                String inner_ID = (String) modelSelected.getValueAt(innerI, 0);
                                                if (i_ID.equals(inner_ID) && i != innerI) {
                                                        successLabel.setText(
                                                                        "Error. Cannot edit multiple times the same employee.");
                                                        successLabel.setVisible(true);
                                                        return true;
                                                }
                                        }
                                }
                                successLabel.setVisible(false);

                                newStart = startShiftTextField.getText();
                                newEnd = endShiftTextField.getText();
                                newDate = dateTextField.getText();

                                // Check for correct date input.
                                LocalDate localDateNew;
                                try {
                                        localDateNew = LocalDate.parse(newDate,
                                                        DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                } catch (Exception DateTimeParseException) {
                                        successLabel.setText("ERROR. The given date is unvalid.");
                                        successLabel.setVisible(true);
                                        return true;
                                }
                                if (localDateNew.isBefore(LocalDate.now())) {
                                        successLabel.setText("ERROR. You can't add a shift that occured in the past.");
                                        successLabel.setVisible(true);
                                        return true;
                                }

                                // Check for correct time input.
                                int startHour = Integer.parseInt(newStart.substring(0, 2));
                                int startMins = Integer.parseInt(newStart.substring(3, 5));
                                int endHour = Integer.parseInt(newEnd.substring(0, 2));
                                int endMins = Integer.parseInt(newEnd.substring(3, 5));
                                if (startHour >= 24 || startMins >= 60 || endHour >= 24 || endMins >= 60) {
                                        successLabel.setText("ERROR. The given time is incorrect.");
                                        successLabel.setVisible(true);
                                        return true;
                                }

                                // Checks that entry time comes before exit time.
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
                                try {
                                        Date start = timeFormat.parse(newStart);
                                        Date end = timeFormat.parse(newEnd);
                                        if (start.after(end)) {
                                                successLabel.setText("ERROR. The shift start must be before the end.");
                                                successLabel.setVisible(true);
                                                return true;
                                        }
                                } catch (ParseException e) {
                                        successLabel.setText("ERROR. The given times are unvalid.");
                                        successLabel.setVisible(true);
                                        return true;
                                }

                                // Checks for conflicts, employees already working on that time.
                                ArrayList<shift> selectedShifts = getSelectedShifts();
                                ArrayList<String> employeeNames = shiftsAPI.getNameOfEmbededShiftsEdit(
                                                selectedShifts, newDate, newStart, newEnd);
                                if (employeeNames.size() > 0) {
                                        successLabel.setText(
                                                        "Error. The following employees already have shifts within the given time: ");
                                        for (String temp : employeeNames)
                                                successLabel.setText(successLabel.getText().concat(temp + ", "));
                                        String text = successLabel.getText();
                                        successLabel.setText(text.substring(0, text.length() - 2).concat("."));
                                        successLabel.setVisible(true);
                                        return true;
                                }

                                // If all checks are passed, input should be valid.
                                return false;
                        }

                        public void editFoodComponent() {
                                boolean successfulUpdate = true;
                                ArrayList<shift> listShifts = getListOfShifts();
                                for (shift temp : listShifts)
                                        shiftsAPI.updateEntryTime(temp, newStart);
                                for (shift temp : listShifts)
                                        shiftsAPI.updateEndTime(temp, newEnd);
                                successfulUpdate = true;

                                if (!datePlaceholder.getValue() && successfulUpdate) {
                                        for (shift temp : listShifts)
                                                shiftsAPI.updateShiftDate(temp, newDate);
                                        successfulUpdate = true;
                                }

                                if (successfulUpdate)
                                        successLabel.setText("The shift(s) was/were successfully updated.");
                                else
                                        successLabel.setText("Something went wrong while updating the shift(s).");
                                successLabel.setVisible(true);

                        }

                        public void updatePlaceholders() {
                                playground.removeAll();
                                new mainShifts(playground, from, to, shiftDate);
                                playground.revalidate();
                                playground.repaint();
                        }
                }
                editButtonFormatter.formatEditButton(editShiftsButton, new editMethodsHolder());
        }

        private ArrayList<shift> getSelectedShifts() {
                ArrayList<shift> selectedShifts = new ArrayList<shift>();
                for (int i = 0; i < modelSelected.getRowCount(); i++) {
                        int ID = Integer.parseInt((String) modelSelected.getValueAt(i, 0));
                        String date = (String) modelSelected.getValueAt(i, 3);
                        String start = (String) modelSelected.getValueAt(i, 4);
                        String end = (String) modelSelected.getValueAt(i, 5);
                        selectedShifts.add(new shift(ID, date, start, end));
                }
                return selectedShifts;
        }

        private ArrayList<shift> getListOfShifts() {
                ArrayList<shift> listShifts = new ArrayList<shift>();
                for (int i = 0; i < modelSelected.getRowCount(); i++) {
                        int id = Integer.parseInt((String) modelSelected.getValueAt(i, 0));
                        String date = (String) modelSelected.getValueAt(i, 3);
                        String startTime = (String) modelSelected.getValueAt(i, 4);
                        String endTime = (String) modelSelected.getValueAt(i, 5);
                        listShifts.add(new shift(id, date, startTime, endTime));
                }
                return listShifts;
        }

        private void applyGenericListeners() {
                new inputFormatterFactory().createInputFormatter("DATE").applyFormat(dateTextField);
                iFormatter timeFormatter = new inputFormatterFactory().createInputFormatter("TIME");
                timeFormatter.applyFormat(startShiftTextField);
                timeFormatter.applyFormat(endShiftTextField);

                iTextFieldListener textListener = new editDateTFFListener();
                textListener.applyListenerTextField(dateTextField, theShift.getDate(), datePlaceholder, false);
                textListener.applyListenerTextField(startShiftTextField, theShift.getStartTime().substring(0, 5),
                                startPlaceholder,
                                false);
                textListener.applyListenerTextField(endShiftTextField, theShift.getEndTime().substring(0, 5),
                                endPlaceholder, false);
        }

        private void setTables(String from, String to, boolean shiftDate) {
                Color white = new Color(255, 255, 255);
                tableEmployees = new JTable();
                tableSelected = new JTable();
                modelEmployees = new DefaultTableModel(
                                new String[] { "id", "Employee", "Role", "Shift Date", "Start", "End" }, 0);
                modelSelected = new DefaultTableModel(
                                new String[] { "id", "Employee", "Role", "Shift Date", "Start", "End" }, 0);
                for (shift tempShift : shiftsAPI.getAllFutureShiftsUntil(to)) {
                        if (!tempShift.equals(theShift)) {
                                String id = Integer.toString(tempShift.getEmployeeId());
                                String name = employeesAPI.getNameOfEmployee(tempShift.getEmployeeId());
                                String role = employeesAPI.getRoleOfEmployee(tempShift.getEmployeeId());
                                String date = tempShift.getDate();
                                String start = tempShift.getStartTime();
                                String end = tempShift.getEndTime();
                                modelEmployees.addRow(new String[] { id, name, role, date, start, end });
                        }
                }
                String id = Integer.toString(theShift.getEmployeeId());
                String name = employeesAPI.getNameOfEmployee(theShift.getEmployeeId());
                String role = employeesAPI.getRoleOfEmployee(theShift.getEmployeeId());
                String date = theShift.getDate();
                String start = theShift.getStartTime();
                String end = theShift.getEndTime();
                modelSelected.addRow(new String[] { id, name, role, date, start, end });

                tableEmployees.setModel(modelEmployees);
                tableEmployees.removeColumn(tableEmployees.getColumn("id"));
                tableEmployees.setDefaultEditor(Object.class, null);
                tableEmployees.setBackground(white);
                unselectedJScrollPane.setBackground(white);
                unselectedJScrollPane.setViewportView(tableEmployees);
                tableLookPretty(tableEmployees);

                tableSelected.setModel(modelSelected);
                tableSelected.removeColumn(tableSelected.getColumn("id"));
                tableSelected.setDefaultEditor(Object.class, null);
                selectedJScrollPane.setViewportView(tableSelected);
                selectedJScrollPane.setBackground(white);
                selectedJScrollPane.getViewport().setBackground(white);
                tableLookPretty(tableSelected);
        }

        private void tableLookPretty(JTable theTable) {
                theTable.setDefaultEditor(Object.class, null);
                theTable.setFocusable(false);

                theTable.getTableHeader().setFont(new Font("Segoe UI", 1, 9));
                theTable.getTableHeader().setBackground(new Color(120, 168, 252));
                theTable.setBackground(new Color(245, 245, 245));

                theTable.setFillsViewportHeight(true);
                theTable.setFont(new Font("Segoe UI", 0, 9));
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                theTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
                theTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
                Dimension temp = new Dimension(20, 1);
                theTable.setIntercellSpacing(temp);
                theTable.setBackground(new Color(255, 255, 255));
                theTable.setRowHeight(theTable.getRowHeight() + 10);
        }
}