       InsuranceApp.java(main class)

package com.example.insurance.main;

import com.example.insurance.model.Policy;
import com.example.insurance.service.PolicyService;

import java.time.LocalDate;
import java.util.Scanner;

public class InsuranceApp {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        PolicyService service = new PolicyService();

        while (true) {
            System.out.println("\n==== INSURANCE MANAGEMENT SYSTEM ====");
            System.out.println("1. Add Policy");
            System.out.println("2. View Policies");
            System.out.println("3. Check Premium Reminders");
            System.out.println("4. Delete Policy");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scan.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Policy ID: ");
                    int id = scan.nextInt();
                    scan.nextLine();

                    System.out.print("Customer Name: ");
                    String name = scan.nextLine();

                    System.out.print("Policy Type: ");
                    String type = scan.nextLine();

                    System.out.print("Premium Amount: ");
                    double premium = scan.nextDouble();

                    System.out.print("Due Date (YYYY-MM-DD): ");
                    String date = scan.next();

                    Policy policy = new Policy(id, name, type, premium, LocalDate.parse(date));
                    service.addPolicy(policy);
                    break;

                case 2:
                    service.viewPolicies();
                    break;

                case 3:
                    service.checkReminders();
                    break;

                case 4:
                    System.out.print("Enter Policy ID to delete: ");
                    int deleteId = scan.nextInt();
                    service.deletePolicy(deleteId);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}


                    Policy.java

package com.example.insurance.model;

import java.time.LocalDate;

public class Policy {

    private int policyId;
    private String customerName;
    private String policyType;
    private double premium;
    private LocalDate dueDate;

    public Policy(int policyId, String customerName, String policyType, double premium, LocalDate dueDate) {
        this.policyId = policyId;
        this.customerName = customerName;
        this.policyType = policyType;
        this.premium = premium;
        this.dueDate = dueDate;
    }

    public int getPolicyId() {
        return policyId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPolicyType() {
        return policyType;
    }

    public double getPremium() {
        return premium;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
}


               PolicyService.java


package com.example.insurance.service;

import com.example.insurance.model.Policy;

import java.time.LocalDate;
import java.util.ArrayList;

public class PolicyService {

    private ArrayList<Policy> policies = new ArrayList<>();

   
    public void addPolicy(Policy policy) {
        policies.add(policy);
        System.out.println("✔ Policy Added Successfully!");
    }

   
    public void viewPolicies() {
        if (policies.isEmpty()) {
            System.out.println("No policies found!");
            return;
        }

        System.out.println("----------------------------------------------");
        System.out.println("ID | Name | Type | Premium | Due Date");
        System.out.println("----------------------------------------------");

        for (Policy p : policies) {
            System.out.println(
                p.getPolicyId() + " | " +
                p.getCustomerName() + " | " +
                p.getPolicyType() + " | ₹" +
                p.getPremium() + " | " +
                p.getDueDate()
            );
        }
    }

   
    public void checkReminders() {
        LocalDate today = LocalDate.now();
        boolean found = false;

        System.out.println("\n--- Premium Reminders (Next 7 Days) ---");

        for (Policy p : policies) {
            if (!p.getDueDate().isBefore(today) &&
                !p.getDueDate().isAfter(today.plusDays(7))) {

                System.out.println("⚠ Premium due soon: Policy ID " + p.getPolicyId());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No premiums due in next 7 days.");
        }
    }

      
    public void deletePolicy(int id) {
        policies.removeIf(p -> p.getPolicyId() == id);
        System.out.println("✔ Policy deleted (if exists).");
    }
}




