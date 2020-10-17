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
     *                                  se uno qualsiasi degli oggetti passati è
     *                                  null
     */
    public Prenotazione(String aula, TimeSlot timeSlot, String docente,
            String motivo) {
        // TODO implementare
        this.aula = null;
        this.timeSlot = null;
        this.docente = null;
        this.motivo = null;
    }

    /**
     * @return the aula
     */
    public String getAula() {
        // TODO implementare
        return null;
    }

    /**
     * @return the timeSlot
     */
    public TimeSlot getTimeSlot() {
     // TODO implementare
        return null;
    }

    /**
     * @return the docente
     */
    public String getDocente() {
     // TODO implementare
        return null;
    }

    /**
     * @return the motivo
     */
    public String getMotivo() {
     // TODO implementare
        return null;
    }

    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
     // TODO implementare
        return -1;
    }

    /*
     * L'uguaglianza è data solo da stessa aula e stesso time slot. Non sono
     * ammesse prenotazioni diverse con stessa aula e stesso time slot.
     */
    @Override
    public boolean equals(Object obj) {
     // TODO implementare
        return false;
    }

    /*
     * Una prenotazione precede un altra in base all'ordine dei time slot. Se
     * due prenotazioni hanno lo stesso time slot allora una precede l'altra in
     * base all'ordine tra le aule.
     */
    @Override
    public int compareTo(Prenotazione o) {
     // TODO implementare
        return 0;
    }

    @Override
    public String toString() {
        return "Prenotazione [aula = " + aula + ", time slot =" + timeSlot
                + ", docente=" + docente + ", motivo=" + motivo + "]";
    }

}
