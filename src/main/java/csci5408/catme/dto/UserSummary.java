package csci5408.catme.dto;


import csci5408.catme.domain.User;

import java.util.Objects;

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

    public static User to(UserSummary summary) {
        return new User(summary.getId(), summary.getFirstName(), summary.getLastName(),
                summary.getStudentId(), summary.getAdmin(), summary.getEmailId());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSummary)) return false;
        UserSummary that = (UserSummary) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getStudentId(), that.getStudentId()) &&
                Objects.equals(getAdmin(), that.getAdmin()) &&
                Objects.equals(getEmailId(), that.getEmailId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getStudentId(), getAdmin(), getEmailId());
    }
}
