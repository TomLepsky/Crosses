package tomlepsky.Dots;

/*
 * Singleton
 */
public class Field {
	
	/*
	 * size - ���������� �����/��������;
	 * fieldSize - ����� ���������� �����;
	 * matching - ����������� ���������� ����������� ������ 
	 * 			  ��� �������� ��������
	 * combination - ������ ���������� ��������
	 */
	private static Field instance 	  = null;
	private int   		 countDown;
	private int  		 size;
	private int 	     matching;
	private int     	 currentIndex = 0;
	private int  		 fieldSize;
	private int[] 		 field;
	private int[]		 emptyCells;
	private int[]		 combination;
	
	private Field() { }

	/*
	 * �������� ������ �� ������� ����
	 */
	public static Field getInstance() {
		if( instance == null )
			instance = new Field();

		return instance;

	}

	/*
	 * �������������� ������� ����: 0 - ������ ������; 1 - ��� ������1; 2 - ���
	 * ������2
	 */
	public void crateField(int size, int matching) {
		this.size     = size;
		this.matching = matching > size ? size : matching;
		combination	  = new int[matching];
		fieldSize     = size * size;
		field		  = new int[fieldSize];
		countDown 	  = fieldSize;
		emptyCells	  = new int[fieldSize];
		
		for( int i = 0; i < fieldSize; i++ ) {
			field[i] 	  = 0;
			emptyCells[i] = i;
		}

	}
	
	/*
	 * �������� ��� ������
	 */
	public boolean mark( int index, int sign ) {
		currentIndex = index;
		if( field[currentIndex] == 0 ) {
			field[currentIndex] = sign;
			countDown--;
			updateEmptyCells();
			return true;
		}
			
		return false;
		
	}
	
	/*
	 * �������� ����
	 */
	public void clearField() {
		for( int i = 0; i < fieldSize; i++ ) 
			field[i] = 0;
	}
	
	
	/*
	 * �������� �������: ���� �� ��������� ������
	 */
	public boolean isFullFilled() {
		return countDown > 0 ? false : true;
	}
	
	/*
	 * ���������� ����������
	 */
	public int[] getWinnerCombination() {
		return combination;
	}
	
	public int getMatching() {
		return matching;
	}
	
	public int getCountDown() {
		return countDown;
	}
	
	public int[] getEmptyCells() {
		return emptyCells;
	}
	
	/*
	 * �������� ���������, ����������� � ����������,
	 * ���������� ��������� ��������� ������
	 */
	public boolean checkLines( int sign ) {
		return checkHorizontal( sign )   ||
			   checkVertical( sign )     ||
			   checkMainDiagonal( sign ) ||
			   checkSubDiagonal( sign );
	}
	
	/*
	 * ��������������� ������ ��� �������� �����������, ���������, ����������
	 */
	private boolean checkHorizontal( int sign) {
		int rowMin = currentIndex - ( currentIndex % size );
		int rowMax = rowMin + size - 1;

		int kMin   = currentIndex - matching + 1;
		int kMax   = currentIndex + matching - 1;

		if( kMin < rowMin ) kMin = rowMin;
		if( kMax > rowMax ) kMax = rowMax;
		
		return comparingIterator( kMin, kMax - matching + 1, 1, sign );

	}

	private boolean checkVertical( int sign ) {
		int colMin = currentIndex % size;
		int colMax = colMin + size * ( size - 1 );
		
		int kMin   = currentIndex - ( matching - 1 ) * size;
		int kMax   = currentIndex + ( matching - 1 ) * size;
		
		if( kMin < colMin ) kMin = colMin;
		if( kMax > colMax ) kMax = colMax;
		
		return comparingIterator(kMin, kMax - ( matching - 1 ) * size, size, sign );
		
	}
	
	private boolean checkMainDiagonal( int sign ) {
		int diagMin = currentIndex - Math.min( currentIndex / size, currentIndex % size ) * ( size + 1 );
		int diagMax;
		if( ( diagMin / size ) != 0 ) 
			diagMax = ( fieldSize - 1 ) - diagMin / size;
		else
			diagMax = ( fieldSize - 1 ) - ( diagMin % size ) * size;
		
		int kMin    = currentIndex - ( matching - 1 ) * ( size + 1 );
		int kMax    = currentIndex + ( matching - 1 ) * ( size + 1 );
		
		if( kMin < diagMin ) kMin = diagMin;
		if( kMax > diagMax ) kMax = diagMax;
		
		return comparingIterator( kMin, kMax - ( matching - 1 ) * ( size + 1 ), size + 1, sign );
				
	}
	
	private boolean checkSubDiagonal( int sign ) {
		int diagMin = currentIndex - Math.min( currentIndex / size, size - ( currentIndex % size ) - 1 ) * ( size - 1 );
		int diagMax;
		if( ( diagMin / size ) != 0 )
			diagMax = ( fieldSize - size ) + diagMin / size;
		else
			diagMax = size * ( diagMin % size );
		
		int kMin    = currentIndex - ( matching - 1 ) * ( size - 1 );
		int kMax    = currentIndex + ( matching - 1 ) * ( size - 1 );
		
		if( kMin < diagMin ) kMin = diagMin;
		if( kMax > diagMax ) kMax = diagMax;
		
		
		return comparingIterator( kMin, kMax - ( matching - 1 ) * ( size - 1 ), size - 1 , sign );
		
	}
	
	private boolean comparingIterator(int start, int end, int step, int sign) {
		for( int i = start; i <= end; ) {
			
			for( int j = i, count = 0; count < matching; j += step ) {
				if( field[j] == sign ) 
					combination[count++] = j;
				else {
					i = j + step;
					break;
				}
				
				if( count == matching ) return true;
				
			}

		}

		return false;

	}
	
	private void updateEmptyCells() {
		int temp = searchIndex();
		if( temp != -1 ) {
			emptyCells[temp]	  = emptyCells[temp] - emptyCells[countDown];
			emptyCells[countDown] = emptyCells[temp] + emptyCells[countDown];
			emptyCells[temp] 	  = emptyCells[countDown] - emptyCells[temp];
		}
		
	}
	
	private int searchIndex() {
		for( int i = 0; i < countDown; i++ ) 
			if( emptyCells[i] == currentIndex ) return i;

		return -1;
	}

}
