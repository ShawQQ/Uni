package it.unicamcs.asdl2021.es3;

/**
 * Una prenotazione riguarda una certa aula per un certo time slot.
 * 
 * @author Template: Luca Tesei Implementazione: collettiva
 *
 */
public class Prenotazione implements Comparable<Prenotazione> {

    private final String aula;

    private final TimeSlot timeSlot;

    private final String docente;

    private final String motivo;

    /**
     * Costruisce una prenotazione.
     * 
     * @param aula
     *                     l'aula a cui la prenotazione si riferisce
     * @param timeSlot
     *                     il time slot della prenotazione
     * @param docente
     *                     il nome del docente che ha prenotato l'aula
     * @param motivo
     *                     il motivo della prenotazione
     * @throws NullPointerException
     *                                  se uno qualsiasi degli oggetti passati �
     *                                  null
     */
    public Prenotazione(String aula, TimeSlot timeSlot, String docente,
            String motivo) {
        // TODO implementare
        if(aula == null || timeSlot == null || docente == null || motivo == null) {
        	throw new NullPointerException("Impossibile processare argomenti nulli");
        }
        
        this.aula = aula;
        this.timeSlot = timeSlot;
        this.docente = docente;
        this.motivo = motivo;
    }

    /**
     * @return the aula
     */
    public String getAula() {
        return this.aula;
    }

    /**
     * @return the timeSlot
     */
    public TimeSlot getTimeSlot() {
    	return this.timeSlot;
    }

    /**
     * @return the docente
     */
    public String getDocente() {
        return this.docente;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
        return this.motivo;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
    	int prime = 59;
    	int result = 1;
    	result = result * prime + this.aula.hashCode();
    	result = result * prime + this.timeSlot.hashCode();
        return result;
    }

    /*
     * L'uguaglianza � data solo da stessa aula e stesso time slot. Non sono
     * ammesse prenotazioni diverse con stessa aula e stesso time slot.
     */
    @Override
    public boolean equals(Object obj) {
    	if(this == obj) {
    		return true;
    	}
    	if(obj == null) {
    		return false;
    	}
    	if(!(obj instanceof Prenotazione)) {
    		return false;
    	}
    	
    	Prenotazione other = (Prenotazione) obj;
    	if(this.aula.equals(other.aula) && this.timeSlot.equals(other.timeSlot)) {
    		return true;
    	}
        return false;
    }

    /*
     * Una prenotazione precede un altra in base all'ordine dei time slot. Se
     * due prenotazioni hanno lo stesso time slot allora una precede l'altra in
     * base all'ordine tra le aule.
     */
    @Override
    public int compareTo(Prenotazione o) {
    	if(this.timeSlot.compareTo(o.timeSlot) == 0) {
    		return this.aula.compareTo(o.aula);
    	}else {
    		return this.timeSlot.compareTo(o.timeSlot);
    	}
    }

    @Override
    public String toString() {
        return "Prenotazione [aula = " + aula + ", time slot =" + timeSlot
                + ", docente=" + docente + ", motivo=" + motivo + "]";
    }

}
