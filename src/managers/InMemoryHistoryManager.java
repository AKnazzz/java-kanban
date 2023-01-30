package managers;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }

    @Override
    public void printHistory() {
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
