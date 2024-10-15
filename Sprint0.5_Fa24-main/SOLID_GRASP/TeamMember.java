public abstract class TeamMember {
    protected String name;
    protected String email;

    // Constructor to initialize name and email
    public TeamMember(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Abstract methods for joining and leaving a project
    public abstract boolean joinProject(Project project);
    public abstract boolean leaveProject(Project project);

    // Getters for name and email
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
