package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import model.Employee;
import model.Product;

public class DaoImplObjectDB implements Dao {

    // ✅ UNA SOLA VEZ EN TODA LA APLICACIÓN
    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("objectdb:objects/users.odb");

    private EntityManager em;

    @Override
    public void connect() {
        // ✅ SOLO abrimos EntityManager
        em = emf.createEntityManager();
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        Employee employee = null;

        try {
            TypedQuery<Employee> query = em.createQuery(
                    "SELECT e FROM Employee e WHERE e.employeeId = :id AND e.password = :pass",
                    Employee.class);

            query.setParameter("id", employeeId);
            query.setParameter("pass", password);

            List<Employee> result = query.getResultList();

            if (!result.isEmpty()) {
                employee = result.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public void disconnect() {
        // ✅ SOLO cerramos el EntityManager
        if (em != null) {
            em.close();
        }
    }

    // ================= NO IMPLEMENTADOS =================

    @Override
    public ArrayList<Product> getInventory() {
        return new ArrayList<>();
    }

    @Override
    public void addProduct(Product product) {}

    @Override
    public void updateProduct(Product product) {}

    @Override
    public void deleteProduct(int productId) {}

    @Override
    public boolean writeInventory(List<Product> inventory) {
        return false;
    }

    @Override
    public boolean exportInventory(List<Product> inventory) {
        return false;
    }
}