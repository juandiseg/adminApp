package windows.productsWindow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Stack;

import componentsFood.product;
import util.abstractManagerDB;

public class productAPI extends abstractManagerDB {

    // GET "product" objects from database.

    public ArrayList<product> getAllCurrentProducts() {
        ArrayList<product> tempList = new ArrayList<product>();
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products ORDER BY product_id";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("product_id");
                    String date = rs.getString("product_date");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    boolean active = rs.getBoolean("active");
                    tempList.add(new product(ID, date, name, price, active));
                }
                tempList = checkRepeatedProducts(tempList);
                connection.close();
                return tempList;
            } catch (Exception e) {
                System.out.println(e);
                return tempList;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public product getProduct(int productID) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products WHERE product_id = " + productID
                    + " ORDER BY product_date DESC LIMIT 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int ID = rs.getInt("product_id");
                    String date = rs.getString("product_date");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    boolean active = rs.getBoolean("active");
                    return new product(ID, date, name, price, active);
                }
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return null;
    }

    private ArrayList<product> checkRepeatedProducts(ArrayList<product> theList) {
        int goal = theList.size();
        for (int i = 0; i < goal - 1; i++) {
            if (theList.get(i).getId() == theList.get(i + 1).getId()) {
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

    public ArrayList<menu> getSelectedProductsInMenu(menu theMenu) {
        ArrayList<ingredient> tempList = new ArrayList<ingredient>();
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM (SELECT ingredient_id, a.product_ingredients_date , ingredientQuantity FROM products_ingredients AS a, (SELECT MAX(product_ingredients_date) AS product_ingredients_date FROM products_ingredients WHERE product_id = "
                    + theProduct.getId() + ") AS b WHERE product_id = " + theProduct.getId()
                    + " AND a.product_ingredients_date = b.product_ingredients_date) AS temp NATURAL JOIN ingredients WHERE active = true";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("ingredient_id");
                    int providerID = rs.getInt("provider_id");
                    String date = rs.getString("product_ingredients_date");
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

    public ArrayList<menu> getNonSelectedProductsInMenu(menu theMenu) {
        ArrayList<ingredient> tempList = new ArrayList<ingredient>();
        // NOT WORKING FOR INGREDIENTS WITH NO INGREDIENTS
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM ingredients WHERE ingredient_id NOT IN (SELECT DISTINCT ingredient_id FROM products_ingredients AS a, (SELECT MAX(product_ingredients_date) AS temp FROM products_ingredients WHERE product_id = 1) AS b WHERE a.product_id = "
                    + theProduct.getId() + " AND a.product_ingredients_date = b.temp) AND active = true";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("ingredient_id");
                    int providerID = rs.getInt("provider_id");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    int amount = rs.getInt("amount");
                    boolean in_inventory = rs.getBoolean("in_inventory");
                    boolean active = rs.getBoolean("active");
                    tempList.add(new ingredient(ID, providerID, "", name, price, amount, in_inventory, active));
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

    // ADD "product" to database.
    public product addProduct(String date, String name, float price, boolean active) {
        int productID = getLastProductID() + 1;
        return addProduct(productID, date, name, price, active);
    }

    private product addProduct(int ID, String date, String name, float price, boolean active) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "INSERT INTO products VALUES (" + ID + ", '" + date + "', '" + name + "', " + price
                    + ", " + active + ");";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return new product(ID, date, name, price, active);
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private int getLastProductID() {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT product_id FROM products ORDER BY product_id DESC LIMIT 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int providerID = rs.getInt("product_id");
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

    public boolean addIngredients(int productID, int ingredientID, String date, float quantity) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "INSERT INTO products_ingredients VALUES (" + productID + ", " + ingredientID + ", '" + date
                    + "', " + quantity + ")";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
            } catch (Exception a) {
                System.out.println(a);
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    // UPDATE something "product" related in database.
    public boolean updateProductName(int productID, String name) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products AS p, (SELECT MAX(product_date) AS product_date FROM products WHERE product_id = "
                    + productID + ") AS temp SET p.name = '" + name
                    + "' WHERE p.product_date = temp.product_date AND p.product_id = " + productID + ";";
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

    public boolean updateProductActive(int productID, boolean active) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products AS p, (SELECT MAX(product_date) AS product_date FROM products WHERE product_id = "
                    + productID + ") AS temp SET p.active = " + active
                    + " WHERE p.product_date = temp.product_date AND p.product_id = " + productID + ";";
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

    public boolean updatePrice(int productID, float productPrice) {
        fixProductDate(productID);
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products AS p, (SELECT MAX(product_date) AS product_date FROM products WHERE product_id = "
                    + productID + ") AS temp SET p.price = " + productPrice
                    + " WHERE p.product_date = temp.product_date AND p.product_id = " + productID + ";";
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

    private void fixProductDate(int productID) {
        if (isLastProductEntryToday(productID))
            return;
        product tempProduct = getProduct(productID);
        String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        addProduct(productID, dateToday, tempProduct.getName(), tempProduct.getPrice(), tempProduct.getActive());
    }

    private boolean isLastProductEntryToday(int productID) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String query = "SELECT product_date FROM products WHERE product_id = " + productID + " AND active = true;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String dateDB = rs.getString("product_date");
                    connection.close();
                    if (dateToday.equals(dateDB))
                        return true;
                    else
                        return false;
                } else {
                    connection.close();
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean isProductIngredientDateToday(int product_id) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String query = "SELECT MAX(products_ingredients_date) FROM products_ingredients WHERE product_id = "
                    + product_id + ";";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String dateDB = rs.getString("MAX(products_ingredients_date)");
                    connection.close();
                    if (dateToday.equals(dateDB))
                        return true;
                    else
                        return false;
                } else {
                    connection.close();
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public boolean updateIngredients(int productID, Stack<Integer> stackIDs, Stack<Integer> stackAmounts) {
        if (areIngredientEntriesToday(productID))
            removeProductIngredientsToday(productID);
        while (!stackIDs.empty() && !stackAmounts.empty()) {
            String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            addIngredients(productID, stackIDs.pop(), dateToday, stackAmounts.pop()); // HAVE TO DO QUANTITY TOO
        }
        return true;
    }

    private boolean areIngredientEntriesToday(int productID) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String query = "SELECT product_ingredients_date FROM products_ingredients WHERE product_id = " + productID
                    + " ORDER BY product_ingredients_date DESC LIMIT 1;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    String dateDB = rs.getString("product_ingredients_date");
                    connection.close();
                    if (dateToday.equals(dateDB))
                        return true;
                    else
                        return false;
                } else {
                    connection.close();
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    // REMOVE "product" from database.
    private void removeProductIngredientsToday(int productID) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String query = "DELETE FROM products_ingredients WHERE product_id = " + productID
                    + " AND product_ingredients_date = '" + dateToday + "';";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

}
