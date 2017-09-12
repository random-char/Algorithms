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

        if (size == 1) {
            Node element = new Node();
            element.item = item;
            first = element;
            last = element;
            return;
        }

        Node newLast = new Node();
        newLast.item = item;
        newLast.prev = last;
        last.next = newLast;
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
            Node nextElement = element.next;
            first = nextElement;
            first.prev = null;
        } else if (element == last){
            Node prevElement = element.prev;
            last = prevElement;
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
        Node element = first;
        for (int i = 0; i < elementIndex; i++) {
            element = element.next;
        }
        return element.item;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node current = first;

            public boolean hasNext() {
                return current != null;
            }


            public Item next() {
                if (!hasNext()) throw new java.util.NoSuchElementException();

                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    public static void main(String[] args) {
    }
}