package model;

public class ResearchPaper {
    private int publicationId;       // Publication_ID
    private String journalName;      // Journal_Name
    private String conferenceName;   // Conference_Name
    private String doi;              // DOI
    private String keywords;         // Keywords
    private String abstractText;     // Abstract
    private String file;             // File
    private int citationsCount;      // Citations_Count

    // Constructors
    public ResearchPaper() {}

    public ResearchPaper(int publicationId, String journalName, String conferenceName,
                         String doi, String keywords, String abstractText, String file,
                         int citationsCount) {
        this.publicationId = publicationId;
        this.journalName = journalName;
        this.conferenceName = conferenceName;
        this.doi = doi;
        this.keywords = keywords;
        this.abstractText = abstractText;
        this.file = file;
        this.citationsCount = citationsCount;
    }

    // Getters and Setters
    public int getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getCitationsCount() {
        return citationsCount;
    }

    public void setCitationsCount(int citationsCount) {
        this.citationsCount = citationsCount;
    }

    // toString
    @Override
    public String toString() {
        return "ResearchPaper{" +
                "publicationId=" + publicationId +
                ", journalName='" + journalName + '\'' +
                ", conferenceName='" + conferenceName + '\'' +
                ", doi='" + doi + '\'' +
                ", keywords='" + keywords + '\'' +
                ", abstractText='" + abstractText + '\'' +
                ", file='" + file + '\'' +
                ", citationsCount=" + citationsCount +
                '}';
    }
}
