/*
* Name: Brandy Christopher
 * SDC330L Project
 * Date: 10/28/25 
 * Purpose: Represents a friend contact.
 */

public class FriendContact extends Contact {
    private String howWeMet;

    public FriendContact(String first, String last, String phone, String email,
                         Address address, String howWeMet) {
        super(first, last, phone, email, address);
        this.howWeMet = safe(howWeMet);
    }

    public FriendContact(String first, String last, String howWeMet) {
        super(first, last);
        this.howWeMet = safe(howWeMet);
    }

    @Override public String getType() { return "Friend"; }

    @Override
    protected String getBadge() {
        return (howWeMet == null || howWeMet.isBlank())
                ? "" : "Met through: " + howWeMet;
    }
}
