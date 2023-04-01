import managers.FileBackedTasksManager;
import managers.Managers;
import managers.TaskManager;
import tasks.Epic;
import tasks.StatusMarker;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        System.out.println(System.lineSeparator() + "Проверка работы программы - будем использовать тесты согласно ТЗ:" + System.lineSeparator());

        System.out.println("Создаёам файл по адресу resourses\\saves.csv\n");

        File file = new File("resourses\\saves.csv");

        TaskManager fileBackedTaskManager = Managers.getDefaultFileBacked(file);



        //Формирование двух задач
        System.out.println("Формирование двух задач типа Task: Task 1 и 2.\n");

/*
        Task task1 = new Task("Task 1", "Описание Task 1", StatusMarker.NEW,Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 10,00));
        fileBackedTaskManager.createNewTask(task1);
        System.out.println(task1);

        Task task2 = new Task("Task 2", "Описание Task 2", StatusMarker.NEW,Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 12,00));
        fileBackedTaskManager.createNewTask(task2);
        System.out.println(task2);



        //Формирование двух Эпиков
        System.out.println("Формирование двух задач типа Epic: Epic 1 и 2.\n");

        Epic epic1 = new Epic("Новая задача типа Epic 1", "Epic 1 у него три подзадачи", StatusMarker.NEW); // с тремя подзадачами
        fileBackedTaskManager.createNewEpic(epic1);
      //  System.out.println(epic1);

        Epic epic2 = new Epic("Новая задача типа Epic 2", "Epic 2 у него нет подзадач", StatusMarker.NEW,
                Duration.ofMinutes(300),LocalDateTime.of(2023,4,5,10,00),
                LocalDateTime.of(2023,4,5,10,00).plus(Duration.ofMinutes(300)));
        fileBackedTaskManager.createNewEpic(epic2);
     //   System.out.println(epic2);

        System.out.println("\nФормирование трёх подзадач типа subTask к Epic 1: subTask 1, 2 и 3.\n");
        Subtask subtask1 = new Subtask("Подзадача типа subTask 1 к Epic 1", "Описание subTask 1", StatusMarker.NEW,
                Duration.ofMinutes(60),LocalDateTime.of(2023,4,10,20,0) ,epic1.getId());
        fileBackedTaskManager.createNewSubTask(subtask1);
     //   System.out.println(epic1);

        Subtask subtask2 = new Subtask("Подзадача типа subTask 2 к Epic 1", "Описание subTask 2", StatusMarker.NEW,
                Duration.ofMinutes(60),LocalDateTime.of(2023,4,11,22,0) ,epic1.getId());
        fileBackedTaskManager.createNewSubTask(subtask2);
     //   System.out.println(epic1);


        Subtask subtask3 = new Subtask("Подзадача типа subTask 3 к Epic 1", "Описание subTask 3", StatusMarker.NEW,
                Duration.ofMinutes(15),LocalDateTime.of(2023,4,12,23,50) ,epic1.getId());
        fileBackedTaskManager.createNewSubTask(subtask3);
      //  System.out.println(epic1);


        System.out.println("\n......Первое обращение к задачам ( -> 1, 2, 3, 4, 5, 6, 7 ->):");
        task1 = fileBackedTaskManager.getTaskById(task1.getId());
        task2 = fileBackedTaskManager.getTaskById(task2.getId());
        epic1 = fileBackedTaskManager.getEpicById(epic1.getId());
        epic2 = fileBackedTaskManager.getEpicById(epic2.getId());
        subtask1 = fileBackedTaskManager.getSubtaskById(subtask1.getId());
        subtask2 = fileBackedTaskManager.getSubtaskById(subtask2.getId());
        subtask3 = fileBackedTaskManager.getSubtaskById(subtask3.getId());


        //Просмотр истории обращения
        System.out.println("Список обращений к задачам:");
        for (Task task : fileBackedTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }

        System.out.println("\n......Второе обращение к задачам ( -> 3, 4, 5, 6, 7, 1, 2 -> ):");
        epic1 = fileBackedTaskManager.getEpicById(epic1.getId());
        epic2 = fileBackedTaskManager.getEpicById(epic2.getId());
        subtask1 = fileBackedTaskManager.getSubtaskById(subtask1.getId());
        subtask2 = fileBackedTaskManager.getSubtaskById(subtask2.getId());
        subtask3 = fileBackedTaskManager.getSubtaskById(subtask3.getId());
        task1 = fileBackedTaskManager.getTaskById(task1.getId());
        task2 = fileBackedTaskManager.getTaskById(task2.getId());


        //Просмотр истории обращения
        System.out.println("Список обращений к задачам:");
        for (Task task : fileBackedTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }


        System.out.println("\n......Третье обращение к задачам ( -> 5, 3, 4, 7, 1, 6, 2 -> ):");
        subtask1 = fileBackedTaskManager.getSubtaskById(subtask1.getId());
        epic1 = fileBackedTaskManager.getEpicById(epic1.getId());
        epic2 = fileBackedTaskManager.getEpicById(epic2.getId());
        subtask3 = fileBackedTaskManager.getSubtaskById(subtask3.getId());
        task1 = fileBackedTaskManager.getTaskById(task1.getId());
        subtask2 = fileBackedTaskManager.getSubtaskById(subtask2.getId());
        task2 = fileBackedTaskManager.getTaskById(task2.getId());



        //Просмотр истории обращения
        System.out.println("Список обращений к задачам:");
        for (Task task : fileBackedTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }
*/
/*
        //Удаляем задачу task1 которая есть в истории
        System.out.println("\nУдалим задачу, которая есть в истории, и убедимся, что при печати она не будет выводиться");
        System.out.println("Удаляем - #1 - Задача 1(Описание 1) NEW");
        fileBackedTaskManager.deleteTaskById(task1.getId())
        ;

        //Просмотр истории обращения
        System.out.println("Выводим историю после:");

        for (Task task : fileBackedTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }

        //Удаляем задачу epic1 которая есть в истории
        System.out.println("\nУдалим эпик с тремя подзадачами и убедимся, что из истории удалился как сам эпик, так и все его подзадачи.");
        System.out.println("Удаляем - #3 - Новая задача типа Epic 1(Epic 1 у него три подзадачи) NEW");
        System.out.println("Его подзадачи: #7 - Подзадача типа subTask 3 к Epic 1(Описание subTask 3) NEW\n" +
                "#6 - Подзадача типа subTask 2 к Epic 1(Описание subTask 2) NEW\n" +
                "#5 - Подзадача типа subTask 1 к Epic 1(Описание subTask 1) NEW");
        fileBackedTaskManager.deleteEpicById(epic1.getId());

        //Просмотр истории обращения
        System.out.println("\nВыводим историю после:");

        for (Task task : fileBackedTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }*//*


        System.out.println("Создаём новый объект типа managerFromFile из файла методом loadFromFile(file):\n");
        FileBackedTasksManager managerFromFile = FileBackedTasksManager.loadFromFile(file);

        System.out.println("\nВыводим данные по всем task из файла:");
        System.out.println(managerFromFile.getAllTasks());
        System.out.println("\nВыводим данные по всем epic из файла:");
        System.out.println(managerFromFile.getAllEpics());
        System.out.println("\nВыводим данные по всем subtask из файла:");
        System.out.println(managerFromFile.getAllSubtasks());
        System.out.println("\nВыводим данные по истории из файла:");
        System.out.println(managerFromFile.history());


        for (Task prioritetedTask : fileBackedTaskManager.getPrioritizedTasks()) {
            System.out.println(prioritetedTask);
        }
*/


        Task task1 = new Task("Task 1", "Описание Task 1", StatusMarker.NEW,Duration.ofMinutes(180),
                LocalDateTime.of(2023, 4, 1, 10,0));
        fileBackedTaskManager.createNewTask(task1);
      //  System.out.println(task1);

        Task task2 = new Task("Task 2", "Описание Task 2", StatusMarker.NEW,Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 1, 11,0));
        fileBackedTaskManager.createNewTask(task2);
      //  System.out.println(task2);

        Task task3 = new Task("Task 3", "Описание Task 3", StatusMarker.NEW,Duration.ofMinutes(60),
                LocalDateTime.of(2023, 4, 3, 11,0));
        fileBackedTaskManager.createNewTask(task3);

        Epic epic1 = new Epic("Epic 1", "Epic 1 у него три подзадачи", StatusMarker.NEW); // с тремя подзадачами
        fileBackedTaskManager.createNewEpic(epic1);
        //  System.out.println(epic1);

/*        Epic epic2 = new Epic("Новая задача типа Epic 2", "Epic 2 у него нет подзадач", StatusMarker.NEW,
                Duration.ofMinutes(300),LocalDateTime.of(2023,4,5,10,00),
                LocalDateTime.of(2023,4,5,10,0).plus(Duration.ofMinutes(300)));
        fileBackedTaskManager.createNewEpic(epic2);*/
        //   System.out.println(epic2);

      //  System.out.println("\nФормирование трёх подзадач типа subTask к Epic 1: subTask 1, 2 и 3.\n");
        Subtask subtask1 = new Subtask("subTask 1", "к Epic 1", StatusMarker.NEW,
                Duration.ofMinutes(60),LocalDateTime.of(2023,4,2,15,0) ,epic1.getId());
        fileBackedTaskManager.createNewSubTask(subtask1);
        //   System.out.println(epic1);

        Subtask subtask2 = new Subtask("subTask 2", "к Epic 1", StatusMarker.NEW,
                Duration.ofMinutes(60),LocalDateTime.of(2023,4,1,11,0) ,epic1.getId());
        fileBackedTaskManager.createNewSubTask(subtask2);
      //     System.out.println(epic1);


        Subtask subtask3 = new Subtask("subTask 3", "к Epic 1", StatusMarker.NEW,
                Duration.ofMinutes(15),LocalDateTime.of(2023,4,6,12,50) ,epic1.getId());
        fileBackedTaskManager.createNewSubTask(subtask3);
     //     System.out.println(epic1);







     //   System.out.println(fileBackedTaskManager.timeNotBusy(task2));
        for (Task allTask : fileBackedTaskManager.getPrioritizedTasks()) {
            System.out.println(allTask);
        }


    }
}