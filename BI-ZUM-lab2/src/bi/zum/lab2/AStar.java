package bi.zum.lab2;

import bi.zum.lab1.PathRecSearch;
import bi.zum.lab2.util.Euclidean;
import bi.zum.lab2.util.ZumPriorityQueue;
import cz.cvut.fit.zum.api.AbstractAlgorithm;
import cz.cvut.fit.zum.api.InformedSearch;
import cz.cvut.fit.zum.api.Node;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Tomáš Řehořek
 */
@ServiceProvider(service = AbstractAlgorithm.class)
public class AStar extends PathRecSearch implements InformedSearch  {
    
    private ZumPriorityQueue<Node> open;
    private HashSet<Node> closed;
    
    @Override
    public String getName() {
        return "A*";
    }
    
    @Override
    public List<Node> findPath(Node startNode, Node endNode) {

        open = new ZumPriorityQueue<Node>();
        closed = new HashSet<Node>();
        prev = new HashMap<Node, Node>();
        
        Map<Node, Double> dist = new HashMap<Node, Double>();
        
        open.enqueue(startNode, 0);
        dist.put(startNode, 0.0);
        
        while ( !open.isEmpty() ) {
            Node current = open.dequeue();
            
            if ( current.isTarget() ) {
                return buildPath(current);
            }
            
            for ( Node y : current.expand() ) {
                if ( !closed.contains(y) ) {
                    if ( !dist.containsKey(y) ){
                        dist.put(y, dist.get(current) + Euclidean.distance(y, current));
                        prev.put(y, current);
                        open.enqueue(y, dist.get(y) + Euclidean.distance(y, endNode));
                    } else {
                        double alternative = dist.get(current) + Euclidean.distance(y, current);
                        if ( alternative < dist.get(y) ) {
                            prev.put(y, current);
                            dist.replace(y, alternative);
                            
                            if ( open.contains(y) ) {
                                open.updateKey(y, alternative+Euclidean.distance(y, endNode));
                            } else {
                                open.enqueue(y, alternative+Euclidean.distance(y, endNode));
                            }
                        }
                    }
                }
            }            
            
            closed.add(current);
        }
        
        return null;
    }

}