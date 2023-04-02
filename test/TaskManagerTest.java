import managers.TaskManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tasks.Epic;
import tasks.StatusMarker;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected TaskManager taskManager;
    protected Epic epic;
    protected Task task;
    protected Subtask subtask;

    //аннотацию @BeforeEach не задействую т.к. это не требуется во всех тестах
    public void setupTasks() { // метод для тестирования используется для создания объектов ()

        subtask = new Subtask("Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 10, 0), 0);
        epic = new Epic("Эпик 1", "описание", StatusMarker.NEW, null, null, null);
        task = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
    }

    // тестируются все ПУБЛИЧНЫЕ методы
    @DisplayName("Проверка метода получения всех задач при их отсутствии (TASKS)")
    @Test
    public void getAllTasksShouldReturnEmptyListWhenNoTasksInside() {

        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался пустой список");
    }

    @DisplayName("Проверка получения всех созданных TASK")
    @Test
    public void getAllTasksShouldReturnListOfTasksWhenTasksInside() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 13, 10, 0));
        taskManager.createNewTask(task2);
        Task[] tasks = {task, task2};

        Assertions.assertArrayEquals(tasks, taskManager.getAllTasks().toArray(), "Ожидался список");
    }

    @DisplayName("Проверка метода получения всех задач при их отсутствии (EPICS)")
    @Test
    public void getAllEpicsShouldReturnEmptyListWhenNoTasksInside() {

        Assertions.assertTrue(taskManager.getAllEpics().isEmpty(), "Ожидался пустой список");
    }

    @DisplayName("Проверка получения всех созданных EPICS")
    @Test
    public void getAllEpicsShouldReturnListOfTasksWhenTasksInside() {
        setupTasks();
        taskManager.createNewEpic(epic);
        Epic epic2 = new Epic("Эпик 2", "описание", StatusMarker.NEW, null, null, null);
        taskManager.createNewEpic(epic2);
        Epic[] epics = {epic, epic2};

        Assertions.assertArrayEquals(epics, taskManager.getAllEpics().toArray(), "Ожидался список");
    }

    @DisplayName("Проверка метода получения всех задач при их отсутствии (SUBTASKS)")
    @Test
    public void getAllSubTasksShouldReturnEmptyListWhenNoTasksInside() {

        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой список");
    }

    @DisplayName("Проверка получения всех созданных SUBTASK")
    @Test
    public void getAllSubTasksShouldReturnListOfTasksWhenTasksInside() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask("Сабтаск 2", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        Subtask[] subTasks = {subtask, subtask2};

        Assertions.assertArrayEquals(subTasks, taskManager.getAllSubtasks().toArray(), "Ожидался список");
    }

    @DisplayName("Проверка удаления всех созданных TASK")
    @Test
    public void deleteAllTasksShouldDeleteAllTasksFromMapTasksAndPrioritizedTasks() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 13, 10, 0));
        taskManager.createNewTask(task2);
        taskManager.deleteAllTasks();

        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался пустой лист задач");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой лист сортированных задач");
    }

    @DisplayName("Проверка удаления всех созданных SUBTASK")
    @Test
    public void deleteAllSubTasksShouldDeleteAllTasksFromMapTasksAndAllSubTasksPrioritizedTasks() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask("Сабтаск 2", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        taskManager.deleteAllSubTasks();

        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой лист задач");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой лист сортированных задач");
    }

    @DisplayName("Проверка удаления всех созданных EPICS")
    @Test
    public void deleteAllEpicsShouldDeleteAllTasksFromMapTasksAndAllSubTasksPrioritizedTasks() {
        setupTasks();
        taskManager.createNewEpic(epic);
        Epic epic2 = new Epic("Эпик 2", "описание", StatusMarker.NEW, null, null, null);
        taskManager.createNewEpic(epic2);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask("Сабтаск 2", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        taskManager.deleteAllEpics();

        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой лист задач");
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty(), "Ожидался пустой лист задач");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой лист задач");
    }

    @DisplayName("Проверка получения TASK при её наличии")
    @Test
    public void getTaskByIdShouldReturnTaskIfItExist() {
        setupTasks();
        taskManager.createNewTask(task);
        taskManager.getTaskById(task.getId());

        Assertions.assertEquals(task, taskManager.getTaskById(task.getId()), "Ожидалась задача с конкретным ID");
    }

    @DisplayName("Проверка получения TASK при её отсутствии")
    @Test
    public void getTaskByIdShouldReturnNullTaskWhenItNotExist() {

        Assertions.assertNull(taskManager.getTaskById(0), "Ожидался null (отсутствие задачи)");
    }

    @DisplayName("Проверка получения SUBTASK при её наличии")
    @Test
    public void getSubTaskByIdShouldReturnSubTaskIfItExist() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.getSubtaskById(subtask.getId());

        Assertions.assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()), "Ожидалась задача с конкретным ID");
    }

    @DisplayName("Проверка получения SUBTASK при её отсутствии")
    @Test
    public void getSubTaskByIdShouldReturnNullTaskWhenItNotExist() {

        Assertions.assertNull(taskManager.getSubtaskById(0), "Ожидался null (отсутствие задачи)");
    }

    @DisplayName("Проверка получения EPIC при её наличии")
    @Test
    public void getEpicByIdShouldReturnTaskIfItExist() {
        setupTasks();
        taskManager.createNewEpic(epic);
        taskManager.getEpicById(epic.getId());

        Assertions.assertEquals(epic, taskManager.getEpicById(epic.getId()), "Ожидалась задача с конкретным ID");
    }

    @DisplayName("Проверка получения SUBTASK при её отсутствии")
    @Test
    public void getEpicByIdShouldReturnNullTaskWhenItNotExist() {

        Assertions.assertNull(taskManager.getEpicById(0), "Ожидался null (отсутствие задачи)");
        Assertions.assertNull(taskManager.getEpicById(0), "Ожидался null (отсутствие задачи)");
    }

    @DisplayName("Проверка создания TASK при её корректности")
    @Test
    public void createNewTaskShouldReturnIDWhenTaskValid() {
        setupTasks();
        Assertions.assertEquals(1, taskManager.createNewTask(task), "Ожидался ID = 1");
    }

    @DisplayName("Проверка создания TASK при её отсутствии (объекта)")
    @Test
    public void createNewTaskShouldReturnNegativeIDTaskNotValid() {

        Assertions.assertEquals(-1, taskManager.createNewTask(task), "Ожидался ID = - 1");
    }

    @DisplayName("Проверка создания TASK при пересечении по времени задачи")
    @Test
    public void createNewTaskShouldReturnNegativeIDWhenTaskInterseption() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 00));

        Assertions.assertEquals(-1, taskManager.createNewTask(task2), "Ожидался ID = - 1");
    }

    @DisplayName("Проверка создания EPIC при её корректности")
    @Test
    public void createNewEpicShouldReturnIDWhenTaskValid() {
        setupTasks();

        Assertions.assertEquals(1, taskManager.createNewEpic(epic), "Ожидался ID = 1");
    }

    @DisplayName("Проверка создания EPIC при её отсутствии (объекта)")
    @Test
    public void createNewEpicShouldReturnNegativeIDTaskNotValid() {

        Assertions.assertEquals(-1, taskManager.createNewEpic(epic), "Ожидался ID = 1");
    }

    @DisplayName("Проверка создания SUBTASK при её корректности")
    @Test
    public void createNewSubTaskShouldReturnIDWhenTaskValid() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());

        Assertions.assertEquals(2, taskManager.createNewSubTask(subtask), "Ожидался ID = 2");
    }

    @DisplayName("Проверка создания SUBTASK при её отсутствии (объекта)")
    @Test
    public void createNewSubTaskShouldReturnNegativeIDTaskNotValid() {

        Assertions.assertEquals(-1, taskManager.createNewSubTask(subtask), "Ожидался ID = - 1");
    }

    @DisplayName("Проверка создания SUBTASK при пересечении по времени задачи")
    @Test
    public void createNewSubTaskShouldReturnNegativeIDWhenTaskInterseption() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask("Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 10, 0), 0);

        Assertions.assertEquals(-1, taskManager.createNewTask(subtask2), "Ожидался ID = - 1");
    }

    @DisplayName("Проверка обновления TASK ")
    @Test
    public void updateTaskByIdShouldReturnUpdatedTask() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task(1, "задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(600),
                LocalDateTime.of(2023, 5, 14, 10, 00));

        Assertions.assertEquals(task2, taskManager.updateTaskById(task2), "Ожидалась обновлённая task");
    }

    @DisplayName("Проверка обновления TASK при пересечении по времени")
    @Test
    public void updateTaskByIdShouldReturnOldTaskIfUpdatedTaskIsDurationByTime() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task3 = new Task("задача 3", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        taskManager.createNewTask(task3);
        Task task2 = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));

        Assertions.assertEquals(task, taskManager.updateTaskById(task2), "Ожидалась обновлённая задача");
    }

    @DisplayName("Проверка обновления TASK при её некорректности")
    @Test
    public void updateTaskByIdShouldReturnOldTaskIfUndatedTaskIsNotCorrect() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task(5, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));

        Assertions.assertNull(taskManager.updateTaskById(task2), "Ожидался null (некорректный ID задачи)");
    }

    @DisplayName("Проверка обновления ЭПИКА ")
    @Test
    public void updateEpicByIdShouldReturnUpdatedTask() {
        setupTasks();
        taskManager.createNewEpic(epic);
        Epic epic2 = new Epic(1, "Эпик 2222", "описание 222", StatusMarker.NEW, null, null, null);

        Assertions.assertEquals(epic2, taskManager.updateEpic(epic2), "Ожидалась обновлённая задача");
    }

    @DisplayName("Проверка обновления ЭПИКА при его некорректности ")
    @Test
    public void updateEpicByIdShouldReturnOldTaskIfUpdatedTaskIsNotCorrect() {
        setupTasks();
        taskManager.createNewEpic(epic);
        Epic epic2 = new Epic(5, "Эпик 2222", "описание 222", StatusMarker.NEW, null, null, null);

        Assertions.assertNull(taskManager.updateEpic(epic2), "Ожидался null (некорректный ID задачи)");
    }

    @DisplayName("Проверка обновления SUBTASK ")
    @Test
    public void updateSubTasksByIdShouldReturnUpdatedTask() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask(2, "Сабтаск 1", "описание", StatusMarker.NEW,
                Duration.ofMinutes(60), LocalDateTime.of(2023, 4, 1, 10, 0), epic.getId());

        Assertions.assertEquals(subtask2, taskManager.updateSubTaskById(subtask2), "Ожидалась обновлённая задача");
    }

    @DisplayName("Проверка обновления SUBTASK при пересечении по времени")
    @Test
    public void updateSubTaskByIdShouldReturnOldTaskIfUpdatedTaskHasIntersection() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask3 = new Subtask("Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 5, 1, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask3);
        Subtask subtask2 = new Subtask(subtask.getId(), "Сабтаск 1", "описание", StatusMarker.NEW,
                Duration.ofMinutes(60), LocalDateTime.of(2023, 5, 1, 10, 0), epic.getId());

        Assertions.assertEquals(subtask, taskManager.updateSubTaskById(subtask2), "Ожидалась старая задача " +
                "(т.к. пересечение по времени)");
    }

    @DisplayName("Проверка удаления TASK ")
    @Test
    public void deleteTaskByIdShouldDeleteTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        int id = taskManager.createNewTask(task);
        taskManager.deleteTaskById(id);

        Assertions.assertNull(taskManager.getTaskById(id), "Ожидался null (отсутствие задачи)");
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался TRUE - пустой список"); // добавлено
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(task), "Ожидалась false (отсутствие задачи)");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался TRUE - пустой список"); // добавлено
    }

    @DisplayName("Проверка удаления TASK из истории")
    @Test
    public void deleteTaskByIdShouldDeleteTaskFromHistory() {
        setupTasks();
        int id = taskManager.createNewTask(task);
        taskManager.getTaskById(id);
        taskManager.deleteTaskById(id);

        Assertions.assertFalse(taskManager.history().contains(task), "Ожидалась false(отсутствие задачи)");
    }

    @DisplayName("Проверка удаления SUBTASK")
    @Test
    public void deleteSubTaskByIdShouldDeleteTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        int id = taskManager.createNewSubTask(subtask);
        taskManager.deleteSubTaskById(id);

        Assertions.assertNull(taskManager.getSubtaskById(id), "Ожидался null (отсутствие задачи)");
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(subtask), "Ожидалась false (отсутствие задачи)");
        Assertions.assertFalse(epic.getSubTasks().contains(subtask), "Ожидалась false (отсутствие задачи)");
    }

    @DisplayName("Проверка удаления SUBTASK из истории")
    @Test
    public void deleteSubTaskByIdShouldDeleteTaskFromHistory() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        int id = taskManager.createNewSubTask(subtask);
        taskManager.getSubtaskById(id);
        taskManager.deleteSubTaskById(id);

        Assertions.assertFalse(taskManager.history().contains(subtask), "Ожидалась false (отсутствие задачи)");
    }

    @DisplayName("Проверка удаления ЭПИКА")
    @Test
    public void deleteEpicByIdShouldDeleteTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        int id = taskManager.createNewEpic(epic);
        taskManager.deleteEpicById(id);

        Assertions.assertNull(taskManager.getEpicById(id), "Ожидался null (отсутствие задачи)");
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(epic), "Ожидалась false (отсутствие задачи)");
    }

    @DisplayName("Проверка удаления ЭПИКА из истории")
    @Test
    public void deleteEpicByIdShouldDeleteTaskFromHistory() {
        setupTasks();
        int id = taskManager.createNewEpic(epic);
        taskManager.getEpicById(id);
        taskManager.deleteEpicById(id);

        Assertions.assertFalse(taskManager.history().contains(epic), "Ожидалась false (отсутствие задачи)");
    }

    @DisplayName("Проверка удаления ЭПИКА и его SUBTASK")
    @Test
    public void deleteEpicByIdShouldDeleteSubTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.deleteEpicById(epic.getId());

        Assertions.assertFalse(taskManager.getAllSubtasks().contains(subtask), "Ожидалась false (отсутствие задачи)");
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(subtask), "Ожидалась false (отсутствие задачи)");
    }

    @DisplayName("Проверка удаления ЭПИКА и его SUBTASK из истории")
    @Test
    public void deleteEpicByIdShouldDeleteHisSubtasksTaskFromHistory() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.getSubtaskById(subtask.getId());
        taskManager.deleteEpicById(epic.getId());

        Assertions.assertFalse(taskManager.getAllSubtasks().contains(subtask), "Ожидалась false (отсутствие задачи)");
        Assertions.assertFalse(taskManager.history().contains(subtask), "Ожидалась false (отсутствие задачи)");
    }

    @DisplayName("Проверка получения у ЭПИКА его SUBTASK ")
    @Test
    public void getEpicSubtasksShouldReturnSubtasksList() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask(subtask.getId(), "Сабтаск 1", "описание", StatusMarker.NEW,
                Duration.ofMinutes(60), LocalDateTime.of(2023, 5, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        Subtask[] subtasks = {subtask, subtask2};

        Assertions.assertArrayEquals(subtasks, taskManager.getEpicSubtasks(epic).toArray(), "Ожидался список сабтасок");
    }

    @DisplayName("Проверка получения у ЭПИКА его SUBTASK (если их нет) ")
    @Test
    public void getEpicSubtasksShouldReturnEmptySubtasksList() {
        setupTasks();
        taskManager.createNewEpic(epic);

        Assertions.assertTrue(taskManager.getEpicSubtasks(epic).isEmpty(), "Ожидался Empty список сабтасок");
    }

    @DisplayName("Проверка получения у ЭПИКА его SUBTASK при некорректности ЭПИКА")
    @Test
    public void getEpicSubtasksShouldReturnEmptySubtasksListWhenEpicIsNullOrWrongID() {
        setupTasks();
        Assertions.assertTrue(taskManager.getEpicSubtasks(epic).isEmpty(), "Ожидался Empty список сабтасок");
        epic = null;

        Assertions.assertTrue(taskManager.getEpicSubtasks(epic).isEmpty(), "Ожидался Empty список сабтасок");
    }


    @DisplayName("Проверка получения у отсортированных по важности задач")
    @Test
    public void getPrioritizedTasksShouldReturnSortedSetByStartTime() {
        setupTasks();
        Task task2 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 13, 10, 0));
        Task task3 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 14, 10, 0));

        taskManager.createNewTask(task3);
        taskManager.createNewTask(task);
        taskManager.createNewTask(task2);

        Task[] tasks = {task, task2, task3};

        Assertions.assertArrayEquals(tasks, taskManager.getPrioritizedTasks().toArray(), "Ожидался список сортированный " +
                "по времени старта");
    }

    @DisplayName("Проверка получения у отсортированных по важности задач если их нет")
    @Test
    public void getPrioritizedTasksShouldReturnEmptySortedSetByStartTime() {
        setupTasks();

        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой список");
    }

    @DisplayName("Проверка удаления ВСЕХ задач отовсюду")
    @Test
    public void clearAllShouldRemoveAllTypeOfTaskEverywhere() {
        setupTasks();
        taskManager.createNewTask(task);
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.clearAll();

        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался пустой список");
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой список");
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty(), "Ожидался пустой список");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой список");
    }

    @DisplayName("Проверка получения истории")
    @Test
    public void historyShouldListOfHistory() {
        setupTasks();
        Task task2 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 13, 10, 0));
        Task task3 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 14, 10, 0));
        taskManager.createNewTask(task);
        taskManager.createNewTask(task2);
        taskManager.createNewTask(task3);
        taskManager.getTaskById(task3.getId());
        taskManager.getTaskById(task.getId());
        taskManager.getTaskById(task2.getId());
        Task[] tasks = {task3, task, task2};

        Assertions.assertArrayEquals(tasks, taskManager.history().toArray(), "Ожидался список в порядке запроса задач");
    }

    @DisplayName("Проверка пересечения по времени когда нет пересечения")
    @Test
    public void timeNotBusyShouldReturnTrueWhenNoIntersaction() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 13, 10, 0));

        Assertions.assertTrue(taskManager.timeNotBusy(task2), "Ожидался TRUE когда нет пересечения по времени");
    }

    @DisplayName("Проверка пересечения по времени когда есть пересечение")
    @Test
    public void timeNotBusyShouldReturnFalseWhenIntersaction() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));

        Assertions.assertFalse(taskManager.timeNotBusy(task2), "Ожидался False когда нет пересечения по времени");
    }

    @DisplayName("Проверка пересечения по времени при NULL")
    @Test
    public void timeNotBusyShouldReturnTrueWhenArgumentNull() {
        setupTasks();

        Assertions.assertTrue(taskManager.timeNotBusy(null), "Ожидался TRUE когда нет пересечения по времени");
    }

    @DisplayName("Проверка обновления статуса у ЭПИКА - когда у сабтасок статус НОВАЯ")
    @Test
    public void updateEpicStatusShouldBeNewWhenAllSubsNew() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask(subtask.getId(), "Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 5, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        taskManager.updateEpicStatus(epic.getId());

        Assertions.assertEquals(StatusMarker.NEW, epic.getStatus(), "Ожидался статус NEW ");
    }

    @DisplayName("Проверка обновления статуса у ЭПИКА - когда нет сабтасок")
    @Test
    public void updateEpicStatusShouldBeNewWhenNoSubs() {
        setupTasks();
        taskManager.createNewEpic(epic);
        taskManager.updateEpicStatus(epic.getId());

        Assertions.assertEquals(StatusMarker.NEW, epic.getStatus(), "Ожидался статус NEW");
    }

    @DisplayName("Проверка обновления статуса у ЭПИКА - когда у сабтасок статус ВЫПОЛНЕНО")
    @Test
    public void updateEpicStatusShouldBeDoneWhenAllSubsDone() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        subtask.setStatus(StatusMarker.DONE);
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask(subtask.getId(), "Сабтаск 1", "описание", StatusMarker.DONE, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 5, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        taskManager.updateEpicStatus(epic.getId());

        Assertions.assertEquals(StatusMarker.DONE, epic.getStatus(), "Ожидался статус DONE");
    }

    @DisplayName("Проверка обновления статуса у ЭПИКА - когда у сабтасок статус ВЫПОЛНЕНО")
    @Test
    public void updateEpicStatusShouldBeInProgresWhenSubsDoneAndNew() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask(subtask.getId(), "Сабтаск 1", "описание", StatusMarker.DONE,
                Duration.ofMinutes(60), LocalDateTime.of(2023, 5, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        taskManager.updateEpicStatus(epic.getId());

        Assertions.assertEquals(StatusMarker.IN_PROGRESS, epic.getStatus(), "Ожидался статус IN_PROGRESS");
    }

    @DisplayName("Проверка обновления статуса у ЭПИКА - когда у сабтасок статус В ПРОЦЕССЕ")
    @Test
    public void updateEpicStatusShouldBeInProgresWhenSubsInProgres() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        subtask.setStatus(StatusMarker.IN_PROGRESS);
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask(subtask.getId(), "Сабтаск 1", "описание", StatusMarker.IN_PROGRESS,
                Duration.ofMinutes(60), LocalDateTime.of(2023, 5, 2, 10, 0), epic.getId());
        taskManager.createNewSubTask(subtask2);
        taskManager.updateEpicStatus(epic.getId());

        Assertions.assertEquals(StatusMarker.IN_PROGRESS, epic.getStatus(), "Ожидался статус IN_PROGRESS");
    }
}
