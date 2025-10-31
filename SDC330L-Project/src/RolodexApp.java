/*
* Name: Brandy Christopher
 * SDC330L Project
 * Date: 10/28/25 
 * Purpose: Week 3 console app demonstrating abstraction, constructors, and access specifiers.
 */

import java.util.*;

public class RolodexApp {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final ContactRepository REPO = new ContactRepository();

    public static void main(String[] args) {
        printHeader();
        preloadData();
        printWelcome();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCAN.nextLine().trim();
            switch (choice) {
                case "1":
                    displayAll();
                    break;
                case "2":
                    displayByType();
                    break;
                case "3":
                    displayByInitial();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a valid option (1–4).");
                    break;
            }
        }

        System.out.println("\nThanks for using the Rolodex App — Week 3!");
    }

    private static void printHeader() {
        System.out.println("==================================================");
        System.out.println(" Project Week 3 | Abstraction, Constructors & Access Specifiers");
        System.out.println(" Author: Brandy Lockhart");
        System.out.println("==================================================\n");
    }

    private static void printWelcome() {
        System.out.println("Welcome! This version shows abstraction, constructor overloads, and access control.\n");
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("  1) Display ALL contacts");
        System.out.println("  2) Display by TYPE (Business/Family/Friend)");
        System.out.println("  3) Display by LAST-NAME INITIAL");
        System.out.println("  4) Exit");
        System.out.print("Enter your choice: ");
    }

    private static void preloadData() {
        Address a1 = new Address("123 Market St", "Richmond", "VA", "23220");
        Address a2 = new Address("Norfolk", "VA");
        Address a3 = new Address("42 River Rd", "Newport News", "VA", "23601");

        Company trendify = new Company("Trendify Marketing", "804-555-0199");
        Company coastal  = new Company("Coastal Freight");

        REPO.add(new BusinessContact("Sarah", "Johnson", "804-555-1122", "sarah.j@trendify.com",
                a1, trendify, "Marketing Manager"));
        REPO.add(new FamilyContact("Brian", "Lockhart", "757-555-3210", "brian.lockhart@email.com",
                a2, "Cousin"));
        REPO.add(new FriendContact("Emily", "Davis", "757-555-7755", "emily.davis@mail.com",
                a3, "College"));
        REPO.add(new BusinessContact("Jason", "Miller", coastal));
    }

    private static void displayAll() {
        System.out.println("\n--- All Contacts ---");
        for (Contact c : REPO.getAll()) {
            System.out.println(c.toDisplayString());
        }
        System.out.println();
    }

    private static void displayByType() {
        System.out.print("\nEnter type (Business/Family/Friend): ");
        String type = SCAN.nextLine().trim();
        var filtered = REPO.getByType(type);

        System.out.println("\n--- Contacts of type: " + type + " ---");
        if (filtered.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            for (Contact c : filtered) System.out.println(c.toDisplayString());
        }
        System.out.println();
    }

    private static void displayByInitial() {
        System.out.print("\nEnter first letter of last name: ");
        String s = SCAN.nextLine().trim();
        if (s.isEmpty()) {
            System.out.println("Please enter a letter.\n");
            return;
        }
        char ch = Character.toUpperCase(s.charAt(0));
        var filtered = REPO.getByLastNameInitial(ch);

        System.out.println("\n--- Contacts with last name starting with '" + ch + "' ---");
        if (filtered.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            for (Contact c : filtered) System.out.println(c.toDisplayString());
        }
        System.out.println();
    }
}
