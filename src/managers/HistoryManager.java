package managers;

import tasks.Task;

import java.util.List;


public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();

    void remove(int id);                    // добавлен новый метод согласно ТЗ


}
