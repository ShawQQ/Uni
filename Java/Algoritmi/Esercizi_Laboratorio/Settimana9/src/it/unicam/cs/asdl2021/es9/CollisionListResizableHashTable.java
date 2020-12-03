/**
 * 
 */
package it.unicam.cs.asdl2021.es9;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

/**
 * Realizza un insieme tramite una tabella hash con indirizzamento primario (la
 *  * funzione di hash primario deve essere passata come parametro nel costruttore
 *  * e deve implementare l'interface PrimaryHashFunction) e liste di collisione.
 *  *
 *  * La tabella, poiché implementa l'interfaccia Set<E> non accetta elementi
 *  * duplicati (individuati tramite il metodo equals() che si assume sia
 *  * opportunamente ridefinito nella classe E) e non accetta elementi null.
 * 
 * La tabella ha una dimensione iniziale di default (16) e un fattore di
 * caricamento di defaut (0.75). Quando il fattore di bilanciamento effettivo
 * eccede quello di default la tabella viene raddoppiata e viene fatto un
 * riposizionamento di tutti gli elementi.
 * 
 * @author Template: Luca Tesei, Implementazione: collettiva
 *
 */
public class CollisionListResizableHashTable<E> implements Set<E> {

    /*
     * La capacità iniziale. E' una potenza di due e quindi la capacità sarà
     * sempre una potenza di due, in quanto ogni resize raddoppia la tabella.
     */
    private static final int INITIAL_CAPACITY = 16;

    /*
     * Fattore di bilanciamento di default. Tipico valore.
     */
    private static final double LOAD_FACTOR = 0.75;

    /*
     * Numero di elementi effettivamente presenti nella hash table in questo
     * momento. ATTENZIONE: questo valore è diverso dalla capacity, che è la
     * lunghezza attuale dell'array di Object che rappresenta la tabella.
     */
    private int size;

    /*
     * L'idea è che l'elemento in posizione i della tabella hash è un bucket che
     * contiene null oppure il puntatore al primo nodo di una lista concatenata
     * di elementi. Si può riprendere e adattare il proprio codice della
     * Esercitazione 6 che realizzava una lista concatenata di elementi
     * generici. La classe interna Node<E> è ripresa proprio da lì.
     * 
     * ATTENZIONE: la tabella hash vera e propria può essere solo un generico
     * array di Object e non di Node<E> per una impossibilità del compilatore di
     * accettare di creare array a runtime con un tipo generics. Ciò infatti
     * comporterebbe dei problemi nel sistema di check dei tipi Java che, a
     * run-time, potrebbe eseguire degli assegnamenti in violazione del tipo
     * effettivo della variabile. Quindi usiamo un array di Object che
     * riempiremo sempre con null o con puntatori a oggetti di tipo Node<E>.
     * 
     * Per inserire un elemento nella tabella possiamo usare il polimorfismo di
     * Object:
     * 
     * this.table[i] = new Node<E>(item, next);
     * 
     * ma quando dobbiamo prendere un elemento dalla tabella saremo costretti a
     * fare un cast esplicito:
     * 
     * Node<E> myNode = (Node<E>) this.table[i];
     * 
     * Ci sarà dato un warning di cast non controllato, ma possiamo eliminarlo
     * con un tag @SuppressWarning,
     */
    private Object[] table;

    /*
     * Funzion di hash primaria usata da questa hash table. Va inizializzata nel
     * costruttore all'atto di creazione dell'oggetto.
     */
    private final PrimaryHashFunction phf;

    /*
     * Contatore del numero di modifiche. Serve per rendere l'iterator
     * fail-fast.
     */
    private int modCount;

    // I due metodi seguenti sono di comodo per gestire la capacity e la soglia
    // oltre la quale bisogna fare il resize.

    /* Numero di elementi della tabella corrente */
    private int getCurrentCapacity() {
        return this.table.length;
    };

    /*
     * Valore corrente soglia oltre la quale si deve fare la resize,
     * getCurrentCapacity * LOAD_FACTOR
     */
    private int getCurrentThreshold() {
        return (int) (getCurrentCapacity() * LOAD_FACTOR);
    }

    /**
     * Costruisce una Hash Table con capacità iniziale di default e fattore di
     * caricamento di default.
     */
    public CollisionListResizableHashTable(PrimaryHashFunction phf) {
        this.phf = phf;
        this.table = new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.modCount = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if(o == null){
            throw new NullPointerException("Elemento null non valido");
        }
        Node<E> first = (Node<E>) this.table[this.phf.hash(o.hashCode(), this.getCurrentCapacity())];
        while(first != null){
            if(first.item.equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    @Override
    public boolean add(E e) {
        if(e == null){
            throw new NullPointerException("Elemento null non valido");
        }
        if(this.contains(e)){
            return false;
        }
        int hash = this.phf.hash(e.hashCode(), this.getCurrentCapacity());
        if(this.table[hash] == null){
            Node<E> first = new Node<E>(e, null);
            this.table[hash] = first;
        }else{
            Node<E> first = (Node<E>) this.table[hash];
            while(first.next != null){
                first = first.next;
            }
            first.next = new Node<E>(e, null);
        }
        this.size++;
        this.modCount++;

        if(this.size > this.getCurrentThreshold()) this.resize();
        return true;
    }

    /*
     * Raddoppia la tabella corrente e riposiziona tutti gli elementi. Da
     * chiamare quando this.size diventa maggiore di getCurrentThreshold()
     */
    private void resize() {
        Object[] newTable = new Object[this.getCurrentCapacity() * 2];

        for(Object list : this.table){
            Node<E> listNode = (Node<E>) list;
            while(listNode != null){
                int hash = this.phf.hash(listNode.item.hashCode(), newTable.length);
                if(newTable[hash] == null){
                    Node<E> first = new Node<E>(listNode.item, null);
                    newTable[hash] = first;
                }else{
                    Node<E> first = (Node<E>) newTable[hash];
                    while(first.next != null){
                        first = first.next;
                    }
                    first.next = new Node<E>(listNode.item, null);
                }
            }
        }

        this.table = newTable;
    }

    @Override
    public boolean remove(Object o) {
        if(o == null){
            throw new NullPointerException("Elemento null non valido");
        }
        int hash = this.phf.hash(o.hashCode(), this.getCurrentCapacity());
        Node<E> first = (Node<E>) this.table[hash];
        if(first.equals(o)){
            this.table[hash] = null;
            this.size--;
            this.modCount++;
            return true;
        }

        while(first.next != null){
            if(first.next.item.equals(o)){
                first.next = null;
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Object item = iterator.next();
            if(!this.contains(item)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            E item = (E) iterator.next();
            this.add(item);
            this.size++;
        }
        this.modCount++;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operazione non supportata");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator iterator = c.iterator();
        while(iterator.hasNext()){
            Object item = iterator.next();
            boolean result = this.remove(item);
            if(result) this.size--;
        }
        this.modCount++;
        return true;
    }

    @Override
    public void clear() {
        // Ritorno alla situazione iniziale
        this.table = new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.modCount = 0;
    }

    /*
     * Classe per i nodi della lista concatenata
     */
    private static class Node<E> {
        private E item;

        private Node<E> next;

        /*
         * Crea un nodo "singolo" equivalente a una lista con un solo elemento.
         */
        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    /*
     * Classe che realizza un iteratore per questa hash table. L'ordine in cui
     * vengono restituiti gli oggetti presenti non è rilevante, ma ogni oggetto
     * presente deve essere restituito dall'iteratore una e una sola volta.
     * L'iteratore deve essere fail-fast, cioè deve lanciare una eccezione
     * IllegalStateException se a una chiamata di next() si "accorge" che la
     * tabella è stata cambiata rispetto a quando l'iteratore è stato creato.
     */
    private class Itr implements Iterator<E> {

        private int numeroModificheAtteso;

        private Node<E> lastItem;

        private int lastIndex;

        private Itr() {
            this.lastItem = null;
            this.lastIndex = -1;
            this.numeroModificheAtteso = modCount;
        }

        @Override
        public boolean hasNext() {
            //Se lastItem è null ho finito la lista precedente e devo controllare se ne esiste un altra
            if(this.lastItem == null){
                //Aggiorno l'indice con il successivo
                int nextIndex = this.lastIndex++;
                //Salto tutti gli indici con valore null
                while(table[nextIndex] == null && nextIndex < getCurrentCapacity()){
                    nextIndex++;
                }
                //Controllo se nell'indice è contenuta una lista
                if(table[nextIndex] != null){
                    this.lastIndex = nextIndex;
                    return true;
                }else{
                    return false;
                }
            }else{
                //La lista ha ancora un elemento
                return true;
            }
        }

        @Override
        public E next() {
            if(this.numeroModificheAtteso != modCount){
                throw new ConcurrentModificationException("L'iteratore è stato modificato");
            }
            E item = this.lastItem.item;
            this.lastItem = this.lastItem.next;
            return item;
        }

    }

}
