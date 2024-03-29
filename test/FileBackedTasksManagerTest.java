import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import tracker.managers.FileBackedTasksManager;

import tracker.exceptions.ManagerSaveException;
import tracker.tasks.Epic;
import tracker.tasks.Subtask;
import tracker.tasks.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tracker.tasks.Status.NEW;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    public FileBackedTasksManagerTest() {
        super(FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/test.csv")));
    }

    // тест на получение исключения при сохранении состояния при неправильном имени файла
    @Test
    public void shouldThrowManagerSaveExceptionWhenWrongFilename() {
        final ManagerSaveException exception = assertThrows(
                ManagerSaveException.class,
                // создание и переопределение экземпляра класса Executable
                new Executable() {
                    @Override
                    public void execute() {
                        FileBackedTasksManager manager;
                        manager = FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/<t.csv"));
                        manager.clearAll();
                    }
                });
        assertEquals("Произошла ошибка во время записи файла!",
                exception.getMessage(), "Выбрасывает исключение при некорректном имени файла");
    }

    @Test
    public void shouldEmptyListTaskWhenFileEmpty() {
        FileBackedTasksManager fileManager;
        fileManager = FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/test.csv"));
        fileManager.clearAll();
        fileManager = FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/test.csv"));
        List<Task> list = new ArrayList<>(fileManager.getAllTasks());
        assertTrue(list.isEmpty(), "Список задач должен быть пустым");
        fileManager.clearAll();
    }

    @Test
    public void shouldReturnEpicWhenEpicWithoutSubtask() {
        FileBackedTasksManager fileManager;
        fileManager = FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/test.csv"));
        Task epic = new Epic("epic", "descriptionOfEpic", NEW);
        fileManager.getTask(fileManager.addTask(epic, 0));
        FileBackedTasksManager fileManagerTest;
        fileManagerTest = FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/test.csv"));
        assertEquals(fileManager.getTask(1), fileManagerTest.getTask(1), "Эпики должны быть равны");
        assertEquals(fileManager.history(), fileManagerTest.history(), "Истории должны совпадать");
        fileManager.clearAll();
    }

    @Test
    public void shouldReturnEmptyHistoryListWhenHistoryEmpty() {

        FileBackedTasksManager fileManager;
        fileManager = FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/test.csv"));
        fileManager.clearAll();
        Task epic = new Epic("epic", "descriptionOfEpic", NEW);
        fileManager.addTask(epic, 0);
        LocalDateTime startTime = LocalDateTime.of(2022, 04, 01, 1, 20);
        Duration duration = Duration.ofMinutes(20);
        Task subtask = new Subtask("subtask", "descriptionOfSubtask", NEW, (Epic) epic,
                startTime, duration);
        fileManager.addTask(subtask, 0);
        FileBackedTasksManager fileManagerTest;
        fileManagerTest = FileBackedTasksManager.loadFromFile(new File("src/tracker/resources/test.csv"));
        assertTrue(fileManagerTest.history().isEmpty(), "История должна быть пустой");
        fileManager.clearAll();
    }
}
