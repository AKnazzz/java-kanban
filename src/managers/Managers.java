package managers;

import java.io.File;

/* Утилитарный класс,
должен сам подбирать нужную реализацию TaskManager и возвращать объект правильного типа.
 */
public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultFileBacked (File file) {
        return new FileBackedTasksManager(file);
    }

}
