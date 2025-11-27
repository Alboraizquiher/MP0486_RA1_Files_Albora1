package dao;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.Amount;
import model.Employee;

public class DaoImplFile implements Dao {

	@Override
	public void connect() {
		// No necesario para fichero
	}

	@Override
	public void disconnect() {
		// No necesario para fichero
	}

	@Override
	public Employee getEmployee(int employeeId, String password) {
		// No implementado para ficheros
		return null;
	}

	@Override
	public List<Product> getInventory() {
	    List<Product> products = new ArrayList<>();
	    
	    String url = "jdbc:mysql://localhost:3306/shop";
	    String user = "root";
	    String pass = "";

	    String sql = "SELECT name, wholesalerPrice, available, stock FROM inventory";

	    try (Connection conn = DriverManager.getConnection(url, user, pass);
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            String name = rs.getString("name");
	            double wholesalerPrice = rs.getDouble("wholesalerPrice"); // <-- corregido
	            boolean available = rs.getBoolean("available");
	            int stock = rs.getInt("stock");

	            Product p = new Product(name, new Amount(wholesalerPrice), available, stock);
	            products.add(p);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return products;
	}
		
		/*File f = new File(System.getProperty("user.dir") + File.separator + "files" + File.separator + "inputInventory.txt");

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] sections = line.split(";");
				String name = "";
				double wholesalerPrice = 0.0;
				int stock = 0;

				for (String section : sections) {
					String[] data = section.split(":");
					if (data.length == 2) {
						switch (data[0].trim()) {
							case "Product":
								name = data[1].trim();
								break;
							case "Wholesaler Price":
								wholesalerPrice = Double.parseDouble(data[1].trim());
								break;
							case "Stock":
								stock = Integer.parseInt(data[1].trim());
								break;
						}
					}
				}

				products.add(new Product(name, new Amount(wholesalerPrice), true, stock));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return products;
	}*/


	@Override
	public boolean writeInventory(List<Product> inventory) {
	    LocalDate date = LocalDate.now();
	    String fileName = "inventory_" + date + ".txt";

	    // Ruta absoluta a la carpeta files (ya existente)
	    String folderPath = System.getProperty("user.dir") + File.separator + "files";
	    File folder = new File(folderPath);

	 

	    // Archivo dentro de la carpeta
	    File f = new File(folder, fileName);

	    try {
	        // Si el archivo no existe, se crea uno nuevo
	        if (!f.exists()) {
	            if (f.createNewFile()) {
	                System.out.println(" Archivo creado: " + f.getAbsolutePath());
	            } else {
	                System.err.println(" No se pudo crear el archivo: " + f.getAbsolutePath());
	                return false;
	            }
	        } else {
	            System.out.println(" El archivo ya existe. Se actualizará su contenido.");
	        }

	        // Escribir o sobrescribir el contenido del inventario
	        try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {
	            int count = 1;
	            for (Product p : inventory) {
	                pw.println(count + ";Product:" + p.getName() + ";Stock:" + p.getStock() + ";");
	                count++;
	            }
	            pw.println("Total number of products:" + inventory.size() + ";");
	            System.out.println(" Archivo guardado correctamente en: " + f.getAbsolutePath());
	        }

	        return true;

	    } catch (IOException e) {
	        e.printStackTrace();
	        System.err.println(" Error al guardar archivo en: " + f.getAbsolutePath());
	        return false;
	    }
	}

	@Override
	public boolean addProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProduct(int productId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exportInventory(List<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}




}
