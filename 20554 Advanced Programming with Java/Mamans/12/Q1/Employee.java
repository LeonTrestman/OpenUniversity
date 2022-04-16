// Fig. 10.4: Employee.java
// Employee abstract superclass.

public abstract class Employee {
    private final String firstName;
    private final String lastName;
    private final String socialSecurityNumber;
    private final BirthDate birthDate;
    private static final int BIRTH_DAY_BONUS = 200;
    private static final int NO_BIRTH_DAY_BONUS = 0;

    //constructor passing int for birthdate
    public Employee(String firstName, String lastName,
                    String socialSecurityNumber, int day, int month, int year) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.birthDate = new BirthDate(day, month, year);
    }


    // return first name
    public String getFirstName() {
        return firstName;
    }

    // return last name
    public String getLastName() {
        return lastName;
    }

    // return social security number
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    // return String representation of Employee object
    @Override
    public String toString() {
        return String.format("%s %s%nsocial security number: %s%n%s",
                getFirstName(), getLastName(), getSocialSecurityNumber(), birthDate);
    }

    // abstract method must be overridden by concrete subclasses
    public abstract double earnings(); // no implementation here

    //returns BIRTH_DAY_BONUS if BirthDay is in current month , else returns 0 (NO_BIRTH_DAY_BONUS)
    private double getBirthDayBonus(){
         if (birthDate.isBirthDayThisMonth()){
             return BIRTH_DAY_BONUS;
         }
         else
             return NO_BIRTH_DAY_BONUS;
    }

    //returns total salary of earnings and bonuses (currently only birthday bonus exits)
    private double getTotalSalary(){
        return  earnings() + getBirthDayBonus();
    }

    //returns total salary as string
    public String totalSalaryToString(){
        return String.format("total salary is: $%,.2f%s" ,getTotalSalary(),getBonusMessage());
    }

    //returns msg of bonuses (currently only BirthDay bonus exists)
    private String getBonusMessage(){
        String bonusMessage = "\n";
        //Birthday Bonus
        if ( birthDate.isBirthDayThisMonth())
            bonusMessage += "Birthday bonus of $" + BIRTH_DAY_BONUS+ " added to salary\n";
        //More bonuses to come here ....
        return  bonusMessage;
    }


}
