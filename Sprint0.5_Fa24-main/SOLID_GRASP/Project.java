import java.util.ArrayList;
import java.util.List;

public class Project {
    private List<TeamMember> members;
    private List<Task> tasks;
    private String name;
    private String description;
    private String start_date;
    private String end_date;

    public Project(String name, String description, String start_date, String end_date)
    {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        members = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public Project(String name, String description, String start_date, String end_date, List<TeamMember> members, List<Task> tasks)
    {
        this.name = name;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        members = new ArrayList<>(members);
        tasks = new ArrayList<>(tasks);
    }

    public boolean add_task(Task new_task)
    {
        return tasks.add(new_task);
    }

    public boolean remove_task(Task removed_task)
    {
        return tasks.remove(removed_task);
    }

    public boolean add_member(TeamMember new_member)
    {
        return members.add(new_member);
    }

    public boolean remove_task(TeamMember removed_member)
    {
        return tasks.remove(removed_member);
    }
}
