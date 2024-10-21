package SOLID_GRASP;

public class RecurringTask extends Task {
    String recurrenceFrequency;
    public RecurringTask(String title, String description, String dueDate) {
        super(title, description, dueDate);
        this.recurrenceFrequency = "Daily";
    }
    public RecurringTask(String title, String description, String dueDate, String recurrenceFrequency) {
        super(title, description, dueDate);
        this.recurrenceFrequency = recurrenceFrequency;
    }
    public RecurringTask(String title, String description, String dueDate, SharedDataTypes.Priority priority, SharedDataTypes.Status status) {
        super(title, description, dueDate, priority, status);
        this.recurrenceFrequency = "Daily";
    }
    public RecurringTask(String title, String description, String dueDate, SharedDataTypes.Priority priority, SharedDataTypes.Status status, String recurrenceFrequency) {
        super(title, description, dueDate, priority, status);
        this.recurrenceFrequency = recurrenceFrequency;
    }
    public String getRecurrenceFrequency() {
        return recurrenceFrequency;
    }
}
