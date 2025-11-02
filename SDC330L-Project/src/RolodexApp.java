/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Week 4 Rolodex application with SQLite CRUD.
 * Notes:
 *  - ABSTRACTION still via Contact hierarchy,
 *  - CONSTRUCTORS used for realistic instantiation,
 *  - ACCESS SPECIFIERS respected across classes.
 */
import java.util.*;
import java.sql.SQLException;

public class RolodexApp {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final ContactDAO DAO = new ContactDAO();

    public static void main(String[] args) {
        printHeader();
        try {
            Database.initialize();
        } catch (Exception e) {
            System.out.println("DB init failed: " + e.getMessage());
            return;
        }

        printWelcome();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCAN.nextLine().trim();
            switch (choice) {
                case "1":
                    createContact();
                    break;
                case "2":
                    readAll();
                    break;
                case "3":
                    readByType();
                    break;
                case "4":
                    readByInitial();
                    break;
                case "5":
                    updateContactBasics();
                    break;
                case "6":
                    deleteContact();
                    break;
                case "7":
                    running = false;
                    break;
                default:
                    System.out.println("Please enter 1–7.");
                    break;
            }
        }

        System.out.println("\nThanks for using the Rolodex App — Week 4!");
    }

    private static void printHeader() {
        System.out.println("==================================================");
        System.out.println(" Project Week 4 | SQLite CRUD for Rolodex");
        System.out.println(" Author: Brandy Lockhart");
        System.out.println("==================================================\n");
    }

    private static void printWelcome() {
        System.out.println("Welcome! This build persists contacts in SQLite and supports full CRUD.");
        System.out.println("Try adding a contact, viewing lists, updating basics, or deleting.\n");
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("  1) CREATE: Add new contact");
        System.out.println("  2) READ:   Display ALL contacts");
        System.out.println("  3) READ:   Display contacts by TYPE");
        System.out.println("  4) READ:   Display contacts by LAST-NAME INITIAL");
        System.out.println("  5) UPDATE: Update contact phone/email (by id)");
        System.out.println("  6) DELETE: Delete contact (by id)");
        System.out.println("  7) Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createContact() {
        try {
            System.out.print("\nType (Business/Family/Friend): ");
            String type = SCAN.nextLine().trim();

            System.out.print("First name: "); String fn = SCAN.nextLine().trim();
            System.out.print("Last name:  "); String ln = SCAN.nextLine().trim();
            System.out.print("Phone:      "); String ph = SCAN.nextLine().trim();
            System.out.print("Email:      "); String em = SCAN.nextLine().trim();

            System.out.print("Street: "); String st = SCAN.nextLine().trim();
            System.out.print("City:   "); String ct = SCAN.nextLine().trim();
            System.out.print("State:  "); String stt = SCAN.nextLine().trim();
            System.out.print("Zip:    "); String zp = SCAN.nextLine().trim();
            Address addr = new Address(st, ct, stt, zp);

            Contact c;
            switch (type) {
                case "Business":
                    System.out.print("Company name:  "); String cn = SCAN.nextLine().trim();
                    System.out.print("Company phone: "); String cp = SCAN.nextLine().trim();
                    System.out.print("Job title:     "); String jt = SCAN.nextLine().trim();
                    c = new BusinessContact(fn, ln, ph, em, addr, new Company(cn, cp), jt);
                    break;
                case "Family":
                    System.out.print("Relationship: "); String rel = SCAN.nextLine().trim();
                    c = new FamilyContact(fn, ln, ph, em, addr, rel);
                    break;
                case "Friend":
                    System.out.print("How we met: "); String hwm = SCAN.nextLine().trim();
                    c = new FriendContact(fn, ln, ph, em, addr, hwm);
                    break;
                default:
                    System.out.println("Unknown type. Aborting create.\n");
                    return;
            }

            int id = DAO.insert(c);
            System.out.println("Created contact with id=" + id + "\n");

        } catch (SQLException e) {
            System.out.println("Create failed: " + e.getMessage() + "\n");
        }
    }

    private static void readAll() {
        try {
            System.out.println("\n--- All Contacts ---");
            var list = DAO.findAll();
            if (list.isEmpty()) System.out.println("(none)");
            else list.forEach(x -> System.out.println(x.toDisplayString()));
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Read failed: " + e.getMessage() + "\n");
        }
    }

    private static void readByType() {
        try {
            System.out.print("\nEnter type (Business/Family/Friend): ");
            String t = SCAN.nextLine().trim();
            var list = DAO.findByType(t);
            System.out.println("\n--- Contacts of type: " + t + " ---");
            if (list.isEmpty()) System.out.println("(none)");
            else list.forEach(x -> System.out.println(x.toDisplayString()));
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Read failed: " + e.getMessage() + "\n");
        }
    }

    private static void readByInitial() {
        try {
            System.out.print("\nEnter first letter of last name: ");
            String s = SCAN.nextLine().trim();
            if (s.isEmpty()) { System.out.println("Please enter a letter.\n"); return; }
            char ch = Character.toUpperCase(s.charAt(0));
            var list = DAO.findByLastInitial(ch);

            System.out.println("\n--- Contacts with last names starting with '" + ch + "' ---");
            if (list.isEmpty()) System.out.println("(none)");
            else list.forEach(x -> System.out.println(x.toDisplayString()));
            System.out.println();
        } catch (SQLException e) {
            System.out.println("Read failed: " + e.getMessage() + "\n");
        }
    }

    private static void updateContactBasics() {
        try {
            System.out.print("\nEnter contact ID to update: ");
            int id = Integer.parseInt(SCAN.nextLine().trim());
            System.out.print("New phone (blank ok): "); String ph = SCAN.nextLine().trim();
            System.out.print("New email (blank ok): "); String em = SCAN.nextLine().trim();
            boolean ok = DAO.updateContactBasics(id, ph, em);
            System.out.println(ok ? "Updated.\n" : "No row updated (check id).\n");
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage() + "\n");
        }
    }

    private static void deleteContact() {
        try {
            System.out.print("\nEnter contact ID to delete: ");
            int id = Integer.parseInt(SCAN.nextLine().trim());
            boolean ok = DAO.deleteById(id);
            System.out.println(ok ? "Deleted.\n" : "No row deleted (check id).\n");
        } catch (Exception e) {
            System.out.println("Delete failed: " + e.getMessage() + "\n");
        }
    }
}
