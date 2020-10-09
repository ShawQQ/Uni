package it.unicam.cs.asdl2021.combinationlock;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Un oggetto cassaforte con combinazione ha una manopola che pu√≤ essere
 * impostata su certe posizioni contrassegnate da lettere maiuscole. La
 * serratura si apre solo se le ultime tre lettere impostate sono uguali alla
 * combinazione segreta.
 * 
 * @author Luca Tesei
 */
public class CombinationLock {
	private String combination;
	private String positions = "";
	private boolean isOpen;
	private boolean modified;
    /**
     * Costruisce una cassaforte <b>aperta</b> con una data combinazione
     * 
     * @param aCombination
     *                         la combinazione che deve essere una stringa di 3
     *                         lettere maiuscole dell'alfabeto inglese
     * @throw IllegalArgumentException se la combinazione fornita non Ë una
     *        stringa di 3 lettere maiuscole dell'alfabeto inglese
     * @throw NullPointerException se la combinazione fornita Ë nulla
     */
    public CombinationLock(String aCombination) {
        if(aCombination == null) {
        	throw new NullPointerException("Combinazione nulla");
        }
        //regex per controllare che aCombination sia una stringa da 3 caratteri inglesi 
        if(!Pattern.matches("[A-Z][A-Z][A-Z]", aCombination)) {
        	throw new IllegalArgumentException("Combinazione non valida");
        }
        
        this.combination = aCombination;
        this.modified = false;
        this.isOpen = true;
    }

    /**
     * Imposta la manopola su una certaposizione.
     * 
     * @param aPosition
     *                      un carattere lettera maiuscola su cui viene
     *                      impostata la manopola
     * @throws IllegalArgumentException
     *                                      se il carattere fornito non Ë una
     *                                      lettera maiuscola dell'alfabeto
     *                                      inglese
     */
    public void setPosition(char aPosition) {
        if(!Character.isLetter(aPosition) || aPosition != Character.toUpperCase(aPosition)) {
        	throw new IllegalArgumentException("Carattere non valido");
        }
        
        positions += aPosition;
        //se l'utente ha inserito pi˘ di 3 caratteri mantengo solo gli ultimi 3
        int len = positions.length();
        if(len > 3) {
        	positions = positions.substring(len - 3, len);
        }
        //stringa modificata
        modified = true;
    }

    /**
     * Tenta di aprire la serratura considerando come combinazione fornita le
     * ultime tre posizioni impostate.
     */
    public void open() {
    	//se non Ë stato inserita una nuova stringa la cassaforte rimane chiusa indipendentemente dall'input dell'utente
    	if(!modified) {
    		isOpen = false;
    		return;
    	}
    	//combinazione inserita dall'utente
        isOpen = positions.equals(combination);
    }

    /**
     * Determina se la cassaforte Ë aperta.
     * 
     * @return true se la cassaforte Ë attualmente aperta, false altrimenti
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Chiude la cassaforte senza modificare la combinazione attuale. Fa in modo
     * che se si prova a riaprire subito senza impostare nessuna nuova posizione
     * della manopola la cassaforte non si apre. Si noti che se la cassaforte
     * era stata aperta con la combinazione giusta le ultime posizioni impostate
     * sono proprio la combinazione attuale.
     */
    public void lock() {
        modified = false;
        isOpen = false;
    }

    /**
     * Chiude la cassaforte e modifica la combinazione. Funziona solo se la
     * cassaforte Ë attualmente aperta. Se la cassaforte Ë attualmente chiusa
     * rimane chiusa e la combinazione non viene cambiata.
     * 
     * @param aCombination
     *                         la nuova combinazione che deve essere una stringa
     *                         di 3 lettere maiuscole dell'alfabeto inglese
     * @throw IllegalArgumentException se la combinazione fornita non Ë una
     *        stringa di 3 lettere maiuscole dell'alfabeto inglese
     * @throw NullPointerException se la combinazione fornita Ë nulla
     */
    public void lockAndChangeCombination(String aCombination) {
		if(aCombination == null) {
			throw new NullPointerException("Combinazione nulla");
	    }
		//regex per controllare che aCombination sia una stringa da 3 caratteri inglesi 
	    if(!Pattern.matches("[A-Z][A-Z][A-Z]", aCombination)) {
	    	throw new IllegalArgumentException("Combinazione non valida");
	    }
	    
	    //se la cassaforte Ë chiusa non faccio nulla
    	if(!isOpen) return;
    	//chiudo la cassaforte e cambio combinazione
    	isOpen = false;
    	combination = aCombination;
    }
}