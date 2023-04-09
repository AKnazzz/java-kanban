import tracker.managers.*;
import tracker.tasks.Epic;
import tracker.tasks.Status;
import tracker.tasks.Subtask;
import tracker.tasks.Task;
import tracker.util.Managers;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static tracker.tasks.Status.NEW;

public class Main {

    public static void main(String[] args) throws IOException {

        new KVServer().start();
        TaskManager taskManager = Managers.getDefault();
        System.out.println(((HTTPTaskManager) (taskManager)).getApiKey());
        HttpTaskServer taskServer = new HttpTaskServer(taskManager);

        //тестирования работы программы

        Task o;

        // Создаем две задачи
        Task firstTask = new Task("Сходить в магазин", "Купить хлеб и молоко", Status.NEW);
        Task secondTask = new Task("Полить цветы", "Полить цветы в гостиной и спальне", Status.NEW);

        // Добавляем задачи в менеджер
        taskManager.addTask(firstTask, 0);
        taskManager.addTask(secondTask, 0);

        LocalDateTime startTime = LocalDateTime.of(2023, 04, 01, 1, 20);
        Duration duration = Duration.ofMinutes(20);

        // Создаем эпик и три подзадачи
        Epic firstEpic = new Epic("Съездить на дачу",
                "В выходные необходимо заехать на дачу на автомобиле", Status.NEW);
        taskManager.addTask(firstEpic, 0);

        Subtask firstSubtask = new Subtask("Заправить автомобиль",
                "Съездить на АЗС", NEW, firstEpic, startTime, duration);
        taskManager.addTask(firstSubtask, 0);

        Subtask secondSubtask = new Subtask("Погрузить вещи в машину",
                "Забрать все ненужное :)", NEW, firstEpic);
        taskManager.addTask(secondSubtask, 0);

        Subtask thirdSubtask = new Subtask("Взять ключи от дачи",
                "Обязательно взять ключи от дачи", NEW, firstEpic, startTime.plusDays(2).plusHours(4),
                Duration.ofMinutes(30));
        System.out.println("!" + thirdSubtask.toString());
        taskManager.addTask(thirdSubtask, 0);

        // Создаем второй эпик без подзадач
        Epic secondEpic = new Epic("Сделать презентацию",
                "Необходимо подготовить презентацию по новому проекту", Status.NEW);
        taskManager.addTask(secondEpic, 0);

        // Запрашиваем задачи, эпики и подзадачи в разном порядке и выводим историю
        // получение задачи по id

        System.out.println("Получаем вторую подзадачу по ID");
        o = taskManager.getTask(secondSubtask.getTaskId());
        System.out.println(o);

        System.out.println("Получаем первую задачу по ID");
        o = taskManager.getTask(firstTask.getTaskId());
        System.out.println(o);
        taskManager.history();

        System.out.println("Получаем вторую задачу по ID");
        o = taskManager.getTask(secondTask.getTaskId());
        System.out.println(o);
        taskManager.history();

        System.out.println("Еще раз получаем первую задачу по ID");
        o = taskManager.getTask(firstTask.getTaskId());
        System.out.println(o);
        taskManager.history();

        System.out.println("Получаем первый эпик по ID");
        o = taskManager.getTask(firstEpic.getTaskId());
        System.out.println(o);
        taskManager.history();

        System.out.println("Получаем второй эпик по ID");
        o = taskManager.getTask(secondEpic.getTaskId());
        System.out.println(o);
        taskManager.history();
    }
}