package tomlepsky.Dots;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = -2782880761789006212L;
	
	private Toolkit kit;
	private int width;
	private int height;
	
	public MainFrame() {
		kit    = Toolkit.getDefaultToolkit();
		width  = kit.getScreenSize().width / 2;
		height = kit.getScreenSize().height / 2;
		setBounds( width / 2, height / 2, height, height );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setTitle( "Dots" );
		
		add( new GameField( new JPanel() ));
		
		setVisible( true );
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension( height, height ); 
	}

}
