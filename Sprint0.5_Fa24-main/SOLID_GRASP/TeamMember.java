public abstract class TeamMember {
    protected String name;
    protected String email;

    //TODO: make super constructor actually set the variables
    public TeamMember()
    {
        name = "";
        email = "";
    }

    public abstract boolean join_project();
    public abstract boolean leave_project();
}
