package model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Task {
    private String _importance;
    private String _from;
    private String _to;
    private String _title;
    private String _details;
    private String _deadline;
    private boolean _completed;

    public Task(){}

    public Task(String title, String details, String from, String to, String deadline) {
        set_title(title);
        set_details(details);
        set_from(from);
        set_to(to);
        set_deadline(deadline);
        set_importance(null);
    }

    public void get_info(){
        System.out.println("The task: -" + get_title() + "-, has been created.");
        System.out.println("Details: " + get_details() + ",\nfrom: " + get_from() + ", to: " + get_to());
        System.out.println("Deadline: " + get_deadline());
        System.out.println("Importance: " + get_importance());
    }
}
