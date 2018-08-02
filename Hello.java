package tomlepsky.Dots;

import java.awt.EventQueue;

public class Hello {

	public static void main(String[] args) {
		
		EventQueue.invokeLater( () -> {
			new MainFrame();
		} );

	}

}
