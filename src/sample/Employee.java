package sample;

public class Employee {

    public int employeeid;
    public String firstName;
    public String lastName;
    public String phone;
    public String birthday;
    public int salary;
    public String employmentDate;
    public int branchid;
    public int positionid;
    public String positionName;

    public Employee(){
        //empty constructor
    }

    public Employee(int employeeid, String firstName, String lastName, String phone, String birthday, int salary, String employmentDate, int branchid, int positionid, String positionName) {
        this.employeeid = employeeid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthday = birthday;
        this.salary = salary;
        this.employmentDate = employmentDate;
        this.branchid = branchid;
        this.positionid = positionid;
        this.positionName = positionName;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getSalary() {
        return salary;
    }

    public String getEmploymentDate() {
        return employmentDate;
    }

    public int getBranchid() {
        return branchid;
    }

    public int getPositionid() {
        return positionid;
    }

    public String getPositionName() {
        return positionName;
    }
}

