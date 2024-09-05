/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.UsuarioJpaController;
import dto.Usuario;
import java.util.List;
import util.DES1;

/**
 *
 * @author USER
 */
public class Validar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            UsuarioJpaController usuDAO = new UsuarioJpaController();
            String ope = request.getParameter("ope");
            String clave = "12345678";
            switch (ope) {
                case "1":
                    String usuario = request.getParameter("logiUsua");
                    String pass = request.getParameter("passUsua");

                    String UsuarioDescifrado = DES1.decifrar(usuario, clave);

                    System.out.println("Usuario descifrado " + UsuarioDescifrado);

                    boolean validar = usuDAO.Validar(UsuarioDescifrado, pass);
                    if (validar) {
                        out.println("{\"resultado\":\"ok\"}");

                    } else {
                        out.println("{\"resultado\":\"error\"}");
                    }
                    break;

                case "2":
                    try {
                        List<Usuario> lista = usuDAO.findUsuarioEntities();
                        for (Usuario usuarios: lista) {
                            usuarios.setPassUsua(DES1.decifrar(usuarios.getPassUsua(), clave));
                        }
                        Gson g = new Gson();
                        String resultado = g.toJson(lista);
                        resultado = "{\"data\": " + resultado + "}";
                        out.print(resultado);
                    } catch (Exception e) {
                        out.print("{\"resultado\":\"error\"}");
                    }
                    break;
            }
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
