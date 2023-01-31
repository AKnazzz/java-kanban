package managers;

import tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public List <Task> getHistory() {
        return history;
    }

    @Override
    public void showHistory() {
        System.out.println(" ");
        System.out.println("Вывод истории просмотра задач: ");

        if (!history.isEmpty()) {
            for (int i = history.size(); i >= 1; i--) {
                System.out.println(history.get(i - 1));
            }
        }
        System.out.println("Должен был выведен список из последних 10 запросов");

    }
}
