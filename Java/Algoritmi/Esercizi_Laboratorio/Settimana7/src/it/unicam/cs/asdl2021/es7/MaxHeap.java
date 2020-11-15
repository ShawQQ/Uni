package it.unicam.cs.asdl2021.es7;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa uno heap binario che può contenere elementi non nulli
 * possibilmente ripetuti.
 * 
 * @author Template: Luca Tesei, Implementation: collettiva
 *
 * @param <E>
 *                il tipo degli elementi dello heap, che devono avere un
 *                ordinamento naturale.
 */
public class MaxHeap<E extends Comparable<E>> {

    /*
     * L'array che serve come base per lo heap
     */
    private ArrayList<E> heap;

    /**
     * Costruisce uno heap vuoto.
     */
    public MaxHeap() {
        this.heap = new ArrayList<E>();
    }

    /**
     * Restituisce il numero di elementi nello heap.
     * 
     * @return il numero di elementi nello heap
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Determina se lo heap è vuoto.
     * 
     * @return true se lo heap è vuoto.
     */
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    /**
     * Costruisce uno heap a partire da una lista di elementi.
     * 
     * @param list
     *                 lista di elementi
     */
    public MaxHeap(List<E> list) {
        for(E element: list){
            this.insert(element);
        }
    }

    /**
     * Inserisce un elemento nello heap
     * 
     * @param el
     *               l'elemento da inserire
     * @throws NullPointerException
     *                                  se l'elemento è null
     * 
     */
    public void insert(E el) {
        if(el == null){
            throw new NullPointerException("Elementi nulli non ammessi");
        }
        int index = this.heap.size();
        int parentIndex = this.parentIndex(index);
        this.heap.add(el);
        //faccio salire l'elemento finché non ha una priorità maggiore del suo parent
        while(el.compareTo(this.heap.get(parentIndex)) > 0){
            this.swap(index, parentIndex);
            index = parentIndex;
            parentIndex = this.parentIndex(parentIndex);
        }
    }

    /*
     * Funzione di comodo per calcolare l'indice del figlio sinistro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int leftIndex(int i) {
        return 2*i + 1;
    }

    /*
     * Funzione di comodo per calcolare l'indice del figlio destro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int rightIndex(int i) {
        return 2*i + 2;
    }

    /*
     * Funzione di comodo per calcolare l'indice del genitore del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int parentIndex(int i) {
        return (int) Math.floor(i / 2) - 1;
    }

    /**
     * Ritorna l'elemento massimo senza toglierlo.
     * 
     * @return l'elemento massimo dello heap oppure null se lo heap è vuoto
     */
    public E getMax() {
        return this.heap.get(0);
    }

    /**
     * Estrae l'elemento massimo dallo heap. Dopo la chiamata tale elemento non
     * è più presente nello heap.
     * 
     * @return l'elemento massimo di questo heap.
     */
    public E extractMax() {
        int lastIndex = this.heap.size() - 1;
        E element = this.heap.get(lastIndex);
        this.heap.set(0, element);
        this.heapify(0);
        return element;
    }

    /*
     * Ricostituisce uno heap a partire dal nodo in posizione i assumendo che i
     * suoi sottoalberi sinistro e destro (se esistono) siano heap.
     */
    private void heapify(int i) {
        int maxIndex = -1;
        //indici dei figli dell'elemento
        int leftIndex = this.leftIndex(i);
        int rightIndex = this.rightIndex(i);
        //trovo l'elemento con priorità minima tra element e i suoi figli
        if(leftIndex < this.heap.size() && this.heap.get(leftIndex).compareTo(this.heap.get(i)) >  0){
            maxIndex = leftIndex;
        }else{
            maxIndex = i;
        }
        if(rightIndex < this.heap.size() && this.heap.get(rightIndex).compareTo(this.heap.get(i)) >  0){
            maxIndex = rightIndex;
        }
        //se element ha priorità maggiore di uno dei suoi figli lo faccio scendere
        if(maxIndex != i){
            this.swap(i, maxIndex);
            this.heapify(maxIndex);
        }
    }

    private void swap(int first, int second){
        int tmp = second;
        this.heap.set(second, this.heap.get(first));
        this.heap.set(first, this.heap.get(tmp));
    }
}
