/**
 * 
 */
package it.unicam.cs.asdl2021.es5;

/**
 * Una Quantitative Facility è una facility che contiene una informazione
         * quantitativa. Ad esempio la presenza di 50 posti a sedere oppure la presenza
         * di 20 thin clients.
         *
         * @author Template: Luca Tesei, Implementation: Collective
        *
        */
public class QuantitativeFacility extends Facility {

    private final int quantity;

    /**
     * Costruisce una facility quantitativa.
     *
     * @param codice
     *                        codice identificativo della facility
     * @param descrizione
     *                        descrizione testuale della facility
     * @param quantity
     *                        quantita' associata alla facility
     * @throws NullPointerException
     *                                  se una qualsiasi delle informazioni
     *                                  {@code codice} e {@code descrizione} è
     *                                  nulla.
     */
    public QuantitativeFacility(String codice, String descrizione,
                                int quantity) {
        super(codice, descrizione);
        this.quantity = quantity;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return this.quantity;
    }

    /*
     * Questa quantitative facility soddisfa una facility data se e solo se la
     * facility data è una quantitative facility, ha lo stesso codice e la
     * quantità associata a questa facility è maggiore o uguale alla quantità
     * della facility data.
     */
    @Override
    public boolean satisfies(Facility o) {
        if(o == null) {
            throw new NullPointerException("Facility nulla");
        }

        if(!(o instanceof QuantitativeFacility)) {
            return false;
        }

        QuantitativeFacility other = (QuantitativeFacility) o;
        if(this.getCodice().equals(other.getCodice()) && this.quantity >= other.quantity) {
            return true;
        }

        return false;
    }

}