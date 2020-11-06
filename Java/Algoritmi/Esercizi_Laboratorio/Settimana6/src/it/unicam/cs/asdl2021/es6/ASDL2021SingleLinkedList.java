package it.unicam.cs.asdl2021.es6;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Lista concatenata singola che non accetta valori null, ma permette elementi
 * duplicati.
 * 
 * @author Template: Luca Tesei, Implementazione: collettiva
 *
 * @param <E>
 *                il tipo degli elementi della lista
 */
public class ASDL2021SingleLinkedList<E> implements List<E> {

    private int size;

    private Node<E> head;

    private Node<E> tail;

    private int numeroModifiche;

    /**
     * Crea una lista vuota.
     */
    public ASDL2021SingleLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
        this.numeroModifiche = 0;
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
     * Classe che realizza un iteratore per ASDL2021SingleLinkedList.
     * L'iteratore deve essere fail-fast, cioè deve lanciare una eccezione
     * IllegalStateException se a una chiamata di next() si "accorge" che la
     * lista è stata cambiata rispetto a quando l'iteratore è stato creato.
     */
    private class Itr implements Iterator<E> {

        private Node<E> lastReturned;

        private int numeroModificheAtteso;

        private Itr() {
            // All'inizio il cursore è null
            this.lastReturned = null;
            this.numeroModificheAtteso = ASDL2021SingleLinkedList.this.numeroModifiche;
        }

        @Override
        public boolean hasNext() {
            if(this.lastReturned == null){
                //se lastReturned è null controllo se la lista ha almeno un elemento
                return ASDL2021SingleLinkedList.this.head != null;
            }else{
                return this.lastReturned.next != null;
            }
        }

        @Override
        public E next() {
            if(this.numeroModificheAtteso != ASDL2021SingleLinkedList.this.numeroModifiche){
                throw new IllegalStateException("Iteratore non valido");
            }
            if(this.lastReturned == null){
                //Se lastReturned è null ritorno il primo elemento della lista
                this.lastReturned = ASDL2021SingleLinkedList.this.head;
                return this.lastReturned.item;
            }else{
                if(this.lastReturned.next == null){
                    throw new NoSuchElementException("Iteratore finito");
                }
                E item = this.lastReturned.next.item;
                //sposto il puntatore sull'elemento successivo
                this.lastReturned = this.lastReturned.next;
                return item;
            }
        }

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
            throw new NullPointerException("Valori nulli non ammessi");
        }
        Iterator<E> iterator = this.iterator();
        while(iterator.hasNext()){
            E item = iterator.next();
            if(item.equals(o)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        if(e == null){
            throw new NullPointerException("Valori nulli non ammessi");
        }
        //nuovo nodo
        Node<E> newNode = new Node(e, null);
        if(this.isEmpty()){
            //se la lista è vuota salvo il nodo sulla coda e sulla testa
            this.head = this.tail = newNode;
        }else{
            //itero la lista fino al primo elemento con next == null
            Node<E> last = this.head;
            while(last.next != null){
                last = last.next;
            }
            //aggiorno il puntatore di last e salvo il nuovo nodo in fondo alla lista
            last.next = newNode;
            this.tail = newNode;
        }
        this.size++;
        this.numeroModifiche++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if(o == null){
            throw new NullPointerException("Valori nulli non ammessi");
        }
        //Se la lista è vuota non c'è nulla da eliminare
        if(this.isEmpty()){
            return false;
        }

        Node<E> currentNode = this.head;

        while(currentNode.next != null){
            if(currentNode.next.item.equals(o)){
                //Sposto il puntatore di currentNode di due posizioni
                currentNode.next = currentNode.next.next;
                //Se non c'è un altro elemento dopo searchedNode aggiorno la coda della lista
                if(currentNode.next == null){
                    this.tail = currentNode;
                }
                this.numeroModifiche++;
                this.size--;
                return true;
            }
            //Scorro la lista
            currentNode = currentNode.next;
        }
        return false;
    }

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
        this.numeroModifiche++;
    }

    @Override
    public E get(int index) {
        //se index è maggiore della grandezza della lista throwo un eccezione
        if(index >= this.size || index < 0){
            throw new IndexOutOfBoundsException("Index out of range");
        }

        Node<E> searchedNode = this.head;
        for(int i = 0; i < index; i++){
            searchedNode = searchedNode.next;
        }
        return searchedNode.item;
    }

    @Override
    public E set(int index, E element) {
        if(index < 0 || index >= this.size){
            throw new IndexOutOfBoundsException("Index out of range");
        }
        if(element == null){
            throw new NullPointerException("Valori nulli non ammessi");
        }

        Node<E> searchedNode = this.head;
        for(int i = 0; i < index; i++){
            searchedNode = searchedNode.next;
        }
        E item = searchedNode.item;
        searchedNode.item = element;
        return item;
    }

    @Override
    public void add(int index, E element) {
        if(index > this.size || index < 0){
            throw new ArrayIndexOutOfBoundsException("Index out of range");
        }
        if(element == null){
            throw new NullPointerException("Valori nulli non ammessi");
        }

        if(index == 0){
            Node<E> newNode = new Node(element, this.head);
            this.head = newNode;
        }else{
            Node<E> searchedNode = this.head;
            for(int i = 0; i < index; i++){
                searchedNode = searchedNode.next;
            }

            Node<E> newNode = new Node(element, searchedNode.next);
            searchedNode.next = newNode;
            //Se index è pari alla dimensione della lista il nuovo elemento è la coda della lista
            if(index == this.size){
                this.tail = newNode;
            }
        }
        this.size++;
        this.numeroModifiche++;
    }

    @Override
    public E remove(int index) {
        if(index >= this.size || index < 0){
            throw new ArrayIndexOutOfBoundsException("Index out of range");
        }

        //Se index è 0 sposto la testa della lista di uno
        if(index == 0){
            this.head = this.head.next;
        }

        Node<E> searchedNode = this.head;
        for(int i = 0; i < index - 1; i++){
            searchedNode = searchedNode.next;
        }

        E item = searchedNode.next.item;
        searchedNode.next = searchedNode.next.next;
        //Se non c'è un altro elemento dopo searchedNode aggiorno la coda della lista
        if(searchedNode.next == null){
            this.tail = searchedNode;
        }
        this.size--;
        this.numeroModifiche++;
        return null;
    }

    @Override
    public int indexOf(Object o) {
        if(o == null){
            throw new NullPointerException("Valri nulli non ammessi");
        }
        Iterator<E> iterator = this.iterator();
        int i = 0;
        while(iterator.hasNext()){
            if(iterator.next().equals(o)){
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if(o == null){
            throw new NullPointerException("Valri nulli non ammessi");
        }
        Iterator<E> iterator = this.iterator();
        int i = 0;
        int index = -1;
        while(iterator.hasNext()){
            if(iterator.next().equals(o)){
                index = i;
            }
            i++;
        }
        return index;
    }
    
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        for(int i = 0; i < this.size; i++){
            arr[i] = this.get(i);
        }
        return arr;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }
}
