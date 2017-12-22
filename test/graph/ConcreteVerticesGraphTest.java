/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //  getSourceWeight():
    //      present, not present
    //  getTargetWeight():
    //      present, not present
    //  setSource():
    //      add, update, remove
    //  setTarget():
    //      add, update, remove
    //  getSources():
    //      0, n
    //  getTargets():
    //      0, n
    
    // tests for operations of Vertex
    @Test
    public void testVertex()
    {
        Vertex v1 = new Vertex("1");
        Vertex v2 = new Vertex("2");
        
        // add
        v1.setTarget(v2, 10);
        v2.setSource(v1, 10);
        v2.setTarget(v1, 10);
        v1.setSource(v2, 10);
        
        // check sources and targets
        assertEquals("two cycle graph", 1, v1.getSources().size());
        assertEquals("two cycle graph", 1, v2.getSources().size());
        assertEquals("two cycle graph", 1, v1.getTargets().size());
        assertEquals("two cycle graph", 1, v2.getTargets().size());
        
        // check weights
        assertEquals("two cycle graph", 10, v1.getTargetWeight(v2));
        assertEquals("two cycle graph", 10, v2.getTargetWeight(v1));
        
        // update
        v2.setTarget(v1, 15);
        v1.setSource(v2, 15);
        
        // check sources and targets
        assertEquals("two cycle graph", 1, v1.getSources().size());
        assertEquals("two cycle graph", 1, v2.getSources().size());
        assertEquals("two cycle graph", 1, v1.getTargets().size());
        assertEquals("two cycle graph", 1, v2.getTargets().size());
        
        // check weights
        assertEquals("two cycle graph", 10, v1.getTargetWeight(v2));
        assertEquals("two cycle graph", 15, v2.getTargetWeight(v1));
        
        // remove
        v1.setTarget(v2, 0);
        v2.setSource(v1, 0);
        
        // check sources and targets
        assertEquals("two cycle graph", 1, v1.getSources().size());
        assertEquals("two cycle graph", 0, v2.getSources().size());
        assertEquals("two cycle graph", 0, v1.getTargets().size());
        assertEquals("two cycle graph", 1, v2.getTargets().size());
        
        // check weights
        assertEquals("two cycle graph", 0, v1.getTargetWeight(v2));
        assertEquals("two cycle graph", 15, v2.getTargetWeight(v1));
    }
    
}
