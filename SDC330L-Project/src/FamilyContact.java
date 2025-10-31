/*
* Name: Brandy Christopher
 * SDC330L Project
 * Date: 10/28/25 
 * Purpose: Represents a family contact.
 */

public class FamilyContact extends Contact {
    private String relationship;

    public FamilyContact(String first, String last, String phone, String email,
                         Address address, String relationship) {
        super(first, last, phone, email, address);
        this.relationship = safe(relationship);
    }

    public FamilyContact(String first, String last, String relationship) {
        super(first, last);
        this.relationship = safe(relationship);
    }

    @Override public String getType() { return "Family"; }

    @Override
    protected String getBadge() {
        return (relationship == null || relationship.isBlank())
                ? "" : "Relationship: " + relationship;
    }
}
