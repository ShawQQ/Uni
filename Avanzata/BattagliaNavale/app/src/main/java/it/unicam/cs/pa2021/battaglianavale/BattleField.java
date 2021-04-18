package it.unicam.cs.pa2021.battaglianavale;

/**
 * Questa interfaccia rappresenta un generico campo di battaglia
 */
public interface BattleField {
	/**
	 * Aggiunge una nuova nave nella posizione indicata con la data direzione
	 *
	 * @param ship la nave da aggiungere
	 * @param fieldPosition coordinate della posizione
	 * @param dir della nave
	 * @return true se il posizionamento &egrave; avvenuto con successo, false altrimenti
	 */
	boolean addShip(Ship ship, FieldPosition fieldPosition,  Direction dir);

	/**
	 * Aggiunge una nuova nave nella posizione indicata con direzione di default
	 *
	 * @param ship la nave da aggiungere
	 * @param fieldPosition coordinate della posizione
	 * @return true se il posizionamento &egrave; avvenuto con successo, false altrimenti
	 */
	default boolean addShip(Ship ship, FieldPosition fieldPosition){
		return addShip(ship, fieldPosition, Direction.defaultDirection());
	}

	/**
	 * Restituisce la nave nella posizione data. Se gli indici utilizzati eccedono le dimensioni del campo di battaglia
	 * o sono numeri negativi, l'eccezione {@link ArrayIndexOutOfBoundsException} verr&agrave; generata.
	 *
	 * @param fieldPosition coordinate della posizione
	 * @return la nave della posizione data, null se nessuna nave &egrave; nella posizione
	 */
	Ship shipAt(FieldPosition fieldPosition);

	/**
	 * Verifica se una data posizione nel campo di battaglia sia libera o meno
	 *
	 * @param fieldPosition coordinate della posizione
	 * @return true se la posizione &egrave; libera, false altrimenti
	 */
	default boolean isFree(FieldPosition fieldPosition){
		return shipAt(fieldPosition) == null;
	}

	/**
	 * Effettuo un lancio nella posizione indicata
	 *
	 * @param fieldPosition coordinate della posizione
	 * @return risultato del lancio
	 */
	ShootResult shootAt(FieldPosition fieldPosition);

	/**
	 * Restituisce la larghezza del campo
	 *
	 * @return larghezza del campo
	 */
	int width();

	/**
	 * Restituisce l'altezza del campo
	 * @return altezza del campo
	 */
	int height();

	/**
	 * Restituisce lo stato di una cella
	 *
	 * @param fieldPosition coordinate della posizione
	 * @return stato della cella
	 */
	ShootResult result(FieldPosition fieldPosition);
}
