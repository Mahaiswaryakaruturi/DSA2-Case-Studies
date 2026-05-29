import java.util.*;

class UnionFindNaive {
    int[] parent;

    UnionFindNaive(int n) {
        parent = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    // No path compression
    int find(int x) {
        while (parent[x] != x) {
            x = parent[x];
        }
        return x;
    }

    // No union by rank
    boolean union(int x, int y) {
        int rx = find(x);
        int ry = find(y);

        if (rx == ry)
            return false;

        parent[rx] = ry;
        return true;
    }
}

public class KruskalNaive {

    static List<String> vertices =
            Arrays.asList("S", "E", "K", "W", "M", "H", "Y");

    static List<int[]> kruskalNaive(int n, int[][] edges) {

        // Sort edges by weight
        Arrays.sort(edges, Comparator.comparingInt(e -> e[0]));

        UnionFindNaive uf = new UnionFindNaive(n);

        List<int[]> mst = new ArrayList<>();

        for (int[] e : edges) {

            int weight = e[0];
            int u = e[1];
            int v = e[2];

            if (uf.union(u, v)) {
                mst.add(e);
            }
        }

        return mst;
    }

    public static void main(String[] args) {

        int n = 7;

        // {weight, u, v}

        int[][] edges = {
                {4, 0, 1},   // S-E
                {5, 2, 3},   // K-W
                {6, 3, 0},   // W-S
                {7, 4, 1},   // M-E
                {9, 5, 6},   // H-Y
                {9, 4, 6},   // M-Y

                // Extra edges
                {10, 0, 2},
                {11, 1, 3},
                {12, 2, 4},
                {13, 3, 5},
                {14, 1, 6},
                {15, 0, 5}
        };

        List<int[]> mst = kruskalNaive(n, edges);

        int totalCost = 0;

        System.out.println("Resulting MST edges:");

        for (int[] e : mst) {

            String u = vertices.get(e[1]);
            String v = vertices.get(e[2]);

            System.out.println(u + " - " + v + " : ₹" + e[0] + " cr");

            totalCost += e[0];
        }

        System.out.println("\nTotal MST Cost = ₹" + totalCost + " crore");
    }
}