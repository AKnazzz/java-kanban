import managers.HistoryManager;
import managers.InMemoryHistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("Проверка добавления задачи в пустую историю")
    @Test
    public void addShouldAddTaskInEmptyHistory() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        historyManager.add(task);
        Task[] tasks = {task};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с одной задачей");
    }

    @DisplayName("Проверка добавления задачи в историю (дубликат задачи)")
    @Test
    public void addShouldDeleteDublicate() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task);
        Task[] tasks = {task};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с одной задачей " +
                "после удаления второй");
    }

    @DisplayName("Проверка добавления задачи в НЕ пустую историю")
    @Test
    public void addShouldAddToNotEmptyHistory() {
        Task task = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        Task task2 = new Task(2, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        historyManager.add(task);
        historyManager.add(task2);
        Task[] tasks = {task, task2};

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с двумя задачами");
    }

    @DisplayName("Проверка добавления удаления задачи из начала истории")
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
                "(удаление из начала)");
    }

    @DisplayName("Проверка добавления удаления задачи из середины истории")
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
                "(удаление из середины)");
    }

    @DisplayName("Проверка добавления удаления задачи из конца истории")
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
                "удаление из конца)");
    }

    @DisplayName("Проверка получения истории")
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

        Assertions.assertArrayEquals(tasks, historyManager.getHistory().toArray(), "Ожидался список с тремя задачами");
    }


    @DisplayName("Проверка получения истории (пустой список)")
    @Test
    public void getHistoryShouldReturnEmptyList() {

        Assertions.assertTrue(historyManager.getHistory().isEmpty(), "Ожидался пустой список (TRUE)");
    }

}
