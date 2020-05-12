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

import model.Forum;
import model.Message;
import model.Utilisateur;

/**
 * Servlet implementation class Connexion
 */
@WebServlet(name = "Connexion", urlPatterns = {"/Connexion"})
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		RequestDispatcher rd=null;
		PrintWriter out = response.getWriter();
		
        try {
        	String username = request.getParameter("username");
    		String password = request.getParameter("password");
    		
    		if(username.isEmpty() || password.isEmpty()) {
    			out.println("<p style='color:red;'>Les champs ne peuvent pas etre vides !</p>");
    			rd = request.getRequestDispatcher("/connexion.html");
    			rd.include(request, response);
    			return;
    		}
            // Vérifier si le login existe
            Utilisateur u = Utilisateur.FindByloginAndPwd(username, password);

            if (u == null) {
    			out.println("<p style='color:red;'>Echec : mot de passe ou login incorrect !</p>");
    			rd = request.getRequestDispatcher("/connexion.html");
    			rd.include(request, response);
           
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("utilisateur", u);
                session.setAttribute("login", u.getLogin());
                String role = u.getRole();
                session.setAttribute("role", role);
                
                if ("admin".equalsIgnoreCase(role)) {
                	/*
                    out.println("<!DOCTYPE html>");
                    out.println("<html><head><title>Navigation Administrateur</title></head>");
                    out.println("<body>");
                    out.println("<div><h1>Bonjour "+session.getAttribute("login")+"</h1>");
                    out.println("<form action=\"/Forum/Deconnexion\" method=\"get\">");
                    out.println("<input type=\"submit\" value=\"Se deconnecter\">");
                    out.println("</form></div>");
                    out.println("<a href='NouveauUtilisateur.html'>Créer un nouveau utilisateur</a>");
                    out.println("<a href='UserManager'>Afficher la liste des utilisateurs</a>");
                    out.println("</body>");
                    out.println("</html>");
                    */
                	rd = request.getRequestDispatcher("/menuAdmin.jsp");
        			rd.include(request, response);
        			
                } else {
                	/*
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet Connexion</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Succes : utilisateur non admin </h1>");
                    out.println("</body>");
                    out.println("</html>");
                    */
                    rd = request.getRequestDispatcher("/menuUtilisateur.jsp");
        			rd.include(request, response);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
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

}