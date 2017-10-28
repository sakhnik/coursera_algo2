
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class SAP {

    private final Digraph g;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException();
        g = new Digraph(G);
    }
    
    private static class Impl {
        private int len;
        private int vert;

        Impl(Digraph g, int v, int w) {
            checkIndex(g, v);
            checkIndex(g, w);
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
            BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
            search(g, bfsV, bfsW);
        }

        Impl(Digraph g, Iterable<Integer> v, Iterable<Integer> w) {
            checkIndices(g, v);
            checkIndices(g, w);
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
            BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
            search(g, bfsV, bfsW);
        }

        private void checkIndex(Digraph g, int v) {
            if (v < 0 || v >= g.V())
                throw new IndexOutOfBoundsException();
        }

        private void checkIndices(Digraph g, Iterable<Integer> v) {
            for (int i : v) {
                if (i < 0 || i >= g.V())
                    throw new IndexOutOfBoundsException();
            }
        }

        private void search(Digraph g, BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
            int p = Integer.MAX_VALUE;
            int x = -1;

            for (int i = 0; i < g.V(); ++i) {
                if (bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
                    int q = bfsV.distTo(i) + bfsW.distTo(i);
                    if (q < p) {
                        p = q;
                        x = i;
                    }
                }
            }

            len =  p != Integer.MAX_VALUE ? p : -1;
            vert = x;
        }

        int getLength() {
            return len;
        }

        int getVert() {
            return vert;
        }
    };

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return (new Impl(g, v, w)).getLength();
    }
    
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return (new Impl(g, v, w)).getVert();
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return (new Impl(g, v, w)).getLength();
    }
    
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return (new Impl(g, v, w)).getVert();
    }
}
