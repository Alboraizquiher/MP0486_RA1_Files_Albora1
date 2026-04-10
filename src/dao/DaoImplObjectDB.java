package dao;

import java.util.ArrayList;

import javax.persistence.*;

import model.Employee;
import model.Product;

public class DaoImplObjectDB implements Dao
{
	private EntityManagerFactory emf;
	private EntityManager em;

	@Override
	public void connect()
	{
		emf = Persistence.createEntityManagerFactory("objects/users.odb");
		em = emf.createEntityManager();
	}

	@Override
	public Employee getEmployee(int employeeId, String password)
	{
		Employee employee = null;

		try
		{
			TypedQuery<Employee> query = em.createQuery(
					"SELECT e FROM Employee e WHERE e.employeeId = :id AND e.password = :pass",
					Employee.class);

			query.setParameter("id", employeeId);
			query.setParameter("pass", password);

			ArrayList<Employee> result = new ArrayList<>(query.getResultList());

			if (!result.isEmpty())
			{
				employee = result.get(0);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return employee;
	}

	@Override
	public void disconnect()
	{
		if (em != null)
			em.close();

		if (emf != null)
			emf.close();
	}


	
	
	
	//All the methods below are created but not implemented because is not needed


	@Override
	public ArrayList<Product> getInventory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventario)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addProduct(Product product)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduct(Product product)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteProduct(int productId)
	{
		// TODO Auto-generated method stub

	}
}