/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import java.io.ByteArrayOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.UsuarioJpaController;
import dto.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DES1;
/**
 *
 * @author USER
 */
public class Reporte extends HttpServlet {

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
        response.setContentType("application/pdf");
         String clave = "12345678";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();
            document.add(new Paragraph("Lista de Usuarios"));
            document.add(new Paragraph("                          "));

            UsuarioJpaController usuarioDAO = new UsuarioJpaController();

            List<Usuario> lista = usuarioDAO.findUsuarioEntities();
            PdfPTable table = new PdfPTable(4);

            table.addCell("N° DNI");
            table.addCell("USUARIO");
            table.addCell("CONTRASEÑA");
            table.addCell("NOMBRES Y APELLIDOS");

            if (lista != null && !lista.isEmpty()) {
                for (Usuario usuarios : lista) {
                    usuarios.setPassUsua(DES1.decifrar(usuarios.getPassUsua(), clave));
                    table.addCell(String.valueOf(usuarios.getCodiUsua()));
                    table.addCell(usuarios.getLogiUsua());
                    table.addCell(usuarios.getPassUsua());
                    table.addCell(usuarios.getNombUsua());
                }
            } else {
                PdfPCell cell = new PdfPCell(new Paragraph("No hay registros de usuarios"));
                cell.setColspan(4);
                table.addCell(cell);
            }
            document.add(table);
            document.close();

            byte[] pdfBytes = baos.toByteArray();

            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (DocumentException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
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
