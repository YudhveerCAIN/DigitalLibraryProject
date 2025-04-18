package model;

public class Researcher {
    private int researcherId;             // Researcher_ID
    private int userId;                   // User_ID
    private String institution;           // Institution
    private String researchInterests;     // Research_Interests
    private int publicationsCount;        // Publications_Count

    // Constructors
    public Researcher() {}

    public Researcher(int researcherId, int userId, String institution,
                      String researchInterests, int publicationsCount) {
        this.researcherId = researcherId;
        this.userId = userId;
        this.institution = institution;
        this.researchInterests = researchInterests;
        this.publicationsCount = publicationsCount;
    }

    // Getters and Setters
    public int getResearcherId() {
        return researcherId;
    }

    public void setResearcherId(int researcherId) {
        this.researcherId = researcherId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getResearchInterests() {
        return researchInterests;
    }

    public void setResearchInterests(String researchInterests) {
        this.researchInterests = researchInterests;
    }

    public int getPublicationsCount() {
        return publicationsCount;
    }

    public void setPublicationsCount(int publicationsCount) {
        this.publicationsCount = publicationsCount;
    }

    // toString method
    @Override
    public String toString() {
        return "Researcher{" +
                "researcherId=" + researcherId +
                ", userId=" + userId +
                ", institution='" + institution + '\'' +
                ", researchInterests='" + researchInterests + '\'' +
                ", publicationsCount=" + publicationsCount +
                '}';
    }
}
