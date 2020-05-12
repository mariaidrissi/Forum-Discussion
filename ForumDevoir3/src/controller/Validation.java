package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utilisateur;

/**
 *
 * @author lounis
 */
@WebServlet(name = "Validation", urlPatterns = {"/Validation"})
public class Validation extends HttpServlet {

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
        boolean valid = true;

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
        	RequestDispatcher rd = request.getRequestDispatcher("Deconnexion");
        	rd.forward(request, response);
        } else {
            try {
            	RequestDispatcher rd = request.getRequestDispatcher("UserManager");
            	PrintWriter out = response.getWriter();
            	
                String firstName = request.getParameter("User first name");
                String lastName = request.getParameter("User familly name");
                String mail = request.getParameter("User email");
                String gender = request.getParameter("gender");
                String password = request.getParameter("User password");
                
                if (firstName == null || lastName == null || mail == null || password == null) {
                    out.println("<p style='color:red;'>Les champs ne peuvent pas etre vides !</p>");
                    rd.include(request, response);
                    valid = false;
                } else if ("".equals(firstName) || "".equals(lastName) || "".equals(mail) || "".equals(password)) {
                	out.println("<p style='color:red;'>Les champs ne peuvent pas etre vides !</p>");
                    rd.include(request, response);
                    valid = false;
                }
                
                if (request.getParameter("validator") != null) {// des doublons ont été détectés et l'utilisateur à valider son choix
                    if ("oui".equals(request.getParameter("valider"))) {// on insère les doublons
                        valid = true;
                    } else {
                        valid = false;
                        rd.forward(request, response); //abandonner insertion
                    }
                    
                } else if (Utilisateur.FindByLastAndFirstName(firstName,lastName)!=null) {
                    valid = false;
              
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet Validation</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Un utilisateur avec les mêmes nom et prénom existe déjà. Voulez-vous l'enregistrer ?  </h1>");
                    out.println("<form method='POST' action='Validation'>");
                    out.println("Oui <input type='radio' name='valider' value='oui' /> ");
                    out.println("Nom <input type='radio' name='valider' value='nom' />");
                    out.println("<input type='hidden' name='User first name' value='" + firstName + "'/>");
                    out.println("<input type='hidden' name='User familly name' value='" + lastName + "'/>");
                    out.println("<input type='hidden' name='User email' value='" + mail + "'/>");
                    out.println("<input type='hidden' name='gender' value='" + gender + "'/>");
                    out.println("<input type='hidden' name='User password' value='" + password + "' />");
                    out.println("<br>");
                    out.println("<input type ='submit' value='Envoyer' name='validator' />");
                    out.println("</form>");
                    out.println("</body>");
                    out.println("</html>");
                }
                if (valid) {
                	out.println("<p style='color:green;'>Insertion effectuee.</p>");
                    rd = request.getRequestDispatcher("UserManager");
                    rd.include(request, response);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

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
    }

}