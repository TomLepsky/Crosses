package tomlepsky.Dots;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class GameMenu extends JPanel {

	private static final long serialVersionUID = 5766651167919366432L;
	
	private GameOptions gameOptions = null;
	private GameField   gameField 	= null;
	
	public GameMenu() {
		setLayout( new BorderLayout() );
		setBackground( new Color( 204, 255, 153 ) );		
		
		JPanel buttonPanel = new JPanel();
		
		gameOptions = new GameOptions( buttonPanel );
		gameField   = new GameField( buttonPanel );
		add( gameField ).setVisible( false );
		add( gameOptions ).setVisible( false );
		
		ActionListener startAction  = ( ActionEvent e ) -> {
			buttonPanel.setVisible( false );
			gameField.setVisible( true );
		};
		
		ActionListener optionAction = ( ActionEvent e ) -> {
			
		};
		
		ActionListener exitAction   = ( ActionEvent e ) -> {
			System.exit( 0 );
		};
		
		
		buttonPanel.setBackground( new Color( 204, 255, 153 ) );
		buttonPanel.setLayout( new GridLayout( 3, 1, 20, 20 ) );
		
		buttonPanel.add( new GameMenuButton( "Начать игру", startAction ) );
		buttonPanel.add( new GameMenuButton( "Настройки", optionAction ) );
		buttonPanel.add( new GameMenuButton( "Выход", exitAction ) );
		
		add( buttonPanel );
		setSize(500, 500);
		
		
	}
	
	private class GameMenuButton extends JButton {

		private static final long serialVersionUID = -7480002790019907948L;
		
		public GameMenuButton( String name, ActionListener listener ) {
			Border raisedEtched = BorderFactory.createEtchedBorder( EtchedBorder.RAISED );
			setBorder( raisedEtched );
			setBackground( new Color( 0, 255, 153 ) );
			setFont( new Font( "Roboto", Font.PLAIN, 18 ) );
			setForeground( Color.BLACK );
			addActionListener( listener );
			setText( name );
			setSize( 200, 100 );
			
		}
		
	}
	

}
