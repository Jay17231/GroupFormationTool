package csci5408.catme.dto;

import csci5408.catme.domain.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseSummary {
    private Long id;

    private String name;

    public CourseSummary(Long id,String name){
        this.id=id;
        this.name=name;

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return name;
    }

    public void setCourseName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CourseSummary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static CourseSummary from(Course course) {
        return new CourseSummary(course.getId(), course.getCourseName());
    }

    public static List<CourseSummary> from(List<Course> courses) {
        ArrayList<CourseSummary> summaries = new ArrayList<>();
        for(Course course: courses) {
            summaries.add(CourseSummary.from(course));
        }
        return summaries;
    }

    public static Course to(CourseSummary course) {
        return new Course(course.getId(), course.getCourseName());
    }
}
