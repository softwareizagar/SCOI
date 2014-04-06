package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;

@SuppressWarnings("serial")
public class Cat_Autorizacion_Finanzas extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_Autorizacion_Finanzas autori = new Obj_Autorizacion_Finanzas().buscar();
	boolean autorizar = autori.isAutorizar();
	
	JLabel lblAutorizar = new JLabel(new ImageIcon("imagen/Aplicar.png"));
	
	public Cat_Autorizacion_Finanzas(){
		this.setTitle("Autorizaci�n Lista Raya");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Aplicar.png"));
		panel.setBorder(BorderFactory.createTitledBorder("-"));
		
		panel.add(new JLabel("Autorizar Lista Raya")).setBounds(15,30,120,25);
		panel.add(lblAutorizar).setBounds(160,30,20,20);
		
		lblAutorizar.setEnabled(autorizar);
		lblAutorizar.addMouseListener(opAutorizar);
		cont.add(panel);
		
		this.setSize(240,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	MouseListener opAutorizar = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {
		}
		public void mousePressed(MouseEvent arg0) {
			
			if(autorizar != true){
				if(JOptionPane.showConfirmDialog(null, "�Desea autorizar la lista de raya?") == 0){
					Obj_Autorizacion_Finanzas prue = new Obj_Autorizacion_Finanzas();
					autorizar = true;
					lblAutorizar.setEnabled(true);
					prue.setAutorizar(autorizar);
					prue.actualizar();
				}else{
					return;
				}
			}else{
				if(JOptionPane.showConfirmDialog(null, "�Desea no autorizar la lista de raya?") == 0){
					Obj_Autorizacion_Finanzas prue = new Obj_Autorizacion_Finanzas();
					autorizar = false;
					lblAutorizar.setEnabled(false);
					prue.setAutorizar(autorizar);
					prue.actualizar();
				}else{
					return;
				}
				
			}
				
		}
		public void mouseExited(MouseEvent arg0) {
		}
		public void mouseEntered(MouseEvent arg0) {
		}
		public void mouseClicked(MouseEvent arg0) {
		}
	};

}
