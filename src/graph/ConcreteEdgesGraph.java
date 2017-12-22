/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   graph representation in edge format.
    // Representation invariant:
    //   'source' and 'target' of every 'edge' is in 'vertices'.
    //   ('source', 'target') is unique in 'edges'.
    // Safety from rep exposure:
    //   'vertices' and 'edges' are unavailable to clients from any method.
    //   they are initialized locally within the method.
    
    // TODO constructor
    
    // checkRep
    private boolean checkRep()
    {
        Set<Edge> edgeSet = new HashSet<>();
        
        for (Edge edge : edges)
        {
            if (!vertices.contains(edge.getSource()) 
                    || !vertices.contains(edge.getTarget())
                    || edgeSet.contains(edge))
            {
                return false;
            }
            
            edgeSet.add(edge);
        }
        
        return true;
    }
    
    @Override public boolean add(String vertex) {
        if (vertices.contains(vertex))
        {
            return false;
        }
        
        vertices.add(vertex);
        assert checkRep();
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {        
        for (Edge edge : edges)
        {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target))
            {
                int pastWeight = edge.getWeight();
                
                if (weight == 0)
                {
                    edges.remove(edge);
                }
                else
                {
                    edge.setWeight(weight);
                }
                
                assert checkRep();
                return pastWeight;
            }
        }
        
        if (weight != 0)
        {
            add(source);
            add(target);
            edges.add(new Edge(source, target, weight));
        }
        
        assert checkRep();
        return 0;
    }
    
    private void removeSourceEdges(String target)
    {
        List<Edge> toBeRemoved = new ArrayList<>();
        
        for (Edge edge : edges)
        {
            if (edge.getTarget().equals(target))
            {
                toBeRemoved.add(edge);
            }
        }
        
        for (Edge edge : toBeRemoved)
        {
            edges.remove(edge);
        }
    }
    
    private void removeTargetEdges(String source)
    {
        List<Edge> toBeRemoved = new ArrayList<>();
        
        for (Edge edge : edges)
        {
            if (edge.getSource().equals(source))
            {
                toBeRemoved.add(edge);
            }
        }
        
        for (Edge edge : toBeRemoved)
        {
            edges.remove(edge);
        }
    }
    
    @Override public boolean remove(String vertex) {
        removeSourceEdges(vertex);
        removeTargetEdges(vertex);
        assert checkRep();
        return vertices.remove(vertex);
    }
    
    @Override public Set<String> vertices() {
        assert checkRep();
        return new HashSet<>(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        
        for (Edge edge : edges)
        {
            if (edge.getTarget().equals(target))
            {
                resultMap.put(edge.getSource(), edge.getWeight());
            }
        }
        
        return resultMap;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        
        for (Edge edge : edges)
        {
            if (edge.getSource().equals(source))
            {
                resultMap.put(edge.getTarget(), edge.getWeight());
            }
        }
        
        return resultMap;
    }
    
    // TODO toString()
    
}

/**
 * Represents an edge. Both vertices represented as strings.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    
    private String source;
    private String target;
    private int weight;
    
    // Abstraction function:
    //   represents an edge from 'source' to 'target' edge of weight 'weight'
    // Representation invariant:
    //   'source' is not equal to 'target'. 
    //   'weight' is strictly greater than zero.
    // Safety from rep exposure:
    //   All of 'source', 'target' and 'weight' are immutable objects
    
    // constructor
    public Edge(String source, String target, int weight)
    {
        this.source = source;
        this.target = target;
        this.weight = weight;
        assert checkRep();
    }
    
    public Edge(Edge thatEdge)
    {
        source = thatEdge.getSource();
        target = thatEdge.getTarget();
        weight = thatEdge.getWeight();
    }
    
    // checkRep
    private boolean checkRep()
    {
        return !source.equals(target) && weight > 0;
    }
    
    // methods
    public String getSource()
    {
        assert checkRep();
        return source;
    }
    
    public String getTarget()
    {
        assert checkRep();
        return target;
    }
    
    public int getWeight()
    {
        assert checkRep();
        return weight;
    }
    
    public void setWeight(int weight) {
        this.weight = weight;
        assert checkRep();
    }
    
    @Override
    public boolean equals(Object thatObject)
    {
        if (!(thatObject instanceof Edge))
        {
            return false;
        }
        
        Edge thatEdge = (Edge)thatObject;
        assert checkRep();
        return thatEdge.getSource().equals(source) && thatEdge.getTarget().equals(target);
    }
    
    @Override
    public int hashCode() {
        return source.hashCode()^target.hashCode();
    }
    
    // toString()
    /**
     * Separates the three fields using ':'.
     */
    @Override
    public String toString()
    {
        return source+":"+target+":"+String.valueOf(weight);
    }
    
}
