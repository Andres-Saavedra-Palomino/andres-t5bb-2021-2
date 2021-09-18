package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Categoria;
import model.Producto;
import model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;

public class FrmManteProd extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextArea txtSalida;
	private JTextField txtCódigo;
	JComboBox<String> cboCategorias;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private ArrayList<Categoria> categorias = new ArrayList<Categoria>();
	private JTable table;
	private DefaultTableModel model;
	private String[] columnas = { "Id", "Descripcion", "Stock", "Precio", "Categoria", "Estado" };

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
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
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnNewButton.setBounds(324, 29, 89, 23);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 414, 143);
		contentPane.add(scrollPane);

		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);

		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(177, 322, 89, 23);
		contentPane.add(btnListado);

		txtCódigo = new JTextField();
		txtCódigo.setBounds(122, 11, 86, 20);
		contentPane.add(txtCódigo);
		txtCódigo.setColumns(10);

		JLabel lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);

		cboCategorias = new JComboBox<String>();
		cboCategorias.setBounds(122, 70, 144, 22);
		contentPane.add(cboCategorias);

		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);

		JLabel lblNomProducto = new JLabel("Nom. Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 144, 20);
		contentPane.add(txtDescripcion);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 106, 102, 14);
		contentPane.add(lblStock);

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 103, 77, 20);
		contentPane.add(txtStock);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 134, 102, 14);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(122, 131, 77, 20);
		contentPane.add(txtPrecio);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 359, 414, 143);
		contentPane.add(scrollPane_1);

		table = new JTable();
		table.setShowVerticalLines(false);
		scrollPane_1.setViewportView(table);
		table.setDefaultEditor(Object.class, null);

		llenaCombo();
	}

	void llenaCombo() {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();

		String sql = "select c from Categoria c";
		TypedQuery<Categoria> query = em.createQuery(sql, Categoria.class);

		try {
			categorias = (ArrayList<Categoria>) query.getResultList();
			cboCategorias.addItem("Seleccione una...");
			for (Categoria c : categorias)
				cboCategorias.addItem(c.getDescripcion());
		} catch (NoResultException ex) {
			System.out.println("cboCategorias no cargo data");
		} finally {
			em.close();
		}
	}

	void listado() {

		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();

		String sql = "select p from Producto p";
		TypedQuery<Producto> query = em.createQuery(sql, Producto.class);

		ArrayList<Producto> productos = new ArrayList<Producto>();
		try {
			productos = (ArrayList<Producto>) query.getResultList();
			txtSalida.setText("");

			for(Producto p:productos)
			txtSalida.append(p.toString()+"\n");

			model = new DefaultTableModel();
			for (int i = 0; i < columnas.length; i++)
				model.addColumn(columnas[i]);
			for (Producto p : productos)
				model.addRow(new Object[] { p.getIdprod(),p.getDescripcion(),p.getStock(),"S/. "+precio(p.getPrecio()),categoria(p.getIdcategoria()),p.getEstado() });
			table.setModel(model);
			DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
			rightRenderer.setHorizontalAlignment( JLabel.RIGHT);
			table.getColumnModel().getColumn(3).setCellRenderer( rightRenderer );
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
		} catch (NoResultException ex) {
			System.out.println("txtResultado no cargo data");
		} finally {
			em.close();
		}
	}

	void registrar() {
		if (cboCategorias.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(this, "Seleccione una categoria para el producto.");
			return;
		}
		Producto p = null;
		try {
			p = new Producto(txtCódigo.getText(), txtDescripcion.getText(), Integer.parseInt(txtStock.getText()),
					Double.parseDouble(txtPrecio.getText()), cboCategorias.getSelectedIndex(), 1);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Complete los campos correctamente.");
			return;
		}
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("mysql");
		EntityManager em = fabrica.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(p);
			em.getTransaction().commit();
//			String sql = "insert into Producto p (p.descripcion, p.estado, p.idcategoria, p.precio, p.stock, p.idprod) values (?1, ?2, ?3, ?4, ?5, ?6)";
//			Query query = em.createNativeQuery(sql, Producto.class);
//			query.setParameter(1, txtDescripcion.getText());
//			query.setParameter(2, 1);
//			query.setParameter(3, cboCategorias.getSelectedIndex());
//			query.setParameter(4, Double.parseDouble(txtPrecio.getText()));
//			query.setParameter(5, Integer.parseInt(txtStock.getText()));
//			query.setParameter(6, txtCódigo.getText());
//			int ok = 0;
//			ok = query.executeUpdate();
//			System.out.println("OK = "+ok);
			JOptionPane.showMessageDialog(this, "Producto Registrado");
			limpiar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "No se logro registrar");
		} finally {
			em.close();
		}
	}
	String categoria(int idcat) {
		return categorias.get(idcat-1).getDescripcion();
	}
	String precio(double precio) {
		return String.format("%,.2f", precio);
	}
	void limpiar() {
		txtCódigo.setText("");
		txtDescripcion.setText("");
		txtStock.setText("");
		txtPrecio.setText("");
		cboCategorias.setSelectedItem(0);
	}
}