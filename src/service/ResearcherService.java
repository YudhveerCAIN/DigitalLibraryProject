package service;

import dao.ResearcherDAO;
import model.Researcher;

import java.util.List;

public class ResearcherService {
    private ResearcherDAO researcherDAO;

    public ResearcherService() {
        this.researcherDAO = new ResearcherDAO();
    }

    public boolean createResearcher(Researcher researcher) {
        return researcherDAO.createResearcher(researcher);
    }

    public Researcher getResearcherById(int researcherId) {
        return researcherDAO.getResearcherById(researcherId);
    }

    public boolean updateResearcher(Researcher researcher) {
        return researcherDAO.updateResearcher(researcher);
    }

    public boolean deleteResearcher(int researcherId) {
        return researcherDAO.deleteResearcher(researcherId);
    }

    public List<Researcher> getAllResearchers() {
        return researcherDAO.getAllResearchers();
    }
}
