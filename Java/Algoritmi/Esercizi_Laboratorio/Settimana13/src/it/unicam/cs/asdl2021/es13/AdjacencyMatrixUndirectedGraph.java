/**
 *
 */
package it.unicam.cs.asdl2021.es13;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Classe che implementa un grafo non orientato tramite matrice di adiacenza.
 * Non sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 *
 * I nodi sono indicizzati da 0 a nodeCoount() - 1 seguendo l'ordine del loro
 * inserimento (0 è l'indice del primo nodo inserito, 1 del secondo e così via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non è rappresentata tramite array
 * ma tramite ArrayList.
 *
 * Gli oggetti GraphNode<L>, cioè i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l'indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l'insieme dei nodi.
 *
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma è null se i nodi i e j non sono
 * collegati da un arco e contiene un oggetto della classe GraphEdge<L> se lo
 * sono. Tale oggetto rappresenta l'arco. Un oggetto uguale (secondo equals) e
 * con lo stesso peso (se gli archi sono pesati) deve essere presente nella
 * posizione j, i della matrice.
 *
 * Questa classe non supporta i metodi di cancellazione di nodi e archi, ma
 * supporta tutti i metodi che usano indici, utilizzando l'indice assegnato a
 * ogni nodo in fase di inserimento.
 *
 * @author Template: Luca Tesei, Implementation: INSERIRE NOME E COGNOME DELLO
 *         STUDENTE - INSERIRE ANCHE L'EMAIL xxxx@studenti.unicam.it
 *
 */
public class AdjacencyMatrixUndirectedGraph<L> extends Graph<L> {
    /*
     * Le seguenti variabili istanza sono protected al solo scopo di agevolare
     * il JUnit testing
     */

    // Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
    // matrice di adiacenza
    protected Map<GraphNode<L>, Integer> nodesIndex;

    // Matrice di adiacenza, gli elementi sono null o oggetti della classe
    // GraphEdge<L>. L'uso di ArrayList permette alla matrice di aumentare di
    // dimensione gradualmente ad ogni inserimento di un nuovo nodo.
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public AdjacencyMatrixUndirectedGraph() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
    }

    @Override
    public int nodeCount() {
        return this.nodesIndex.size();
    }

    @Override
    public int edgeCount() {
        int edgeCount = 0;
        for(ArrayList<GraphEdge<L>> list : this.matrix){
            for(GraphEdge<L> edge : list){
                if(edge == null) continue;
                edgeCount++;
            }
        }

        return edgeCount;
    }

    @Override
    public void clear() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa un grafo non orientato
        return false;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        return this.nodesIndex.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi nulli non ammessi");

        this.matrix.add(new ArrayList<GraphEdge<L>>());
        for(ArrayList<GraphEdge<L>> edgeList : this.matrix){
            edgeList.add(this.nodeCount(), null);
        }
        return this.nodesIndex.putIfAbsent(node, this.nodeCount() + 1) == null;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Remove di nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        return this.nodesIndex.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        if(label == null) throw new NullPointerException("Label nulle non ammesse");
        for(Map.Entry<GraphNode<L>, Integer> entry : this.nodesIndex.entrySet()){
            if(entry.getKey().getLabel().equals(label)) return entry.getKey();
        }
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        if(label == null) throw new NullPointerException("Label nulle non ammesse");
        for(Map.Entry<GraphNode<L>, Integer> entry : this.nodesIndex.entrySet()){
            if(entry.getKey().getLabel().equals(label)) return entry.getValue();
        }

        throw new IndexOutOfBoundsException("Indice non presente");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        for(Map.Entry<GraphNode<L>, Integer> map : this.nodesIndex.entrySet()){
            if(map.getValue() == i) return map.getKey();
        }

        throw new IndexOutOfBoundsException("Indice non presente");
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi null non supportati");
        if(!this.nodesIndex.containsKey(node)) throw new IllegalArgumentException("Nodo non presente");

        Set<GraphNode<L>> nodeSet = new HashSet<GraphNode<L>>();
        int nodeIndex = this.nodesIndex.get(node);
        for(GraphEdge<L> edge : this.matrix.get(nodeIndex)){
            if(edge == null) continue;
            nodeSet.add(edge.getNode2());
        }
        return nodeSet;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        Set<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>();

        for(ArrayList<GraphEdge<L>> edgeList : this.matrix){
            for(GraphEdge<L> edge : edgeList){
                edgeSet.add(edge);
            }
        }
        return edgeSet;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        if(edge == null) throw new NullPointerException("Archi nulli non supportati");
        if(!this.nodesIndex.containsKey(edge.getNode1())) throw new IllegalArgumentException("Nodo di partenza non presente");
        if(!this.nodesIndex.containsKey(edge.getNode2())) throw new IllegalArgumentException("Nodo di arrivo non presente");
        if(this.isDirected() != edge.isDirected()) throw new IllegalArgumentException("Orientamento del grafo diverso da quello dell'arco");

        int sourceIndex = this.nodesIndex.get(edge.getNode1());
        int exitIndex = this.nodesIndex.get(edge.getNode2());
        if(this.matrix.get(sourceIndex).get(exitIndex).equals(edge)) return false;
        this.matrix.get(sourceIndex).add(exitIndex, edge);
        this.matrix.get(exitIndex).add(sourceIndex, edge);
        return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        throw new UnsupportedOperationException(
                "Operazione di remove non supportata in questa classe");
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        if(edge == null) throw new NullPointerException("Archi nulli non supportati");
        if(!this.nodesIndex.containsKey(edge.getNode1())) throw new IllegalArgumentException("Nodo di partenza non presente");
        if(!this.nodesIndex.containsKey(edge.getNode2())) throw new IllegalArgumentException("Nodo di arrivo non presente");

        int sourceIndex = this.nodesIndex.get(edge.getNode1());
        int exitIndex = this.nodesIndex.get(edge.getNode2());
        return !(this.matrix.get(sourceIndex).get(exitIndex) == null);
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi nulli non supportati");
        if(!this.nodesIndex.containsKey(node)) throw new IllegalArgumentException("Nodo non presente");

        Set<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>();
        int sourceIndex = this.nodesIndex.get(node);

        for(GraphEdge<L> edge : this.matrix.get(sourceIndex)){
            edgeSet.add(edge);
        }
        return edgeSet;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

}
