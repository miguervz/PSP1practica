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

JScrollPane scrollPane = new JScrollPane(textArea1); 

scrollPane.getVerticalScrollBar().setUnitIncrement(10);


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


//JScrollPane scroll = new JScrollPane(textArea1);
//textArea1.setEditable(true);
//scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


//contentPane.add(scroll);

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
	String text=textField.getText();
	try
	{
	Process process = runtime.exec(text);
	// Flujo de entrada(padre) para la salida estándar (hijo)
	InputStream is = process.getInputStream();
	// Flujo de lectura para esa entrada
	InputStreamReader isr = new InputStreamReader(is);
	
	// Buffer para leer línea a línea
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
		Runtime.getRuntime().exec("notepad.exe");
		listModel.addElement("Notas");
		bNotas.setEnabled(false);
		list.setSelectedValue("Notas",true);
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}
}
});

bPaint.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
	try {
		Runtime.getRuntime().exec("mspaint.exe");
		listModel.addElement("Paint");
		bPaint.setEnabled(false);
		list.setSelectedValue("Paint",true);
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}
}
});



bArticulos.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
	try {
		Process proceso = runtime.exec("java -jar \"C:\\Users\\migue\\Desktop\\VentanaPrincipal.jar");
		

		listModel.addElement("Artículos");
		list.setSelectedValue("Artículos",true);
		bArticulos.setEnabled(false);
		
		bFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = list.getSelectedValue().toString();
				
				list.setSelectedValue(0,true);
				if (selected.equals("Artículos"))
				{
				proceso.destroy();
				bArticulos.setEnabled(true);
				int index = list.getSelectedIndex();
		 		listModel.remove(index);
				}
			}
			});
		
		
	} catch (IOException e1) {
		e1.printStackTrace();
	}
}
});


bBombero.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
	try {
		Process proceso =runtime.exec("java -jar \"C:\\Users\\migue\\Desktop\\MenuPrincipal.jar");
		listModel.addElement("Parque de Bomberos");
		list.setSelectedValue("Parque de Bomberos",true);
		bBombero.setEnabled(false);
		bFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = list.getSelectedValue().toString();
				
				list.setSelectedValue(0,true);
				if (selected.equals("Parque de Bomberos"))
				{
					bBombero.setEnabled(true);
				proceso.destroy();
				bBombero.setEnabled(true);
				int index = list.getSelectedIndex();
		 		listModel.remove(index);
				}
			}
			});
	} catch (IOException e1) {
		e1.printStackTrace();
	}
}
});


bFinalizar.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
	
	String selected = list.getSelectedValue().toString();
	
	list.setSelectedValue(0,true);
	if (selected.equals("Paint")) {
    	 String cmd = "taskkill /IM mspaint.exe";

 		try {
 			Runtime.getRuntime().exec(cmd);





 		
 		} catch (IOException e1) {

 			e1.printStackTrace();
 		}
 		int index = list.getSelectedIndex();
 		listModel.remove(index);
    	 bPaint.setEnabled(true);
    	 }
	
	
	
     if (selected.equals("Notas")) {
    	 String cmd = "taskkill /IM notepad.exe";

 		try {
 			Runtime.getRuntime().exec(cmd);





 		
 		} catch (IOException e1) {

 			e1.printStackTrace();
 		}
 		int index = list.getSelectedIndex();
 		listModel.remove(index);
    	 bNotas.setEnabled(true);
    	 
    	 }
     
     if (selected.equals("Juego")) {
    	 String cmd = "";

 		try {
 			Runtime.getRuntime().exec(cmd);
 		
 		} catch (IOException e1) {

 			e1.printStackTrace();
 		}
 		int index = list.getSelectedIndex();
 		listModel.remove(index);
    	 bBombero.setEnabled(true);
    	 
    	 }
     
     
    
     
     
}
});


}


}
