/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Abstract base class for all contacts.
 * Notes: Demonstrates ABSTRACTION (abstract class + abstract methods),
 *        CONSTRUCTORS (overloads), and ACCESS (private fields + protected helpers).
 */
public abstract class Contact implements Display {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Address address;

    protected Contact(String firstName, String lastName, String phone, String email, Address address) {
        this.firstName = safe(firstName);
        this.lastName  = safe(lastName);
        this.phone     = safe(phone);
        this.email     = safe(email);
        this.address   = address;
    }
    protected Contact(String firstName, String lastName) {
        this(firstName, lastName, "", "", null);
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getPhone()     { return phone; }
    public String getEmail()     { return email; }
    public Address getAddress()  { return address; }

    protected void setPhone(String phone)   { this.phone = safe(phone); }
    protected void setEmail(String email)   { this.email = safe(email); }
    protected void setAddress(Address a)    { this.address = a; }

    public abstract String getType();          // e.g., "Business"
    protected abstract String getBadge();      // subtype-specific tag for UI

    @Override
    public String toDisplayString() {
        String addr = (address != null ? address.toString() : "Address: (none)");
        return String.format("%s | %s %s | Phone: %s | Email: %s | %s%s",
                getType(), firstName, lastName,
                phone.isEmpty()? "N/A": phone,
                email.isEmpty()? "N/A": email,
                addr,
                getBadge().isEmpty()? "" : " | " + getBadge());
    }

    protected String safe(String s) { return s == null ? "" : s.trim(); }
}
