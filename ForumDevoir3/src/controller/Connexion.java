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
    		
    		if(username.isEmpty() || password.isEmpty()) { //si les champs sont vides
    			out.println("<p class='invalid'>Les champs ne peuvent pas être vides !</p>");
    			rd = request.getRequestDispatcher("/connexion.html");
    			rd.include(request, response);
    			return;
    		}
            // Vérifier si le login existe
            Utilisateur u = Utilisateur.FindByloginAndPwd(username, password);

            if (u == null) { //si l'utilisateur n'existe pas
    			out.println("<p class='invalid'>Echec : mot de passe ou login incorrect !</p>");
    			rd = request.getRequestDispatcher("/connexion.html");
    			rd.include(request, response);
           
            } else { //sinon, le connecter
                HttpSession session = request.getSession();
                //stocker dans la session les informations dont on va avoir besoin
                session.setAttribute("utilisateur", u);
                session.setAttribute("login", u.getLogin());
                session.setAttribute("role", u.getRole());
                session.setAttribute("nom", u.getLastName());
                session.setAttribute("prenom", u.getFirstName());
                session.setAttribute("gender", u.getGender());
               
                rd = request.getRequestDispatcher("/menu.jsp");
    			rd.include(request, response);
            }
        } catch (ClassNotFoundException | SQLException ex) {
        	out.println("<p class='invalid'>Echec.</p>");
			rd = request.getRequestDispatcher("/connexion.html");
			rd.include(request, response);
        }
    }
	
    @Override
    public void init() throws ServletException {
        super.init();
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