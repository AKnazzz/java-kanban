import managers.FileBackedTasksManager;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.StatusMarker;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    File file = new File("resourses/savesTest.csv");

    @BeforeEach
    public void init() {
        taskManager = new FileBackedTasksManager(file);
    }

    // тестируются все ПУБЛИЧНЫЕ методы

    @DisplayName("Проверка что файл пустой при пустом списке задач")
    @Test
    public void saveFileShouldBeEmptyWhenNoTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            taskManager.getTaskById(0);  // метод вызовет save()
            String expected = "id,type,name,status,description,epicID,startTime,duration,endTime";
            StringBuilder result = new StringBuilder();
            while (reader.ready()) {
                result.append(reader.readLine());
            }

            Assertions.assertEquals(expected, result.toString(), "Ожидался пустой файл без задач");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Проверка записи эпика без подзадач")
    @Test
    public void saveShouldSaveEpicWithoutSubtasks() {
        Epic epic = new Epic("Эпик 1", "описание", StatusMarker.NEW, null, null, null);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            taskManager.createNewEpic(epic);
            taskManager.getEpicById(epic.getId());
            String expected = epic.toString() + "1,";
            StringBuilder result = new StringBuilder();
            reader.readLine();  // считываем заголовок в пустоту чтобы он не сохранился в StringBuilder
            while (reader.ready()) {
                result.append(reader.readLine());
            }

            Assertions.assertEquals(expected, result.toString(), "Ожидался файл с один эпиком");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Проверка записи при пустом списке истории")
    @Test
    public void saveShouldSaveTasksWithoutHistory() {
        Subtask subtask = new Subtask("Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 10, 0), 0);
        Epic epic = new Epic("Эпик 1", "описание", StatusMarker.NEW, null, null, null);
        Task task = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));

        taskManager.createNewTask(task);
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine();  // считываем заголовок в пустоту чтобы он не сохранился в StringBuilder
            String expected1 = reader.readLine();
            String expected2 = reader.readLine();
            String expected3 = reader.readLine();

            Assertions.assertEquals(expected1, task.toString(), "Ожидалась строка с task без истории");
            Assertions.assertEquals(expected2, epic.toString(), "Ожидалась строка с epic без истории");
            Assertions.assertEquals(expected3, subtask.toString(), "Ожидалась строка с subtask без истории");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Проверка выгрузке при пустом списке задач")
    @Test
    public void loadFromFileShouldCreateManagerWithoutTasksWhenFileIsEmpty() {
        taskManager.clearAll();
        TaskManager fileBacked = FileBackedTasksManager.loadFromFile(file);

        Assertions.assertTrue(fileBacked.getAllTasks().isEmpty(), "Ожидался пустой список task");
        Assertions.assertTrue(fileBacked.getAllEpics().isEmpty(), "Ожидался пустой список epic");
        Assertions.assertTrue(fileBacked.getAllSubtasks().isEmpty(), "Ожидался пустой список subtask");
        Assertions.assertTrue(fileBacked.getPrioritizedTasks().isEmpty(), "Ожидался пустой список сортированных задач");
    }

    @DisplayName("Проверка выгрузки эпика без подзадач")
    @Test
    public void loadFromFileShouldReturnManagerWithEpic() {
        Epic epic = new Epic("Эпик 1", "описание", StatusMarker.NEW, null, null, null);
        taskManager.createNewEpic(epic);
        taskManager.getEpicById(epic.getId());

        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);
        Task [] tasks = {epic};
        Task [] history = {epic};

        Assertions.assertArrayEquals(history, fileBackedTasksManager.history().toArray(), "Ожидался список (история)");
        Assertions.assertArrayEquals(tasks, fileBackedTasksManager.getAllEpics().toArray(), "Ожидался список epic");
        Assertions.assertTrue(fileBackedTasksManager.getAllTasks().isEmpty(), "Ожидался пустой список задач");
        Assertions.assertTrue(fileBackedTasksManager.getAllSubtasks().isEmpty(), "Ожидался пустой список подзадач");
    }

    @DisplayName("Проверка выгрузки при пустом списке истории")
    @Test
    public void loadFromFileShouldReturnManagerWithEmptyHistory() {
        Subtask subtask = new Subtask("Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 10, 0), 0);
        Epic epic = new Epic("Эпик 1", "описание", StatusMarker.NEW, null, null, null);
        Task task = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
        taskManager.createNewTask(task);
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(file);

        Assertions.assertTrue(fileBackedTasksManager.history().isEmpty(), "Ожидался пустой список");
    }

}
