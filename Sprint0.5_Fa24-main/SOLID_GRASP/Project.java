import java.util.ArrayList;
import java.util.List;

public class Project {
    private List<TeamMember> members;
    private List<Task> tasks;
    private String name;
    private String description;
    private String startDate;
    private String endDate;

    public Project(String name, String description, String startDate, String endDate)
    {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public Project(String name, String description, String startDate, String endDate, List<TeamMember> members, List<Task> tasks)
    {
        this(name, description, startDate, endDate);
        this.members = new ArrayList<>(members);
        this.tasks = new ArrayList<>(tasks);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public List<TeamMember> getMembers() {
        return new ArrayList<>(members);
    }

    public void addTask(Task newTask)
    {
        tasks.add(newTask);
    }

    public void removeTask(Task removedTask)
    {
        tasks.remove(removedTask);
    }

    public void addMember(TeamMember newMember)
    {
        members.add(newMember);
    }

    public void removeMember(TeamMember removedMember)
    {
        members.remove(removedMember);
    }
}
