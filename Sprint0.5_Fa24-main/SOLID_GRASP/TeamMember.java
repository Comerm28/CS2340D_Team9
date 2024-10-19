public abstract class TeamMember {
    protected String name;
    protected String email;
    protected SharedDataTypes.Role role; // requirement: "Some team members might have specific roles or responsibilities within a project"

    // Constructor to initialize name and email
    protected TeamMember(String name, String email) {
        this.name = name;
        this.email = email;
        role = SharedDataTypes.Role.TEAM_MEMBER; // by default
    }

    // Constructor to initialize name, email, and role
    protected TeamMember(String name, String email, SharedDataTypes.Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Abstract methods for joining and leaving a project
    public abstract boolean joinProject(Project project);
    public abstract boolean leaveProject(Project project);

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public SharedDataTypes.Role getRole() {
        return role;
    }

    // Method to update task status
    public void updateRole(SharedDataTypes.Role newRole) {
        this.role = newRole;
    }

    // Example: Check if the member is responsible for overseeing a project
    public boolean isProjectManager() {
        return SharedDataTypes.Role.PROJECT_MANAGER.equals(this.role);
    }
}
