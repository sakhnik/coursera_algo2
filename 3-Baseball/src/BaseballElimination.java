
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sakhnik
 */
public class BaseballElimination {

    private final int numberOfTeams;
    private final Map<String, Integer> teams;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] against;
    private final List<Bag<String>> certs;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {

        In in = new In(filename);
        numberOfTeams = in.readInt();
        teams = new HashMap<>(numberOfTeams);
        String[] names = new String[numberOfTeams];
        wins = new int[numberOfTeams];
        losses = new int[numberOfTeams];
        remaining = new int[numberOfTeams];
        against = new int[numberOfTeams][numberOfTeams];
        certs = new ArrayList<>();

        for (int i = 0; i < numberOfTeams; ++i) {
            String team = in.readString();
            teams.put(team, i);
            names[i] = team;
            wins[i] = in.readInt();
            losses[i] = in.readInt();
            remaining[i] = in.readInt();
            for (int j = 0; j < numberOfTeams; ++j) {
                against[i][j] = in.readInt();
            }
        }

        // A simple mapping:
        //   [0..numberOfTeams) -- team vertices
        //   [numberOfTeams..) -- game vertices
        //   the last two -- the source and the target
        // Not all the vertices are connected in a graph, but the mapping is simple
        int v = numberOfTeams * (numberOfTeams+1) + 2;
        int s = v - 1;
        int t = v - 2;
        for (int x = 0; x < numberOfTeams; ++x) {
            FlowNetwork G = new FlowNetwork(v);
            for (int i = 0; i < numberOfTeams; ++i) {
                if (i == x) {
                    continue;
                }
                double pot = wins[x] + remaining[x];
                for (int j = i+1; j < numberOfTeams; ++j) {
                    if (j == x) {
                        continue;
                    }
                    int k = (i+1)*numberOfTeams+j;
                    G.addEdge(new FlowEdge(s, k, against[i][j]));
                    G.addEdge(new FlowEdge(k, i, Double.POSITIVE_INFINITY));
                    G.addEdge(new FlowEdge(k, j, Double.POSITIVE_INFINITY));
                }
                G.addEdge(new FlowEdge(i, t, pot - wins[i]));
            }

            // Find maxflow/mincut
            FordFulkerson ff = new FordFulkerson(G, s, t);

            // Analyze the result
            for (FlowEdge e : G.adj(s)) {
                if (Double.compare(e.flow(), e.capacity()) != 0) {
                    Bag<String> cert = new Bag<>();
                    for (int i = 0; i < numberOfTeams; ++i) {
                        if (ff.inCut(i)) {
                            cert.add(names[i]);
                        }
                    }
                    certs.add(x, cert);
                    break;
                }
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return numberOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        return teams.keySet();
    }

    private int getIdx(String team) {
        Integer idx = teams.get(team);
        if (idx == null)
            throw new IllegalArgumentException();
        return idx;
    }

    // number of wins for given team
    public int wins(String team) {
        return wins[getIdx(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return losses[getIdx(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remaining[getIdx(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return against[getIdx(team1)][getIdx(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return certs.get(getIdx(team)) != null;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return certs.get(getIdx(team));
    }
}