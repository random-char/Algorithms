public class QuickUnionUF {

    private int[] id;

    public QuickUnionUF(int N) {
        this.id = new int[N];
        for (int i = 0; i < N; i++) {
            this.id[i] = i;
        }
    }

    public boolean connected(int p, int q) {
        return this.id[p] == this.id[q];
    }

    public void union(int p, int q) {
        int pid = this.id[p];
        int qid = this.id[q];
        for (int i = 0; i < this.id.length; i++) {
            if (this.id[i] == pid) {
                this.id[i] = qid;
            }
        }
    }

    public static void main(String[] args) {
        QuickUnionUF quickUnionUF = new QuickUnionUF(Integer.parseInt(args[0]));
    }
}
