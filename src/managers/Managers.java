package managers;

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

}
