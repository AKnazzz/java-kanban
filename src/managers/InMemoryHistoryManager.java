package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> customLinkedMap = new HashMap<>();
    private Node head;
    private Node tail;


    @Override
    public void add(Task task) {
        if (customLinkedMap.containsKey(task.getId())) {
            removeNode(customLinkedMap.get(task.getId()));
        }

        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }


    public void linkLast(Task task) {                      // метод для добавления задачи в конец списка
        final Node exTail = tail;
        final Node newNode = new Node(task, exTail, null);
        tail = newNode;

        if (exTail == null) {
            head = newNode;
        } else {
            exTail.setNext(newNode);
        }
        customLinkedMap.put(task.getId(), tail);
    }

    public List<Task> getTasks() {                       //  метод для сбора всех задачи в ArrayList
        List<Task> listHistory = new ArrayList<>();
        Node node = head;

        while (node != null) {
            listHistory.add(node.getTask());
            node = node.getNext();
        }

        return listHistory;

    }

    @Override
    public void remove(int id) {                // метод удаления по ID
        removeNode(customLinkedMap.get(id));
        customLinkedMap.remove(id);
    }

    public void removeNode(Node node) { // метод принимает узел связного списка и вырезает его

        if (!(node == null)) {
            if (customLinkedMap.containsKey(node.getTask().getId())) {
                Node prevNode = node.getPrev();
                Node nextNode = node.getNext();
                if (prevNode != null) {
                    prevNode.setNext(nextNode);
                }
                if (nextNode != null) {
                    nextNode.setPrev(prevNode);
                }
                customLinkedMap.remove(node.getTask().getId());

                if (head == node) {
                    head = nextNode;
                } else if (tail == node) {
                    tail = prevNode;
                }
            }
        }
    }

}
