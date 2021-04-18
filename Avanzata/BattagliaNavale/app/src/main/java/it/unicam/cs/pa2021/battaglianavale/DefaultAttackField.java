package it.unicam.cs.pa2021.battaglianavale;

import java.util.HashMap;
import java.util.Map;

public class DefaultAttackField implements AttackField{
	private final Map<FieldPosition, ShootResult> map;

	public DefaultAttackField(){
		this.map = new HashMap<>();
	}

	@Override
	public void set(FieldPosition fieldPosition, ShootResult result) {
		this.map.put(fieldPosition, result);
	}

	@Override
	public ShootResult get(FieldPosition fieldPosition) {
		return this.map.get(fieldPosition);
	}
}
