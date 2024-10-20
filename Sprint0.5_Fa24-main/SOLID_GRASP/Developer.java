import java.util.List;

public class Developer extends TeamMember {

    public Developer(String name, String email) {
        super(name, email);
    }
    public Developer(String name, String email, List<Project> projects) {
        super(name, email, SharedDataTypes.Role.DEVELOPER, projects);
    }
    public void workOnProject() {
        System.out.println(name + " is writing code for the project.");
    }
}
