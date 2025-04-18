package service;

import dao.ResearchPaperDAO;
import model.ResearchPaper;

import java.util.List;

public class ResearchPaperService {
    private ResearchPaperDAO researchPaperDAO;

    public ResearchPaperService() {
        this.researchPaperDAO = new ResearchPaperDAO();
    }

    public boolean createResearchPaper(ResearchPaper paper) {
        return researchPaperDAO.createResearchPaper(paper);
    }

    public ResearchPaper getResearchPaperByPublicationId(int publicationId) {
        return researchPaperDAO.getResearchPaperByPublicationId(publicationId);
    }

    public boolean updateResearchPaper(ResearchPaper paper) {
        return researchPaperDAO.updateResearchPaper(paper);
    }

    public boolean deleteResearchPaper(int publicationId) {
        return researchPaperDAO.deleteResearchPaper(publicationId);
    }

    public List<ResearchPaper> getAllResearchPapers() {
        return researchPaperDAO.getAllResearchPapers();
    }
}
