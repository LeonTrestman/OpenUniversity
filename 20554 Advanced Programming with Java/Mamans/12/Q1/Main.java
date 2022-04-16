
public class Main {

    public static void main(String[] args) {

        //testing array of random employees
        Employee[] employees = new Employee[]{
                new PieceWorkerEmployee("David", "Cohen", "352-000-061",
                        10, 120.5, 20, 10, 1997),

                new PieceWorkerEmployee("Yossi", "Levi", "321-156-119",
                        100, 15.7, 11, 11, 2000),

                new SalariedEmployee("Maya", "Rottman", "356-666-229",
                        1200, 2, 12, 1994),

                new SalariedEmployee("Benjamin", "Viceman", "390-106-100",
                        900, 18, 11, 1996),

                new HourlyEmployee("John","Smith","289-996-589",
                        28.5,70,1,12,1988),

                new HourlyEmployee("Adam","Levin","211-423-845",
                        50,45,27,11,1990),

                new CommissionEmployee("Ron","Altman","322-059-990",
                        16000,0.1,9,11,1992),

                new CommissionEmployee("Dana","Mizarhi","390-177-178",
                        5000,0.2,3,12,1989),

                new BasePlusCommissionEmployee("Yarin","Buzaglo","311-000-102",
                        15000,0.05,300,7,8,1995),

                new BasePlusCommissionEmployee("Efraim","Dahan","222-040-995",
                        200,0.5,500,25,11,1985)
        };

        printEmployeesInfo(employees);
    }

    //info includes first and last name , social security number ,salary with all of its components
    public static void printEmployeesInfo(Employee[] employees) {
        for (Employee employee : employees) {
            System.out.println(employee.toString() + "\n" + employee.totalSalaryToString());
            System.out.println("----------------------------------");//divider for easier reading
        }
    }

}
