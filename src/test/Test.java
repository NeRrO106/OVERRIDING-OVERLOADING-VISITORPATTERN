package test;

import java.util.ArrayList;
import java.util.LinkedList;

interface Visitor{
    public void visit(Employee employee);
    public void visit(Manager manager);
    public void visit(Intern intern);
}

interface Visitable{
    public void accept(Visitor v);
}


class Intern implements Visitable{
    String nume;
    double durata;
    public void accept(Visitor v) {v.visit(this);}
}

class Employee implements Visitable{
    private String name;

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", extraHours=" + extraHours +
                '}';
    }

    private float salary;

    private float extraHours;

    public Employee(String name, float salary) {
        this.name = name;
        this.salary = salary;
    }

    public Employee(String name, float salary, float extraHours) {
        this.name = name;
        this.salary = salary;
        this.extraHours = extraHours;
    }

    public float getExtraHours() {
        return extraHours;
    }

    public void setExtraHours(float extraHours) {
        this.extraHours = extraHours;
    }

    public String getName() {
        return name;
    }

    public float getSalary() {
        return salary;
    }
    public float getTotalRevenue(){
        return salary;
    }
    public float getBonusPercentuage(){
        return 0;
    }
    public void accept(Visitor v) {
        v.visit(this);
    }
}

class Manager extends Employee{
    private float bonus;
    ArrayList<Visitable> subordonatiDirecti = new ArrayList<>();

    public Manager(String name, float salary) {
        super(name, salary);
        this.bonus = 0;
    }

    public Manager(String name, float salary, float bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    public Manager(String name, float salary, float extraHours, float bonus) {
        super(name, salary, extraHours);
        this.bonus = bonus;
    }
    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public float getTotalRevenue(){
        return getSalary() + bonus;
    }

    public float getBonusPercentuage() {
        return bonus/getSalary();
    }
    public void accept(Visitor v){
        v.visit(this);
    }
    public ArrayList<Visitable> getSubordinates(){
        return subordonatiDirecti;
    }
    public void addSubordinate(Visitable subordinate){
        subordonatiDirecti.add(subordinate);
    }

}

class TreeVisitor  implements Visitor{
    public void visit(Employee employee) {
        System.out.println("Angajatul: " + employee.getName() + " are in subordinea sa 0 persoane");
    }
    public void visit(Manager manager) {
        System.out.println("Managerul: " + manager.getName() + " are in subordinea sa " +manager.getSubordinates().size()+" persoane");
    }

    public void visit(Intern intern) {}
}

class MostHardworkingEmployeeFinder implements Visitor{

    float employeesHours;
    float managerHours;
    int employeesCount;
    int managerCount;
    float employeesAverageHours;
    float managersAverageHours;

    public void visit(Employee employee) {
        employeesCount ++;
        employeesHours = employee.getExtraHours();
        System.out.println(employeesCount + " " + employeesHours);
    }
    public void visit(Manager manager) {
        managerCount ++;
        managerHours = manager.getExtraHours();
        System.out.println(managerCount + " " + managerHours);
    }
    public void visit(Intern intern) {}

    boolean isBossHardWorking(){
        managersAverageHours = managerHours/managerCount;
        employeesAverageHours = employeesHours/employeesCount;
        if(employeesAverageHours < managersAverageHours) return true;
        else return false;
    }
}

class RevenueVisitor implements Visitor{
    public void visit(Employee employee){
        System.out.println("Angajatul: " + employee.getName() + " are raportul bonus/salariu: " + employee.getSalary() + 25 * employee.getExtraHours());
    }
    public void visit(Manager manager){
        System.out.println("Managerul: " + manager.getName() + " are raportul bonus/salariu = " + (manager.getBonus()/ manager.getSalary() + 50 * manager.getExtraHours()));
    }
    public void visit(Intern intern) {}
}

public class Test {
    public static void main(String[] args){
        testTask1();
        testTask2();
    }
    public static void testTask1(){
        LinkedList<Employee>  employees = new LinkedList<>();
        employees.add(new Employee("Alice", 20,20));
        Manager manager;
        employees.add(manager = new Manager("Bob", 1000, 100,100));
        manager.setBonus(100);

        Visitor venit = new RevenueVisitor();
        for (Employee e : employees){
            e.accept(venit);
        }

        Visitor procent = new RevenueVisitor();
        for (Employee e : employees){
            e.accept(procent);
        }
    }
    public static void testTask2(){
        Manager ceo = new Manager("Seful Mare", 10000, 2, 42);
        Manager adjunct = new Manager("Seful Mic", 9000, 7, 20);

        Employee maria = new Employee("Maria", 2200, 0);
        Employee dan = new Employee("Dan", 2500, 10);
        Employee vasile = new Employee("Vasile", 2000, 5);

        ceo.addSubordinate(adjunct);
        ceo.addSubordinate(maria);

        adjunct.addSubordinate(dan);
        adjunct.addSubordinate(vasile);

        System.out.println(ceo.getSubordinates());
        System.out.println(adjunct.getSubordinates());

        LinkedList<Employee> totiAngajatii = new LinkedList<>();

        totiAngajatii.add(ceo);
        totiAngajatii.add(adjunct);
        totiAngajatii.add(maria);
        totiAngajatii.add(dan);
        totiAngajatii.add(vasile);

        TreeVisitor tv = new TreeVisitor();
        for(Employee e : totiAngajatii){
            e.accept(tv);
        }

        MostHardworkingEmployeeFinder most = new MostHardworkingEmployeeFinder();
        for(Employee v : totiAngajatii){
            v.accept(most);
        }
        System.out.println(most.isBossHardWorking());
        System.out.println(most.managersAverageHours);
        System.out.println(most.employeesAverageHours);
    }
}
