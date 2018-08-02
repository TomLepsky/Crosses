package tomlepsky.Dots;

public class HumanVSAI extends GameMode {
	
	public HumanVSAI() {
		super( new Human( "Human", true ), new AI( "AI", false ) );
	}
	
}
