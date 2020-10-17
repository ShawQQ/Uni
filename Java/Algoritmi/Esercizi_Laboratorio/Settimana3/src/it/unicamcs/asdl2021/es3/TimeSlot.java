/**
 * 
 */
package it.unicamcs.asdl2021.es3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Un time slot è un intervallo di tempo continuo che può essere associato ad
 * una prenotazione o a una facility. Gli oggetti della classe sono immutabili.
 * Non sono ammessi time slot che iniziano e finiscono nello stesso istante.
 * 
 * @author Template: Luca Tesei, Implementazione: collettiva
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
    
    /*
     * Timestamp relativi alla data di inizio e alla data di fine
     */
    private final long startTimeStamp;
    
    private final long stopTimeStamp;

    /**
     * Crea un time slot tra due istanti di inizio e fine
     * 
     * @param start
     *                  inizio del time slot
     * @param stop
     *                  fine del time slot
     * @throws NullPointerException
     *                                      se uno dei due istanti, start o
     *                                      stop, è null
     * @throws IllegalArgumentException
     *                                      se start è uguale o successivo a
     *                                      stop
     */
    public TimeSlot(GregorianCalendar start, GregorianCalendar stop) {
        if(start == null || stop == null) {
        	throw new NullPointerException("Intervallo nullo");
        }
        if(start.after(stop)) {
        	throw new IllegalArgumentException("Intervallo non valido");
        }
    	this.start = start;
        this.stop  = stop;
        //timestamp in secondi (based Java che usa i millisecondi al posto dei secondi come il resto del mondo)
        this.startTimeStamp = this.start.getTimeInMillis() / 1000L;
        this.stopTimeStamp = this.stop.getTimeInMillis() / 1000L;
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
    public long getStartStamp() {
    	return this.startTimeStamp;
    }
    
    /*
     * @return stop in milliseconds
     */
    public long getStopStamp() {
    	return this.stopTimeStamp;
    }
    /*
     * Ridefinire in accordo con equals
     */
    @Override
    public int hashCode() {
        int prime = 47;
        int result = 1;
        result = result * prime + (int) (this.startTimeStamp ^ (this.startTimeStamp >>> 32));
        result = result * prime + (int) (this.stopTimeStamp ^ (this.stopTimeStamp >>> 32));
        return result;
    }

    /*
     * Un time slot è uguale a un altro se rappresenta esattamente lo stesso
     * intervallo di tempo, cioé se inizia nello stesso istante e termina nello
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
        long otherStart = otherSlot.startTimeStamp;
        long otherStop = otherSlot.stopTimeStamp;
        //confronto tra timestamp
        if(this.startTimeStamp != otherStart || this.stopTimeStamp != otherStop) {
        	return false;
        }
        return true;
    }

    /*
     * Un time slot precede un altro se inizia prima. Se due time slot iniziano
     * nello stesso momento quello che finisce prima precede l'altro. Se hanno
     * stesso inizio e stessa fine sono uguali, in compatibilità  con equals.
     * @throws NullPointerException 
     * 								se il timeslot passato è nullo 
     */
    @Override
    public int compareTo(TimeSlot o) {
        if(o == null) {
        	throw new NullPointerException("Impossibile comparare un timeslot null");
        }
        
        //confronto timestamp start
        if(this.startTimeStamp < o.startTimeStamp) {
        	return -1;
        }
        if(this.stopTimeStamp > o.stopTimeStamp) {
        	return 1;
        }
        
        //se i due timestamp start sono == confronto i timestamp stop
        if(this.startTimeStamp == o.startTimeStamp) {
        	//se il timeslot finisce prima succede 
        	if(this.stopTimeStamp < this.stopTimeStamp) {
        		return -1;
        	}
        	//se il timeslot finisce dopo precede
        	if(this.stopTimeStamp > this.stopTimeStamp) {
        		return 1;
        	}
        }
        
        //intervalli uguali
        return 0;
    }

    /**
     * Determina se questo time slot si sovrappone a un altro time slot dato,
     * considerando la soglia di tolleranza.
     * 
     * @param o
     *              il time slot che viene passato per il controllo di
     *              sovrapposizione
     * @return true se questo time slot si sovrappone per più di
     *         MINUTES_OF_TOLERANCE_FOR_OVERLAPPING minuti a quello passato
     * @throws NullPointerException
     *                                  se il time slot passato è nullo
     */
    public boolean overlapsWith(TimeSlot o) {
        if(o == null) {
        	throw new NullPointerException("Impossibile controllare timeslot nulli");
        }
        
        //controllo se i due intervalli si sovrappongono
        if(this.stopTimeStamp > o.startTimeStamp) {
        	//estraggo minuti dalle date
        	int startOverlap = this.stop.get(Calendar.MINUTE);
        	int endOverlap = o.start.get(Calendar.MINUTE);
        	if(Math.abs(endOverlap - startOverlap) > MINUTES_OF_TOLERANCE_FOR_OVERLAPPING) {
        		return true;
        	}else {
        		return false;
        	}
        }
        //i due intervalli non si sovrappongono
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
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH.mm");
    	interval = "[" + dateFormat.format(this.start.getTime()) + " - " + dateFormat.format(this.stop.getTime()) + "]";
    	return interval;
    }

}
