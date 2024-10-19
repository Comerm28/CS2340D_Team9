public abstract class Task {
    protected String title;
    protected String description;
    protected String dueDate;
    protected SharedDataTypes.Priority priority;
    protected SharedDataTypes.Status status;

    // Constructor to initialize Task fields
    protected Task(String title, String description, String dueDate, SharedDataTypes.Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = SharedDataTypes.Priority.LOW_PRIORITY; // By default
        this.status = SharedDataTypes.Status.NOT_STARTED; // By default
    }

    // Getter methods for Task properties
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public SharedDataTypes.Priority getPriority() {
        return priority;
    }

    public SharedDataTypes.Status getStatus() {
        return status;
    }

    // Method to update task priority
    public void updatePriority(SharedDataTypes.Priority newPriority) {
        this.priority = newPriority;
    }

    // Method to update task status
    public void updateStatus(SharedDataTypes.Status newStatus) {
        this.status = newStatus;
    }

    // Method to mark task as completed
    public boolean isCompleted() {
        return status == SharedDataTypes.Status.COMPLETED;
    }

    // Abstract method to define specific task behavior
    // If a task is recurring, then it could be stated through this method
    public abstract void performTask();
}

