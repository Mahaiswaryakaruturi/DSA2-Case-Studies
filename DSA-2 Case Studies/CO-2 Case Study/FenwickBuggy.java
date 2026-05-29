class FenwickBuggy {
    int[] bit;
    int n;

    FenwickBuggy(int n) {
        this.n = n;
        this.bit = new int[n + 1];
    }

    /** Return spend[1] + spend[2] + ... + spend[i]. */
    long prefixSum(int i) {
        long s = 0;
        while (i > 0) {
            s += bit[i];
            i -= (i & -i);   // FIXED: was (i & -i) + 1
        }
        return s;
    }

    /** Add `delta` to the element at index i. */
    void pointUpdate(int i, int delta) {
        while (i <= n) {
            bit[i] += delta;
            i += i & -i;
        }
    }

    public static void main(String[] args) {
        FenwickBuggy fen = new FenwickBuggy(15);

        // 16 elements: index 0 unused, index 1-15 = Jan 1 to Jan 15
        int[] spend = {0, 1200, 800, 0, 2400, 1500, 600, 0, 0, 3500, 0, 1100, 950, 700, 0, 0};

        for (int i = 1; i <= 15; i++) {
            fen.pointUpdate(i, spend[i]);
        }

        long p12 = fen.prefixSum(12);
        long p4  = fen.prefixSum(4);

        System.out.println("prefix(12) = " + p12);
        System.out.println("prefix(4)  = " + p4);
        System.out.println("Spend Jan 5-12 = Rs." + (p12 - p4));
    }
}