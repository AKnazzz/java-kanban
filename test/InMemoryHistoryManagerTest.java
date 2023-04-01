import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.StatusMarker;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public class InMemoryHistoryManagerTest {

    HistoryManager historyManager;


    // тестируются все ПУБЛИЧНЫЕ методы

    @BeforeEach
    public void init() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void addShouldAddTaskInEmptyHistory() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        historyManager.add(task);
        Task[] tasks = {task};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с одной задачей," +
                " вернулся не такой список");
    }


    @Test
    public void addShouldDeleteDublicate() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task);
        Task[] tasks = {task};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с одной задачей " +
                "после удаления второй , вернулся не такой");
    }

    @Test
    public void addShouldAddToNotEmptyHistory() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        Task task2 = new Task(2, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task2);
        Task[] tasks = {task, task2};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с двумя задачами," +
                " вернулся не такой");
    }

    @Test
    public void removeShouldDeleteFromStartOfHistory() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        Task task2 = new Task(2, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        Task task3 = new Task(3, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 6, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task.getId());
        Task[] tasks = {task2, task3};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался с двумя задачами " +
                "(удаление из начала), вернулся не такой");
    }

    @Test
    public void removeShouldDeleteFromMidOfHistory() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        Task task2 = new Task(2, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        Task task3 = new Task(3, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 6, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());
        Task[] tasks = {task, task3};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с двумя задачами " +
                "(удаление из середины), вернулся не такой");
    }

    @Test
    public void removeShouldDeleteFromEndOfHistory() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        Task task2 = new Task(2, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        Task task3 = new Task(3, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 6, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());
        Task[] tasks = {task, task2};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с двумя задачами (" +
                "удаление из конца), вернулся не такой");
    }

    @Test
    public void getHistoryShouldRetunList() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        Task task2 = new Task(2, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        Task task3 = new Task(3, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 6, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(task3);
        Task[] tasks = {task, task2, task3};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с тремя " +
                "задачами , вернулся не такой");
    }

    @Test
    public void getHistoryShouldRetunEmptyList() {

        Assertions.assertTrue(historyManager.getHistory().isEmpty(), "Ожидался пустой список (TRUE), " +
                "вернулся не такой (FALSE)");
    }

}
