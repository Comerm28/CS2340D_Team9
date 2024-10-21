package solidgrasp;

import java.util.List;

public class ProjectManager extends TeamMember {
    public ProjectManager(String name, String email) {
        super(name, email, SharedDataTypes.Role.PROJECT_MANAGER);
    }
    public ProjectManager(String name, String email, List<Project> projects) {
        super(name, email, SharedDataTypes.Role.PROJECT_MANAGER, projects);
    }
    public void workOnProject() {
        //manager tasks
    }
}