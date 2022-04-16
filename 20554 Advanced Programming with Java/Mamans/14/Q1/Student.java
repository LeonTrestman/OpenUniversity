/*Student Class*/
public class Student implements Comparable<Student> {

    private String firstName;
    private String lastName;
    private int id;
    private int birthYear;

    public Student(String firstName, String lastName, int id, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.birthYear = birthYear;

    }

    public int getId() {
        return id;
    }

    //compareTo using id , lower number id is smaller , higher is bigger
    @Override
    public int compareTo(Student o) {
        return this.getId() - o.getId();
    }

    //equals method for using id
    @Override
    public boolean equals(Object o){
        if(o instanceof Student){
            Student s = (Student) o;
            return this.getId() == s.getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + id + " " + birthYear ;
    }

}
