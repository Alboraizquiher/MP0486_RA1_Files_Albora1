package dao;

import java.util.List;
import model.Employee;
import model.Product;

public interface Dao {

    // Conexión a BD
    void connect();
    void disconnect();

    // Login
    Employee getEmployee(int employeeId, String password);

    // Inventario
    List<Product> getInventory();
    boolean writeInventory(List<Product> inventory);
    boolean exportInventory(List<Product> inventory);

    // CRUD de productos
    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
}

