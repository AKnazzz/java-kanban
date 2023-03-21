package managers;

import tasks.Task;

class Node {                                    // элемент-узел для двухсвязного списка
    private Task data;
    private Node prev;
    private Node next;

    public Node(Task data, Node prev, Node next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public Task getTask() {
        return data;
    }

    public void setTask(Task data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}