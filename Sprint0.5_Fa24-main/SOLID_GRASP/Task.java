public abstract class Task {
    protected String title;
    protected String description;
    protected String dueDate;
    protected SharedDataTypes.Priority priority;
    protected SharedDataTypes.Status status;

    // Constructor to initialize Task fields
    public Task(String title, String description, String dueDate, SharedDataTypes.Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = SharedDataTypes.Status.NOT_STARTED; // Default status is NOT_STARTED
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

    // Method to update task status
    public void updateStatus(SharedDataTypes.Status newStatus) {
        this.status = newStatus;
    }

    // Method to mark task as completed
    public boolean isCompleted() {
        return status == SharedDataTypes.Status.COMPLETED;
    }

    // Abstract method to define specific task behavior
    public abstract void performTask();
}
