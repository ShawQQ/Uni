package it.unicam.cs.asdl2021.combinationlock;

/**
 * Uno scassinatore è un oggetto che prende una certa cassaforte e trova la
 * combinazione utilizzando la "forza bruta".
 * 
 * @author Luca Tesei
 *
 */
public class Burglar {
	private int attempts = 0;
	private CombinationLock lock;
    /**
     * Costruisce uno scassinatore per una certa cassaforte.
     * 
     * @param aCombinationLock
     * @throw NullPointerException se la cassaforte passata è nulla
     */
    public Burglar(CombinationLock aCombinationLock) {
        if(aCombinationLock == null) {
        	throw new NullPointerException("Cassaforte nulla");
        }
        
        this.lock = aCombinationLock;
    }

    /**
     * Forza la cassaforte e restituisce la combinazione.
     * 
     * @return la combinazione della cassaforte forzata.
     */
    public String findCombination() {
    	String combination = "";
        for (int i = 65; i < 91; i++) {
        	for(int j = 65; j < 91; j++) {
        		for(int k = 65; k < 91; k++) {
        			attempts++;
        			lock.setPosition((char) i);
        			lock.setPosition((char) j);
        			lock.setPosition((char) k);
        			lock.open();
        			
        			if(lock.isOpen()) {
        				//salvo la combinazione e ritorno
        				combination += (char) i;
        				combination += (char) j;
        				combination += (char) k;
        				return combination;
        			}
        		}
        	}
        }
        
        return combination;
    }

    /**
     * Restituisce il numero di tentativi che ci sono voluti per trovare la
     * combinazione. Se la cassaforte non è stata ancora forzata restituisce -1.
     * 
     * @return il numero di tentativi che ci sono voluti per trovare la
     *         combinazione, oppure -1 se la cassaforte non è stata ancora
     *         forzata.
     */
    public int getAttempts() {
        return attempts;
    }
}
