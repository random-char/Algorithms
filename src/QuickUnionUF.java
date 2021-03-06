public class QuickUnionUF {

    private int[] id;

    public QuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    private boolean connected(int p, int q) {
        return findRoot(p) == findRoot(q);
    }

    private void union(int p, int q) {
        int pRoot = findRoot(p);
        int qRoot = findRoot(q);
        id[pRoot] = qRoot;
    }

    private int findRoot(int p) {
        if (id[p] != p) {
            return findRoot(id[p]);
        } else {
            return p;
        }
    }

    public static void main(String[] args) {
        QuickUnionUF quickUnionUF = new QuickUnionUF(Integer.parseInt(args[0]));
    }
}
