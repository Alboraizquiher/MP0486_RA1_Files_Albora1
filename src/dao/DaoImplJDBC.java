package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {

    private Connection connection;

  
    @Override
    public void connect() {
        String url = "jdbc:mysql://127.0.0.1:3306/shop?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Conectado correctamente a MySQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Employee getEmployee(int employeeId, String password) {
        Employee employee = null;
        String query = "SELECT * FROM employee WHERE employeeId = ? AND password = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, employeeId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("employeeId"),
                        rs.getString("name"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

 

    @Override
    public List<Product> getInventory() {

        List<Product> inventory = new ArrayList<>();
        String sql = "SELECT id, name, wholesalerPrice, available, stock FROM inventory";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("wholesalerPrice"),
                        rs.getInt("available") == 1,
                        rs.getInt("stock")
                );
                inventory.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    

    @Override
    public boolean addProduct(Product product) {

        String query = "INSERT INTO inventory (name, wholesalerPrice, available, stock) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getWholesalerPrice().getValue());
            pstmt.setBoolean(3, product.isAvailable());
            pstmt.setInt(4, product.getStock());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al añadir producto");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {

        String query = "UPDATE inventory SET name = ?, wholesalerPrice = ?, available = ?, stock = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getWholesalerPrice().getValue());
            pstmt.setBoolean(3, product.isAvailable());
            pstmt.setInt(4, product.getStock());
            pstmt.setInt(5, product.getId());

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int productId) {

        String query = "DELETE FROM inventory WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar producto");
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public boolean writeInventory(List<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exportInventory(List<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}
}


   



