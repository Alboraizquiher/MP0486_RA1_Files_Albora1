package view;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import utils.Constants;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class ShopView extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private Shop shop;

	private JPanel contentPane;
	private JButton btnShowCash;
	private JButton btnAddProduct;
	private JButton btnAddStock;
	private JButton btnRemoveProduct;
	private JButton btnExportInventory; // NUEVO BOTÓN

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopView frame = new ShopView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShopView() {
		setTitle("MiTienda.com - Menú principal");
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		// Crear shop e inventario inicial
		shop = new Shop();
		shop.loadInventory();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblShowCash = new JLabel("Seleccione o pulse una opción:");
		lblShowCash.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblShowCash.setBounds(57, 20, 300, 14);
		contentPane.add(lblShowCash);

		// NUEVA OPCIÓN: Exportar inventario
		btnExportInventory = new JButton("0. Exportar inventario");
		btnExportInventory.setHorizontalAlignment(SwingConstants.LEFT);
		btnExportInventory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnExportInventory.setBounds(99, 40, 236, 40);
		contentPane.add(btnExportInventory);
		btnExportInventory.addActionListener(this);

		// Opción 1: Contar caja
		btnShowCash = new JButton("1. Contar caja");
		btnShowCash.setHorizontalAlignment(SwingConstants.LEFT);
		btnShowCash.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnShowCash.setBounds(99, 90, 236, 40);
		contentPane.add(btnShowCash);
		btnShowCash.addActionListener(this);

		// Opción 2: Añadir producto
		btnAddProduct = new JButton("2. Añadir producto");
		btnAddProduct.setHorizontalAlignment(SwingConstants.LEFT);
		btnAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddProduct.setBounds(99, 140, 236, 40);
		contentPane.add(btnAddProduct);
		btnAddProduct.addActionListener(this);

		// Opción 3: Añadir stock
		btnAddStock = new JButton("3. Añadir stock");
		btnAddStock.setHorizontalAlignment(SwingConstants.LEFT);
		btnAddStock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAddStock.setBounds(99, 190, 236, 40);
		contentPane.add(btnAddStock);
		btnAddStock.addActionListener(this);

		// Opción 9: Eliminar producto
		btnRemoveProduct = new JButton("9. Eliminar producto");
		btnRemoveProduct.setHorizontalAlignment(SwingConstants.LEFT);
		btnRemoveProduct.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRemoveProduct.setBounds(99, 240, 236, 40);
		contentPane.add(btnRemoveProduct);
		btnRemoveProduct.addActionListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();

		if (key == '0') {
			exportInventory();
		}
		if (key == '1') {
			openCashView();
		}
		if (key == '2') {
			openProductView(Constants.OPTION_ADD_PRODUCT);
		}
		if (key == '3') {
			openProductView(Constants.OPTION_ADD_STOCK);
		}
		if (key == '9') {
			openProductView(Constants.OPTION_REMOVE_PRODUCT);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExportInventory) {
			exportInventory();
		}
		if (e.getSource() == btnShowCash) {
			openCashView();
		}
		if (e.getSource() == btnAddProduct) {
			openProductView(Constants.OPTION_ADD_PRODUCT);
		}
		if (e.getSource() == btnAddStock) {
			openProductView(Constants.OPTION_ADD_STOCK);
		}
		if (e.getSource() == btnRemoveProduct) {
			openProductView(Constants.OPTION_REMOVE_PRODUCT);
		}
	}

	/**
	 * Método para exportar inventario (según PDF)
	 */
	private void exportInventory() {
		boolean success = shop.writeInventory();

		if (success) {
			JOptionPane.showMessageDialog(
				this,
				" Inventario exportado correctamente.\nEl fichero se ha guardado en la base de datos Shop",
				"Exportación exitosa",
				JOptionPane.INFORMATION_MESSAGE
			);
		} else {
			JOptionPane.showMessageDialog(
				this,
				" Error al exportar el inventario.\nRevisa permisos o ruta.",
				"Error en exportación",
				JOptionPane.ERROR_MESSAGE
			);
		}
	}

	/**
	 * open dialog to show shop cash
	 */
	public void openCashView() {
		CashView dialog = new CashView(shop);
		dialog.setSize(400, 400);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	/**
	 * open dialog to add/remove/stock product
	 */
	public void openProductView(int option) {
		ProductView dialog = new ProductView(shop, option);
		dialog.setSize(400, 400);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
}

