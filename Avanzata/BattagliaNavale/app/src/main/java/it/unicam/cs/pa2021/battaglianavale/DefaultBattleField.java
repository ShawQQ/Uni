package it.unicam.cs.pa2021.battaglianavale;

/**
 * Implementazione di default del campo di battaglia
 */
public class DefaultBattleField implements BattleField{
	private static final int DEFAULT_SIZE = 10;
	private static final int DEFAULT_WIDTH = DEFAULT_SIZE;
	private static final int DEFAULT_HEIGHT = DEFAULT_SIZE;

	private final Ship[][] field;
	private boolean[][] positionFlag;
	private final int width;
	private final int height;

	/**
	 * Crea un campo di battaglia con le dimensioni di default
	 */
	public DefaultBattleField(){
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Crea un campo di battaglia con le dimensioni date
	 * @param width larghezza del campo di battaglia
	 * @param height altezza del campo di battaglia
	 */
	public DefaultBattleField(int width, int height){
		this.field = new Ship[width][height];
		this.positionFlag = new boolean[width][height];
		this.width = width;
		this.height = height;
	}


	@Override
	public boolean addShip(Ship ship, FieldPosition fieldPosition, Direction dir) {
		int size = ship.size();
		FieldPosition[] positions = dir.computePositions(fieldPosition.getRow(), fieldPosition.getColumn(), size);
		if(!checkPositions(positions)) return false;
		addShip(ship, positions);
		return true;
	}

	private void addShip(Ship ship, FieldPosition[] positions) {
		for(FieldPosition p: positions){
			this.field[p.getRow()][p.getColumn()] = ship;
		}
	}

	private boolean checkPositions(FieldPosition[] positions) {
		for(FieldPosition p: positions){
			if(!checkPosition(p)) return false;
		}
		return true;
	}

	private boolean checkPosition(FieldPosition p) {
		if(p.getColumn() < 0 || p.getColumn() >= this.width()) return false;
		if(p.getRow() < 0 || p.getRow() >= this.height()) return false;
		return isFree(p);
	}

	@Override
	public Ship shipAt(FieldPosition fieldPosition) {
		return this.field[fieldPosition.getRow()][fieldPosition.getColumn()];
	}

	@Override
	public ShootResult shootAt(FieldPosition fieldPosition) {
		Ship ship = shipAt(fieldPosition);
		this.recordShotAt(fieldPosition);
		if(ship == null){
			return ShootResult.MISS;
		}else{
			return ship.shotAt(fieldPosition);
		}
	}

	private void recordShotAt(FieldPosition fieldPosition) {
		this.positionFlag[fieldPosition.getRow()][fieldPosition.getColumn()] = true;
	}

	@Override
	public int width() {
		return this.width;
	}

	@Override
	public int height() {
		return this.height;
	}

	@Override
	public ShootResult result(FieldPosition fieldPosition) {
		if(this.hasBeenUsed(fieldPosition)){
			Ship ship = shipAt(fieldPosition);
			if(ship == null){
				return ShootResult.MISS;
			}else{
				return ship.status(fieldPosition);
			}
		}

		return null;
	}

	private boolean hasBeenUsed(FieldPosition fieldPosition) {
		return this.positionFlag[fieldPosition.getRow()][fieldPosition.getColumn()];
	}
}
