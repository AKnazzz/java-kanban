package managers;

import exceptions.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public int createNewTask(Task task) {
        int a = super.createNewTask(task);
        save();
        return a;
    }

    @Override
    public Task updateTaskById(Task task) {
        Task task1 = super.updateTaskById(task);
        save();
        return task1;
    }

    @Override
    public Task deleteTaskById(Integer id) {
        Task task = super.deleteTaskById(id);
        save();
        return task;
    }


    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public int createNewEpic(Epic epic) {
        int a = super.createNewEpic(epic);
        save();
        return a;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        Epic epic1 = super.updateEpic(epic);
        save();
        return epic1;
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }


    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public int createNewSubTask(Subtask subTask) {
        int a = super.createNewSubTask(subTask);
        save();
        return a;
    }

    @Override
    public Subtask updateSubTaskById(Subtask task) {
        Subtask subtask = super.updateSubTaskById(task);
        save();
        return subtask;
    }

    @Override
    public void setNewStatus(Subtask task) {
        super.setNewStatus(task);
        save();
    }

    @Override
    public Subtask deleteSubTaskById(Integer id) {
        Subtask subtask = super.deleteSubTaskById(id);
        save();
        return subtask;
    }

    private void save() {                                   // метод сохранения текущего состояние менеджера в файл
        try (FileWriter wr = new FileWriter(file)) {

            for (Task value : tasks.values()) {
                wr.write(value.toString() + "\n");
            }

            for (Epic value : epics.values()) {
                wr.write(value.toString() + "\n");
            }

            for (Subtask value : subtasks.values()) {
                wr.write(value.toString() + "\n");
            }

            wr.write("\n" + historyToString(historyManager));

        } catch (IOException e) {
            throw new ManagerSaveException("Что то пошло не так...");
        }
    }

    private Task fromString(String value) {              // метод создания задачи из строки
        String[] taskString = value.split(","); // id,type,name,status,description,epic

        if (taskString.length < 5) {
            return null;
        }

        if (taskString[1].equals(TypeOfTasks.TASK.name())) {
            return new Task(Integer.parseInt(taskString[0]), taskString[2], taskString[4], StatusMarker.valueOf(taskString[3]));
        }

        if (taskString[1].equals(TypeOfTasks.EPIC.name())) {
            return new Epic(Integer.parseInt(taskString[0]), taskString[2], taskString[4], StatusMarker.valueOf(taskString[3]));
        }

        if (taskString[1].equals(TypeOfTasks.SUBTASK.name())) {
            return new Subtask(Integer.parseInt(taskString[0]), taskString[2], taskString[4], StatusMarker.valueOf(taskString[3]), Integer.parseInt(taskString[5]));
        }

        return null;
    }

    private static String historyToString(HistoryManager manager) { // метод преобразования истории в строку с ID
        StringBuilder sb = new StringBuilder();
        for (Task task : manager.getHistory()) {
            sb.append(task.getId()).append(",");
        }
        return sb.toString();
    }

    private static List<Integer> historyFromString(String value) { // метод восстановления менеджера истории из CSV
        List<Integer> list = new ArrayList<>();
        String[] elements = value.split(",");
        for (String string : elements) {
            try {
                list.add(Integer.parseInt(string));
            } catch (NumberFormatException e) {
                System.err.println("Не могу распарсить " + string);
                ;
            }
        }
        return list;
    }

    public static FileBackedTasksManager loadFromFile(File file) {      // метод восстановления из CSV
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String input = bufferedReader.readLine();
            while (!input.isBlank()) {
                Task task = fileBackedTasksManager.fromString(input);
                if (task instanceof Epic) {
                    fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                } else if (task instanceof Subtask) {
                    fileBackedTasksManager.subtasks.put(task.getId(), (Subtask) task);
                } else if (task != null) {
                    fileBackedTasksManager.tasks.put(task.getId(), task);
                }
                input = bufferedReader.readLine();
            }

            String history = bufferedReader.readLine();
            List<Integer> historyList = historyFromString(history);
            for (Integer integer : historyList) {
                if (fileBackedTasksManager.tasks.containsKey(integer)) {
                    fileBackedTasksManager.getTaskById(integer);
                } else if (fileBackedTasksManager.epics.containsKey(integer)) {
                    fileBackedTasksManager.getEpicById(integer);
                } else if (fileBackedTasksManager.subtasks.containsKey(integer)) {
                    fileBackedTasksManager.getSubtaskById(integer);
                }
            }
            return fileBackedTasksManager;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}



