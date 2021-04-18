package it.unicam.cs.pa2021.battaglianavale;

/**
 * Interfaccia utilizzata per rappresentare le navi sul campo di battaglia
 */
public interface Ship {
	/**
	 * Restituisce la dimensione della nave
	 *
	 * @return dimensione della nave
	 */
	int size();

	/**
	 * Restituisce il numero dei colpi ricevuti dalla nave
	 *
	 * @return numero dei colpi ricevuti dalla nave
	 */
	int shots();

	/**
	 * Restituisce i punti vita della nave
	 *
	 * @return i punti vita della nave
	 */
	int strength();

	/**
	 * Restituisce true se la nave &egrave; stata distrutta, false altrimenti
	 *
	 * @return true se la nave &egrave; stata distrutta, false altrimenti
	 */
	boolean isDestroyed();

	/**
	 * Restituisce il nome della nave
	 * @return il nome della nave
	 */
	String getName();

	/**
	 * Restituisce il risultato di un colpo ricevuto alla data posizione
	 *
	 * @param fieldPosition posizione del colpo
	 * @return risultato del colpo
	 */
	ShootResult shotAt(FieldPosition fieldPosition);

	/**
	 * Restituisce lo stato della nave nella posizione data
	 *
	 * @param fieldPosition posizione della nave
	 * @return stato della nave nella posizione data
	 */
	ShootResult status(FieldPosition fieldPosition);
}
