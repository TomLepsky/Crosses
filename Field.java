package tomlepsky.Dots;

/*
 * Singleton
 */
public class Field {
	
	/*
	 * size - количество строк/столбцов;
	 * fieldSize - общее количество ячеек;
	 * matching - необходимое количество заполненных подряд 
	 * 			  для выигрыша клеточек
	 * combination - номера выигрышных клеточек
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
	 * Получаем ссылку на игровое поле
	 */
	public static Field getInstance() {
		if( instance == null )
			instance = new Field();

		return instance;

	}

	/*
	 * Инициализируем игровое поле: 0 - пустая клетка; 1 - ход игрока1; 2 - ход
	 * игрока2
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
	 * Отмечаем ход игрока
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
	 * Очистить поле
	 */
	public void clearField() {
		for( int i = 0; i < fieldSize; i++ ) 
			field[i] = 0;
	}
	
	
	/*
	 * Проверка условия: есть ли свободные ячейки
	 */
	public boolean isFullFilled() {
		return countDown > 0 ? false : true;
	}
	
	/*
	 * Выигрышная комбинация
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
	 * Проверка вертикали, горизонтали и диагоналей,
	 * содержащих последнюю выбранную ячейку
	 */
	public boolean checkLines( int sign ) {
		return checkHorizontal( sign )   ||
			   checkVertical( sign )     ||
			   checkMainDiagonal( sign ) ||
			   checkSubDiagonal( sign );
	}
	
	/*
	 * Вспомогательные методы для проверки горизонтали, вертикали, диагоналей
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
