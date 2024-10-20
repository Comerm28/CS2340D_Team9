import java.util.List;

public class Developer extends TeamMember {

    public Developer(String name, String email) {
        super(name, email);
    }
    public Developer(String name, String email, List<Project> projects) {
        super(name, email, SharedDataTypes.Role.DEVELOPER, projects);
    }
    public void workOnProject() {
        SimpleLogger.info(name + " is working on the project.");
    }
}
