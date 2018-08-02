package tomlepsky.Dots;

public class HumanVSHuman extends GameMode {
	
	public HumanVSHuman() {
		super( new Human( "Player 1", true ), new Human( "Player 2", false ) );
	}

}
