package tomlepsky.Dots;

public class AI extends Player {

	public AI( String name, boolean ready ) {
		super( name, ready );
	}

	@Override
	public boolean makeMove( int index ) {
		return field.mark( index, sign );
	}

}
