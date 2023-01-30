package managers;

import tasks.Task;

import java.util.ArrayList;


public interface HistoryManager {

    void add (Task task);
    ArrayList<Task>  getHistory ();
    void printHistory();   // вспомогательный метод для контроля и визуализации работы программы

}
