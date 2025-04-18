package service;

import dao.CitationDAO;
import java.util.List;
import model.Citation;

public class CitationService {
    private CitationDAO citationDAO;
    private UserService userService;
    private PublicationService publicationService;

    public CitationService() {
        this.citationDAO = new CitationDAO();
        this.userService = new UserService();
        this.publicationService = new PublicationService();
    }

    public List<Citation> getAllCitations() {
        return citationDAO.getAllCitations();
    }

    public Citation getCitationById(int citationId) {
        return citationDAO.getCitationById(citationId);
    }

    public boolean createCitation(Citation citation) {
        // Validate user exists
        if (userService.getUserById(citation.getUserId()) == null) {
            System.out.println("Error: User does not exist");
            return false;
        }

        // Validate publication exists
        if (publicationService.getPublicationById(citation.getPublicationId()) == null) {
            System.out.println("Error: Publication does not exist");
            return false;
        }

        return citationDAO.createCitation(citation);
    }

    public boolean updateCitation(Citation citation) {
        // Validate user exists
        if (userService.getUserById(citation.getUserId()) == null) {
            System.out.println("Error: User does not exist");
            return false;
        }

        // Validate publication exists
        if (publicationService.getPublicationById(citation.getPublicationId()) == null) {
            System.out.println("Error: Publication does not exist");
            return false;
        }

        return citationDAO.updateCitation(citation);
    }

    public boolean deleteCitation(int citationId) {
        return citationDAO.deleteCitation(citationId);
    }

    public List<Citation> getCitationsByCitingPublication(int publicationId) {
        return citationDAO.getCitationsByCitingPublication(publicationId);
    }

    public int getCitationCount(Citation citation) {
        return citationDAO.getCitationCountUsingFunction(citation);
    }
}
