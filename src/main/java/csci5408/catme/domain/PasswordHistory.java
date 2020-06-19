package csci5408.catme.domain;

import java.sql.Timestamp;

public class PasswordHistory {
    private Long id;

    private String password;

    private Timestamp creationTime;

    private Long userId;

    public PasswordHistory(Long id, String password, Timestamp creationTime, Long userId) {
        this.id = id;
        this.password = password;
        this.creationTime = creationTime;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
