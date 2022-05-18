/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Role;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BuyerFacade;
import session.CoverFacade;
import session.HistoryFacade;
import session.RoleFacade;
import session.SneakerCoverFacade;
import session.SneakerFacade;
import session.UserFacade;
import session.UserRolesFacade;

/**
 *
 * @author Deniss
 */
@WebServlet(name = "AdminPanel", urlPatterns = {"/showAdminPanel", "/changeRole"})
public class AdminPanel extends HttpServlet {
 @EJB BuyerFacade buyerFacade;
    @EJB SneakerFacade sneakerFacade;
    @EJB UserRolesFacade userRolesFacade;
    @EJB CoverFacade coverFacade;
    @EJB SneakerCoverFacade sneakerCoverFacade;
    @EJB HistoryFacade historyFacade;
    @EJB UserFacade userFacade;
    @EJB RoleFacade roleFacade;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if(session == null){
            request.setAttribute("info", "Авторизуйтесь!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        User authUser = (User) session.getAttribute("authUser");
        if(authUser == null){
            request.setAttribute("info", "Авторизуйтесь!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        if(!userRolesFacade.isRole("ADMINISTRATOR",authUser)){
            request.setAttribute("info", "Только администратор может пользоваться этой функцией!!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
              
        session.setAttribute("topRole", userRolesFacade.getTopRole(authUser));
        String path = request.getServletPath();
        switch (path){             
            case "/showAdminPanel":
                Map<User,String> mapUsers = new HashMap<>();
                List<User> users = userFacade.findAll();
                for (User u : users) {
                    String topRole = userRolesFacade.getTopRole(u);
                    mapUsers.put(u, topRole);
                }
                request.setAttribute("mapUsers", mapUsers);
                List<Role> roles = roleFacade.findAll();
                request.setAttribute("roles", roles);
                request.getRequestDispatcher("/WEB-INF/showAdminPanel.jsp").forward(request, response);
                break;
            case "/changeRole":
                String userId = request.getParameter("userId");
                String roleId = request.getParameter("roleId");
                User userLogin = userFacade.find(Long.parseLong(userId));
                if(userLogin.getLogin().equals("admin")){
                    request.setAttribute("info", "Этому пользователю роль изменить невозможно.");
                    request.getRequestDispatcher("/showAdminPanel").forward(request, response);
                    break;
                }

                User u = userFacade.find(Long.parseLong(userId));
                Role r = roleFacade.find(Long.parseLong(roleId));
                userRolesFacade.setRoleToUser(r,u);
                
                request.setAttribute("info", "Роль изменена");
                
                request.getRequestDispatcher("/showAdminPanel").forward(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}