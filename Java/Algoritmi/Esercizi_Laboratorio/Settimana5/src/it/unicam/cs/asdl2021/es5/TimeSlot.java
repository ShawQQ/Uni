/**
 * 
 */
package it.unicam.cs.asdl2021.es5;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Un time slot è un intervallo di tempo continuo che può essere associato ad
 * una prenotazione o a una facility. Gli oggetti della classe sono immutabili.
 * Non sono ammessi time slot che iniziano e finiscono nello stesso istante.
 *
 * @author Template: Luca Tesei, Implementation: Collective
 *
 */
public class TimeSlot implements Comparable<TimeSlot> {

    /**
     * Rappresenta la soglia di tolleranza da considerare nella sovrapposizione
     * di due Time Slot. Se si sovrappongono per un numero di minuti minore o
     * uguale a questa soglia allora NON vengono considerati sovrapposti.
     */
    public static final int MINUTES_OF_TOLERANCE_FOR_OVERLAPPING = 5;

    private final GregorianCalendar start;

    private final GregorianCalendar stop;

    /**
     * Crea un time slot tra due istanti di inizio e fine
     *
     * @param start
     *                  inizio del time slot
     * @param stop
     *                  fine del time slot
     * @throws NullPointerException
     *                                      se uno dei due istanti, start o
     *                                      stop, � null
     * @throws IllegalArgumentException
     *                                      se start � uguale o successivo a
     *                                      stop
     */
    public TimeSlot(GregorianCalendar start, GregorianCalendar stop) {
        if(start == null || stop == null) {
            throw new NullPointerException("Intervallo nullo");
        }
        if(start.after(stop) || start.equals(stop)) {
            throw new IllegalArgumentException("Intervallo non valido");
        }
        this.start = start;
        this.stop  = stop;
    }

    /**
     * @return the start
     */
    public GregorianCalendar getStart() {
        return this.start;
    }

    /**
     * @return the stop
     */
    public GregorianCalendar getStop() {
        return this.stop;
    }

    /**
     * @return start in milliseconds
     */
    @Override
    public int hashCode() {
        int prime = 47;
        int result = 1;
        result = result * prime + (int) (this.start.getTimeInMillis() ^ (this.start.getTimeInMillis() >>> 32));
        result = result * prime + (int) (this.stop.getTimeInMillis() ^ (this.stop.getTimeInMillis() >>> 32));
        return result;
    }

    /*
     * Un time slot � uguale a un altro se rappresenta esattamente lo stesso
     * intervallo di tempo, cio� se inizia nello stesso istante e termina nello
     * stesso istante.
     */
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof TimeSlot)){
            return false;
        }

        TimeSlot otherSlot = (TimeSlot) obj;
        if(!this.start.equals(otherSlot.start) || !this.stop.equals(otherSlot.stop)){
            return false;
        }
        return true;
    }

    /*
     * Un time slot precede un altro se inizia prima. Se due time slot iniziano
     * nello stesso momento quello che finisce prima precede l'altro. Se hanno
     * stesso inizio e stessa fine sono uguali, in compatibilit�  con equals.
     * @throws NullPointerException
     * 								se il timeslot passato � nullo
     */
    @Override
    public int compareTo(TimeSlot o) {
        if(o == null) {
            throw new NullPointerException("Impossibile comparare un timeslot null");
        }
        if(this.start.equals(o.start) && this.stop.equals(o.stop)){
            return 0;
        }
        //se i due start sono uguali confronto stop
        if(this.start.equals(o.start)){
            if(this.stop.before(o.stop)){
                return -1;
            }else{
                return 1;
            }
            //altrimenti confronto start
        }else{
            if(this.start.before(o.start)){
                return -1;
            }else{
                return 1;
            }
        }
    }

    /**
     * Determina se questo time slot si sovrappone a un altro time slot dato,
     * considerando la soglia di tolleranza.
     *
     * @param o
     *              il time slot che viene passato per il controllo di
     *              sovrapposizione
     * @return true se questo time slot si sovrappone per pi� di
     *         MINUTES_OF_TOLERANCE_FOR_OVERLAPPING minuti a quello passato
     * @throws NullPointerException
     *                                  se il time slot passato � nullo
     */
    public boolean overlapsWith(TimeSlot o) {
        if(o == null) {
            throw new NullPointerException("Impossibile controllare timeslot nulli");
        }

        TimeSlot firstSlot;
        TimeSlot secondSlot;
        long overlapTime;
        //assicuro che gli slot siano ordinati
        if(this.compareTo(o) < 0){
            firstSlot = this;
            secondSlot = o;
        }else{
            firstSlot = o;
            secondSlot = this;
        }

        //controllo che il secondo timeslot parta prima della fine del secondo
        if(firstSlot.stop.after(secondSlot.start)){
            //calcolo tempo di overlap tra i due timeslot
            if(firstSlot.stop.before(secondSlot.stop)){
                overlapTime = firstSlot.stop.getTimeInMillis() - secondSlot.start.getTimeInMillis();
            }else{
                overlapTime = secondSlot.stop.getTimeInMillis() - secondSlot.start.getTimeInMillis();
            }
            //converto overlapTime in minuti
            overlapTime = overlapTime / 60000L;
            if(overlapTime > MINUTES_OF_TOLERANCE_FOR_OVERLAPPING){
                return true;
            }
            return false;
        }
        return false;
    }

    /*
     * Esempio di stringa: [4/11/2019 11.0 - 4/11/2019 13.0] Esempio di stringa:
     * [10/11/2019 11.15 - 10/11/2019 23.45]
     */
    @Override
    public String toString() {
        String interval;
        //formato data
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy HH.m");
        interval = "[" + dateFormat.format(this.start.getTime()) + " - " + dateFormat.format(this.stop.getTime()) + "]";
        return interval;
    }

}