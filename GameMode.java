package tomlepsky.Dots;

public abstract class GameMode {
	
	private Player firstPlayer;
	private Player secondPlayer;
	
	public GameMode( Player p1, Player p2 ) {
		firstPlayer  = p1;
		secondPlayer = p2;
	}
	
	public Player whoseTurn() {
		return firstPlayer.getReady() ? firstPlayer : secondPlayer;
	}
	
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	
	public Player getSecondPlayer() {
		return secondPlayer;
	}
	
	
}
