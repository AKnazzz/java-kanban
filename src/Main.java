import managers.Manager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.util.ArrayList;
import static tasks.StatusMarker.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Проверка работы программы - будем использовать вызовы методов с комментариями\n");

        Manager manager = new Manager();

        System.out.println("Проверка логики создания задач Task. Метод makeNewTask(task).");
        final Task task1 = new Task("Задача типа task_1", "Описание задачи task_1", -1, NEW);
        final Task task2 = new Task("Задача типа  task_2", "Описание задачи task_2", -1, NEW);

        System.out.println("Создание двух задач типа task");
        final Task newTask1 = manager.makeNewTask(task1);
        final Task newTask2 = manager.makeNewTask(task2);

        System.out.println("Выводим содержания двух задач типа task:");
        System.out.println(newTask1);
        System.out.println(newTask2);
        if (!task1.equals(newTask1) && !newTask1.equals(newTask2)) {
            System.out.println("Вывод: метод makeNewTask(task) корректен\n");
        } else {
            System.out.println("Вывод: метод makeNewTask(task) не корректен\n");
        }

        System.out.println("Проверка вывода всех задач Task. Метод getAllTasks().");
        ArrayList<Task> tasksArrayList = manager.getAllTasks();
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
        Task neededTask = manager.findTaskById(2);
        System.out.println("Выводим найденную задачу task");
        System.out.println(neededTask);
        if (neededTask.getId() != null) {
            System.out.println("Метод findTaskById(id) корректен\n");
        } else {
            System.out.println("Метод findTaskById(id) не корректен\n");
        }

        System.out.println("Проверка метода обновления задач типа Task. Метод updateTask(task).");
        final Task oldTask1 = manager.updateTask(newTask1);
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
        System.out.println(manager.findTaskById(1));
        manager.deleteTaskById(1);
        if (manager.findTaskById(1) == null) {
            System.out.println("Задача удалена. Метод deleteTaskById(id) корректен.\n");
        } else {
            System.out.println("Метод deleteTaskById(id) не корректен\n");
        }

        System.out.println("Проверка метода по удалению всех задач типа Task. Метод deleteAllTask().");
        manager.deleteAllTask();
        if (manager.getAllTasks().isEmpty()) {
            System.out.println("Метод deleteAllTask() корректен\n");
        } else {
            System.out.println("Метод deleteAllTask() не корректен\n");
        }

        System.out.println("Проверка метода по созданию задач типа Epic. Метод makeNewEpic(Epic).");
        final Epic epic = new Epic("Новая задача типа Epic", "Описание задачи типа Epic", -1);
        System.out.println("Делаем две задачи типа Epic");
        final Epic newEpic1 = manager.makeNewEpic(epic);
        final Epic newEpic2 = manager.makeNewEpic(epic);
        System.out.println("Вывод содержания двух созданных задач типа Epic");
        System.out.println(newEpic1);
        System.out.println(newEpic2);
        if (!epic.equals(newEpic1) && !newEpic1.equals(newEpic2)) {
            System.out.println("Метод makeNewEpic(Epic) корректен\n");
        } else {
            System.out.println("Метод makeNewEpic(Epic) не корректен\n");
        }

        System.out.println("Проверка метода по выводу списка всех задач типа Epic. Метод getAllEpics().");
        ArrayList<Epic> epicArrayList = manager.getAllEpics();
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
        final SubTask subTask = new SubTask("Подзадача типа subTask", "Описание подзадачи типа subTask", -1, 1);
        System.out.println("Делаем две подзадачи типа subTask в рамках одной задачи типа Epic:");
        final SubTask subTask1 = manager.makeNewSubTask(subTask, newEpic1);
        final SubTask subTask2 = manager.makeNewSubTask(subTask, newEpic1);
        System.out.println("Выводим данные этих двух подзадач: ");
        System.out.println(subTask1);
        System.out.println(subTask2);
        System.out.println("Делаем две подзадачи типа subTask в рамках другой задачи типа Epic:");
        final SubTask subTask3 = manager.makeNewSubTask(subTask, newEpic2);
        final SubTask subTask4 = manager.makeNewSubTask(subTask, newEpic2);
        System.out.println("Выводим данные этих двух подзадач: ");
        System.out.println(subTask3);
        System.out.println(subTask4);
        if (subTask1.getEpicID().equals(subTask2.getEpicID()) && subTask3.getEpicID().equals(subTask4.getEpicID())) {
            System.out.println("Метод makeNewSubTask(subTask, Epic) корректен\n");
        } else {
            System.out.println("Метод makeNewSubTask(subTask, Epic) не корректен\n");
        }

        System.out.println("Проверка метода по поиску подзадачи типа subtask по номеру ID.  Метод findSubTaskById(id).");
        SubTask neededSubTask = manager.findSubTaskById(2);
        System.out.println("Выводим данные необходимой подзадачи типа subTask: ");
        System.out.println(neededSubTask);
        if (neededTask.getId() != null) {
            System.out.println("Метод findSubTaskById(id) корректен\n");
        } else {
            System.out.println("Метод findSubTaskById(id) не корректен\n");
        }

        System.out.println("Проверка статуса задачи типа Epic ДО внесения изменений в Subtask: ");
        System.out.println(manager.findEpicById(1) + "\n");

        System.out.println("Проверка метода по внесению изменений в задачу типа subTask. Метод updateSubTask(subTask).");
        System.out.println("Вывод данных подзадачи типа subTask:");
        System.out.println(subTask1);
        System.out.println("Вносим изменения по статусу подзадачи - ставим DONE:");
        subTask1.setStatus(DONE);           // обновляем статус
        manager.updateSubTask(subTask1);
        System.out.println("Вывод данных подзадачи типа subTask после внесения изменений:");
        System.out.println(subTask1);
        if (subTask1.getStatus() == DONE) {
            System.out.println("Метод updateSubTask(subTask) корректен\n");
        } else {
            System.out.println("Метод updateSubTask(subTask) не корректен\n");
        }

        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ внесения изменений в Subtask: ");
        System.out.println(manager.findEpicById(1) + "\n");

        System.out.println("Проверка по изменения статусов и удаления подзадач типа subTask из Epic:");
        System.out.println("Выводим список всех подзадач Epic");
        ArrayList<SubTask> listEpics = manager.findAllSubTasksOfEpic(manager.findEpicById(2));
        System.out.println(listEpics + "\n");
        System.out.println("Также обновим и проверим статусы и возможность удаления подзадач типа subTask из Epic:");
        SubTask subTask5 = new SubTask("Подзадача типа subTask 5", "Описание subTask 5", 3, 2);
        SubTask subTask6 = new SubTask("Подзадача типа subTask 6", "Описание subTask 6", 4, 2);
        subTask5.setStatus(DONE);       // обновляем статус
        subTask6.setStatus(DONE);       // обновляем статус
        manager.updateSubTask(subTask5);
        manager.updateSubTask(subTask6);
        System.out.println("Выводим список всех подзадач Epic с изменениями:");
        System.out.println(listEpics + "\n");
        /*System.out.println("Выводим список всех подзадач Epic ПОСЛЕ внесения изменений в две Subtask: ");
        System.out.println(manager.findAllSubTasksOfEpic(manager.findEpicById(2))+ "\n");*/
        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ внесения изменений в две Subtask: ");
        System.out.println(manager.findEpicById(2)+ "\n");
        manager.deleteSubTaskById(3);
        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ удаления одной из Subtask: ");
        System.out.println(manager.findAllSubTasksOfEpic(manager.findEpicById(2)) + "\n");

        System.out.println("Проверка метода по выводу задачи типа Epic номеру ID. Метод findEpicById(id).");
        Epic epicById = manager.findEpicById(1);
        System.out.println("Выводим необходимую задачу типа Epic: ");
        System.out.println(epicById);
        if (1 == epicById.getId()) {
            System.out.println("Метод findEpicById(id) корректен\n");
        } else {
            System.out.println("Метод findEpicById(id) не корректен\n");
        }

        System.out.println("Проверка метода по удалению всех задач типа Epic. Метод deleteAllEpics().");
        manager.deleteAllEpics();
        if (manager.getAllEpics().isEmpty()) {
            System.out.println("Метод deleteAllEpics() корректен\n");
        } else {
            System.out.println("Метод deleteAllEpics() не корректен\n");
        }

    }
}