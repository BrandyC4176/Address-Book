/*
* Name: Brandy Christopher
 * SDC330L Project
 * Date: 10/28/25 
 * Purpose: Company value object implementing Displayable.
 */

public class Company implements Interface {
    private String name;
    private String mainPhone;

    public Company(String name, String mainPhone) {
        this.name      = safe(name);
        this.mainPhone = safe(mainPhone);
    }

    public Company(String name) {
        this(name, "");
    }

    private String safe(String s) { return s == null ? "" : s.trim(); }

    @Override
    public String toDisplayString() {
        return String.format("%s%s",
                name.isEmpty() ? "(Unnamed Company)" : name,
                mainPhone.isEmpty() ? "" : " (Main: " + mainPhone + ")");
    }

    @Override
    public String toString() { return toDisplayString(); }
}
