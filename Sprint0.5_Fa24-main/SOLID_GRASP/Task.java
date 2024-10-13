public abstract class Task {
    protected String title;
    protected String description;
    protected String due_date;
    protected SharedDataTypes.Priority priority;
    protected SharedDataTypes.Status status;

    //TODO: make super constructor actually set the variables
    public Task()
    {
        title = "";
        description = "";
        due_date = "";
        priority = SharedDataTypes.Priority.LOW_PRIORITY;
        status = SharedDataTypes.Status.NOT_STARTED;
    }

    public boolean isCompleted()
    {
        return status == SharedDataTypes.Status.COMPLETED;
    }
}
