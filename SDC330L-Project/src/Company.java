/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Company info for business contacts (composition) + Displayable for UI lists.
 */
public class Company implements Display {
    private String name;
    private String mainPhone;

    public Company(String name, String mainPhone) {
        this.name      = safe(name);
        this.mainPhone = safe(mainPhone);
    }
    public Company(String name) { this(name, ""); }

    public String getName()      { return name; }
    public String getMainPhone() { return mainPhone; }

    private String safe(String s) { return s == null ? "" : s.trim(); }

    @Override
    public String toDisplayString() {
        return String.format("%s%s", name.isEmpty()? "(Unnamed Company)": name,
                mainPhone.isEmpty()? "" : " (Main: " + mainPhone + ")");
    }

    @Override public String toString() { return toDisplayString(); }
}
