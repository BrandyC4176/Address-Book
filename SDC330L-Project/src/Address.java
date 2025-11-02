/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Address value object (composition).
 */
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;

    public Address(String street, String city, String state, String zip) {
        this.street = safe(street);
        this.city   = safe(city);
        this.state  = safe(state);
        this.zip    = safe(zip);
    }

    public Address(String city, String state) {
        this("", city, state, "");
    }

    public String getStreet() { return street; }
    public String getCity()   { return city; }
    public String getState()  { return state; }
    public String getZip()    { return zip; }

    private String safe(String s) { return s == null ? "" : s.trim(); }

    @Override
    public String toString() {
        String line1 = street.isEmpty() ? "(no street)" : street;
        String line2 = String.format("%s, %s %s",
                city.isEmpty()? "(no city)": city,
                state.isEmpty()? "(no state)": state,
                zip.isEmpty()? "(no zip)": zip);
        return "Address: " + line1 + ", " + line2;
    }
}
