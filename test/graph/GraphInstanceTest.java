/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //  add():
    //      vertex = present and not
    //  set():
    //      weight = zero or not
    //      vertex = present or not
    //      edge = present or not
    //  remove():
    //      vertex = present or not
    //      edges = none  or incoming or outgoing
    //  vertices():
    //      |vertices| = 0, 1, n
    //  sources():
    //      |sources| = 0, 1, n
    //      target = present or not
    //  targets():
    //      |targets| = 0, 1, n
    //      source = present or not
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testAdd()
    {
        Graph<String> testGraph = emptyInstance();
        assertTrue("add vertex not present in graph", testGraph.add("1"));
        assertFalse("add vertex present in graph", testGraph.add("1"));
    }
    
    @Test
    public void testSet()
    {
        Graph<String> testGraph = emptyInstance();
        assertEquals("source, target not present", 0, testGraph.set("1", "2", 5));
        assertEquals("update edge", 5, testGraph.set("1", "2", 10));
        assertEquals("source, target present", 0, testGraph.set("2", "1", 10));
        assertEquals("remove edge", 10, testGraph.set("1", "2", 0));
    }
    
    /**
     * 
     * @param origSet
     * @return a HashSet with the contents exactly that of origSet
     */
    public Set<String> getHashSet(Set<String> origSet)
    {
        Set<String> resultSet = new HashSet<>();
        
        for (String elem : origSet)
        {
            resultSet.add(elem);
        }
        
        return resultSet;
    }
    
    /**
     * 
     * @param origMap
     * @return a HashMap with contents exactly that of origMap
     */
    public Map<String, Integer> getHashMap(Map<String, Integer> origMap)
    {
        Map<String, Integer> resultMap = new HashMap<String ,Integer>();
        
        for (Map.Entry<String, Integer> pair : origMap.entrySet())
        {
            resultMap.put(pair.getKey(), pair.getValue());
        }
        
        return resultMap;
    }
    
    @Test
    public void testRemoveVerticesSourcesAndTargets()
    {
        // empty instance
        Graph<String> testGraph = emptyInstance();
        assertFalse("no vertex", testGraph.remove("1"));
        
        // both vertices present
        assertEquals("add edge", 0, testGraph.set("1", "2", 10));
        assertEquals("vertices", new HashSet<String>(Arrays.asList("1", "2")), getHashSet(testGraph.vertices()));
        
        // sources
        Map<String, Integer> sourceMap1 = new HashMap<>();
        sourceMap1.put("1", 10);
        assertEquals("sources", sourceMap1, getHashMap(testGraph.sources("2")));
        
        // targets
        Map<String, Integer> targetMap1 = new HashMap<>();
        targetMap1.put("2", 10);
        assertEquals("targets", targetMap1, getHashMap(testGraph.targets("1")));
        
        // only a single vertex present
        assertTrue("remove 1", testGraph.remove("1"));
        assertEquals("vertices", new HashSet<String>(Arrays.asList("2")), getHashSet(testGraph.vertices()));
        
        // sources and targets
        assertEquals("sources", Collections.emptyMap(), getHashMap(testGraph.sources("2")));
        assertEquals("targets", Collections.emptyMap(), getHashMap(testGraph.targets("2")));
        
        // no vertices present
        assertTrue("remove 2", testGraph.remove("2"));
        assertEquals("vertices", Collections.emptySet(), getHashSet(testGraph.vertices()));
    }
}
