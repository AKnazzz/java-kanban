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


    public class CustomLinkedList<T> {   // список для хранения порядка вызовов метода add (в этом порядке просмотры будут выстраиваться в истории)
        private final HashMap<Integer, Node<Task>> customLinkedMap = new HashMap<>();
        private Node<Task> head;
        private Node<Task> tail;


        public void linkLast(Task task) {                      // метод для добавления задачи в конец списка
            final Node<Task> exTail = tail;
            final Node<Task> newNode = new Node<>(task, exTail, null);
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
            Node<Task> node = head;

            while (node != null) {
                listHistory.add(node.getTask());
                node = node.getNext();
            }
            //прошу уточнить почему использование данного метода выглядит не совсем корректно ?
            // Collections.reverse(listHistory);  вызывается для вывода истории в порядке, иначе порядок будет не тот
            //   return listHistory;
            //}

            List<Task> reverse = new ArrayList<>(listHistory.size()); // лист для сохранения списка в обратном порядке
            for (int i = listHistory.size() - 1; i >= 0; i--) {
                reverse.add(listHistory.get(i));
            }
            return reverse;
        }


        public void remove(int id) {                // метод удаления по ID
            removeNode(customLinkedMap.get(id));
            customLinkedMap.remove(id);
        }

        public void removeNode(Node<Task> node) { // метод принимает узел связного списка и вырезает его

            if (!(node == null)) {
                if (customLinkedMap.containsKey(node.getTask().getId())) {
                    Node<Task> prevNode = node.getPrev();
                    Node<Task> nextNode = node.getNext();
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