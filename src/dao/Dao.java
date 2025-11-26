package dao;

import java.util.List;
import model.Employee;
import model.Product;

public interface Dao {
    
    public void connect();
    public void disconnect();

    // Login empleado
    public Employee getEmployee(int employeeId, String password);
    
    // Inventario
    public List<Product> getInventory();
    public boolean writeInventory(List<Product> inventory);

    // NUEVOS MÉTODOS
    public boolean addProduct(Product product);   // Insertar nuevo producto
    public boolean updateProduct(Product product); // Actualizar stock o datos
    public boolean deleteProduct(int productId);   // Eliminar producto

}
