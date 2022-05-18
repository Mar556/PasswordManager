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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
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
import session.SneakerFacade;
import session.UserFacade;
import session.UserRolesFacade;

/**
 *
 * @author Deniss
 */
@WebServlet(name = "WebController", loadOnStartup = 1, urlPatterns = {
    "/showReceipt",
    "/createReceipt",
    "/showAddSneaker",
    "/createSneaker",
    "/showListBuyers",
})
public class WebController extends HttpServlet {
    @EJB BuyerFacade buyerFacade;
    @EJB SneakerFacade sneakerFacade;
    @EJB UserRolesFacade userRolesFacade;
    @EJB CoverFacade coverFacade;
    @EJB SneakerCoverFacade sneakerCoverFacade;
    @EJB HistoryFacade historyFacade;
    @EJB UserFacade userFacade;
    @EJB RoleFacade roleFacade;

    
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
        if(!userRolesFacade.isRole("MANAGER",authUser)){
            request.setAttribute("info", "У вас нет прав!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        session.setAttribute("topRole", userRolesFacade.getTopRole(authUser));
        String path = request.getServletPath();
        switch (path){
            case "/showListBuyers":
                List<Buyer> buyers = buyerFacade.findAll();
                request.setAttribute("buyers", buyers);
                request.getRequestDispatcher("/WEB-INF/showListBuyers.jsp").forward(request, response);
                break;
            case "/showReceipt":
                List<Sneaker> sneakers = sneakerFacade.findAll();
                request.setAttribute("listSneakers", sneakers); 
                request.getRequestDispatcher("/WEB-INF/showReceipt.jsp").forward(request, response);
                break;
            case "/createReceipt": 
                try {  
                String sneaker1 = request.getParameter("sneakers");
                String receipt = request.getParameter("receipt");
                Sneaker selectedSneaker = sneakerFacade.find(Long.parseLong(sneaker1));
                selectedSneaker.setSneakerQuantity(selectedSneaker.getSneakerQuantity()+Integer.parseInt(receipt));
                sneakerFacade.edit(selectedSneaker);
                request.setAttribute("info", "Поступления добавлены");
                request.getRequestDispatcher("/showReceipt").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("info", "Напишите поступление");
                    request.getRequestDispatcher("/showReceipt").forward(request, response);
                }
                break;
            case "/showAddSneaker":
                List<Cover> covers = coverFacade.findAll();
                request.setAttribute("listCovers", covers);
                request.getRequestDispatcher("/WEB-INF/showAddSneaker.jsp").forward(request, response);
                break;
            case "/createSneaker":
                String firm = request.getParameter("firm");
                String model = request.getParameter("model");
                String size = request.getParameter("size");
                String price = request.getParameter("price");
                String quantity = request.getParameter("quantity");
                String cover = request.getParameter("coversId");
                if ("".equals(model) || "".equals(size) || "".equals(price) || "".equals(quantity) || "".equals(cover)) {
                    request.setAttribute("model", model);
                    request.setAttribute("size", size);
                    request.setAttribute("price", price);
                    request.setAttribute("coversId", cover);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("info", "Заполните все поля!");
                    request.getRequestDispatcher("/showAddSneaker").forward(request, response);
                    break;
                }
                Sneaker sneaker = new Sneaker();
                sneaker.setSneakerFirm(firm);
                sneaker.setSneakerModel(model);
                try {                    
                sneaker.setSneakerPrice(Double.parseDouble(price));
                sneaker.setSneakerQuantity(Integer.parseInt(quantity));
                sneaker.setSneakerSize(Integer.parseInt(size));
                } catch (Exception e) {
                    request.setAttribute("model", model);
                    request.setAttribute("size", size);
                    request.setAttribute("price", price);
                    request.setAttribute("quantity", quantity);
                    request.setAttribute("info", "Неверный ввод полей с цифрами");
                    request.getRequestDispatcher("/showAddSneaker").forward(request, response);
                    break;
                }
                sneakerFacade.create(sneaker);
                Cover selectedCover = coverFacade.find(Long.parseLong(cover));
                SneakerCover sneakerCover = new SneakerCover();
                sneakerCover.setSneaker(sneaker);
                sneakerCover.setCover(selectedCover);
                sneakerCoverFacade.create(sneakerCover);
                request.setAttribute("info", "Вы добавили кроссовок!");
                request.getRequestDispatcher("/showAddSneaker").forward(request, response);
                break;
            default: 
                throw new AssertionError();
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

    private boolean summator(Date date, int choicemonth, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        if(month==choicemonth && year==years){
            return true;
    }
        return false;
    }
    

}


