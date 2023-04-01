package managers;

import exceptions.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTasksManager(File file) {
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
        int id = super.createNewTask(task);
        save();
        return id;
    }

    @Override
    public Task updateTaskById(Task task) {
        Task newTask = super.updateTaskById(task);
        save();
        return newTask;
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

    public void updateEpicStatus(Subtask task) {
        super.updateEpicStatus(task.getEpicID());
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
            if (tasks.isEmpty() && epics.isEmpty() && subtasks.isEmpty()) {
                wr.write("");
            }

            wr.write("id,type,name,status,description,epicID,startTime,duration,endTime" + "\n");

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
            // throw new RuntimeException(e);
            throw new ManagerSaveException("Can't read form file: " + file.getName(), e);
        }
    }

    private Task fromString(String value) {              // метод создания задачи из строки
        String[] taskString = value.split(","); // id,type1,name2,status 3,description 4,epicID 5,startTime 6,duration 7,endTime 8

        if (taskString.length < 5) {
            return null;
        }

        if (taskString[1].equals(TypeOfTasks.TASK.name())) { // id, name, description, status, duration, startTime
            return new Task(Integer.parseInt(taskString[0]), taskString[2], taskString[4], StatusMarker.valueOf(taskString[3]), Duration.parse(taskString[7]), LocalDateTime.parse(taskString[6]));
        }

        if (taskString[1].equals(TypeOfTasks.EPIC.name())) { // id, name,description, status, duration, startTime, endTime
            LocalDateTime startTime = taskString[6].equals("null") ? null : LocalDateTime.parse(taskString[6]);
            LocalDateTime endTime = taskString[8].equals("null") ? null : LocalDateTime.parse(taskString[8]);
            Duration duration = taskString[7].equals("null") ? null : Duration.parse(taskString[7]);
            return new Epic(Integer.parseInt(taskString[0]), taskString[2], taskString[4], StatusMarker.valueOf(taskString[3]), duration, startTime, endTime);
        }

        if (taskString[1].equals(TypeOfTasks.SUBTASK.name())) { //id, name, description,  status,  duration,  startTime,  epicID
            return new Subtask(Integer.parseInt(taskString[0]), taskString[2], taskString[4], StatusMarker.valueOf(taskString[3]), Duration.parse(taskString[7]), LocalDateTime.parse(taskString[6]), Integer.parseInt(taskString[5]));
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
        if (value == null || value.isBlank()) {
            return list;
        }
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

    public File getFile() {
        return file;
    }

    public static FileBackedTasksManager loadFromFile(File file) {      // метод восстановления из CSV
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String input = bufferedReader.readLine();
            while (!input.isBlank()) {
                Task task = fileBackedTasksManager.fromString(input);
                if (task != null) {
                    if (task instanceof Epic) {
                        fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                    } else if (task instanceof Subtask) {
                        fileBackedTasksManager.subtasks.put(task.getId(), (Subtask) task);
                    } else {
                        fileBackedTasksManager.tasks.put(task.getId(), task);
                    }
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
            fileBackedTasksManager.returnSubtasksToEpic();
            return fileBackedTasksManager;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void returnSubtasksToEpic() {
        for (Epic epic : epics.values()) {
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getEpicID() == epic.getId()) {
                    epic.getSubTasks().add(subtask);
                }
            }
        }
    }

}



