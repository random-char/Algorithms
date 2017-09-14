import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Node first, last;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int orderIndex = 0;
        private int[] order = new int[size];

        public RandomizedQueueIterator() {
            if (size > 0) order[0] = 0;
            for (int i = 1; i < size; i++) {
                order[i] = i;
                int changeBy = StdRandom.uniform(i + 1);
                int aux = order[i];
                order[i] = order[changeBy];
                order[changeBy] = aux;
            }
        }

        public boolean hasNext() {
            return orderIndex < size;
        }


        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            return sample(order[orderIndex++]);
        }
    }

    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();

        size++;

        Node newLast = new Node();
        newLast.item = item;

        if (size == 1) {
            first = newLast;
        } else {
            newLast.prev = last;
            last.next = newLast;
        }

        last = newLast;
    }

    public Item dequeue() {
        if (size == 0) throw new java.util.NoSuchElementException();

        int elementIndex = StdRandom.uniform(size--);

        Node element = first;
        for (int i = 0; i < elementIndex; i++) {
            element = element.next;
        }

        Item item = element.item;

        if (element == first && element == last) {
            first = null;
            last = null;
        } else  if (element == first) {
            first = element.next;
            first.prev = null;
        } else if (element == last) {
            last = element.prev;
            last.next = null;
        } else {
            Node prevElement = element.prev;
            Node nextElement = element.next;
            prevElement.next = nextElement;
            nextElement.prev = prevElement;
        }

        return item;
    }

    public Item sample() {
        if (size == 0) throw new java.util.NoSuchElementException();
        int elementIndex = StdRandom.uniform(size);
        return sample(elementIndex);
    }

    private Item sample(int index) {
        Node element = first;
        for (int i = 0; i < index; i++) {
            element = element.next;
        }
        return element.item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args) {
    }
}