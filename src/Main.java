import managers.InMemoryTaskManager;
import managers.Managers;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {

        System.out.println("\nПроверка работы программы - будем использовать тесты согласно ТЗ:\n");

        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();

        //Формирование двух задач
        System.out.println("Формирование двух задач типа Task: Task 1 и 2.\n");
        Task task1 = new Task(-1, "Task 1", "Описание 1");
        task1 = inMemoryTaskManager.createNewTask(task1);

        Task task2 = new Task(-1, "Task 2", "Описание 2");
        task2 = inMemoryTaskManager.createNewTask(task2);


        //Формирование двух Эпиков
        System.out.println("Формирование двух задач типа Epic: Epic 1 и 2.\n");
        Epic epic1 = new Epic(-1, "Новая задача типа Epic 1", "Epic 1 у него три подзадачи"); // с тремя подзадачами
        epic1 = inMemoryTaskManager.createNewEpic(epic1);

        Epic epic2 = new Epic(-1, "Новая задача типа Epic 2", "Epic 2 Без подзадач");
        epic2 = inMemoryTaskManager.createNewEpic(epic2);

        //Три подзадачи к первому Эпику
        System.out.println("Формирование трёх подзадач типа subTask к Epic 1: subTask 1, 2 и 3.\n");
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

        //Удаляем задачу task1 которая есть в истории
        System.out.println("\nУдалим задачу, которая есть в истории, и убедимся, что при печати она не будет выводиться");
        System.out.println("Удаляем - #1 - Задача 1(Описание 1) NEW");
        inMemoryTaskManager.deleteTaskById(task1.getId());

        //Просмотр истории обращения
        System.out.println("Выводим историю после:");
        for (Task task : inMemoryTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }

        //Удаляем задачу epic1 которая есть в истории
        System.out.println("\nУдалим эпик с тремя подзадачами и убедимся, что из истории удалился как сам эпик, так и все его подзадачи.");
        System.out.println("Удаляем - #3 - Новая задача типа Epic 1(Epic 1 у него три подзадачи) NEW");
        System.out.println("Его подзадачи: #7 - Подзадача типа subTask 3 к Epic 1(Описание subTask 3) NEW\n" +
                "#6 - Подзадача типа subTask 2 к Epic 1(Описание subTask 2) NEW\n" +
                "#5 - Подзадача типа subTask 1 к Epic 1(Описание subTask 1) NEW");
        inMemoryTaskManager.deleteEpicById(epic1.getId());

        //Просмотр истории обращения
        System.out.println("\nВыводим историю после:");
        for (Task task : inMemoryTaskManager.history()) {
            System.out.println("#" + task.getId() + " - " + task.getName() + "(" + task.getDescription() + ") " + task.getStatus());
        }

    }
}