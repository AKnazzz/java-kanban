package tracker.util;

import tracker.managers.*;

public class Managers {
    public static TaskManager getDefault() {
        return HTTPTaskManager.loadFromKVServer("http://localhost:8078");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
