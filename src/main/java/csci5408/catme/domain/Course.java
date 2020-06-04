package csci5408.catme.domain;

import java.io.Serializable;

public class Course implements Serializable {
    private Long id;

    private String name;

    public Course(Long id,String name){
        this.id=id;
        this.name=name;
    }

    public Course(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
