public class WeightedQuickUnionUF {

    private int[] id, size;

    public WeightedQuickUnionUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private boolean connected(int p, int q) {
        return findRoot(p) == findRoot(q);
    }

    private void union(int p, int q) {
        int pRoot = findRoot(p);
        int qRoot = findRoot(q);
        if (size[p] > size[q]) {
            id[qRoot] = pRoot;
            size[p] += size[q];
        } else {
            id[pRoot] = qRoot;
            size[q] += size[p];
        }
    }

    private int findRoot(int p) {
        if (id[p] != p) {
            //adding path compression
            id[p] = id[id[p]];
            return findRoot(id[p]);
        } else {
            return p;
        }
    }

    public static void main(String[] args) {
        WeightedQuickUnionUF quickUnionUF = new WeightedQuickUnionUF(Integer.parseInt(args[0]));
    }
}
