import java.util.List;
import java.util.ArrayList;

public abstract class TeamMember {
    protected String name;
    protected String email;
    protected SharedDataTypes.Role role; // requirement: "Some team members might have specific roles or responsibilities within a project"
    protected List<Project> projects;

    // Constructor to initialize name and email
    public TeamMember(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = SharedDataTypes.Role.DEVELOPER; // by default
        this.projects = new ArrayList<>();
    }

    // Constructor to initialize name, email, and role
    public TeamMember(String name, String email, SharedDataTypes.Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.projects = new ArrayList<>();
    }

    public TeamMember(String name, String email, SharedDataTypes.Role role, List<Project> projects) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.projects = new ArrayList<>(projects);
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SharedDataTypes.Role getRole() {
        return role;
    }

    public void setRole(SharedDataTypes.Role role) {
        this.role = role;
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects);
    }

    public void joinProject(Project project) {
        projects.add(project);
    }

    public void leaveProject(Project project) {
        projects.remove(project);
    }
}
