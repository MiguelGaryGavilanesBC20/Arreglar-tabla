package Menu_P;

import javax.swing.JFrame;
import java.awt.Font;

public class Principal {
    
	public static void main(String[] args) {

    CambiarFuente.cambiarFuenteGlobal(new Font("Franklin Gothic Light", Font.PLAIN, 16));

        MenuFrame mimarco = new MenuFrame();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mimarco.setVisible(true);
        mimarco.setResizable(false);
		mimarco.setLocationRelativeTo(null);

    }
}