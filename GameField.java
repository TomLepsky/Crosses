package tomlepsky.Dots;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class GameField extends JPanel {

	private static final long serialVersionUID = 2850438607190803194L;
	
	private JPanel gameMenu 		= null;
	private Field field = null;
	private JToggleButton[] cells;
	private ActionListener listener;
	private GameMode gameMode = new HumanVSAI();
	private int n;
	
	public GameField( JPanel gameMenu ) {
		n = 15;
		this.gameMenu = gameMenu;
		field = Field.getInstance();
		field.crateField( n, 5 );
		cells = new JToggleButton[n*n];
		listener = getListener();
		
		setBackground( new Color( 204, 255, 153 ) );
		setLayout( new BorderLayout() );
		
		JButton backButton= new JButton( "назад" );
		backButton.addActionListener( ( ActionEvent e ) -> {
			//setVisible( false );
			//gameMenu.setVisible( true );
		});
		add( backButton, BorderLayout.SOUTH );
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout( new GridLayout( n, n, 2, 2 ) );		
		
		for( int i = 0; i < n * n; i++ ) {
			cells[i] = new GameFieldButton( i );
			fieldPanel.add( cells[i] );
		}
		
		add( fieldPanel, BorderLayout.CENTER );		
		
	}
	
	public void setDisable() {
		for( int i = 0; i < n * n; i++ ) 
			cells[i].setEnabled( false );
	}
	
	public void setWinnerCombination() {
		int[] combination = field.getWinnerCombination();
		for( int i = 0; i < combination.length; i++ ) 
			cells[combination[i]].setBackground( Color.RED );
		
	}
	
	public ActionListener getListener() {
		if( gameMode instanceof HumanVSHuman )
			return new HumanVsHumanListener();
		else if( gameMode instanceof HumanVSAI )
			return new HumanVsAIListener();
		
		return null;
	}
	
	
	private class GameFieldButton extends JToggleButton {

		private static final long serialVersionUID = 2241944363855436598L;
		private int index;
		
		public GameFieldButton( int index ) {
			this.index = index;
			Border raisedEtched = BorderFactory.createEtchedBorder( EtchedBorder.RAISED );
			setBorder( raisedEtched );
			setBackground( new Color( 204, 102, 204 ) );
			setFont( new Font( "Roboto", Font.PLAIN, 16 ) );
			setForeground( Color.BLACK );
			addActionListener( listener );
			
		}
		
		public int getIndex() {
			return index;
		}
		
	}// *** class GameFieldButton ***
	
	
	private class HumanVsHumanListener implements ActionListener {
		
		private Player player;
		long t;
		
		@Override
		public void actionPerformed( ActionEvent event ) {
			t = System.nanoTime();
			player = gameMode.whoseTurn();
			GameFieldButton button = ( GameFieldButton )event.getSource();
			
			if( player.makeMove( button.getIndex() ) ){
				gameMode.getFirstPlayer().changeState();
				gameMode.getSecondPlayer().changeState();
				
				button.setText( String.valueOf( player.getSign() ) );
				button.setEnabled( false );
			}

			System.out.println( button.getAction() );
			if( player.isWin() ) {
				System.out.println( player.getName() + " is win!!!" );
				setDisable();
				setWinnerCombination();				
			}
			
			if( field.isFullFilled() ) {
				System.out.println( "Draw!" );
				setDisable();
			}
			System.out.println( System.nanoTime() - t );			
		}


	} // *** class GameFieldListener ***
	
	
	private class HumanVsAIListener implements ActionListener {
		
		private Player human = gameMode.getFirstPlayer();
		private Player AI = gameMode.getSecondPlayer();

		long t;
		Random generator = new Random();
		int randomIndex;
		int[] emptyCells = field.getEmptyCells();
		
		public void actionPerformed( ActionEvent event ) {
			t = System.nanoTime();
			//GameFieldButton - класс унаследован от JToggleButton
			GameFieldButton button = ( GameFieldButton )event.getSource();
			//makeMove - сделать ход, передаём туда значение индекса кнопки
			human.makeMove( button.getIndex() );
			
			button.setText( String.valueOf( human.getSign() ) );
			button.setEnabled( false );
			
			if( human.isWin() ) {
				System.out.println( human.getName() + " is win!!!" );
				setDisable();
				setWinnerCombination();				
			}

			randomIndex = emptyCells[generator.nextInt( field.getCountDown() )];
			AI.makeMove( randomIndex );
			cells[randomIndex].setSelected( true );
			cells[randomIndex].setText( String.valueOf( AI.getSign() ) );
			cells[randomIndex].setEnabled( false );
			
			if( AI.isWin() ) {
				System.out.println( AI.getName() + " is win!!!" );
				setDisable();
				setWinnerCombination();				
			}
				
			if( field.isFullFilled() ) {
				System.out.println( "Draw!" );
				setDisable();
			}
			
			System.out.println( System.nanoTime() - t );
			
		}
		
	} // *** class HumanVsAIListener ***

	
}
