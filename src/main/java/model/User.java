package model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class User {
    private String username;
    private String email;
    private String phone;
    private long id;
    private String password;

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        setEmail(email);
        setPhone(phone);
    }

    // checking if email is similar to the standard pattern
    public final void setEmail(String email) {
        if(checkEmail(email)){
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    public boolean checkEmail(String email){
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailPattern);
    }

    // checking if phone number is similar to the standard pattern
    public final void setPhone(String phone) {
        String phonePattern = "^07\\d{2}\\s\\d{3}\\s\\d{3}$";
        if (phone == null || !phone.matches(phonePattern)){
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phone = phone;
    }

    public void showInfo(){
        System.out.println("User email: " + this.getEmail());
    }
}
