package managers;

import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedList<Task> history = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (history.customLinkedMap.containsKey(task.getId())) {
            history.removeNode(history.customLinkedMap.get(task.getId()));
        }

        if (task == null) {
            return;
        } else {
            history.linkLast(task);
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


    public class CustomLinkedList<T extends Task> {   // список для хранения порядка вызовов метода add (в этом порядке просмотры будут выстраиваться в истории)
        private final HashMap<Integer, Node<T>> customLinkedMap = new HashMap<>();
        private Node<T> head;
        private Node<T> tail;


        public void linkLast(Task task) {                      // метод для добавления задачи в конец списка
            final Node<T> exTail = tail;
            final Node<T> newNode = new Node<>(task, exTail, null);
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
            Node<T> node = head;

            while (node != null) {
                listHistory.add(node.getTask());
                node = node.getNext();
            }
              return listHistory;

            /* Удалил отображение в обратном порядке, но всё равно не совсем понимаю:
             отображение было реализовано аналогично отображению звонков в телефоне
             т.е. последний вызов отображается первым - сейчас первым отображается первый вызов
             */

            }



        public void remove(int id) {                // метод удаления по ID
            removeNode(customLinkedMap.get(id));
            customLinkedMap.remove(id);
        }

        public void removeNode(Node<T> node) { // метод принимает узел связного списка и вырезает его

            if (!(node == null)) {
                if (customLinkedMap.containsKey(node.getTask().getId())) {
                    Node<T> prevNode = node.getPrev();
                    Node<T> nextNode = node.getNext();
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
}