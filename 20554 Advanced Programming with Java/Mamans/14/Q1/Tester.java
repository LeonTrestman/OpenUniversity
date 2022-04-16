/*Tester for the program*/
public class Tester {

    public static void main(String[] args) {

        // creating Student objects
        Student David = new Student("David","Levi",2,1990);
        Student Shlomo = new Student("Shlomo","Cohen",3,1995);
        Student Mia = new Student("Mia","Regev",4,1986);

        Student[] students = {David,Shlomo,Mia};
        String[] phoneNumbers = {"053-3256705","054-6759844","051-3456872"};


        try {
            AssociationTable table1 = new AssociationTable<Student,String>(students,phoneNumbers);
            //print table
            System.out.println("table size: " + table1.size());
            System.out.println(table1);

            //add another student to table
            Student rachel = new Student("Rachel","Smith",1,1988);
            table1.add(rachel,"099-1346798");
            System.out.println("-----------------\nTesting adding,Rachel should appear as" +
                    " first new student in the table");
            System.out.println(table1);

            //change last Student's phone number (last digit for 8 to 9)
            table1.updateKeyValue(rachel,"099-1346799");
            System.out.println("-----------------\nTesting Update Value" +
                    ",Change of Rachels (first student) phone number, now ends with 9");
            System.out.println(table1);

            //remove rachel from table
            if (table1.remove(rachel))
                System.out.println("-----------------\nTesting remove,Rachel should not " +
                        "appear in the table");
            System.out.println(table1);


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //testing IllegalArgumentException
        System.out.println("-----------------\nTest IllegalArgumentException Exception" +
                "\nCreating a table with 2 students and 3 phone numbers");
        Student[] students2 = {David,Shlomo};
        try {
            AssociationTable table2 = new AssociationTable<Student,String>(students2,phoneNumbers);
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }



}

