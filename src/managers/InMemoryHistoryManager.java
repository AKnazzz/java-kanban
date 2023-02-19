package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedList<Task> history = new CustomLinkedList<>();

    @Override
    public void add(Task task) {

        if (!(task == null)) {
            history.linkLast(task);
        } else {
            return;
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getTasks();
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

    public HashMap<Integer, Node<Task>> getNodeHistory() {
        return history.customLinkedMap;
    }

    public class CustomLinkedList<T> {   // список для хранения порядка вызовов метода add (в этом порядке просмотры будут выстраиваться в истории)
        private final HashMap<Integer, Node<Task>> customLinkedMap = new HashMap<>();
        private Node<Task> head;
        private Node<Task> tail;
        // private int size = 0;


        public void linkLast(Task task) {                      // метод для добавления задачи в конец списка
            final Node<Task> exTail = tail;
            final Node<Task> newNode = new Node<>(task, exTail, null);
            tail = newNode;

            if (exTail == null) {
                head = newNode;
            } else {
                exTail.setNext(newNode);
            }

            if (customLinkedMap.containsKey(task.getId())) {
                removeNode(customLinkedMap.get(task.getId()));
            }

            customLinkedMap.put(task.getId(), tail);
            // size++;

        }

        public List<Task> getTasks() {                       //  метод для сбора всех задачи в ArrayList
            List<Task> listHistory = new ArrayList<>();
            Node<Task> node = head;

            while (node != null) {
                listHistory.add(node.getData());
                node = node.getNext();
            }
            Collections.reverse(listHistory);
            return listHistory;
        }


        public void remove(int id) {                // метод удаления по ID
            removeNode(customLinkedMap.get(id));
            customLinkedMap.remove(id);
        }

        public void removeNode(Node<Task> node) { // метод принимает узел связного списка и вырезает его

            if (!(node == null)) {
                if (customLinkedMap.containsKey(node.getData().getId())) {
                    Node<Task> prevNode = node.getPrev();
                    Node<Task> nextNode = node.getNext();
                    if (prevNode != null) {
                        prevNode.setNext(nextNode);
                    }
                    if (nextNode != null) {
                        nextNode.setPrev(prevNode);
                    }
                    customLinkedMap.remove(node.getData().getId());

                    if (head == node) {
                        head = nextNode;
                    } else if (tail == node) {
                        tail = prevNode;
                    }
                }
            }
        }

    }

}

