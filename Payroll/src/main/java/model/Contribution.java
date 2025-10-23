package model;

public class Contribution {
    private int id;
    private double employerEPF;
    private double employeeEPF;
    private double employerETF;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getEmployerEPF() {
        return employerEPF;
    }

    public void setEmployerEPF(double employerEPF) {
        this.employerEPF = employerEPF;
    }

    public double getEmployerETF() {
        return employerETF;
    }

    public void setEmployerETF(double employerETF) {
        this.employerETF = employerETF;
    }

    public double getEmployeeEPF() {
        return employeeEPF;
    }

    public void setEmployeeEPF(double employeeEPF) {
        this.employeeEPF = employeeEPF;
    }
}
