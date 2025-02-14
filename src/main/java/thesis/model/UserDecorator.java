package thesis.model;

public abstract class UserDecorator extends UserComponent implements IDecorator {
    private UserComponent record;

    public UserDecorator() {}

    public UserDecorator(UserComponent record) {
        this.record = record;
    }

    @Override
    public UserComponent getRecord() {
        return record;
    }

    @Override
    public void setRecord(Object record) {
        this.record = (UserComponent) record;
    }

    @Override
    public int getId() {
        return record.getId();
    }

    @Override
    public void setId(int id) {
        this.record.setId(id);
    }

    @Override
    public String getUsername() {
        return record.getUsername();
    }

    @Override
    public void setUsername(String username) {
        record.setUsername(username);
    }
}
