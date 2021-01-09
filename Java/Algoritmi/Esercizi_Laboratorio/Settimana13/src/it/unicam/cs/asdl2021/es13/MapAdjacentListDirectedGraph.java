/**
 *
 */
package it.unicam.cs.asdl2021.es13;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Implementazione della classe astratta {@code Graph<L>} che realizza un grafo
 * orientato. Non sono accettate etichette dei nodi null e non sono accettate
 * etichette duplicate nei nodi (che in quel caso sono lo stesso nodo).
 *
 * Per la rappresentazione viene usata una variante della rappresentazione con
 * liste di adiacenza. A differenza della rappresentazione standard si usano
 * strutture dati più efficienti per quanto riguarda la complessità in tempo
 * della ricerca se un nodo è presente (pseudocostante, con tabella hash) e se
 * un arco è presente (pseudocostante, con tabella hash). Lo spazio occupato per
 * la rappresentazione risultà tuttavia più grande di quello che servirebbe con
 * la rappresentazione standard.
 *
 * Le liste di adiacenza sono rappresentate con una mappa (implementata con
 * tabelle hash) che associa ad ogni nodo del grafo i nodi adiacenti. In questo
 * modo il dominio delle chiavi della mappa è l'insieme dei nodi, su cui è
 * possibile chiamare il metodo contains per testare la presenza o meno di un
 * nodo. Ad ogni chiave della mappa, cioè ad ogni nodo del grafo, non è
 * associata una lista concatenata dei nodi collegati, ma un set di oggetti
 * della classe GraphEdge<L> che rappresentano gli archi uscenti dal nodo: in
 * questo modo la rappresentazione riesce a contenere anche l'eventuale peso
 * dell'arco (memorizzato nell'oggetto della classe GraphEdge<L>). Per
 * controllare se un arco è presenta basta richiamare il metodo contains in
 * questo set. I test di presenza si basano sui metodi equals ridefiniti per
 * nodi e archi nelle classi GraphNode<L> e GraphEdge<L>.
 *
 * Questa classe non supporta le operazioni di rimozione di nodi e archi e le
 * operazioni indicizzate di ricerca di nodi e archi.
 *
 * @author Template: Luca Tesei, Implementation: INSERIRE NOME E COGNOME DELLO
 *         STUDENTE - INSERIRE ANCHE L'EMAIL xxxx@studenti.unicam.it
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class MapAdjacentListDirectedGraph<L> extends Graph<L> {

    /*
     * Le liste di adiacenza sono rappresentate con una mappa. Ogni nodo viene
     * associato con l'insieme degli archi uscenti. Nel caso in cui un nodo non
     * abbia archi uscenti è associato con un insieme vuoto. La variabile
     * istanza è protected solo per scopi di test JUnit.
     */
    protected final Map<GraphNode<L>, Set<GraphEdge<L>>> adjacentLists;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public MapAdjacentListDirectedGraph() {
        // Inizializza la mappa con la mappa vuota
        this.adjacentLists = new HashMap<GraphNode<L>, Set<GraphEdge<L>>>();
    }

    @Override
    public int nodeCount() {
        return this.adjacentLists.keySet().size();
    }

    @Override
    public int edgeCount() {
        int edgeCount = 0;
        for(Map.Entry<GraphNode<L>, Set<GraphEdge<L>>> entry : this.adjacentLists.entrySet()){
            edgeCount += entry.getValue().size();
        }

        return edgeCount;
    }

    @Override
    public void clear() {
        this.adjacentLists.clear();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa grafi orientati
        return true;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        return this.adjacentLists.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi null non supportati");
        return this.adjacentLists.putIfAbsent(node, new HashSet<GraphEdge<L>>()) == null;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        if (node == null)
            throw new NullPointerException(
                    "Tentativo di rimuovere un nodo null");
        throw new UnsupportedOperationException(
                "Rimozione dei nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi null non supportati");
        return this.adjacentLists.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        if(label == null) throw new NullPointerException("Nodi null non supportati");
        for(GraphNode<L> node : this.adjacentLists.keySet()){
            if(node.getLabel().equals(label)) return node;
        }
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        if (label == null)
            throw new NullPointerException(
                    "Tentativo di ricercare un nodo con etichetta null");
        throw new UnsupportedOperationException(
                "Ricerca dei nodi con indice non supportata");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int i) {
        throw new UnsupportedOperationException(
                "Ricerca dei nodi con indice non supportata");
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi null non supportati");
        if(!this.adjacentLists.containsKey(node)) throw new IllegalArgumentException("Nodo non presente");

        Set<GraphNode<L>> nodeSet = new HashSet<GraphNode<L>>();
        for(GraphEdge<L> edge : this.adjacentLists.get(node)){
            nodeSet.add(edge.getNode2());
        }

        return nodeSet;
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi nulli non supportati");
        if(!this.adjacentLists.keySet().contains(node)) throw new IllegalArgumentException("Nodo non presente");
        if(!this.isDirected()) throw new UnsupportedOperationException("Grafo non orientato");

        Set<GraphNode<L>> nodeSet = new HashSet<GraphNode<L>>();
        for(GraphNode<L> currentNode : this.adjacentLists.keySet()){
            for(GraphEdge<L> edge : this.adjacentLists.get(currentNode)){
                if(edge.getNode2().equals(node)) nodeSet.add(currentNode);
            }
        }

        return nodeSet;
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        HashSet<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>();

        for(Set<GraphEdge<L>> edge : this.adjacentLists.values()){
            for(GraphEdge<L> e : edge){
                edgeSet.add(e);
            }
        }

        return edgeSet;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        if(edge == null) throw new NullPointerException("Archi nulli non supportati");
        if(!this.adjacentLists.containsKey(edge.getNode1())) throw new IllegalArgumentException("Nodo di partenza non presente");
        if(!this.adjacentLists.containsKey(edge.getNode2())) throw new IllegalArgumentException("Nodo di arrivo non presente");
        if(this.isDirected() != edge.isDirected()) throw new IllegalArgumentException("Orientamento del grafo diverso da quello dell'arco");

        return this.adjacentLists.get(edge.getNode1()).add(edge);
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        throw new UnsupportedOperationException(
                "Rimozione degli archi non supportata");
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        if(edge == null) throw new NullPointerException("Archi nulli non supportati");
        if(!this.adjacentLists.containsKey(edge.getNode1())) throw new IllegalArgumentException("Nodo di partenza non presente");
        if(!this.adjacentLists.containsKey(edge.getNode2())) throw new IllegalArgumentException("Nodo di arrivo non presente");

        return this.adjacentLists.get(edge.getNode1()).contains(edge);
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi nulli non supportati");
        if(!this.adjacentLists.containsKey(node)) throw new IllegalArgumentException("Nodo non presente");

        return this.adjacentLists.get(node);
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        if(node == null) throw new NullPointerException("Nodi nulli non supportati");
        if(!this.adjacentLists.containsKey(node)) throw new IllegalArgumentException("Nodo non presente");

        Set<GraphEdge<L>> edgeSet = new HashSet<GraphEdge<L>>();

        for(Set<GraphEdge<L>> currentEdge : this.adjacentLists.values()){
            for(GraphEdge<L> edge : currentEdge){
                if(edge.getNode2().equals(node)) edgeSet.add(edge);
            }
        }

        return edgeSet;
    }

}
