package it.unicam.cs.pa2021.battaglianavale;

/**
 * Classe utilizzata per tenere traccia degli attacchi effettuati
 */
public interface AttackField {
	/**
	 * Registra il risultato dell'attacco nella data posizione.
	 *
	 * @param fieldPosition posizione nel campo
	 * @param result risultato dell'attacco
	 */
	void set(FieldPosition fieldPosition, ShootResult result);

	/**
	 * Restituisce il valore registrato nella data posizione
	 *
	 * @param fieldPosition posizione nel campo
	 * @return valore registrato nella data posizione, restituisce
	 * <code>null</code> se la posizione non &egrave; stata usata.
	 */
	ShootResult get(FieldPosition fieldPosition);
}
