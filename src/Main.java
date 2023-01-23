import managers.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import static tasks.StatusMarker.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Проверка работы программы - будем использовать вызовы методов с комментариями\n");

        TaskManager taskManager = new TaskManager();

        System.out.println("Проверка логики создания задач Task. Метод makeNewTask(task).");
        final Task task1 = new Task(-1,"Задача типа task_1", "Описание задачи task_1", NEW);
        final Task task2 = new Task(-1,"Задача типа  task_2", "Описание задачи task_2", NEW);

        System.out.println("Создание двух задач типа task");
        final Task newTask1 = taskManager.makeNewTask(task1);
        final Task newTask2 = taskManager.makeNewTask(task2);

        System.out.println("Выводим содержания двух задач типа task:");
        System.out.println(newTask1);
        System.out.println(newTask2);
        if (!task1.equals(newTask1) && !newTask1.equals(newTask2)) {
            System.out.println("Вывод: метод makeNewTask(task) корректен\n");
        } else {
            System.out.println("Вывод: метод makeNewTask(task) не корректен\n");
        }

        System.out.println("Проверка вывода всех задач Task. Метод getAllTasks().");
        ArrayList<Task> tasksArrayList = taskManager.getAllTasks();
        System.out.println("Выводим весь список задач типа Task:");
        for (Task value : tasksArrayList) {
            System.out.println(value);
        }
        if (tasksArrayList.isEmpty()) {
            System.out.println("Метод getAllTasks() не корректен\n");
        } else {
            System.out.println("Метод getAllTasks() корректен\n");
        }

        System.out.println("Проверка метода поиска и вывода задач типа Task по ID. Метод findTaskById(id).");
        Task neededTask = taskManager.findTaskById(2);
        System.out.println("Выводим найденную задачу task");
        System.out.println(neededTask);
        if (neededTask.getId() != null) {
            System.out.println("Метод findTaskById(id) корректен\n");
        } else {
            System.out.println("Метод findTaskById(id) не корректен\n");
        }

        System.out.println("Проверка метода обновления задач типа Task. Метод updateTask(task).");
        final Task oldTask1 = taskManager.updateTaskById(newTask1);
        System.out.println("Выводим переданную в метод и обновленную задачу:");
        System.out.println(newTask1);
        System.out.println(oldTask1);
        if (oldTask1.equals(newTask1)) {
            System.out.println("Метод updateTask(task) корректен\n");
        } else {
            System.out.println("Метод updateTask(task) не корректен\n");
        }

        System.out.println("Проверка метода по удалению задач Task по ID.Метод deleteTaskById(id).");
        System.out.println("Выводим задачу типа task которую будем удалять:");
        System.out.println(taskManager.findTaskById(1));
        taskManager.deleteTaskById(1);
        if (taskManager.findTaskById(1) == null) {
            System.out.println("Задача удалена. Метод deleteTaskById(id) корректен.\n");
        } else {
            System.out.println("Метод deleteTaskById(id) не корректен\n");
        }

        System.out.println("Проверка метода по удалению всех задач типа Task. Метод deleteAllTask().");
        taskManager.deleteAllTasks();
        if (taskManager.getAllTasks().isEmpty()) {
            System.out.println("Метод deleteAllTask() корректен\n");
        } else {
            System.out.println("Метод deleteAllTask() не корректен\n");
        }

        System.out.println("Проверка метода по созданию задач типа Epic. Метод makeNewEpic(Epic).");
        final Epic epic = new Epic(-1,"Новая задача типа Epic", "Описание задачи типа Epic");
        System.out.println("Делаем две задачи типа Epic");
        final Epic newEpic1 = taskManager.makeNewEpic(epic);
        final Epic newEpic2 = taskManager.makeNewEpic(epic);
        System.out.println("Вывод содержания двух созданных задач типа Epic");
        System.out.println(newEpic1);
        System.out.println(newEpic2);
        if (!epic.equals(newEpic1) && !newEpic1.equals(newEpic2)) {
            System.out.println("Метод makeNewEpic(Epic) корректен\n");
        } else {
            System.out.println("Метод makeNewEpic(Epic) не корректен\n");
        }

        System.out.println("Проверка метода по выводу списка всех задач типа Epic. Метод getAllEpics().");
        ArrayList<Epic> epicArrayList = taskManager.getAllEpics();
        System.out.println("Вывод список задач типа Epic:");
        for (Task value : epicArrayList) {
            System.out.println(value);
        }
        if (epicArrayList.isEmpty()) {
            System.out.println("Метод getAllEpics() не корректен\n");
        } else {
            System.out.println("Метод getAllEpics() корректен\n");
        }

        System.out.println("Проверка метода по созданию подзадач типа subTask. Метод makeNewSubTask(subTask, Epic).");
        final Subtask subTask = new Subtask(-1,"Подзадача типа subTask", "Описание подзадачи типа subTask",1);
        System.out.println("Делаем две подзадачи типа subTask в рамках одной задачи типа Epic:");
        final Subtask subtask1 = taskManager.makeNewSubTask(subTask, newEpic1);
        final Subtask subtask2 = taskManager.makeNewSubTask(subTask, newEpic1);
        System.out.println("Выводим данные этих двух подзадач: ");
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println("Делаем две подзадачи типа subTask в рамках другой задачи типа Epic:");
        final Subtask subtask3 = taskManager.makeNewSubTask(subTask, newEpic2);
        final Subtask subtask4 = taskManager.makeNewSubTask(subTask, newEpic2);
        System.out.println("Выводим данные этих двух подзадач: ");
        System.out.println(subtask3);
        System.out.println(subtask4);
        if (subtask1.getEpicID().equals(subtask2.getEpicID()) && subtask3.getEpicID().equals(subtask4.getEpicID())) {
            System.out.println("Метод makeNewSubTask(subTask, Epic) корректен\n");
        } else {
            System.out.println("Метод makeNewSubTask(subTask, Epic) не корректен\n");
        }

        System.out.println("Проверка метода по поиску подзадачи типа Subtask по номеру ID.  Метод findSubTaskById(id).");
        Subtask neededSubtask = taskManager.findSubTAskById(6);
        System.out.println("Выводим данные необходимой подзадачи типа subTask: ");
        System.out.println(neededSubtask);
        if (neededTask.getId() != null) {
            System.out.println("Метод findSubTaskById(id) корректен\n");
        } else {
            System.out.println("Метод findSubTaskById(id) не корректен\n");
        }

        System.out.println("Проверка статуса задачи типа Epic ДО внесения изменений в Subtask: ");
        System.out.println(taskManager.findEpicById(3) + "\n");

        System.out.println("Проверка метода по внесению изменений в задачу типа subTask. Метод updateSubTask(subTask).");
        System.out.println("Вывод данных подзадачи типа subTask:");
        System.out.println(subtask1);
        System.out.println("Вносим изменения по статусу подзадачи - ставим DONE:");
        subtask1.setStatus(DONE);           // обновляем статус
        taskManager.updateSubTaskById(subtask1);
        System.out.println("Вывод данных подзадачи типа subTask после внесения изменений:");
        System.out.println(subtask1);
        if (subtask1.getStatus() == DONE) {
            System.out.println("Метод updateSubTask(subTask) корректен\n");
        } else {
            System.out.println("Метод updateSubTask(subTask) не корректен\n");
        }

        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ внесения изменений в Subtask: ");
        System.out.println(taskManager.findEpicById(3) + "\n");

        System.out.println("Проверка по изменения статусов и удаления подзадач типа subTask из Epic:");
        System.out.println("Выводим список всех подзадач Epic");
        ArrayList<Subtask> listEpics = taskManager.findAllOfEpic(taskManager.findEpicById(3));
        System.out.println(listEpics + "\n");
        System.out.println("Также обновим и проверим статусы и возможность удаления подзадач типа subTask из Epic:");
        Subtask subtask5 = new Subtask(7,"Подзадача типа subTask 5", "Описание subTask 5", 3);
        Subtask subtask6 = new Subtask(8,"Подзадача типа subTask 6", "Описание subTask 6", 3);
        subtask5.setStatus(DONE);       // обновляем статус
        subtask6.setStatus(DONE);       // обновляем статус
        taskManager.updateSubTaskById(subtask5);
        taskManager.updateSubTaskById(subtask6);
        System.out.println("Выводим список всех подзадач Epic с изменениями:");
        System.out.println(listEpics + "\n");
        /*System.out.println("Выводим список всех подзадач Epic ПОСЛЕ внесения изменений в две Subtask: ");
        System.out.println(manager.findAllSubTasksOfEpic(manager.findEpicById(2))+ "\n");*/
        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ внесения изменений в две Subtask: ");
        System.out.println(taskManager.findEpicById(3)+ "\n");
        taskManager.deleteSubTaskById(5);
        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ удаления одной из Subtask: ");
        System.out.println(taskManager.findAllOfEpic(taskManager.findEpicById(3)) + "\n");

        System.out.println("Проверка метода по выводу задачи типа Epic номеру ID. Метод findEpicById(id).");
        Epic epicById = taskManager.findEpicById(3);
        System.out.println("Выводим необходимую задачу типа Epic: ");
        System.out.println(epicById);
        if (3 == epicById.getId()) {
            System.out.println("Метод findEpicById(id) корректен\n");
        } else {
            System.out.println("Метод findEpicById(id) не корректен\n");
        }

        System.out.println("Проверка метода по удалению всех задач типа Epic. Метод deleteAllEpics().");
        taskManager.deleteAllEpics();
        if (taskManager.getAllEpics().isEmpty()) {
            System.out.println("Метод deleteAllEpics() корректен\n");
        } else {
            System.out.println("Метод deleteAllEpics() не корректен\n");
        }

    }
}