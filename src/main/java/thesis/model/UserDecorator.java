package thesis.model;

public abstract class UserDecorator implements User {
    private User user;

    public UserDecorator() {}

    public UserDecorator(User user) {
        this.user = user;
    }

    @Override
    public int getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        user.setUsername(username);
    }
}
