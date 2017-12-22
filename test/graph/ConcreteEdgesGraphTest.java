/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   empty, (disconnected, connected), (acyclic, cyclic), updated edge
    
    // tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   Call get methods after modifying original references.
    
    // tests for operations of Edge
    @Test
    public void testEdge()
    {
        String source = String.valueOf(1);
        String target = String.valueOf(2);
        int weight = 5;
        Edge edge = new Edge(source, target, weight);
        source = String.valueOf(3);
        target = String.valueOf(4);
        weight = 10;
        assertEquals("source", String.valueOf(1), edge.getSource());
        assertEquals("target", String.valueOf(2), edge.getTarget());
        assertEquals("weight", 5, edge.getWeight());
    }
    
}
