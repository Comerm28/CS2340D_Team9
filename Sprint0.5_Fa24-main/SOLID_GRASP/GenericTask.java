package SOLID_GRASP;

import java.util.ArrayList;
import java.util.List;

public class GenericTask extends Task {
    List<Task> specificTasks;
    public GenericTask(String title, String description, String dueDate) {
        super(title, description, dueDate);
        this.specificTasks = new ArrayList<>();
    }
    public GenericTask(String title, String description, String dueDate, List<Task> specificTasks) {
        super(title, description, dueDate);
        this.specificTasks = new ArrayList<>(specificTasks);
    }
    public GenericTask(String title, String description, String dueDate, SharedDataTypes.Priority priority, SharedDataTypes.Status status) {
        super(title, description, dueDate, priority, status);
        this.specificTasks = new ArrayList<>();
    }
    public GenericTask(String title, String description, String dueDate, SharedDataTypes.Priority priority, SharedDataTypes.Status status, List<Task> specificTasks) {
        super(title, description, dueDate, priority, status);
        this.specificTasks = new ArrayList<>(specificTasks);
    }
    public List<Task> getSpecificTasks() {
        return new ArrayList<>(specificTasks);
    }
    public void addSpecificTask(Task specificTask) {
        specificTasks.add(specificTask);
    }
    public void removeSpecificTask(Task specificTask) {
        specificTasks.remove(specificTask);
    }
}
