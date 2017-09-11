import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first, last;

    private class Node {
        Item item;
        Node next;
    }


    public Deque() {

    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    private void addTheOnlyElement(Item item) {
        Node element = new Node();
        element.item = item;
        first = element;
        last = element;
    }

    public void addFirst(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();

        size++;

        if (size == 1) {
            addTheOnlyElement(item);
            return;
        }

        Node oldFirst = first;
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = oldFirst;
        first = newFirst;
    }

    public void addLast(Item item) {
        if (item == null) throw new java.lang.IllegalArgumentException();

        size++;

        if (size == 1) {
            addTheOnlyElement(item);
            return;
        }

        Node newLast = new Node();
        newLast.item = item;
        last.next = newLast;
        last = newLast;
    }

    private Item removeTheOnlyElement() {
        Item item = first.item;
        first = null;
        last = null;
        return item;
    }

    public Item removeFirst() {
        if (size == 0) throw new java.util.NoSuchElementException();

        size--;

        if (size == 0) {
            return removeTheOnlyElement();
        }

        Item firstItem = first.item;
        first = first.next;
        return firstItem;
    }

    public Item removeLast() {
        if (size == 0) throw new java.util.NoSuchElementException();

        size--;

        if (size == 0) {
            return removeTheOnlyElement();
        }

        Item lastItem = last.item;
        last = null;
        return lastItem;
    }

    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node current;

            public boolean hasNext() {
                return current.next != null;
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