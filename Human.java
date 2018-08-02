package tomlepsky.Dots;

public class Human extends Player {
	
	public Human( String name, boolean ready ) {
		super( name, ready );
	}
	
	@Override
	public boolean makeMove( int index ) {
		return field.mark( index, sign );
	}
	
}
