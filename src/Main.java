import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.StatusMarker;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

import static tasks.StatusMarker.*;


public class Main {
    public static void main(String[] args) {

        System.out.println("Проверка работы программы - будем использовать вызовы методов с комментариями\n");

        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();
        InMemoryHistoryManager inMemoryHistoryManager = (InMemoryHistoryManager) Managers.getDefaultHistory();


        //Формирование двух задач


        Task task1 = new Task(-1, "Задача 1", "Описание 1");
        task1 = inMemoryTaskManager.createNewTask(task1);

        Task task2 = new Task(-1, "Задача 2", "Описание 2");
        task2 = inMemoryTaskManager.createNewTask(task2);

        //Формирование двух Эпиков
        Epic epic1 = new Epic(-1, "Новая задача типа Epic 1", "Epic 1 у него три подзадачи"); // с тремя подзадачами
        epic1 = inMemoryTaskManager.createNewEpic(epic1);

        Epic epic2 = new Epic(-1, "Новая задача типа Epic 2", "Epic 2 Без подзадач");
        epic2 = inMemoryTaskManager.createNewEpic(epic2);

        //Три подзадачи к первому Эпику

        Subtask subtask1 = new Subtask(-1, "Подзадача типа subTask 1 к Epic 1", "Описание subTask 1", 30);
        subtask1 = inMemoryTaskManager.crateNewSubTask(subtask1, epic1);

        Subtask subtask2 = new Subtask(-1, "Подзадача типа subTask 2 к Epic 1", "Описание subTask 2", 30);
        subtask2 = inMemoryTaskManager.crateNewSubTask(subtask2, epic1);

        Subtask subtask3 = new Subtask(-1, "Подзадача типа subTask 3 к Epic 1", "Описание subTask 3", 30);
        subtask3 = inMemoryTaskManager.crateNewSubTask(subtask3, epic1);


        System.out.println("\n......Первое обращение к задачам ( -> 1, 2, 3, 4, 5, 6, 7 ->):");
        task1 = inMemoryTaskManager.getTaskById(task1.getId());
        task2 = inMemoryTaskManager.getTaskById(task2.getId());
        epic1 = inMemoryTaskManager.getEpicById(epic1.getId());
        epic2 = inMemoryTaskManager.getEpicById(epic2.getId());
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1.getId());
        subtask2 = inMemoryTaskManager.getSubtaskById(subtask2.getId());
        subtask3 = inMemoryTaskManager.getSubtaskById(subtask3.getId());



        //Просмотр истории обращения
        System.out.println("Список обращений к задачам:");

        for (Task task : inMemoryTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }

        System.out.println("\n......Второе обращение к задачам ( -> 3, 4, 5, 6, 7, 1, 2 -> ):");
        epic1 = inMemoryTaskManager.getEpicById(epic1.getId());
        epic2 = inMemoryTaskManager.getEpicById(epic2.getId());
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1.getId());
        subtask2 = inMemoryTaskManager.getSubtaskById(subtask2.getId());
        subtask3 = inMemoryTaskManager.getSubtaskById(subtask3.getId());
        task1 = inMemoryTaskManager.getTaskById(task1.getId());
        task2 = inMemoryTaskManager.getTaskById(task2.getId());


        //Просмотр истории обращения
        System.out.println("Список обращений к задачам:");

        for (Task task : inMemoryTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }


        System.out.println("\n......Третье обращение к задачам ( -> 5, 3, 4, 7, 1, 6, 2 -> ):");

        task2 = inMemoryTaskManager.getTaskById(task2.getId());
        subtask1 = inMemoryTaskManager.getSubtaskById(subtask1.getId());
        epic1 = inMemoryTaskManager.getEpicById(epic1.getId());
        epic2 = inMemoryTaskManager.getEpicById(epic2.getId());
        subtask3 = inMemoryTaskManager.getSubtaskById(subtask3.getId());
        task1 = inMemoryTaskManager.getTaskById(task1.getId());
        subtask2 = inMemoryTaskManager.getSubtaskById(subtask2.getId());
        task2 = inMemoryTaskManager.getTaskById(task2.getId());


        //Просмотр истории обращения
        System.out.println("Список обращений к задачам:");

        for (Task task : inMemoryTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }



        System.out.println("\nУдалим задачу, которая есть в истории, и убедимся, что при печати она не будет выводиться");
        System.out.println("Удаляем - #1 - Задача 1(Описание 1) NEW");
        inMemoryTaskManager.deleteTaskById(task1.getId());

        System.out.println("Выводим историю после:");
        for (Task task : inMemoryTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }

        System.out.println("\nУдалим эпик с тремя подзадачами и убедимся, что из истории удалился как сам эпик, так и все его подзадачи.");
        System.out.println("Удаляем - #3 - Новая задача типа Epic 1(Epic 1 у него три подзадачи) NEW");
        System.out.println("Его подзадачи: #7 - Подзадача типа subTask 3 к Epic 1(Описание subTask 3) NEW\n" +
                "#6 - Подзадача типа subTask 2 к Epic 1(Описание subTask 2) NEW\n" +
                "#5 - Подзадача типа subTask 1 к Epic 1(Описание subTask 1) NEW");
        inMemoryTaskManager.deleteEpicById(epic1.getId());

        System.out.println("\nВыводим историю после:");
        for (Task task : inMemoryTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }

// это старые тесты - которые использовал в прошлых двух ТЗ = закомментировал - при необходимости можно будет восстановить

      /*  System.out.println("Проверка логики создания задач Task. Метод createNewTask(task).");
        final Task task1 = new Task(-1, "Задача типа task_1", "Описание задачи task_1", NEW);
        final Task task2 = new Task(-1, "Задача типа  task_2", "Описание задачи task_2", NEW);

        System.out.println("Создание двух задач типа task");
        final Task newTask1 = inMemoryTaskManager.createNewTask(task1);
        final Task newTask2 = inMemoryTaskManager.createNewTask(task2);

        System.out.println("Выводим содержания двух задач типа task:");
        System.out.println(newTask1);
        System.out.println(newTask2);
        if (!task1.equals(newTask1) && !newTask1.equals(newTask2)) {
            System.out.println("Вывод: метод makeNewTask(task) корректен\n");
        } else {
            System.out.println("Вывод: метод makeNewTask(task) не корректен\n");
        }

        System.out.println("Проверка вывода всех задач Task. Метод getAllTasks().");
        ArrayList<Task> tasksArrayList = inMemoryTaskManager.getAllTasks();
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
        Task neededTask = inMemoryTaskManager.getTaskById(2);
        System.out.println("Выводим найденную задачу task");
        System.out.println(neededTask);
        if (neededTask.getId() != null) {
            System.out.println("Метод findTaskById(id) корректен\n");
        } else {
            System.out.println("Метод findTaskById(id) не корректен\n");
        }

        System.out.println("Проверка метода обновления задач типа Task. Метод updateTask(task).");
        final Task oldTask1 = inMemoryTaskManager.updateTaskById(newTask1);
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
        System.out.println(inMemoryTaskManager.getTaskById(1));
        inMemoryTaskManager.deleteTaskById(1);
        if (inMemoryTaskManager.getTaskById(1) == null) {
            System.out.println("Задача удалена. Метод deleteTaskById(id) корректен.\n");
        } else {
            System.out.println("Метод deleteTaskById(id) не корректен\n");
        }


        System.out.println("Проверка метода по удалению всех задач типа Task. Метод deleteAllTask().");
        inMemoryTaskManager.deleteAllTasks();
        if (inMemoryTaskManager.getAllTasks().isEmpty()) {
            System.out.println("Метод deleteAllTask() корректен\n");
        } else {
            System.out.println("Метод deleteAllTask() не корректен\n");
        }


        System.out.println("Проверка метода по созданию задач типа Epic. Метод makeNewEpic(Epic).");
        final Epic epic = new Epic(-1, "Новая задача типа Epic", "Описание задачи типа Epic");
        System.out.println("Делаем две задачи типа Epic");
        final Epic newEpic1 = inMemoryTaskManager.createNewEpic(epic);
        final Epic newEpic2 = inMemoryTaskManager.createNewEpic(epic);
        System.out.println("Вывод содержания двух созданных задач типа Epic");
        System.out.println(newEpic1);
        System.out.println(newEpic2);
        if (!epic.equals(newEpic1) && !newEpic1.equals(newEpic2)) {
            System.out.println("Метод makeNewEpic(Epic) корректен\n");
        } else {
            System.out.println("Метод makeNewEpic(Epic) не корректен\n");
        }

        System.out.println("Проверка метода по выводу списка всех задач типа Epic. Метод getAllEpics().");
        ArrayList<Epic> epicArrayList = inMemoryTaskManager.getAllEpics();
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
        final Subtask subTask = new Subtask(-1, "Подзадача типа subTask", "Описание подзадачи типа subTask", 1);
        System.out.println("Делаем две подзадачи типа subTask в рамках одной задачи типа Epic:");
        final Subtask subtask1 = inMemoryTaskManager.crateNewSubTask(subTask, newEpic1);
        final Subtask subtask2 = inMemoryTaskManager.crateNewSubTask(subTask, newEpic1);
        System.out.println("Выводим данные этих двух подзадач: ");
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println("Делаем две подзадачи типа subTask в рамках другой задачи типа Epic:");
        final Subtask subtask3 = inMemoryTaskManager.crateNewSubTask(subTask, newEpic2);
        final Subtask subtask4 = inMemoryTaskManager.crateNewSubTask(subTask, newEpic2);
        System.out.println("Выводим данные этих двух подзадач: ");
        System.out.println(subtask3);
        System.out.println(subtask4);
        if (subtask1.getEpicID().equals(subtask2.getEpicID()) && subtask3.getEpicID().equals(subtask4.getEpicID())) {
            System.out.println("Метод makeNewSubTask(subTask, Epic) корректен\n");
        } else {
            System.out.println("Метод makeNewSubTask(subTask, Epic) не корректен\n");
        }

        System.out.println("Проверка метода по поиску подзадачи типа Subtask по номеру ID.  Метод findSubTaskById(id).");
        Subtask neededSubtask = inMemoryTaskManager.getSubtaskById(6);
        System.out.println("Выводим данные необходимой подзадачи типа subTask: ");
        System.out.println(neededSubtask);
        if (neededTask.getId() != null) {
            System.out.println("Метод findSubTaskById(id) корректен\n");
        } else {
            System.out.println("Метод findSubTaskById(id) не корректен\n");
        }

        System.out.println("Проверка статуса задачи типа Epic ДО внесения изменений в Subtask: ");
        System.out.println(inMemoryTaskManager.getEpicById(3) + "\n");

        System.out.println("Проверка метода по внесению изменений в задачу типа subTask. Метод updateSubTask(subTask).");
        System.out.println("Вывод данных подзадачи типа subTask:");
        System.out.println(subtask1);
        System.out.println("Вносим изменения по статусу подзадачи - ставим DONE:");
        subtask1.setStatus(DONE);           // обновляем статус
        inMemoryTaskManager.updateSubTaskById(subtask1);
        System.out.println("Вывод данных подзадачи типа subTask после внесения изменений:");
        System.out.println(subtask1);
        if (subtask1.getStatus() == DONE) {
            System.out.println("Метод updateSubTask(subTask) корректен\n");
        } else {
            System.out.println("Метод updateSubTask(subTask) не корректен\n");
        }

        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ внесения изменений в Subtask: ");
        System.out.println(inMemoryTaskManager.getEpicById(3) + "\n");

        System.out.println("Проверка по изменения статусов и удаления подзадач типа subTask из Epic:");
        System.out.println("Выводим список всех подзадач Epic");
        ArrayList<Subtask> listEpics = inMemoryTaskManager.findAllOfEpic(inMemoryTaskManager.getEpicById(3));
        System.out.println(listEpics + "\n");
        System.out.println("Также обновим и проверим статусы и возможность удаления подзадач типа subTask из Epic:");
        Subtask subtask5 = new Subtask(7, "Подзадача типа subTask 5", "Описание subTask 5", 3);
        Subtask subtask6 = new Subtask(8, "Подзадача типа subTask 6", "Описание subTask 6", 3);
        subtask5.setStatus(DONE);       // обновляем статус
        subtask6.setStatus(DONE);       // обновляем статус
        inMemoryTaskManager.updateSubTaskById(subtask5);
        inMemoryTaskManager.updateSubTaskById(subtask6);
        System.out.println("Выводим список всех подзадач Epic с изменениями:");
        System.out.println(listEpics + "\n");
        //   System.out.println("Выводим список всех подзадач Epic ПОСЛЕ внесения изменений в две Subtask: ");
        //   System.out.println(manager.findAllSubTasksOfEpic(manager.findEpicById(2))+ "\n");
        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ внесения изменений в две Subtask: ");
        System.out.println(inMemoryTaskManager.getEpicById(3) + "\n");
        inMemoryTaskManager.deleteSubTaskById(5);
        System.out.println("Проверка статуса задачи типа Epic ПОСЛЕ удаления одной из Subtask: ");
        System.out.println(inMemoryTaskManager.findAllOfEpic(inMemoryTaskManager.getEpicById(3)) + "\n");

        System.out.println("Проверка метода по выводу задачи типа Epic номеру ID. Метод findEpicById(id).");
        Epic epicById = inMemoryTaskManager.getEpicById(3);
        System.out.println("Выводим необходимую задачу типа Epic: ");
        System.out.println(epicById);
        if (3 == epicById.getId()) {
            System.out.println("Метод findEpicById(id) корректен\n");
        } else {
            System.out.println("Метод findEpicById(id) не корректен\n");
        }

        System.out.println("Проверка метода по удалению всех задач типа Epic. Метод deleteAllEpics().");
        inMemoryTaskManager.deleteAllEpics();
        if (inMemoryTaskManager.getAllEpics().isEmpty() && inMemoryTaskManager.getAllSubtasks().isEmpty()) {
            System.out.println("Метод deleteAllEpics() корректен\n");
        } else {
            System.out.println("Метод deleteAllEpics() не корректен\n");
        }


        System.out.println("Проверка метода по смене статуса типа Epic при изменении статусов его Subtasks. Метод setNewStatus().");
        System.out.println("Создаём новую задачу типа Epic");
        final Epic newEpic5 = inMemoryTaskManager.createNewEpic(epic);
        System.out.println("Вывод содержания новой задачи типа Epic");
        System.out.println(newEpic5);
        System.out.println("Создаём две Subtask в этот Epic");
        final Subtask subtask55 = inMemoryTaskManager.crateNewSubTask(subTask, newEpic5);
        final Subtask subtask56 = inMemoryTaskManager.crateNewSubTask(subTask, newEpic5);
        System.out.println("Вывод обновлённого содержания задачи типа Epic");
        System.out.println(newEpic5);
        System.out.println("Устанавливаем статус одной из subtask - DONE");
        subtask55.setStatus(DONE);
        System.out.println("Запускаем метод для обновления статуса Epic");
        inMemoryTaskManager.setNewStatus(subtask55);
        inMemoryTaskManager.setNewStatus(subtask56);
        System.out.println("Вывод обновлённого содержания задачи типа Epic\n");
        System.out.println(newEpic5);


        System.out.println("Проверка метода по удалению Epic по ID (должны также удалиться все его subtask). Метод deleteEpicById().");
        System.out.println("выводим значение Epic");
        System.out.println(newEpic5.toString());
        final Epic newEpic6 = inMemoryTaskManager.createNewEpic(epic);
        final Subtask subtask7 = inMemoryTaskManager.crateNewSubTask(subTask, newEpic6);
        System.out.println("выводим значение всех подзадач - в списке будут подзадачи newEpic5");
        System.out.println(inMemoryTaskManager.getAllSubtasks());
        System.out.println("Удаляем значение Epic по ID методом deleteEpicById()");
        inMemoryTaskManager.deleteEpicById(9);
        System.out.println("выводим значение всех подзадач - в списке НЕ будет подзадач newEpic5, но останутся подзадачи других Эпиков\n");
        System.out.println(inMemoryTaskManager.getAllSubtasks());

        System.out.println("Проверка записи и вывода истории:");
        System.out.println("Создаём задачу типа Task");
        final Task task3 = new Task(-1, "Задача типа  task_3", "Описание задачи task_3", NEW);
        final Task newTask4 = inMemoryTaskManager.createNewTask(task1);
        final Task newTask5 = inMemoryTaskManager.createNewTask(task2);
        final Task newTask3 = inMemoryTaskManager.createNewTask(task3);

        inMemoryTaskManager.getTaskById(16);
        inMemoryTaskManager.getTaskById(14);
        inMemoryTaskManager.getTaskById(16);
        inMemoryTaskManager.getTaskById(15);
        inMemoryTaskManager.getTaskById(15);
        inMemoryTaskManager.getTaskById(15);
        inMemoryTaskManager.getTaskById(16);
        inMemoryTaskManager.getTaskById(14);
        inMemoryTaskManager.getTaskById(14);
        inMemoryTaskManager.getEpicById(12);
        inMemoryTaskManager.getSubtaskById(13);
        inMemoryTaskManager.getTaskById(16);
        inMemoryTaskManager.getTaskById(14);

        inMemoryTaskManager.historyManager.showHistory();*/

    }
}