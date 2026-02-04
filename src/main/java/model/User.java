package model;

public abstract class User {
    private String _username;
    private String _email;
    private double _phone;
    private long _id;
    private String _password;

    // setters
    public void set_username(String username){ _username = username; }
    public void set_email(String email){ _email = email; }
    public void set_phone(double phone){ _phone = phone; }
    public void set_password(String password){ _password = password; }

    // getters
    public String get_username(){ return _username; }
    public String get_email(){ return _email; }
    public double get_phone(){ return _phone; }
    public long get_id(){ return _id; }
}
