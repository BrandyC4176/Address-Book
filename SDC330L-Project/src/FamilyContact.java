/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Family contact with relationship field.
 */
public class FamilyContact extends Contact {
    private String relationship;

    public FamilyContact(String first, String last, String phone, String email,
                         Address address, String relationship) {
        super(first, last, phone, email, address);
        this.relationship = (relationship == null ? "" : relationship.trim());
    }
    public FamilyContact(String first, String last, String relationship) {
        super(first, last);
        this.relationship = (relationship == null ? "" : relationship.trim());
    }

    @Override public String getType() { return "Family"; }

    @Override
    protected String getBadge() {
        return (relationship == null || relationship.isBlank()) ? "" : "Relationship: " + relationship;
    }

    public String getRelationship() { return relationship; }
}
