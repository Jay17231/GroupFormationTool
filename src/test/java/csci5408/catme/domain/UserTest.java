package csci5408.catme.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void UserConstructorTest() {
        Long id = 1L;
        Boolean admin = true;
        String studentId = "B00XX", password = "Test 123";
        String firstName = "Jon", lastName = "Doe", emailId = "jon.doe@example.com";
        User u = new User(id, firstName, lastName, studentId, admin, emailId);
        u.setPassword(password);

        Assertions.assertEquals(u.isAdmin(), admin);
        Assertions.assertEquals(u.getFirstName(), firstName);
        Assertions.assertEquals(u.getLastName(), lastName);
        Assertions.assertEquals(u.getEmailId(), emailId);
        Assertions.assertEquals(u.getPassword(), password);
        Assertions.assertEquals(u.getId(), id);
        Assertions.assertEquals(u.getStudentId(), studentId);

        studentId = "B00123";
        firstName = "Jane";
        lastName = "D";
        emailId = "jane.d@example.com";
        admin = false;
        id = 2L;

        u.setAdmin(admin);
        u.setEmailId(emailId);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setStudentId(studentId);
        u.setId(id);

        Assertions.assertEquals(u.isAdmin(), admin);
        Assertions.assertEquals(u.getFirstName(), firstName);
        Assertions.assertEquals(u.getLastName(), lastName);
        Assertions.assertEquals(u.getEmailId(), emailId);
        Assertions.assertEquals(u.getId(), id);
        Assertions.assertEquals(u.getStudentId(), studentId);
    }

}
