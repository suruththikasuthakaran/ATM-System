package services;

import com.google.gson.Gson;
import dao.ContributionDao;
import model.Contribution;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/contributions/*")
public class ContributionServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final ContributionDao contributionDao = new ContributionDao();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (BufferedReader reader = request.getReader()) {
            Contribution contribution = gson.fromJson(reader, Contribution.class);
            boolean isSaved = contributionDao.saveContribution(contribution);

            if (isSaved) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\":\"Contribution saved successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Failed to insert contribution\"}");
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {

            List<Contribution> list = contributionDao.getAllContributions();
            response.getWriter().write(gson.toJson(list));
        } else {

            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                Contribution contribution = contributionDao.getContributionById(id);

                if (contribution != null) {
                    response.getWriter().write(gson.toJson(contribution));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\":\"Contribution not found\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Invalid ID format\"}");
            }
        }
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try (BufferedReader reader = request.getReader()) {
            Contribution contribution = gson.fromJson(reader, Contribution.class);

            if (contribution.getId() == 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Missing contribution ID\"}");
                return;
            }

            boolean isUpdated = contributionDao.updateContribution(contribution);
            if (isUpdated) {
                response.getWriter().write("{\"message\":\"Contribution updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Contribution not found\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid JSON: " + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID is required for delete\"}");
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            boolean isDeleted = contributionDao.deleteContribution(id);

            if (isDeleted) {
                response.getWriter().write("{\"message\":\"Contribution deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Contribution not found\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
        }
    }
}
