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
}
