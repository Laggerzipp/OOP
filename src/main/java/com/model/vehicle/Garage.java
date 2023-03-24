package com.model.vehicle;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class Garage<T extends Vehicle> implements Iterable<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Garage() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(T vehicle) {
        if (head == null) {
            head = new Node<>(null, vehicle, null);
            tail = new Node<>(null, vehicle, null);
        } else {
            Node<T> node = new Node<>(tail, vehicle, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void addFirst(T vehicle) {
        Node<T> node;
        if (head == null) {
            head = new Node<>(null, vehicle, null);
            tail = new Node<>(null, vehicle, null);
        } else {
            node = new Node<>(null, vehicle, head);
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void add(int index, T vehicle) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0");
        }
        if (index == 0) {
            addFirst(vehicle);
        }
        if (index == size) {
            add(vehicle);
        } else {
            Node<T> prevNode = getNode(index - 1);
            Node<T> node = new Node<>(prevNode, vehicle, prevNode.next);
            prevNode.next.prev = node;
            prevNode.next = node;
        }
        size++;
    }

    public void remove(int index) {
        if (size == 0) {
            throw new IllegalStateException("Cant remove from empty garage");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0 and more then " + size);
        } else if (index == 0) {
            head = head.next;
            head.prev = null;
        } else if (index == size - 1) {
            tail = tail.prev;
            tail.next = null;
        } else {
            Node<T> prevNode = getNode(index - 1);
            Node<T> nextNode = getNode(index + 1);
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
    }

    private Node<T> getNode(int index) {
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }

    public T get(int index) {
        return getNode(index).vehicle;
    }

    public int getSize() {
        return this.size;
    }

    public int getRestyling(int index) {
        return getNode(index).restylingNumber;
    }

    public Date getCreationDate(int index) {
        return getNode(index).creationDate;
    }


    public void set(int index, T vehicle) {
        if (size == 0) {
            throw new IllegalStateException("Cant update empty garage");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index cannot be less than 0 and more then \" + size");
        } else if (index == 0) {
            Node<T> node = new Node<>(null, vehicle, head.next);
            head.next.prev = node;
            head = node;
        } else if (index == size - 1) {
            Node<T> node = new Node<>(tail.prev, vehicle, null);
            tail.prev.next = node;
            tail = node;
        } else {
            Node<T> prevNode = getNode(index - 1);
            Node<T> nextNode = getNode(index + 1);
            Node<T> node = new Node<>(prevNode, vehicle, nextNode);
            prevNode.next = node;
            nextNode.prev = node;
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Garage: \n");
        for (Node<T> current = head; current != null; current = current.next) {
            builder.append(current.vehicle)
                    .append("; creation date=").append(current.creationDate).append("\n");
        }
        return builder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new GarageIterator();
    }

    private class GarageIterator implements Iterator<T> {
        private Node<T> currentNode;

        public GarageIterator() {
            currentNode = head;
        }

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            T vehicle = currentNode.vehicle;
            currentNode = currentNode.next;
            return vehicle;
        }
    }

    private static class Node<T extends Vehicle> {
        private final T vehicle;
        private Node<T> prev;
        private Node<T> next;

        int restylingNumber;
        Date creationDate;

        Node(Node<T> prev, T vehicle, Node<T> next) {
            this.prev = prev;
            this.vehicle = vehicle;
            this.next = next;
            this.creationDate = new Date();
            this.restylingNumber = new Random().nextInt(5);
        }
    }
}
