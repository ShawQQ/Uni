package it.unicam.cs.asdl2021.es4;


import java.util.Arrays;

/**
 * Un oggetto della classe aula rappresenta una certa aula con le sue facilities
 * e le sue prenotazioni.
 * 
 * @author Template: Luca Tesei, Implementation: Collective
 *
 */
public class Aula implements Comparable<Aula> {

    // numero iniziale delle posizioni dell'array facilities. Se viene richiesto
    // di inserire una facility e l'array è pieno questo viene raddoppiato. La
    // costante è protected solo per consentirne l'accesso ai test JUnit
    protected static final int INIT_NUM_FACILITIES = 5;

    // numero iniziale delle posizioni dell'array prenotazioni. Se viene
    // richiesto di inserire una prenotazione e l'array è pieno questo viene
    // raddoppiato. La costante è protected solo per consentirne l'accesso ai
    // test JUnit.
    protected static final int INIT_NUM_PRENOTAZIONI = 100;

    // Identificativo unico di un'aula
    private final String nome;

    // Location dell'aula
    private final String location;

    // Insieme delle facilities di quest'aula. L'array viene creato all'inizio
    // della dimensione specificata nella costante INIT_NUM_FACILITIES. Il
    // metodo addFacility(Facility) raddoppia l'array qualora non ci sia più
    // spazio per inserire la facility.
    private Facility[] facilities;

    // numero corrente di facilities inserite
    private int numFacilities;

    // Insieme delle prenotazioni per quest'aula. L'array viene creato
    // all'inizio
    // della dimensione specificata nella costante INIT_NUM_PRENOTAZIONI. Il
    // metodo addPrenotazione(TimeSlot, String, String) raddoppia l'array
    // qualora non ci sia più
    // spazio per inserire la prenotazione.
    private Prenotazione[] prenotazioni;

    // numero corrente di prenotazioni inserite
    private int numPrenotazioni;

    /**
     * Costruisce una certa aula con nome e location. Il set delle facilities è
     * vuoto. L'aula non ha inizialmente nessuna prenotazione.
     * 
     * @param nome
     *                     il nome dell'aula
     * @param location
     *                     la location dell'aula
     * 
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  richieste è nulla
     */
    public Aula(String nome, String location) {
    	if(nome == null || location == null) {
    		throw new NullPointerException("Argomenti nulli");
    	}
    	
    	this.nome = nome;
    	this.location = location;
    	this.numFacilities = 0;
    	this.numPrenotazioni = 0;
    	this.facilities = new Facility[INIT_NUM_FACILITIES];
    	this.prenotazioni = new Prenotazione[INIT_NUM_PRENOTAZIONI];
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
        int prime = 91;
        int result = prime + this.nome.hashCode();
        return result;
    }

    /* Due aule sono uguali se e solo se hanno lo stesso nome */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
        	return true;
        }
        if(obj == null) {
        	return false;
        }
        if(!(obj instanceof Aula)) {
        	return false;
        }
        
        Aula other = (Aula) obj;
        if(this.nome.equals(other.nome)) {
        	return true;
        }
        return false;
    }

    /* L'ordinamento naturale si basa sul nome dell'aula */
    @Override
    public int compareTo(Aula o) {
        return this.nome.compareTo(o.nome);
    }

    /**
     * @return the facilities
     */
    public Facility[] getFacilities() {
        return this.facilities;
    }

    /**
     * @return il numero corrente di facilities
     */
    public int getNumeroFacilities() {
    	return this.numFacilities;
    }

    /**
     * @return the nome
     */
    public String getNome() {
    	return this.nome;
    }

    /**
     * @return the location
     */
    public String getLocation() {
    	return this.location;
    }

    /**
     * @return the prenotazioni
     */
    public Prenotazione[] getPrenotazioni() {
    	return this.prenotazioni;
    }

    /**
     * @return il numero corrente di prenotazioni
     */
    public int getNumeroPrenotazioni() {
    	return this.numPrenotazioni;
    }

    /**
     * Aggiunge una faciltity a questa aula. Controlla se la facility è già
     * presente, nel qual caso non la inserisce.
     * 
     * @param f
     *              la facility da aggiungere
     * @return true se la facility non era già presente e quindi è stata
     *         aggiunta, false altrimenti
     * @throws NullPointerException
     *                                  se la facility passata è nulla
     */
    public boolean addFacility(Facility f) {
    	if(f == null) {
    		throw new NullPointerException("Facility nulla");
    	}

    	for(Facility facility : this.facilities){
    	    if(facility == null){
    	        break;
            }
    	    if(f.equals(facility)){
    	        return true;
            }
        }

        try{
            this.facilities[this.numFacilities] = f;
        }catch(ArrayIndexOutOfBoundsException e){
            this.facilities = Arrays.copyOf(this.facilities, this.facilities.length * 2);
            this.facilities[this.numFacilities] = f;
        }
        this.numFacilities++;
    	return false;
    }

    /**
     * Determina se l'aula è libera in un certo time slot.
     * 
     * 
     * @param ts
     *               il time slot da controllare
     * 
     * @return true se l'aula risulta libera per tutto il periodo del time slot
     *         specificato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean isFree(TimeSlot ts) {
    	if(ts == null) {
    		throw new NullPointerException("Timeslot nullo");
    	}

        for (Prenotazione prenotazione : prenotazioni) {
            if(prenotazione == null){
                break;
            }
            TimeSlot otherSlot = prenotazione.getTimeSlot();
            if (ts.overlapsWith(otherSlot)) {
                return false;
            }
        }
    	return true;
    }

    /**
     * Determina se questa aula soddisfa tutte le facilities richieste
     * rappresentate da un certo insieme dato.
     * 
     * @param requestedFacilities
     *                                l'insieme di facilities richieste da
     *                                soddisfare, sono da considerare solo le
     *                                posizioni diverse da null
     * @return true se e solo se tutte le facilities di
     *         {@code requestedFacilities} sono soddisfatte da questa aula.
     * @throws NullPointerException
     *                                  se il set di facility richieste è nullo
     */
    public boolean satisfiesFacilities(Facility[] requestedFacilities) {
    	if(requestedFacilities == null) {
    		throw new NullPointerException("Set facility nullo");
    	}

    	boolean satisfy;
        for(Facility requestedFacility : requestedFacilities){
            satisfy = false;
            if(requestedFacility == null){
                break;
            }
            for(Facility facility: this.facilities){
                if(facility == null){
                    break;
                }
                if(facility.satisfies(requestedFacility)){
                    satisfy = true;
                }
            }
            if(!satisfy){
                return false;
            }
        }
    	return true;
    }

    /**
     * Prenota l'aula controllando eventuali sovrapposizioni.
     * 
     * @param ts
     * @param docente
     * @param motivo
     * @throws IllegalArgumentException
     *                                      se la prenotazione comporta una
     *                                      sovrapposizione con un'altra
     *                                      prenotazione nella stessa aula.
     * @throws NullPointerException
     *                                      se una qualsiasi delle informazioni
     *                                      richieste è nulla.
     */
    public void addPrenotazione(TimeSlot ts, String docente, String motivo) {
        if(ts == null || docente == null || motivo == null){
            throw new NullPointerException("Parametri nulli");
        }
        if(!this.isFree(ts)){
            throw new IllegalArgumentException("Aula non disponibile");
        }

        try{
            this.prenotazioni[this.numPrenotazioni] = new Prenotazione(this, ts, docente, motivo);
        }catch(ArrayIndexOutOfBoundsException e){
            this.prenotazioni = Arrays.copyOf(this.prenotazioni, this.prenotazioni.length * 2);
            this.prenotazioni[this.numPrenotazioni] = new Prenotazione(this, ts, docente, motivo);
        }
        this.numPrenotazioni++;
    }
}
