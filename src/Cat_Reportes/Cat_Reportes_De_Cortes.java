package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Cortes extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Corte", 10, "String");
	
	JTextField txtNombreEmpleado = new Componentes().text(new JTextField(), "Nombre del Empleado",100, "String");
	JTextField txtEstablecimiento = new Componentes().text(new JTextField(), "Establecimiento",100, "String");
	JTextField txtDepartamento = new Componentes().text(new JTextField(), "Departamento",100, "String");
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	public Cat_Reportes_De_Cortes(){

		
		panel.setBorder(BorderFactory.createTitledBorder("Reporte De Cortes"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/archivo-icono-8809-32.png"));
		this.setTitle("Reportes de Cortes");
		
		panel.add(txtFolio).setBounds(60,25,100,20);
//		txtFolio.setEnabled(false);
//		txtFolio.setText();
				
//		panel.add(new JLabel("Nombre:")).setBounds(15,25,50,20);
//		panel.add(txtNombreEmpleado).setBounds(100,25,280,20);
//		txtNombreEmpleado.setEnabled(false);
//        txtNombreEmpleado.setText(Nombre);
        
//		panel.add(new JLabel("Establecimiento:")).setBounds(15,55,80,20);
//		panel.add(txtEstablecimiento).setBounds(100,55,280,20);
//		txtEstablecimiento.setEnabled(false);
//        txtEstablecimiento.setText(Establecimiento);
//        
//		panel.add(new JLabel("Departamento:")).setBounds(15,85,80,20);
//		panel.add(txtDepartamento).setBounds(100,85,280,20);
//		txtDepartamento.setEnabled(false);
//        txtDepartamento.setText(Departamento);        
//        
//        
//		panel.add(new JLabel("Fecha Inicio:")).setBounds(15,115,100,20);
//		panel.add(c_inicio).setBounds(80,115,100,20);
//		panel.add(new JLabel("Fecha Final:")).setBounds(220,115,100,20);
//		panel.add(c_final).setBounds(280,115,100,20);		

		
		panel.add(btngenerar).setBounds(150, 150, 100, 20);
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		
		this.setSize(400, 210);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			 new Cat_Reporte_De_Corte_De_Caja(txtFolio.getText()+"");
			 
//		if(validar_fechas().equals("")){
//			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
//			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
//			String Establecimiento=txtEstablecimiento.getText()+"";
//			String Departamento=txtDepartamento.getText()+"";
//			String folio_empleado=txtFolio.getText()+"";
//			
//			if(c_inicio.getDate().before(c_final.getDate())){
//				new Cat_Reporte_General_de_Asistencia_Por_Establecimiento(fecha_inicio,fecha_final,Establecimiento,Departamento,folio_empleado);
//				
//			}else{
//				JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
//				return;
//			}
//	 	}else{
//			JOptionPane.showMessageDialog(null,"Los siguientes campos est�n vac�os: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
		}
	};
	
//	public String validar_fechas(){
//		String error = "";
//		String fechainicioNull = c_inicio.getDate()+"";
//		String fechafinalNull = c_final.getDate()+"";
//	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
//		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
//		return error;
//	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}
}
