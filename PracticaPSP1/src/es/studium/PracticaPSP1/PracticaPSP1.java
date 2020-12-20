package es.studium.PracticaPSP1;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
public class PracticaPSP1 extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PracticaPSP1 frame = new PracticaPSP1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public PracticaPSP1()
	{
		this.setTitle("Práctica PSP 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(430, 150, 900, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextArea textArea1 = new JTextArea();
		textArea1.setEditable(true);
		textArea1.setBounds(46, 90, 300, 450);
		JLabel texto = new JLabel("Procesos activos");
		texto.setBounds(400, 270, 140, 30);

		JTextField textField = new JTextField();
		textField.setBounds(46, 60, 180, 23);

		DefaultListModel<String> listModel;
		listModel = new DefaultListModel<String>();
		JList<String> list = new JList<String>(listModel);
		list.setBounds(400, 300, 340, 200);

		JButton bEjecutar = new JButton("Ejecutar");
		JButton bFinalizar = new JButton("Finalizar");
		JButton bBombero = new JButton("Parque De Bomberos");
		JButton bArticulos = new JButton("Artículos y tickets");
		JButton bPaint = new JButton("Paint");
		JButton bNotas = new JButton("Notas");
		bNotas.setBounds(590, 20, 200, 40);
		bPaint.setBounds(590, 80, 200, 40);
		bArticulos.setBounds(590, 140, 200, 40);
		bBombero.setBounds(590, 200, 200, 40);
		bEjecutar.setBounds(246, 60, 100, 23);
		bFinalizar.setBounds(745, 300, 100, 60);
		
		contentPane.add(bNotas);
		contentPane.add(bPaint);
		contentPane.add(bBombero);
		contentPane.add(bArticulos);
		contentPane.add(bFinalizar);
		contentPane.add(bEjecutar);
		contentPane.add(textField);
		contentPane.add(texto);
		contentPane.add(list);
		contentPane.add(textArea1);
		Runtime runtime = Runtime.getRuntime();
		bEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea1.setText("");
				String text=textField.getText();
				try
				{
					Process process = runtime.exec("cmd /c "+text);
					InputStream is = process.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;
					while ((line = br.readLine()) != null)
					{
						textArea1.append(line+"\n");
					}
					is.close();
				}
				catch (IOException e1)
				{
					System.err.println("Introduzca algún comando");
					System.exit(-1);
				}
			}
		});

		bNotas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Process proceso = Runtime.getRuntime().exec("notepad.exe");
					long pid = proceso.pid();      
					listModel.addElement("Notas "+ pid);
					bNotas.setEnabled(false);
					list.setSelectedValue("Notas "+ pid,true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		bPaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Process proceso =Runtime.getRuntime().exec("mspaint.exe");
					long pid = proceso.pid();      
					listModel.addElement("Paint "+ pid);
					bPaint.setEnabled(false);
					list.setSelectedValue("Paint "+ pid,true);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});

		bArticulos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Process proceso =runtime.exec("java -jar \"C:\\Users\\migue\\Desktop\\VentanaPrincipal.jar");
					long pid = proceso.pid();      
					listModel.addElement("Articulos "+ pid);
					list.setSelectedValue("Articulos "+ pid,true);
					bArticulos.setEnabled(false);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		bBombero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Process proceso =runtime.exec("java -jar \"C:\\Users\\migue\\Desktop\\MenuPrincipal.jar");
					long pid = proceso.pid();      
					listModel.addElement("Bomberos "+ pid);
					list.setSelectedValue("Bomberos "+ pid,true);
				
					bBombero.setEnabled(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		bFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String selected = list.getSelectedValue().toString();
					int n = selected.lastIndexOf(" ");
					String pid;
					pid = selected.substring(n,selected.length());
					String nombreProceso = selected.substring(0,n);
					list.setSelectedValue(0,true);
					
					if (nombreProceso.equals("Notas")) {
						try {
							runtime.exec("taskkill /F /PID "+pid);

						} catch (IOException e1) {

							e1.printStackTrace();
						}
						int index = list.getSelectedIndex();
						listModel.remove(index);
						bNotas.setEnabled(true);
					}
					if (nombreProceso.equals("Paint")) {
						try {
							runtime.exec("taskkill /F /PID "+pid);
						} catch (IOException e1) {

							e1.printStackTrace();
						}
						int index = list.getSelectedIndex();
						listModel.remove(index);
						bPaint.setEnabled(true);
					}
					if (nombreProceso.equals("Articulos")) {
						try {
							runtime.exec("taskkill /F /PID "+pid);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						int index = list.getSelectedIndex();
						listModel.remove(index);
						bArticulos.setEnabled(true);
					}
					if (nombreProceso.equals("Bomberos")) {
						try {
							runtime.exec("taskkill /F /PID "+pid);
						} catch (IOException e1) {

							e1.printStackTrace();
						}
						int index = list.getSelectedIndex();
						listModel.remove(index);
						bBombero.setEnabled(true);
					}
				}
				catch(java.lang.NullPointerException r1) {
					System.out.println("Seleccione algo.");
				}
			}
		});
	}
}
