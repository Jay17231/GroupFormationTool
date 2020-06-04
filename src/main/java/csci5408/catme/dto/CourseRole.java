package csci5408.catme.dto;

import csci5408.catme.domain.Operation;

import java.util.List;

public class CourseRole {
    private Long courseId;
    private String courseName;
    private Long roleId;
    private String roleName;
    private List<Operation> permissions;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Operation> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Operation> permissions) {
        this.permissions = permissions;
    }
}
