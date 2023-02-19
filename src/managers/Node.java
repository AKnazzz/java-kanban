package managers;

class Node <Task> {                                    // элемент-узел для двухсвязного списка
    private Task data;
    private Node prev;
    private Node next;

   public Node(Task data, Node<Task> prev, Node<Task> next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public Task getData() {
        return data;
    }

    public void setData(Task data) {
        this.data = data;
    }

    public Node<Task> getNext() {
        return next;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }
}