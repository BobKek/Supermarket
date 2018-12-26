package sample;

public class Employee {

    public int employeeid;
    public String name;
    public String phone;
    public String birthday;
    public int salary;
    public String employmentDate;
    public int branchid;
    public String country;
    public String city;
    public int positionid;
    public String positionName;

    public Employee(){
        //empty constructor
    }

    public Employee(int employeeid, String name, String phone, String birthday, int salary, String employmentDate, int branchid, int positionid, String positionName, String country, String city) {
        this.employeeid = employeeid;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.salary = salary;
        this.employmentDate = employmentDate;
        this.branchid = branchid;
        this.positionid = positionid;
        this.positionName = positionName;
        this.country = country;
        this.city = city;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public String getName() {
        return name;
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

    public String getBranchLocation(){
        return "" + city + ", " + country;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate = employmentDate;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public void setPositionid(int positionid) {
        this.positionid = positionid;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setPosition(Position position){
        this.positionid = position.positionid;
        this.positionName = position.name;
    }

    public void setBranch(Branch branch){
        this.branchid = branch.branchid;
        this.country = branch.country;
        this.city = branch.city;
    }
}

