package csci5408.catme.dto;


import csci5408.catme.domain.User;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
public class UserSummary {
    private Long id;

    private String firstName;

    private String lastName;

    private String studentId;

    private Boolean admin;

    private String emailId;

    public static UserSummary from(User user) {
        UserSummary userSummary = new UserSummary();
        userSummary.setAdmin(user.isAdmin());
        userSummary.setEmailId(user.getEmailId());
        userSummary.setFirstName(user.getFirstName());
        userSummary.setLastName(user.getLastName());
        userSummary.setStudentId(user.getStudentId());
        return userSummary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
