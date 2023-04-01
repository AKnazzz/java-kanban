import managers.TaskManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tasks.Epic;
import tasks.StatusMarker;
import tasks.Subtask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class TaskManagerTest<T extends TaskManager> {
    TaskManager taskManager;
    Epic epic;
    Task task;
    Subtask subtask;


    public void setupTasks() { // метод для тестирования используется для создания объектов

        subtask = new Subtask("Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 10, 0), 0);
        epic = new Epic("Эпик 1", "описание", StatusMarker.NEW, null, null, null);
        task = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));
    }

    // тестируются все ПУБЛИЧНЫЕ методы
    @Test
    public void getAllTasksShouldReturnEmptyListWhenNoTasksInside() {

        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался пустой лист, вернулся не пустой");
    }

    @Test
    public void getAllTasksShouldReturnListOfTasksWhenTasksInside() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 13, 10, 0));
        taskManager.createNewTask(task2);
        Task[] tasks = {task, task2};

        Assertions.assertArrayEquals(tasks, taskManager.getAllTasks().toArray(), "Ожидался лист, вернулся пустой лист");
    }


    @Test
    public void getAllEpicsShouldReturnEmptyListWhenNoTasksInside() {

        Assertions.assertTrue(taskManager.getAllEpics().isEmpty(), "Ожидался пустой лист, вернулся не пустой");
    }

    @Test
    public void getAllEpicsShouldReturnListOfTasksWhenTasksInside() {
        setupTasks();
        taskManager.createNewEpic(epic);
        Epic epic2 = new Epic("Эпик 2", "описание", StatusMarker.NEW, null, null, null);
        taskManager.createNewEpic(epic2);
        Epic[] epics = {epic, epic2};

        Assertions.assertArrayEquals(epics, taskManager.getAllEpics().toArray(), "Ожидался лист, вернулся пустой лист");
    }

    @Test
    public void getAllSubTasksShouldReturnEmptyListWhenNoTasksInside() {

        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой лист, вернулся не пустой");
    }

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

        Assertions.assertArrayEquals(subTasks, taskManager.getAllSubtasks().toArray(), "Ожидался лист, вернулся пустой лист");
    }


    @Test
    public void deleteAllTasksShouldDeleteAllTasksFromMapTasksAndPrioritizedTasks() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 13, 10, 0));
        taskManager.createNewTask(task2);
        taskManager.deleteAllTasks();

        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался пустой лист задач, вернулся не пустой");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой лист сортированных задач, вернулся не пустой");
    }

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

        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой лист задач, вернулся не пустой");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой лист сортированных задач, " +
                "вернулся не пустой");
    }


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

        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой лист задач, вернулся не пустой");
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty(), "Ожидался пустой лист задач, вернулся не пустой");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой лист задач, вернулся не пустой");
    }


    @Test
    public void getTaskByIdShouldReturnTaskIfItExist() {
        setupTasks();
        taskManager.createNewTask(task);
        taskManager.getTaskById(task.getId());

        Assertions.assertEquals(task, taskManager.getTaskById(task.getId()), "Ожидалась задача с конкретным ID");
    }

    @Test
    public void getTaskByIdShouldReturnNullTaskWhenItNotExist() {

        Assertions.assertNull(taskManager.getTaskById(0), "Ожидался null (отсутствие задачи) , вернулся не null");
    }

    @Test
    public void getSubTaskByIdShouldReturnSubTaskIfItExist() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.getSubtaskById(subtask.getId());

        Assertions.assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()), "Ожидалась задача с конкретным ID");
    }

    @Test
    public void getSubTaskByIdShouldReturnNullTaskWhenItNotExist() {

        Assertions.assertNull(taskManager.getSubtaskById(0), "Ожидался null (отсутствие задачи) , вернулся не null");
    }

    @Test
    public void getEpicByIdShouldReturnTaskIfItExist() {
        setupTasks();
        taskManager.createNewEpic(epic);
        taskManager.getEpicById(epic.getId());

        Assertions.assertEquals(epic, taskManager.getEpicById(epic.getId()), "Ожидалась задача с конкретным ID");
    }

    @Test
    public void getEpicByIdShouldReturnNullTaskWhenItNotExist() {

        Assertions.assertNull(taskManager.getEpicById(0), "Ожидался null (отсутствие задачи) , вернулся не null");
    }

    @Test
    public void createNewTaskShouldReturnIDWhenTaskValid() {
        setupTasks();

        Assertions.assertEquals(1, taskManager.createNewTask(task), "Ожидался ID = 1, вернулся не 1");
    }

    @Test
    public void createNewTaskShouldReturnNegativeIDTaskNotValid() {

        Assertions.assertEquals(-1, taskManager.createNewTask(task), "Ожидался ID = - 1, вернулся не -1");
    }

    @Test
    public void createNewTaskShouldReturnNegativeIDWhenTaskInterseption() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 00));

        Assertions.assertEquals(-1, taskManager.createNewTask(task2), "Ожидался ID = - 1, вернулся не -1");
    }

    @Test
    public void createNewEpicShouldReturnIDWhenTaskValid() {
        setupTasks();

        Assertions.assertEquals(1, taskManager.createNewEpic(epic), "Ожидался ID = 1, вернулся не 1");
    }

    @Test
    public void createNewEpicShouldReturnNegativeIDTaskNotValid() {

        Assertions.assertEquals(-1, taskManager.createNewEpic(epic), "Ожидался ID = 1, вернулся не 1");
    }

    @Test
    public void createNewSubTaskShouldReturnIDWhenTaskValid() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());

        Assertions.assertEquals(2, taskManager.createNewSubTask(subtask), "Ожидался ID = 2, вернулся не 2");
    }

    @Test
    public void createNewSubTaskShouldReturnNegativeIDTaskNotValid() {

        Assertions.assertEquals(-1, taskManager.createNewSubTask(subtask), "Ожидался ID = - 1, вернулся не -1");
    }

    @Test
    public void createNewSubTaskShouldReturnNegativeIDWhenTaskInterseption() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask("Сабтаск 1", "описание", StatusMarker.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 10, 0), 0);

        Assertions.assertEquals(-1, taskManager.createNewTask(subtask2), "Ожидался ID = - 1, вернулся не -1");
    }

    @Test
    public void updateTaskByIdShouldReturnUpdatedTask() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task(1, "задача 2", "описание 2", StatusMarker.NEW, Duration.ofMinutes(600),
                LocalDateTime.of(2023, 5, 14, 10, 00));

        Assertions.assertEquals(task2, taskManager.updateTaskById(task2), "Ожидалась обновлённая, вернулась не она");
    }

    @Test
    public void updateTaskByIdShouldReturnOldTaskIfUpdatedTaskIsDurationByTime() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task3 = new Task("задача 3", "описание 2", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));
        taskManager.createNewTask(task3);
        Task task2 = new Task(1, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));

        Assertions.assertEquals(task, taskManager.updateTaskById(task2), "Ожидалась обновлённая задача , вернулся не она");
    }

    @Test
    public void updateTaskByIdShouldReturnOldTaskIfUndatedTaskIsNotCorrect() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task(5, "задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 12, 10, 0));

        Assertions.assertNull(taskManager.updateTaskById(task2), "Ожидался null (некорректный ID задачи), вернулся не null");
    }

    @Test
    public void updateEpicByIdShouldReturnUpdatedTask() {
        setupTasks();
        taskManager.createNewEpic(epic);
        Epic epic2 = new Epic(1, "Эпик 2222", "описание 222", StatusMarker.NEW, null, null, null);

        Assertions.assertEquals(epic2, taskManager.updateEpic(epic2), "Ожидалась обновлённая задача , вернулся не она");
    }

    @Test
    public void updateEpicByIdShouldReturnOldTaskIfUpdatedTaskIsNotCorrect() {
        setupTasks();
        taskManager.createNewEpic(epic);
        Epic epic2 = new Epic(5, "Эпик 2222", "описание 222", StatusMarker.NEW, null, null, null);

        Assertions.assertNull(taskManager.updateEpic(epic2), "Ожидался null (некорректный ID задачи), вернулся не null");
    }

    @Test
    public void updateSubTasksByIdShouldReturnUpdatedTask() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        Subtask subtask2 = new Subtask(2, "Сабтаск 1", "описание", StatusMarker.NEW,
                Duration.ofMinutes(60), LocalDateTime.of(2023, 4, 1, 10, 0), epic.getId());

        Assertions.assertEquals(subtask2, taskManager.updateSubTaskById(subtask2), "Ожидалась обновлённая задача , " +
                "вернулся не она");
    }

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
                "(т.к. пересечение по времени) , вернулся не она");
    }

    @Test
    public void deleteTaskByIdShouldDeleteTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        int id = taskManager.createNewTask(task);
        taskManager.deleteTaskById(id);

        Assertions.assertNull(taskManager.getTaskById(id), "Ожидался null (отсутствие задачи), вернулся не null");
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(task), "Ожидалась false (отсутствие задачи), вернулся true");
    }

    @Test
    public void deleteTaskByIdShouldDeleteTaskFromHistory() {
        setupTasks();
        int id = taskManager.createNewTask(task);
        taskManager.getTaskById(id);
        taskManager.deleteTaskById(id);

        Assertions.assertFalse(taskManager.history().contains(task), "Ожидалась false(отсутствие задачи), вернулся true");
    }

    @Test
    public void deleteSubTaskByIdShouldDeleteTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        int id = taskManager.createNewSubTask(subtask);
        taskManager.deleteSubTaskById(id);

        Assertions.assertNull(taskManager.getSubtaskById(id), "Ожидался null (отсутствие задачи), вернулся не null");
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(subtask), "Ожидалась false " +
                "(отсутствие задачи), вернулся true");
        Assertions.assertFalse(epic.getSubTasks().contains(subtask), "Ожидалась false (отсутствие задачи), вернулся true");
    }

    @Test
    public void deleteSubTaskByIdShouldDeleteTaskFromHistory() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        int id = taskManager.createNewSubTask(subtask);
        taskManager.getSubtaskById(id);
        taskManager.deleteSubTaskById(id);

        Assertions.assertFalse(taskManager.history().contains(subtask), "Ожидалась false (отсутствие задачи), " +
                "вернулся true");
    }

    @Test
    public void deleteEpicByIdShouldDeleteTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        int id = taskManager.createNewEpic(epic);
        taskManager.deleteEpicById(id);

        Assertions.assertNull(taskManager.getEpicById(id), "Ожидался null (отсутствие задачи), вернулся не null");
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(epic), "Ожидалась false (отсутствие задачи), " +
                "вернулся true");
    }

    @Test
    public void deleteEpicByIdShouldDeleteTaskFromHistory() {
        setupTasks();
        int id = taskManager.createNewEpic(epic);
        taskManager.getEpicById(id);
        taskManager.deleteEpicById(id);

        Assertions.assertFalse(taskManager.history().contains(epic), "Ожидалась false (отсутствие задачи), " +
                "вернулся true");
    }

    @Test
    public void deleteEpicByIdShouldDeleteSubTaskFromMapAndPrioritizedTasks() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.deleteEpicById(epic.getId());

        Assertions.assertFalse(taskManager.getAllSubtasks().contains(subtask), "Ожидалась false (отсутствие задачи), " +
                "вернулся true");
        Assertions.assertFalse(taskManager.getPrioritizedTasks().contains(subtask), "Ожидалась false (отсутствие задачи), " +
                "вернулся true");
    }

    @Test
    public void deleteEpicByIdShouldDeleteHisSubtasksTaskFromHistory() {
        setupTasks();
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.getSubtaskById(subtask.getId());
        taskManager.deleteEpicById(epic.getId());

        Assertions.assertFalse(taskManager.getAllSubtasks().contains(subtask), "Ожидалась false (отсутствие задачи), " +
                "вернулся true");
        Assertions.assertFalse(taskManager.history().contains(subtask), "Ожидалась false (отсутствие задачи), " +
                "вернулся true");
    }

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

        Assertions.assertArrayEquals(subtasks, taskManager.getEpicSubtasks(epic).toArray(), "Ожидался список сабтасок, " +
                "пришёл другого содержания");
    }

    @Test
    public void getEpicSubtasksShouldReturnEmptySubtasksList() {
        setupTasks();
        taskManager.createNewEpic(epic);

        Assertions.assertTrue(taskManager.getEpicSubtasks(epic).isEmpty(), "Ожидался Empty список сабтасок, " +
                "пришёл не пустой");
    }

    @Test
    public void getEpicSubtasksShouldReturnEmptySubtasksListWhenEpicIsNullOrWrongID() {
        setupTasks();
        Assertions.assertTrue(taskManager.getEpicSubtasks(epic).isEmpty(), "Ожидался Empty список сабтасок," +
                "пришёл не пустой");
        epic = null;

        Assertions.assertTrue(taskManager.getEpicSubtasks(epic).isEmpty(), "Ожидался Empty список сабтасок, " +
                "пришёл не пустой");
    }

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
                "по времени старта, вернулся не в тот который ожидался ");
    }

    @Test
    public void getPrioritizedTasksShouldReturnEmptySortedSetByStartTime() {
        setupTasks();

        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой список, вернулся не пустой");
    }

    @Test
    public void clearAllShouldRemoveAllTypeOfTaskEverywhere() {
        setupTasks();
        taskManager.createNewTask(task);
        taskManager.createNewEpic(epic);
        subtask.setEpicID(epic.getId());
        taskManager.createNewSubTask(subtask);
        taskManager.clearAll();

        Assertions.assertTrue(taskManager.getAllTasks().isEmpty(), "Ожидался пустой список, вернулся не пустой");
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty(), "Ожидался пустой список, вернулся не пустой");
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty(), "Ожидался пустой список, вернулся не пустой");
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty(), "Ожидался пустой список, вернулся не пустой");
    }

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

        Assertions.assertArrayEquals(tasks, taskManager.history().toArray(), "Ожидался список в порядке запроса " +
                "задач, вернулся не в том порядке который ожидался");
    }

    @Test
    public void timeNotBusyShouldReturnTrueWhenNoIntersaction() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 5, 13, 10, 0));

        Assertions.assertTrue(taskManager.timeNotBusy(task2), "Ожидался TRUE когда нет пересечения по времени, " +
                "вернулся False");
    }

    @Test
    public void timeNotBusyShouldReturnFalseWhenIntersaction() {
        setupTasks();
        taskManager.createNewTask(task);
        Task task2 = new Task("задача 1", "описание 1", StatusMarker.NEW, Duration.ofMinutes(500),
                LocalDateTime.of(2023, 4, 12, 10, 0));

        Assertions.assertFalse(taskManager.timeNotBusy(task2), "Ожидался False когда нет пересечения по времени, " +
                "вернулся TRUE");
    }

    @Test
    public void timeNotBusyShouldReturnTrueWhenArgumentNull() {
        setupTasks();

        Assertions.assertTrue(taskManager.timeNotBusy(null), "Ожидался TRUE когда нет пересечения по времени, " +
                "вернулся False");
    }

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

        Assertions.assertEquals(StatusMarker.NEW, epic.getStatus(), "Ожидался статус NEW , вернулся не NEW");
    }

    @Test
    public void updateEpicStatusShouldBeNewWhenNoSubs() {
        setupTasks();
        taskManager.createNewEpic(epic);
        taskManager.updateEpicStatus(epic.getId());

        Assertions.assertEquals(StatusMarker.NEW, epic.getStatus(), "Ожидался статус NEW , вернулся не NEW");
    }

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

        Assertions.assertEquals(StatusMarker.DONE, epic.getStatus(), "Ожидался статус DONE , вернулся не DONE");
    }

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

        Assertions.assertEquals(StatusMarker.IN_PROGRESS, epic.getStatus(), "Ожидался статус IN_PROGRESS , " +
                "вернулся не IN_PROGRESS");
    }

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

        Assertions.assertEquals(StatusMarker.IN_PROGRESS, epic.getStatus(), "Ожидался статус IN_PROGRESS , " +
                "вернулся не IN_PROGRESS");
    }
}
