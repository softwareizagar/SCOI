package Cat_Checador;

import javax.swing.JLabel;

import Obj_Checador.Obj_Reloj_Sincronizado_Servidor;

public class Cat_Reloj_Sincronizado_Servidor{
	
	public JLabel lblHora = new JLabel();
	
	public segundero seg = new segundero();
	
	public Cat_Reloj_Sincronizado_Servidor() {
		
		seg.start();
	}
	int reconsultar;
	public class segundero extends Thread {
		@SuppressWarnings("unused")
		public void run() {
			
			String h;
			String m;
			String s;
			
			int[] hora = new Obj_Reloj_Sincronizado_Servidor().get_hora_minuto_segundo();
			int bandera = 0;
			while(true){
				for(int i=0; i<24; i++){
					for(int j=0; j<60; j++){
						for(int z=0; z<60; z++){
							if(bandera==0){
								i=hora[0];
								j=hora[1];
								z=hora[2]++;
								bandera++;
								
					            h = i > 9 ? "" + i : "0" + i;
					            m = j > 9 ? "" + j : "0" + j;
					            s = z > 9 ? "" + z : "0" + z;
					            
//								System.out.println("Hora entrada: [ "+h+":"+m+":"+s+" ]");
								lblHora.setText(h+":"+m/*+":"+s*/);
								
							}else{
							 	h = i > 9 ? "" + i : "0" + i;
					            m = j > 9 ? "" + j : "0" + j;
					            s = z > 9 ? "" + z : "0" + z;
					            
//								System.out.println("Hora entrada: [ "+h+":"+m+":"+s+" ]");
								lblHora.setText(h+":"+m/*+":"+s*/);
							}
							
							try {
								Thread.sleep(1000);
								reconsultar+=1;
								if(reconsultar==300)
								{
									reconsultar=0;
									run();
								}
							} catch (InterruptedException e) {
								System.err.println("Error: "+ e.getMessage());
							}
						}
					}
				}
			}
		}
	}
	
}
