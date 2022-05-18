/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Buyer;
import entity.Cover;
import entity.History;
import entity.Role;
import entity.Sneaker;
import entity.SneakerCover;
import session.SneakerCoverFacade;
import entity.User;
import entity.UserRoles;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
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
import session.HistoryFacade;
import session.RoleFacade;
import session.SneakerFacade;
import session.UserFacade;
import session.UserRolesFacade;
import tools.PasswordProtected;

/**
 *
 * @author Deniss
 */
@WebServlet(name = "LoginServlet", urlPatterns = {
    "/showLogin",
    "/showRegistration",
    "/login",
    "/logout",
    "/registration",
})
public class LoginServlet extends HttpServlet {
    @EJB HistoryFacade historyFacade;
    @EJB UserFacade userFacade;
    @EJB BuyerFacade buyerFacade;
    @EJB RoleFacade roleFacade;
    @EJB UserRolesFacade userRolesFacade;
    @EJB SneakerFacade sneakerFacade;
    @EJB SneakerCoverFacade sneakerCoverFacade;
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        if(userFacade.count() != 0) return;
        Buyer buyer = new Buyer();
        buyer.setBuyerFirstName("Juri");
        buyer.setBuyerLastName("Melnikov");
        buyer.setBuyerPhone("5654456767");
        buyerFacade.create(buyer);
        User user = new User();
        user.setLogin("admin");
        PasswordProtected pp = new PasswordProtected();
        String salt = pp.getSalt();
        user.setSalt(salt);
        String password = pp.passwordEncript("12345", salt);
        user.setPassword(password);
        user.setBuyer(buyer);
        userFacade.create(user);
        Role role = new Role();
        role.setRoleName("BUYER");
        roleFacade.create(role);
        UserRoles userRoles = new UserRoles();
        userRoles.setRole(role);
        userRoles.setUser(user);
        userRolesFacade.create(userRoles);
        role = new Role();
        role.setRoleName("MANAGER");
        roleFacade.create(role);
        userRoles = new UserRoles();
        userRoles.setRole(role);
        userRoles.setUser(user);
        userRolesFacade.create(userRoles);
        role = new Role();
        role.setRoleName("ADMINISTRATOR");
        roleFacade.create(role);
        userRoles = new UserRoles();
        userRoles.setRole(role);
        userRoles.setUser(user);
        userRolesFacade.create(userRoles);
    }

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
        String path = request.getServletPath();
        switch (path){
            case "/showLogin":
                request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                break;
            case "/login":
                String login = request.getParameter("login");
                String password = request.getParameter("password");
                User authUser = userFacade.findByLogin(login);
                if (authUser == null) {
                    request.setAttribute("info", "Неверный логин или пароль");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                PasswordProtected pp = new PasswordProtected();
                String salt = authUser.getSalt();
                String sequrePassword = pp.passwordEncript(password, salt);
                if(!sequrePassword.equals(authUser.getPassword())){
                    request.setAttribute("info", "Неверный логин или пароль!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                    break;
                }
                HttpSession session = request.getSession();
                session.setAttribute("authUser", authUser);
                request.setAttribute("info", "Здравствуйте "+authUser.getBuyer().getBuyerFirstName());
                session.setAttribute("topRole", userRolesFacade.getTopRole(authUser));
                request.getRequestDispatcher("/listSneakers").forward(request, response);
                break;
            case "/logout":
                session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                    request.setAttribute("info", "Вы вышли");
                }
                request.getRequestDispatcher("/showLogin").forward(request, response);
                break;
            case "/showRegistration":
                request.getRequestDispatcher("/showRegistration.jsp").forward(request, response);
                break;
            case "/registration":
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");
                String buyerPhone = request.getParameter("buyerPhone");
                String buyerMoney = request.getParameter("buyerMoney");
                login = request.getParameter("login");
                password = request.getParameter("password");
                String password2 = request.getParameter("password2");
                if (!password.equals(password2)) {
                    request.setAttribute("firstName", firstName);
                    request.setAttribute("lastName", lastName);
                    request.setAttribute("buyerPhone", buyerPhone);
                    request.setAttribute("buyerMoney", buyerMoney);
                    request.setAttribute("login", login);
                    request.setAttribute("info", "Пароли не совпадают");
                    request.getRequestDispatcher("/showRegistration").forward(request, response);
                    break;
                }
                List<User> users = userFacade.findAll();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getLogin().equals(login)) {
                       request.setAttribute("firstName", firstName);
                       request.setAttribute("lastName", lastName);
                       request.setAttribute("buyerPhone", buyerPhone);
                       request.setAttribute("buyerMoney", buyerMoney);
                       request.setAttribute("info", "Пользователь с таким логином уже зарегистрирован");
                       request.getRequestDispatcher("/showRegistration").forward(request, response);
                       return; 
                    }}
                if ("".equals(firstName) || "".equals(lastName) || 
                    "".equals(buyerPhone) || "".equals(buyerMoney) || 
                    "".equals(login) || "".equals(password) || "".equals(password2)) {
                    request.setAttribute("firstName", firstName);
                    request.setAttribute("lastName", lastName);
                    request.setAttribute("buyerPhone", buyerPhone);
                    request.setAttribute("buyerMoney", buyerMoney);
                    request.setAttribute("login", login);
                    request.setAttribute("info", "Заполните все поля");
                    request.getRequestDispatcher("/showRegistration").forward(request, response);
                    break; 
                }
                Buyer buyer = new Buyer();
                buyer.setBuyerFirstName(firstName);
                buyer.setBuyerLastName(lastName);
                buyer.setBuyerMoney(Double.parseDouble(buyerMoney));
                buyer.setBuyerPhone(buyerPhone);
                buyerFacade.create(buyer);
                User user = new User();
                user.setBuyer(buyer);
                user.setLogin(login);
                pp = new PasswordProtected();
                salt = pp.getSalt();
                user.setSalt(salt);
                sequrePassword = pp.passwordEncript(password, salt);
                user.setPassword(sequrePassword);
                userFacade.create(user);
                Role readerRole = roleFacade.findByRoleName("BUYER");
                UserRoles userRoles = new UserRoles();
                userRoles.setRole(readerRole);
                userRoles.setUser(user);
                userRolesFacade.create(userRoles);
                request.setAttribute("info", "Вы успешно зарегистрировались");
                request.getRequestDispatcher("/showLogin").forward(request, response);
                break;
            default: 
                throw new AssertionError();
        
    }}

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
    }}// </editor-fold>



