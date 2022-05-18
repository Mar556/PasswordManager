/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import entity.Buyer;
import entity.Cover;
import entity.History;
import entity.Sneaker;
import entity.SneakerCover;
import entity.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import session.HistoryFacade;
import session.SneakerCoverFacade;
import session.SneakerFacade;
import session.UserFacade;
import session.UserRolesFacade;
import tools.PasswordProtected;

/**
 *
 * @author Deniss
 */
@WebServlet(name = "UserServlet", urlPatterns = {
    "/buySneaker",
    "/listSneakers",
    "/showPerchase",
    "/showPurSneakers",
    "/listSneakersForBuy"
})
public class UserServlet extends HttpServlet {
    @EJB UserRolesFacade userRolesFacade;
    @EJB SneakerFacade sneakerFacade;
    @EJB HistoryFacade historyFacade;
    @EJB BuyerFacade buyerFacade;
    @EJB SneakerCoverFacade sneakerCoverFacade;
    @EJB UserFacade userFacade;
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
        switch(path){
            case "/listSneakersForBuy":
                List<Sneaker> sneakers = sneakerFacade.findAll();
                Map<Sneaker,Cover> mapSneakers = new HashMap<>();
                for (Sneaker b : sneakers) {
                    SneakerCover sneakerCover = sneakerCoverFacade.findCoverBySneaker(b);
                    mapSneakers.put(b, sneakerCover.getCover());
                }
                request.setAttribute("count", sneakers.size());
                request.setAttribute("mapSneakers", mapSneakers);
                request.getRequestDispatcher("/WEB-INF/showBuySneaker.jsp").forward(request, response);
                break;
            case "/listSneakers":
                sneakers = sneakerFacade.findAll();
                mapSneakers = new HashMap<>();
                for (Sneaker b : sneakers) {
                    SneakerCover sneakerCover = sneakerCoverFacade.findCoverBySneaker(b);
                    mapSneakers.put(b, sneakerCover.getCover());
                }
                request.setAttribute("count", sneakers.size());
                request.setAttribute("mapSneakers", mapSneakers);
                request.getRequestDispatcher("/showListSneakers.jsp").forward(request, response);
                break;
            case "/showPerchase":
                String selectedSneaker = request.getParameter("sneakerId");
                Sneaker sneaker = sneakerFacade.find(Long.parseLong(selectedSneaker));
                SneakerCover sneakercover = sneakerCoverFacade.findCoverBySneaker(sneaker);
                request.setAttribute("firm", sneaker.getSneakerFirm());
                request.setAttribute("model", sneaker.getSneakerModel());
                request.setAttribute("price", sneaker.getSneakerPrice());
                request.setAttribute("count", sneaker.getSneakerQuantity());
                request.setAttribute("size", sneaker.getSneakerSize());
                request.setAttribute("id", sneaker.getId());
                request.setAttribute("cover", sneakercover.getCover().getFileName());
                int c = sneaker.getSneakerQuantity();
                if (c==1||c==21||c==31||c==41||c==51||c==61||c==71||c==81||c==91) {
                    request.setAttribute("end", "а");
                }
                if (c==2||c==22||c==32||c==42||c==52||c==62||c==72||c==82||c==92||
                    c==3||c==23||c==33||c==43||c==53||c==63||c==73||c==83||c==93||
                    c==4||c==24||c==34||c==44||c==54||c==64||c==74||c==84||c==94) {
                    request.setAttribute("end", "ы");
                }
                request.getRequestDispatcher("/listSneakers").forward(request, response);
                break;
            case "/buySneaker":
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
                if(!userRolesFacade.isRole("BUYER",authUser)){
                    request.setAttribute("info", "У вас нет прав!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                session.setAttribute("topRole", userRolesFacade.getTopRole(authUser));
                try {
                    
                selectedSneaker = request.getParameter("sneakerId");
                sneaker = sneakerFacade.find(Long.parseLong(selectedSneaker));
                Buyer buyer = buyerFacade.find(authUser.getBuyer().getId());
                if (buyer.getBuyerMoney()<sneaker.getSneakerPrice()) {
                    request.setAttribute("info", "У вас не хватает денег. Пополните баланс");
                    request.getRequestDispatcher("/listSneakersForBuy").forward(request, response);
                    break;
                }
                if (sneaker.getSneakerQuantity()==0) {
                    request.setAttribute("info", "Сожалеем, кроссовок нет в наличии");
                    request.getRequestDispatcher("/listSneakersForBuy").forward(request, response);
                    break;
                }
                History history = new History();
                history.setBuyer(buyer);
                history.setSneaker(sneaker);
                history.setSoldSneaker(Calendar.getInstance().getTime());
                buyer.setBuyerMoney(buyer.getBuyerMoney()-sneaker.getSneakerPrice());
                sneaker.setSneakerQuantity(sneaker.getSneakerQuantity()-1);
                historyFacade.create(history);
                sneakerFacade.edit(sneaker);
                buyerFacade.edit(buyer);
                request.setAttribute("info", "Вы успешно купили обувь");
                request.getRequestDispatcher("/listSneakersForBuy").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("info", "Что-то пошло не так, обратитесь к разработчику");
                    request.getRequestDispatcher("/listSneakersForBuy").forward(request, response);
                }
                break;
            case "/showPurSneakers":
                session = request.getSession(false);
                if(session == null){
                    request.setAttribute("info", "Авторизуйтесь!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                authUser = (User) session.getAttribute("authUser");
                if(authUser == null){
                    request.setAttribute("info", "Авторизуйтесь!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                if(!userRolesFacade.isRole("BUYER",authUser)){
                    request.setAttribute("info", "У вас нет прав!");
                    request.getRequestDispatcher("/showLogin").forward(request, response);
                }
                session.setAttribute("topRole", userRolesFacade.getTopRole(authUser));
                
                List<History> history = historyFacade.findAll();
                List<History> neededHistory = new ArrayList<>();
                for (int i = 0; i < history.size(); i++) {
                    if(history.get(i).getBuyer().getBuyerPhone().equals(authUser.getBuyer().getBuyerPhone())){
                        neededHistory.add(history.get(i));
                    }
                }
                request.setAttribute("history", neededHistory);
                request.getRequestDispatcher("/WEB-INF/perchaseList.jsp").forward(request, response);
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

}
