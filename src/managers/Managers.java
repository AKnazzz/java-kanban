package managers;

public class Managers { // Утилитарный класс,
                        // должен сам подбирать нужную реализацию TaskManager и возвращать объект правильного типа.
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    public static HistoryManager getHistory(){
        return new InMemoryHistoryManager();
    }

}
