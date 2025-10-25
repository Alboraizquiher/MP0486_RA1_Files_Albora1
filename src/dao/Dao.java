package dao;

import model.Employee;

public interface Dao {
	
	public void connect();

	public void disconnect();
	
	public void name();

	public Employee getEmployee(int employeeId, String password);
	
}
