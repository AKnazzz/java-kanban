package managers;

import tasks.Task;

class Node<T extends Task> {                                    // элемент-узел для двухсвязного списка
    private Task data;
    private Node<T> prev;
    private Node<T> next;

    public Node(Task data, Node<T> prev, Node<T> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public Task getTask() {
        return data;
    }

    public void setData(Task data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
}