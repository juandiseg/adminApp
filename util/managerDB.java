package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

import java.sql.ResultSet;
import componentsFood.*;

public class managerDB {

    private final static String url = "jdbc:mysql://localhost:3306/beatneat";
    private final String user = "juandi"; // Change to your local user
    private final String password = "Juandi"; // Change to your local password

    public Connection getConnection() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Database connected!");
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean addProvider(String name, String email) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            int provID = getLastProvID() + 1;
            String query = "INSERT INTO providers VALUES (" + provID + ", '" + name + "', '" + email + "');";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private int getLastProvID() {
        try (Connection connection = DriverManager.getConnection(url, "juandi", "Juandi")) {
            String query = "SELECT provider_id FROM beatneat.providers ORDER BY provider_id DESC LIMIT 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int providerID = rs.getInt("provider_id");
                    connection.close();
                    return providerID;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private int getLastIngredientID() {
        try (Connection connection = DriverManager.getConnection(url, "juandi", "Juandi")) {
            String query = "SELECT ingredient_id FROM beatneat.ingredients ORDER BY ingredient_id DESC LIMIT 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int providerID = rs.getInt("ingredient_id");
                    connection.close();
                    return providerID;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public int addIngredient(int provID, String date, String name, String price, int amount, boolean in_inventory,
            boolean active) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            int ingrID = getLastIngredientID() + 1;
            String query = "INSERT INTO ingredients VALUES (" + ingrID + ", " + provID + ", '" + date + "', '" + name
                    + "', " + price + ", " + amount + ", " + in_inventory + ", " + active + ");";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return ingrID;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean editIngredient(int ingID, int provID, String date, String name, double price, int amount,
            boolean in_inventory, boolean active) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE ingredients SET provider_id = " + provID + ", ingredients_date = '" + date
                    + "', name = '" + name + "', price = " + price + ", amount = " + amount + ", in_inventory = "
                    + in_inventory + ", active = " + active + " WHERE provider_id = " + Integer.toString(ingID);
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public ArrayList<provider> getAllProviders() {
        ArrayList<provider> tempList = new ArrayList<provider>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM providers";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int providerID = rs.getInt("provider_id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    tempList.add(new provider(providerID, name, email));
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public provider getProvider(int ID) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM providers WHERE provider_id = " + Integer.toString(ID);
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int providerID = rs.getInt("provider_id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    provider temp = new provider(providerID, name, email);
                    connection.close();
                    return temp;
                }
                connection.close();
                return null;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean editProvider(int ID, String name, String email) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE providers SET name = '" + name + "', email = '" + email
                    + "' WHERE provider_id = " + Integer.toString(ID);
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private int getLastAllergenID() {
        try (Connection connection = DriverManager.getConnection(url, "juandi", "Juandi")) {
            String query = "SELECT allergen_id FROM beatneat.allergens ORDER BY allergen_id DESC LIMIT 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int allergenID = rs.getInt("allergen_id");
                    connection.close();
                    return allergenID;
                }
                return -1;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public allergen getAllergen(int ID) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM allergens WHERE allergen_id = " + ID;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int allergenID = rs.getInt("allergen_id");
                    String name = rs.getString("name");
                    allergen temp = new allergen(allergenID, name);
                    connection.close();
                    return temp;
                }
                connection.close();
                return null;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean addAllergen(String name) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String queryTest = "SELECT * FROM allergens WHERE name LIKE '%" + name + "%' LIMIT 1";
            try (Statement stmt1 = connection.createStatement()) {
                ResultSet rs = stmt1.executeQuery(queryTest);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "There is a similar entry with that name.", "Error",
                            JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
            int allergenID = getLastAllergenID() + 1;
            String query = "INSERT INTO allergens VALUES (" + allergenID + ", '" + name + "');";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public ArrayList<allergen> getAllAllergens() {
        ArrayList<allergen> tempList = new ArrayList<allergen>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM allergens";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int allergenID = rs.getInt("allergen_id");
                    String name = rs.getString("name");
                    tempList.add(new allergen(allergenID, name));
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean editAllergen(int ID, String name) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE allergens SET name = '" + name + "' WHERE allergen_id = " + Integer.toString(ID);
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public ArrayList<ingredient> getAllIngredients() {
        ArrayList<ingredient> tempList = new ArrayList<ingredient>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM ingredients";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("ingredient_id");
                    int providerID = rs.getInt("provider_id");
                    String date = rs.getString("ingredients_date");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    int amount = rs.getInt("amount");
                    boolean in_inventory = rs.getBoolean("in_inventory");
                    boolean active = rs.getBoolean("active");
                    tempList.add(new ingredient(ID, providerID, date, name, price, amount, in_inventory, active));
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public ArrayList<ingredient> getAllCurrentIngredients() {
        ArrayList<ingredient> tempList = getNonRepeatedIngredients();
        tempList = getCorrectRepeatedIngredient(tempList);
        return tempList;
    }

    private ArrayList<ingredient> getNonRepeatedIngredients() {
        ArrayList<ingredient> tempList = new ArrayList<ingredient>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM ingredients WHERE ingredient_id NOT IN (SELECT ingredient_id FROM ingredients GROUP BY ingredient_id HAVING count(*) > 1);";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("ingredient_id");
                    int providerID = rs.getInt("provider_id");
                    String date = rs.getString("ingredients_date");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    int amount = rs.getInt("amount");
                    boolean in_inventory = rs.getBoolean("in_inventory");
                    boolean active = rs.getBoolean("active");
                    tempList.add(new ingredient(ID, providerID, date, name, price, amount, in_inventory, active));
                }
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private ArrayList<ingredient> getCorrectRepeatedIngredient(ArrayList<ingredient> theList) {
        ArrayList<ingredient> tempList = new ArrayList<ingredient>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM ingredients WHERE ingredient_id IN (SELECT ingredient_id FROM ingredients GROUP BY ingredient_id HAVING count(*) > 1);";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("ingredient_id");
                    int providerID = rs.getInt("provider_id");
                    String date = rs.getString("ingredients_date");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    int amount = rs.getInt("amount");
                    boolean in_inventory = rs.getBoolean("in_inventory");
                    boolean active = rs.getBoolean("active");
                    tempList.add(new ingredient(ID, providerID, date, name, price, amount, in_inventory, active));
                }
                checkLatestIngredients(tempList);
                theList.addAll(tempList);
                connection.close();
                return theList;
            } catch (Exception e) {
                System.out.println(e);
                return theList;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private ArrayList<ingredient> checkLatestIngredients(ArrayList<ingredient> theList) {
        int goal = theList.size();
        for (int i = 0; i < goal - 1; i++) {
            if (compareIDs(theList.get(i), theList.get(i + 1))) {
                LocalDate date1 = LocalDate.parse(theList.get(i).getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate date2 = LocalDate.parse(theList.get(i + 1).getDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (date1.isBefore(date2)) {
                    theList.remove(i);
                    goal--;
                    i--;
                } else {
                    theList.remove(i + 1);
                    goal--;
                    i--;
                }
            }
        }
        return theList;
    }

    private boolean compareIDs(ingredient a, ingredient b) {
        if (a.getId() == b.getId())
            return true;
        return false;
    }

    public boolean addAlergensOfIngredient(Stack<allergen> stackSelected, int ingredientID) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            while (!stackSelected.isEmpty()) {
                allergen temp = stackSelected.pop();
                String query = "INSERT INTO ingredients_allergens VALUES (" + ingredientID + ", '" + temp.getId()
                        + "');";
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeUpdate(query);
                } catch (Exception a) {
                    System.out.println(a);
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean removeAllergen(String ID) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM ingredients_allergens WHERE allergen_id = " + ID + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
            } catch (Exception e) {
                return false;
            }
            query = "DELETE FROM allergens WHERE allergen_id = " + ID + ";";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public ingredient getIngredient(int ID, int prov_id, String date) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM ingredients WHERE ingredient_id = " + ID + " AND provider_id  = " + prov_id
                    + " AND ingredients_date = '" + date + "';";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    int amount = rs.getInt("amount");
                    boolean in_inventory = rs.getBoolean("in_inventory");
                    boolean active = rs.getBoolean("active");
                    connection.close();
                    return new ingredient(ID, prov_id, date, name, price, amount, in_inventory, active);
                } else {
                    connection.close();
                    return null;
                }
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public String getIngredientName(String ID) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM ingredients WHERE ingredient_id = " + ID;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String name = rs.getString("name");
                    connection.close();
                    return name;
                } else {
                    connection.close();
                    return "";
                }
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public ArrayList<String> getAllergensOfIngredient(String ID) {
        Stack<Integer> allergenIDs = new Stack<Integer>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT allergen_id FROM ingredients_allergens WHERE ingredient_id = " + ID;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next())
                    allergenIDs.add(rs.getInt("allergen_id"));
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
            ArrayList<String> containingAllergenNames = new ArrayList<String>();
            while (!allergenIDs.isEmpty()) {
                query = "SELECT name FROM allergens WHERE allergen_id = " + allergenIDs.pop();
                try (Statement stmt = connection.createStatement()) {
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next())
                        containingAllergenNames.add(rs.getString("name"));
                } catch (Exception e) {
                    System.out.println(e);
                    return null;
                }
            }
            return containingAllergenNames;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean ingredientSimpleEdit(ingredient theIngredient, String newName, boolean newInventory,
            boolean newActive) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE ingredients SET name = '" + newName + "', in_inventory = " + newInventory
                    + ", active = " + newActive + " WHERE ingredient_id = " + theIngredient.getId()
                    + " AND provider_id = "
                    + theIngredient.getProviderID() + " AND ingredients_date ='" + theIngredient.getDate() + "'";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean ingredientComplexIngredientEdit(ingredient theIngredient, int prov_id, int amount, float price) {
        if (theIngredient.getProviderID() == prov_id && theIngredient.getAmount() == amount
                && theIngredient.getPrice() == price)
            return false;
        LocalDate dateObj = LocalDate.now();
        String date = dateObj.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (theIngredient.getDate().equals(date)) {
            return updateComplexIngredient(theIngredient, prov_id, amount, price);
        }
        if (theIngredient.getDate().equals(date))
            return false;
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "INSERT INTO ingredients VALUES (" + theIngredient.getId() + ", " + prov_id + ", '"
                    + date
                    + "', '" + theIngredient.getName()
                    + "', " + price + ", " + amount + ", " + theIngredient.getInInventory() + ", "
                    + theIngredient.getActive() + ");";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean updateComplexIngredient(ingredient theIngredient, int prov_id, int amount, float price) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "UPDATE ingredients SET provider_id = " + prov_id
                    + ", amount = " + amount + ", price = " + price
                    + " WHERE ingredient_id = " + theIngredient.getId() + " AND provider_id = "
                    + theIngredient.getProviderID() + " AND ingredients_date = '" + theIngredient.getDate() + "';";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

}
