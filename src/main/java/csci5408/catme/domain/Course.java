package csci5408.catme.domain;

public class Course {
    private Long id;

    private String name;

    public Course(Long id,String name){
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
    
    
}
