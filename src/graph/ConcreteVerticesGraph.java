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
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   adjacency list representation of a graph.
    // Representation invariant:
    //   every element in the adjacent list (both 'sources' and 'targets') are present in 'vertices'.
    //   adjacency list must not contain the same vertex.
    //   only one vertex each in vertices.
    //   weight from source = weight to target.
    // Safety from rep exposure:
    //   'vertices' is a private variable and never exposed.
    
    // TODO constructor
    
    // checkRep
    private boolean checkRep()
    {
        Set<Vertex> seenVertices = new HashSet<>();
        
        for (Vertex vertex : vertices)
        {
            if (seenVertices.contains(vertex))
            {
                return false;
            }
            
            seenVertices.add(vertex);
            
            for (Map.Entry<Vertex, Integer> sourceEntry : vertex.getSources().entrySet())
            {
                Vertex source = sourceEntry.getKey();
                int sourceWeight = sourceEntry.getValue();
                
                if (vertex.equals(source))
                {
                    return false;
                }
                
                if (!vertices.contains(source))
                {
                    return false;
                }
                
                if (source.getTargetWeight(vertex) != sourceWeight)
                {
                    return false;
                }
            }
            
            for (Map.Entry<Vertex, Integer> targetEntry : vertex.getTargets().entrySet())
            {
                Vertex target = targetEntry.getKey();
                int targetWeight = targetEntry.getValue();
                
                if (vertex.equals(target))
                {
                    return false;
                }
                
                if (!vertices.contains(target))
                {
                    return false;
                }
                
                if (target.getSourceWeight(vertex) != targetWeight)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override public boolean add(String vertex) {
        for (Vertex entry : vertices)
        {
            if (entry.getName().equals(vertex))
            {
                return false;
            }
        }
        
        vertices.add(new Vertex(vertex));
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        Vertex sourceVertex = null;
        Vertex targetVertex = null;
        
        if (weight == 0)
        {
            for (Vertex vertex : vertices)
            {
                if (vertex.getName().equals(source))
                {
                    sourceVertex = vertex;
                }
                
                if (vertex.getName().equals(target))
                {
                    targetVertex = vertex;
                }
            }
            
            int pastWeight = 0;
            
            if (sourceVertex != null && targetVertex != null)
            {
                pastWeight = sourceVertex.setTarget(targetVertex, 0);
                assert pastWeight == targetVertex.setSource(sourceVertex, 0);
            }
            
            checkRep();
            return pastWeight;
        }
        
        add(source);
        add(target);
        
        for (Vertex vertex : vertices)
        {
            if (vertex.getName().equals(source))
            {
                sourceVertex = vertex;
            }
            
            if (vertex.getName().equals(target))
            {
                targetVertex = vertex;
            }
        }
        
        int pastWeight;
        pastWeight = sourceVertex.setTarget(targetVertex, weight);
        assert pastWeight == targetVertex.setSource(sourceVertex, weight);
        checkRep();
        return pastWeight;
    }
    
    private void removeSources(Vertex source)
    {
        for (Vertex target : source.getTargets().keySet())
        {
            target.setSource(source, 0);
        }
    }
    
    private void removeTargets(Vertex target)
    {
        for (Vertex source : target.getSources().keySet())
        {
            source.setTarget(target, 0);
        }
    }
    
    @Override public boolean remove(String vertex) {
        for (Vertex entry : vertices)
        {
            if (entry.getName().equals(vertex))
            {
                removeSources(entry);
                removeTargets(entry);
                vertices.remove(entry);
                checkRep();
                return true;
            }
        }
        
        return false;
    }
    
    @Override public Set<String> vertices() {
        Set<String> resultSet = new HashSet<>();
        
        for (Vertex vertex : vertices)
        {
            resultSet.add(vertex.getName());
        }
        
        return resultSet;
    }
    
    private Map<String, Integer> reduceMap(Map<Vertex, Integer> origMap)
    {
        Map<String, Integer> resultMap = new HashMap<>();
        
        for (Map.Entry<Vertex, Integer> origEntry : origMap.entrySet())
        {
            resultMap.put(origEntry.getKey().getName(), origEntry.getValue());
        }
        
        return resultMap;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        for (Vertex vertex : vertices)
        {
            if (vertex.getName().equals(target))
            {
                return reduceMap(vertex.getSources()); 
            }
        }
        
        return null;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        for (Vertex vertex : vertices)
        {
            if (vertex.getName().equals(source))
            {
                return reduceMap(vertex.getTargets());
            }
        }
        
        return null;
    }
    
    // TODO toString()
    
}

/**
 * Represents a vertex along with the adjacent list (both 'sources' and 'targets')
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    // fields
    private final String name;
    private final Map<Vertex, Integer> sources;
    private final Map<Vertex, Integer> targets;
    
    // Abstraction function:
    //   Represents a vertex and its adjacent list.
    //   There are two types of adjacent vertices - sources and targets.
    //   Adjacent vertices are stored along with their weights.
    // Representation invariant:
    //   Weights of all neighbours are strictly greater than zero.
    // Safety from rep exposure:
    //   private fields.
    //   'vertex' is immutable.
    //   references not returned, but their copy is.
    
    // constructor
    public Vertex(String name)
    {
        this.name = name;
        sources = new HashMap<>();
        targets = new HashMap<>();
    }
    
    // checkRep
    private boolean checkRep()
    {
        for (Integer weight : sources.values())
        {
            if (weight <= 0)
            {
                return false;
            }
        }
        
        for (Integer weight : targets.values())
        {
            if (weight <= 0)
            {
                return false;
            }
        }
        
        return true;
    }
    
    // methods
    public String getName()
    {
        assert checkRep();
        return name;
    }
    
    public Map<Vertex, Integer> getSources()
    {
        assert checkRep();
        return new HashMap<Vertex, Integer>(sources);
    }
    
    public Map<Vertex, Integer> getTargets()
    {
        assert checkRep();
        return new HashMap<Vertex, Integer>(targets);
    }
    
    /**
     * 
     * @param source
     * @return weight of edge from source into the vertex, zero if not present
     */
    public int getSourceWeight(Vertex source)
    {        
        if (sources.containsKey(source))
        {
            assert checkRep();
            return sources.get(source);
        }
        else
        {
            assert checkRep();
            return 0;
        }
    }
    
    /**
     * 
     * @param target
     * @return weight of edge from vertex to target, zero if not present
     */
    public int getTargetWeight(Vertex target)
    {
        if (targets.containsKey(target))
        {
            assert checkRep();
            return targets.get(target);
        }
        else
        {
            assert checkRep();
            return 0;
        }
    }
    
    /**
     * 
     * @param source
     * @param weight if zero remove the edge otherwise update the edge to current value.
     * @return past weight of the edge if present, zero otherwise.
     */
    public int setSource(Vertex source, int weight)
    {
        int pastWeight = 0;
        
        if (sources.containsKey(source))
        {
            pastWeight = sources.get(source);
        }
        
        if (weight == 0)
        {
            sources.remove(source);
        }
        else
        {
            sources.put(source, weight);
        }
        
        assert checkRep();
        return pastWeight;
    }
    
    /**
     * 
     * @param target
     * @param weight if zero remove the edge otherwise update the edge to current value.
     * @return past weight of the edge if present, zero otherwise.
     */
    public int setTarget(Vertex target, int weight)
    {
        int pastWeight = 0;
        
        if (targets.containsKey(target))
        {
            pastWeight = targets.get(target);
        }
        
        if (weight == 0)
        {
            targets.remove(target);
        }
        else
        {
            targets.put(target, weight);
        }
        
        assert checkRep();
        return pastWeight;
    }
    
    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Vertex))
        {
            return false;
        }
        
        Vertex thatVertex = (Vertex)thatObject;
        assert checkRep();
        return thatVertex.getName().equals(name);
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    // TODO toString()
    
}
