/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Cover;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import static jdk.nashorn.internal.objects.NativeError.getFileName;
import session.CoverFacade;
import session.UserRolesFacade;

/**
 *
 * @author Deniss
 */
@WebServlet(name = "FileServlet", urlPatterns = {"/showUploadCover", "/uploadCover"})
@MultipartConfig
public class FileServlet extends HttpServlet {
@EJB UserRolesFacade userRolesFacade;
@EJB CoverFacade coverFacade;
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
        if(!userRolesFacade.isRole("MANAGER",authUser)){
            request.setAttribute("info", "У вас нет прав!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
        }
        String uploadFolder = "C:\\UploadDir\\WebBootsShop";
        session.setAttribute("topRole", userRolesFacade.getTopRole(authUser));
        String path = request.getServletPath();
        switch (path){
            case "/showUploadCover":
                request.getRequestDispatcher("/WEB-INF/uploadFile.jsp").forward(request, response);
                break;
            case "/uploadCover":
                try {
                    
                List<Part> fileParts = request.getParts().stream()
                .filter(part -> "file".equals(part.getName()))
                .collect(Collectors.toList());
                StringBuilder sb = new StringBuilder();
                for (Part filePart : fileParts) {
                    sb.append(uploadFolder+File.separator + getFileName(filePart));
                    File file = new File(sb.toString());
                    file.mkdirs();
                    try (InputStream fileContent = filePart.getInputStream()){
                        Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } 
                }
                String description = request.getParameter("description");
                    if ("".equals(description)) {
                        request.setAttribute("info", "Напишите описание");
                        request.getRequestDispatcher("/showUploadCover").forward(request, response);
                    }
                Cover cover = new Cover();
                cover.setDescription(description);
                cover.setFileName(sb.toString());
                coverFacade.create(cover);
                request.setAttribute("info", "Вы добавили обложку");
                request.getRequestDispatcher("/showUploadCover").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("info", "Нужно выбрать файл");
                    request.getRequestDispatcher("/showUploadCover").forward(request, response);
                }
                break;
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
    }// </editor-fold>

    private String getFileName(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")){
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=')+1).trim().replace("\"","");
            }
        }
        return null;
    }

}
