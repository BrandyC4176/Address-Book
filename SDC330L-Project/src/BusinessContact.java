/*****
 * Name: Brandy Christopher
 * Date: 11/1/25
 * Purpose: Business contact (inheritance + composition with Company).
 */
public class BusinessContact extends Contact {
    private Company company;
    private String jobTitle;

    public BusinessContact(String first, String last, String phone, String email,
                           Address address, Company company, String jobTitle) {
        super(first, last, phone, email, address);
        this.company  = company;
        this.jobTitle = (jobTitle == null ? "" : jobTitle.trim());
    }

    public BusinessContact(String first, String last, Company company) {
        super(first, last);
        this.company  = company;
        this.jobTitle = "";
    }

    @Override public String getType() { return "Business"; }

    @Override
    protected String getBadge() {
        String co = (company != null ? company.toDisplayString() : "No Company");
        String jt = (jobTitle == null || jobTitle.isBlank()) ? "" : (" | Title: " + jobTitle);
        return "Company: " + co + jt;
    }

    public Company getCompany() { return company; }
    public String getJobTitle() { return jobTitle; }
}
