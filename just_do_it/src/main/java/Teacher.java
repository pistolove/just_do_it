package com;

public class Teacher {

    private String name;// 教师姓名
    private String staffNumber;// 教工号
    private int basicSalary;// 基本工资
    private int positionValue;// 岗位工资
    private int meritPay;// 绩效工资

    public static void main(String[] args) {
        Teacher teacher = new Teacher("李雷", "100001", 4300, 800, 600);
        teacher.printInfomation();
        System.out.println("税前总工资为" + teacher.totalSalary());
        System.out.println("税后总工资为" + teacher.afterTaxSalary());
    }

    public Teacher(String name, String staffNumber, int basicSalary, int positionValue, int meritPay) {
        this.name = name;
        this.staffNumber = staffNumber;
        this.basicSalary = basicSalary;
        this.positionValue = positionValue;
        this.meritPay = meritPay;
    }

    public int totalSalary() {
        return basicSalary + positionValue + meritPay;
    }

    public int afterTaxSalary() {
        int totalSalary = totalSalary();
        if (totalSalary <= 3000) {
            return totalSalary;
        } else if (totalSalary <= 5000) {
            double tax = (totalSalary - 3000) * 0.1;
            return totalSalary - (int) tax;
        } else {
            double tax = (5000 - 3000) * 0.1 + (totalSalary - 5000) * 0.15;
            return totalSalary - (int) tax;
        }

    }

    public void printInfomation() {
        System.out.println("教师姓名:" + name);
        System.out.println("教工号:" + staffNumber);
        System.out.println("基本工资:" + basicSalary);
        System.out.println("岗位工资:" + positionValue);
        System.out.println("绩效工资:" + meritPay);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public void setBasicSalary(int basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setPositionValue(int positionValue) {
        this.positionValue = positionValue;
    }

    public void setMeritPay(int meritPay) {
        this.meritPay = meritPay;
    }
}
