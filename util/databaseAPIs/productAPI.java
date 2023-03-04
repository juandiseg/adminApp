package util.databaseAPIs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Stack;

import componentsFood.menu;
import componentsFood.product;

public class productAPI extends abstractManagerDB {

    // GET "product" objects from database.

    public ArrayList<product> getAllCurrentProducts() {
        ArrayList<product> tempList = new ArrayList<product>();
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products WHERE active = true ORDER BY product_id";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("product_id");
                    int catID = rs.getInt("category_id");
                    String date = rs.getString("product_date");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    tempList.add(new product(ID, catID, dateInverter.invert(date), name, price, true));
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
                    int catID = rs.getInt("category_id");
                    String date = rs.getString("product_date");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    boolean active = rs.getBoolean("active");
                    return new product(ID, catID, date, name, price, active);
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
                LocalDate date1 = LocalDate.parse(theList.get(i).getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalDate date2 = LocalDate.parse(theList.get(i + 1).getDate(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public ArrayList<product> getSelectedProductsInMenu(menu theMenu) {
        ArrayList<product> tempList = new ArrayList<product>();
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products NATURAL JOIN (SELECT product_id, a.menu_products_date, productQuantity FROM menus_products AS a, (SELECT MAX(menu_products_date) AS menu_products_date FROM menus_products WHERE menu_id = "
                    + theMenu.getId() + ") AS b WHERE menu_id = " + theMenu.getId()
                    + " AND a.menu_products_date = b.menu_products_date) as x WHERE active = true";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("product_id");
                    int catID = rs.getInt("product_id");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    boolean active = rs.getBoolean("active");
                    tempList.add(new product(ID, catID, "", name, price, active));
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

    public ArrayList<product> getNonSelectedProductsInMenu(menu theMenu) {
        ArrayList<product> tempList = new ArrayList<product>();
        // NOT WORKING FOR INGREDIENTS WITH NO INGREDIENTS
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products WHERE product_id NOT IN (SELECT DISTINCT product_id FROM menus_products AS a, (SELECT MAX(menu_products_date) AS temp FROM menus_products WHERE menu_id = "
                    + theMenu.getId() + ") AS b WHERE a.menu_id = " + theMenu.getId()
                    + " AND a.menu_products_date = b.temp) AND active = true";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("product_id");
                    int catID = rs.getInt("category_id");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    boolean active = rs.getBoolean("active");
                    tempList.add(new product(ID, catID, "", name, price, active));
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

    public Stack<Integer> getAllActiveMenuIDs() {
        Stack<Integer> tempStack = new Stack<Integer>();
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT DISTINCT menu_id FROM menus WHERE active = true;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    int ID = rs.getInt("menu_id");
                    tempStack.add(ID);
                }
                connection.close();
                return tempStack;
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private boolean isProductContainedInMenu(int menuID, Integer productID) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT menu_id FROM menus_products WHERE menu_id = " + menuID + " AND product_id = "
                    + productID
                    + " AND menu_products_date IN (SELECT MAX(menu_products_date) FROM menus_products WHERE menu_id = "
                    + menuID + ")";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return true;
                }
                return false;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    // ADD "product" to database.
    public int addProduct(int catID, String name, float price) {
        int productID = getLastProductID() + 1;
        return addProduct(productID, catID, name, price, true);
    }

    private int addProduct(int ID, int catID, String name, float price, boolean active) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "INSERT INTO products VALUES (" + ID + ", " + catID + ", CURDATE(), '" + name + "', "
                    + price
                    + ", " + active + ");";
            try (Statement stmt = connection.createStatement()) {
                stmt.executeUpdate(query);
                connection.close();
                return ID;
            } catch (Exception e) {
                System.out.println(e);
                return -1;
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

    public boolean addIngredients(int productID, Stack<Integer> ingredientIDs, Stack<Float> quantities) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            while (!ingredientIDs.isEmpty()) {
                String query = "INSERT INTO products_ingredients VALUES (" + productID + ", " + ingredientIDs.pop()
                        + ", CURDATE(), " + quantities.pop() + ")";
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

    // UPDATE something "product" related in database.
    public boolean updateName(product theProduct, String name) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products SET name = '" + name + "' WHERE product_id = " + theProduct.getId()
                    + " AND active = true";
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

    public boolean updatePrice(product theProduct, float productPrice) {
        fixProductDate(theProduct);
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products SET price = " + productPrice + " WHERE product_id = " + theProduct.getId()
                    + " AND active = true AND product_date = CURDATE();";
            System.out.println(query);
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

    public boolean updateCategory(product theProduct, int categoryID) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products SET category_id = " + categoryID + " WHERE product_id = "
                    + theProduct.getId()
                    + " AND active = true;";
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

    public void updateActive(int productID, boolean active) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products SET active = " + active + " WHERE product_id = " + productID;
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

    private void fixProductDate(product theProduct) {
        if (isLastProductEntryToday(theProduct))
            return;
        setProductIDUnactive(theProduct);
        addProduct(theProduct.getId(), theProduct.getCategoryID(), theProduct.getName(),
                theProduct.getPrice(), true);
    }

    public void setProductIDUnactive(product theProduct) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE products SET active = false WHERE product_id = " + theProduct.getId();
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

    private boolean isLastProductEntryToday(product theProduct) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products WHERE product_id = " + theProduct.getId()
                    + " AND product_date = CURDATE() AND active = true;";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return true;
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
            String dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
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

    public boolean updateIngredients(int productID, Stack<Integer> stackIDs, Stack<Float> stackAmounts) {
        if (areIngredientEntriesToday(productID)) {
            removeProductIngredientsToday(productID);
        }
        return addIngredients(productID, stackIDs, stackAmounts);
    }

    private boolean areIngredientEntriesToday(int productID) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products_ingredients WHERE product_id = 14 AND product_ingredients_date = CURDATE()";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return true;
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
            String query = "DELETE FROM products_ingredients WHERE product_id = " + productID
                    + " AND product_ingredients_date = CURDATE();";
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

    public int getAmountOfProductInMenu(menu theMenu, product theProduct) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT productQuantity FROM menus_products AS a, (SELECT menu_products_date FROM menus_products WHERE menu_id = "
                    + theMenu.getId()
                    + " ORDER BY menu_products_date DESC LIMIT 1) AS b WHERE a.menu_products_date = b.menu_products_date AND menu_id = "
                    + theMenu.getId() + " AND product_id = " + theProduct.getId() + "";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int name = rs.getInt("productQuantity");
                    connection.close();
                    return name;
                } else {
                    connection.close();
                    return -1;
                }
            } catch (Exception e) {
                System.out.println(e);
                return -1;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void deleteMenuWithProduct(int menuID, int productID) {
        if (!isProductContainedInMenu(menuID, productID))
            return;
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "UPDATE menus SET active = false WHERE menu_id = " + menuID;
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

    public void deleteProductsInMenu(int menuID, product theProduct) {
        if (!isProductContainedInMenu(menuID, theProduct.getId()))
            return;
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "DELETE FROM menus_products WHERE menu_id = " + menuID + " AND product_id = "
                    + theProduct.getId()
                    + " AND menu_products_date IN (SELECT * FROM (SELECT MAX(menu_products_date) FROM menus_products WHERE menu_id = "
                    + menuID + ") AS x)";
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

    public boolean isNameTaken(String name) {
        try (Connection connection = DriverManager.getConnection(getURL(), getUser(), getPassword())) {
            String query = "SELECT * FROM products WHERE name = '" + name + "' AND active = true";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    connection.close();
                    return true;
                }
                connection.close();
                return false;
            } catch (Exception e) {
                System.out.println(e);
                return true;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
