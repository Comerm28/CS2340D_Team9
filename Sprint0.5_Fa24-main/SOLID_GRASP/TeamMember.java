public abstract class TeamMember {
    protected String name;
    protected String email;
    protected String role; // requirement: "Some team members might have specific roles or responsibilities within a project"

    // Constructor to initialize name and email
    public TeamMember(String name, String email, String role) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Example: Check if the member is responsible for overseeing a project
    public boolean isProjectManager() {
        return "Project Manager".equalsIgnoreCase(this.role);
    }
}
