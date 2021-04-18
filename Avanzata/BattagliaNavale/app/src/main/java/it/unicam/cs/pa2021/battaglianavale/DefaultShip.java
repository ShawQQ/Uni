package it.unicam.cs.pa2021.battaglianavale;


import java.util.Set;
import java.util.HashSet;

/**
 * Implementazione di default di una nave
 */
public class DefaultShip implements Ship {

	private final int size;
	private int strenght;
	private final String name;
	private final Set<FieldPosition> positions;

	public DefaultShip(String name, int size){
		this.name = name;
		this.size = size;
		this.strenght = size;
		this.positions = new HashSet<>();
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public int shots() {
		return this.size - this.strenght;
	}

	@Override
	public int strength() {
		return this.strenght;
	}

	@Override
	public boolean isDestroyed() {
		return this.strenght == 0;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ShootResult shotAt(FieldPosition fieldPosition) {
		if(!this.positions.contains(fieldPosition)){
			this.positions.add(fieldPosition);
			this.strenght--;
		}

		return this.status();
	}

	@Override
	public ShootResult status(FieldPosition fieldPosition) {
		if(this.positions.contains(fieldPosition)){
			return status();
		}
		return null;
	}

	private ShootResult status() {
		return this.isDestroyed() ? ShootResult.SUNK : ShootResult.HIT;
	}
}
