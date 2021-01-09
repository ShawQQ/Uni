package it.unicam.cs.asdl2021.es13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Classe singoletto che implementa l'algoritmo di Kruskal per trovare un
 * Minimum Spanning Tree di un grafo non orientato, pesato e con pesi non
 * negativi.
 * 
 * @author Template: Luca Tesei, Implementation: INSERIRE NOME E COGNOME DELLO
 *         STUDENTE - INSERIRE ANCHE L'EMAIL xxxx@studenti.unicam.it
 * 
 * @param <L>
 *                etichette dei nodi del grafo
 *
 */
public class KruskalMSP<L> {

    /*
     * Struttura dati per rappresentare gli insiemi disgiunti utilizzata
     * dall'algoritmo di Kruskal.
     */
    private ArrayList<HashSet<GraphNode<L>>> disjointSets;

    /**
     * Costruisce un calcolatore di un albero di copertura minimo che usa
     * l'algoritmo di Kruskal su un grafo non orientato e pesato.
     */
    public KruskalMSP() {
        this.disjointSets = new ArrayList<HashSet<GraphNode<L>>>();
    }

    /**
     * Utilizza l'algoritmo goloso di Kruskal per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non
     * negativi. L'albero restituito non è radicato, quindi è rappresentato
     * semplicemente con un sottoinsieme degli archi del grafo.
     * 
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @return l'insieme degli archi del grafo g che costituiscono l'albero di
     *         copertura minimo trovato
     * @throw NullPointerException se il grafo g è null
     * @throw IllegalArgumentException se il grafo g è orientato, non pesato o
     *        con pesi negativi
     */
    public Set<GraphEdge<L>> computeMSP(Graph<L> g) {
        if(g == null) throw new NullPointerException("Grafo nullo");
        if(!this.checkGraph(g)) throw new IllegalArgumentException("Grafo non valido");

        Set<GraphEdge<L>> result = new HashSet<GraphEdge<L>>();
        for(GraphNode<L> node : g.getNodes()){
            HashSet<GraphNode<L>> set = new HashSet<GraphNode<L>>();
            node.setPriority(0);
            node.setPrevious(node);
            set.add(node);
            this.disjointSets.add(set);
        }
        for(GraphEdge<L> edge : g.getEdges()){
            if(!this.findSet(edge.getNode1()).equals(this.findSet(edge.getNode2()))){
                result.add(edge);
                this.union(edge.getNode1(), edge.getNode2());
            }
        }
        return result;
    }

    private boolean checkGraph(Graph<L> g) {
        if(!g.isDirected()) return false;
        for(GraphEdge<L> edge : g.getEdges()){
            if(!edge.hasWeight() || edge.getWeight() < 0) return false;
        }
        return true;
    }

    private void union(GraphNode<L> firstNode, GraphNode<L> secondNode) {
        if(firstNode.getPriority() > secondNode.getPriority()){
            secondNode.setPrevious(firstNode);
        }else{
            firstNode.setPrevious(secondNode);

            if(firstNode.getPriority() == secondNode.getPriority()){
                secondNode.setPriority(secondNode.getPriority() + 1);
            }
        }
    }

    private GraphNode<L> findSet(GraphNode<L> node) {
        if(!node.getPrevious().equals(node)){
            node.setPrevious(this.findSet(node.getPrevious()));
        }

        return node.getPrevious();
    }

}
