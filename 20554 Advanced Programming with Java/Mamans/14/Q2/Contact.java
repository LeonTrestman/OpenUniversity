/*Class Represents a Contact in phone book*/
import java.io.Serializable;

public class Contact implements Serializable, Comparable<Contact> {

    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    //compareTo method for using name
    @Override
    public int compareTo(Contact o) {
        return name.compareTo(o.getName());
    }

    //equals method for using name
    @Override
    public boolean equals(Object o){
        if(o instanceof Contact){
            Contact c = (Contact) o;
            return this.getName() == c.getName();
        }
        return false;
    }

    @Override
    public String toString() {
        return  name + "\t" + phoneNumber;
    }

}
