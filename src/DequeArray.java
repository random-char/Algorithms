import java.util.Iterator;

public class DequeArray<Item> implements Iterable<Item> {

    private Item[] items;

    private int first = 1;
    private int current = 1;

    public DequeArray() {
        items = (Item[]) new Object[3];
    }

    private void resizeHead() {
        int headLength = size() / 2;
        Item[] copy = (Item[]) new Object[headLength + (items.length - first - 1)];

        int oldFirst = first;
        int oldCurrent = current;

        first = headLength - 1;
        current = first + size();

        for (int i = 0, numElements = oldCurrent - oldFirst; i < numElements; i++) {
            copy[first + i] = items[oldFirst + i];
        }

        items = copy;
    }

    private void resizeTail() {
        int tailLength = size() / 2;
        Item[] copy = (Item[]) new Object[(items.length - current) + tailLength];

        for (int i = first; i < current; i++) {
            copy[i] = items[i];
        }

        items = copy;
    }

    public boolean isEmpty() {
        return first == current;
    }

    public int size() {
        return current - first;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (first <= 0) {
            resizeHead();
        }
        items[first--] = item;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (current >= items.length) {
            resizeTail();
        }
        items[current++] = item;
    }

    public Item removeFirst() {
        if (!(first >= 0 && first < items.length)) throw new java.util.NoSuchElementException();
        Item item = items[first];
        items[first++] = null;
        if (first > items.length / 4) resizeHead();
        return item;
    }

    public Item removeLast() {
        if (current < 0 || current >= items.length) throw new java.util.NoSuchElementException();
        Item item = items[--current];
        items[current] = null;
        if (current < items.length * 3 / 4) resizeTail();
        return item;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int localCurrent = first;

            public boolean hasNext() {
                return localCurrent < items.length && items[localCurrent + 1] != null;
            }

            public Item next() {
                if (hasNext()) {
                    return items[localCurrent++];
                } else {
                    throw new java.util.NoSuchElementException();
                }

            }
        };
    }

    public static void main(String[] args) {
        DequeArray<Integer> deque = new DequeArray<>();
        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);

        for (int i = 0; i < 3; i++) {
            System.out.println(deque.removeFirst());
        }
    }
}