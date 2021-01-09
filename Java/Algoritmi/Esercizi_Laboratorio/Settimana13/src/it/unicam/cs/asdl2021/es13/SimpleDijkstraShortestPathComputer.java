package it.unicam.cs.asdl2021.es13;

import java.util.*;

/**
 * Gli oggetti di questa classe sono calcolatori di cammini minimi con sorgente
 * singola su un certo grafo orientato e pesato dato. Il grafo su cui lavorare
 * deve essere passato quando l'oggetto calcolatore viene costruito e non può
 * contenere archi con pesi negativi. Il calcolatore implementa il classico
 * algoritmo di Dijkstra per i cammini minimi con sorgente singola utilizzando
 * una coda con priorità che estrae il minimo in tempo lineare rispetto alla
 * lunghezza della coda. In questo caso il tempo di esecuzione dell'algoritmo di
 * Dijkstra è {@code O(n^2)} dove {@code n} è il numero dei nodi del grafo. Una
 * implementazione più efficiente richiederebbe una coda di priorità che
 * realizza le operazioni di estrazione del minimo e di decreasePriority in
 * tempo logaritmico.
 * 
 * @author Luca Tesei
 *
 * @param <L>
 *                il tipo delle etichette dei nodi del grafo
 */
public class SimpleDijkstraShortestPathComputer<L> {

    private Graph<L> graph;

    private GraphNode<L> source;

    private PriorityQueue<L> queue;
    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * orientato e pesato privo di pesi negativi.
     * 
     * @param graph
     *                  il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException
     *                                      se il grafo passato è nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato è vuoto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è orientato
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è pesato,
     *                                      cioè esiste almeno un arco il cui
     *                                      peso è {@code Double.NaN}.
     * @throws IllegalArgumentException
     *                                      se il grafo passato contiene almeno
     *                                      un peso negativo.
     */
    public SimpleDijkstraShortestPathComputer(Graph<L> graph) {
        if(graph == null) throw new NullPointerException("Grafo nullo");
        if(graph.isEmpty()) throw new IllegalArgumentException("Grafo vuoto");
        if(!graph.isDirected()) throw new IllegalArgumentException("Grafo non orientanto");
        if(!this.checkGraph(graph)) throw new IllegalArgumentException("Pesi del grafo non validi");

        this.graph = graph;
        this.source = null;
        this.queue = new PriorityQueue<L>();
    }

    /**
     * Inizializza le informazioni necessarie associate ai nodi del grafo
     * associato a questo calcolatore ed esegue un algoritmo per il calcolo dei
     * cammini minimi a partire da una sorgente data.
     * 
     * @param sourceNode
     *                       il nodo sorgente da cui calcolare i cammini minimi
     *                       verso tutti gli altri nodi del grafo
     * @throws NullPointerException
     *                                      se il nodo passato è nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il nodo passato non esiste nel
     *                                      grafo associato a questo calcolatore
     * @throws IllegalStateException
     *                                      se il calcolo non può essere
     *                                      effettuato per via dei valori dei
     *                                      pesi del grafo, ad esempio se il
     *                                      grafo contiene cicli di peso
     *                                      negativo.
     */
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        if(sourceNode == null) throw new NullPointerException("Nodo nullo");
        if(!this.graph.containsNode(sourceNode)) throw new IllegalArgumentException("Nodo non presente");
        if(!this.checkGraph(this.graph)) throw new IllegalStateException("Pesi del grafo non validi");

        this.source = sourceNode;
        this.source.setIntegerDistance(0);

        Set<GraphNode<L>> settleNodes = new HashSet<>();
        Set<GraphNode<L>> unsettledNodes = new HashSet<>();
        unsettledNodes.add(this.source);

        while(!unsettledNodes.isEmpty()){
            GraphNode<L> currentNode = this.getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            for(GraphNode<L> adjacentNode : this.graph.getAdjacentNodesOf(currentNode)){
                if(!settleNodes.contains(adjacentNode)){
                }
            }
        }
    }

    /**
     * Determina se è stata invocata almeno una volta la procedura di calcolo
     * dei cammini minimi a partire da un certo nodo sorgente specificato.
     * 
     * @return true, se i cammini minimi da un certo nodo sorgente sono stati
     *         calcolati almeno una volta da questo calcolatore
     */
    public boolean isComputed() {
        return !(this.source == null);
    }

    /**
     * Restituisce il nodo sorgente specificato nell'ultima chiamata effettuata
     * a {@code computeShortestPathsFrom(GraphNode<L>)}.
     * 
     * @return il nodo sorgente specificato nell'ultimo calcolo dei cammini
     *         minimi effettuato
     * 
     * @throws IllegalStateException
     *                                   se non è stato eseguito nemmeno una
     *                                   volta il calcolo dei cammini minimi a
     *                                   partire da un nodo sorgente
     */
    public GraphNode<L> getLastSource() {
        if(this.source == null) throw new IllegalStateException("Calcolo cammini minimi non eseguito");
        return this.source;
    }

    /**
     * Restituisce il grafo su cui opera questo calcolatore.
     * 
     * @return il grafo su cui opera questo calcolatore
     */
    public Graph<L> getGraph() {
        return this.graph;
    }

    /**
     * Restituisce una lista di archi dal nodo sorgente dell'ultimo calcolo di
     * cammini minimi al nodo passato. Tale lista corrisponde a un cammino
     * minimo tra il nodo sorgente e il nodo target passato.
     * 
     * @param targetNode
     *                       il nodo verso cui restituire il cammino minimo
     *                       dalla sorgente
     * @return la lista di archi corrispondente al cammino minimo; la lista è
     *         vuota se il nodo passato è il nodo sorgente. Viene restituito
     *         {@code null} se il nodo passato non è raggiungibile dalla
     *         sorgente
     * 
     * @throws NullPointerException
     *                                      se il nodo passato è nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il nodo passato non esiste
     * 
     * @throws IllegalStateException
     *                                      se non è stato eseguito nemmeno una
     *                                      volta il calcolo dei cammini minimi
     *                                      a partire da un nodo sorgente
     * 
     */
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        if(targetNode == null) throw new NullPointerException("Nodo nullo");
        if(!this.graph.containsNode(targetNode)) throw new IllegalArgumentException("Nodo non presente");
        if(this.source == null) throw new IllegalStateException("Calcolo cammini minimi non eseguito");

        return null;
    }

    /**
     * Genera una stringa di descrizione di un path riportando i nodi
     * attraversati e i pesi degli archi. Nel caso di cammino vuoto genera solo
     * la stringa {@code "[ ]"}.
     * 
     * @param path
     *                 un cammino minimo
     * @return una stringa di descrizione del cammino minimo
     * @throws NullPointerException
     *                                  se il cammino passato è nullo
     */
    public String printPath(List<GraphEdge<L>> path) {
        if (path == null)
            throw new NullPointerException(
                    "Richiesta di stampare un path nullo");
        if (path.isEmpty())
            return "[ ]";
        // Costruisco la stringa
        StringBuffer s = new StringBuffer();
        s.append("[ " + path.get(0).getNode1().toString());
        for (int i = 0; i < path.size(); i++)
            s.append(" -- " + path.get(i).getWeight() + " --> "
                    + path.get(i).getNode2().toString());
        s.append(" ]");
        return s.toString();
    }

    private boolean checkGraph(Graph<L> g){
        for(GraphEdge<L> edge : g.getEdges()){
            if(!edge.hasWeight() || edge.getWeight() < 0) return false;
        }
        return true;
    }

    private GraphNode<L> getLowestDistanceNode(Set<GraphNode<L>> unsettledNodes) {
        return null;
    }
}
