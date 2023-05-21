package aton.task1;

public class User {
    private long account;
    private String name;
    private double value;
    public User(long account, String name, double value) {
        this.setAccount(account);
        this.setName(name);
        this.setValue(value);
    }
    public String info(){
        return "Account: "+this.getAccount()+", name: "+this.getName()+", value: "+this.getValue();
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
