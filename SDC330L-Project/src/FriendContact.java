/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Friend contact with howWeMet note.
 */
public class FriendContact extends Contact {
    private String howWeMet;

    public FriendContact(String first, String last, String phone, String email,
                         Address address, String howWeMet) {
        super(first, last, phone, email, address);
        this.howWeMet = (howWeMet == null ? "" : howWeMet.trim());
    }
    public FriendContact(String first, String last, String howWeMet) {
        super(first, last);
        this.howWeMet = (howWeMet == null ? "" : howWeMet.trim());
    }

    @Override public String getType() { return "Friend"; }

    @Override
    protected String getBadge() {
        return (howWeMet == null || howWeMet.isBlank()) ? "" : "Met through: " + howWeMet;
    }

    public String getHowWeMet() { return howWeMet; }
}
