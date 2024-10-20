public abstract class Task {
    protected String title;
    protected String description;
    protected String dueDate;
    protected SharedDataTypes.Priority priority;
    protected SharedDataTypes.Status status;

    // Constructor to initialize Task fields
    protected Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = SharedDataTypes.Priority.LOW_PRIORITY; // By default
        this.status = SharedDataTypes.Status.NOT_STARTED; // By default
    }

    protected Task(String title, String description, String dueDate, SharedDataTypes.Priority priority, SharedDataTypes.Status status) {
        this(title, description, dueDate);
        this.priority = priority;
        this.status = status;
    }

    // Getter methods for Task properties
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public SharedDataTypes.Priority getPriority() {
        return priority;
    }

    public void setPriority(SharedDataTypes.Priority priority) {
        this.priority = priority;
    }

    public SharedDataTypes.Status getStatus() {
        return status;
    }

    public void setStatus(SharedDataTypes.Status status) {
        this.status = status;
    }
}

