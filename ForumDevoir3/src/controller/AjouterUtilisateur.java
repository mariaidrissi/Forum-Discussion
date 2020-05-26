package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

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
 * Servlet implementation class ajouterUtilisateur
 */
@WebServlet("/AjouterUtilisateur")
public class AjouterUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterUtilisateur() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequestDispatcher rd=null;
		response.setContentType("text/html");  
		//si l'utilisateur n'est pas connecté ou si ce n'est pas un administrateur (page administrateur uniquement), on le déconnecte
        if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
           rd=request.getRequestDispatcher("Deconnexion");
           rd.forward(request, response);
        } else  { //sinon on lui sert la page pour gérer les utilisateurs
        	PrintWriter out = response.getWriter();
    		rd = request.getRequestDispatcher("/gererUtilisateur.jsp");
    		rd.forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd=null;
		rd = request.getRequestDispatcher("/gererUtilisateur.jsp");
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		//si l'utilisateur n'est pas connecté ou si ce n'est pas un administrateur (page administrateur uniquement), on le déconnecte
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
		       rd=request.getRequestDispatcher("Deconnexion");
		       rd.forward(request, response);
		       return;
		 } 
		
		//récupérer les paramètres du nouvel utilisateur
		 String firstName = request.getParameter("User first name");
		 String lastName = request.getParameter("User familly name");
		 String mail = request.getParameter("User login");
		 String gender = request.getParameter("gender");
		 String password = request.getParameter("User password");
		 
		 if (firstName == null || lastName == null || mail == null || password == null) { //les champs n'existent pas
		     out.println("<p class='invalid'>Les champs ne peuvent pas être vides !</p>");
		     rd.include(request, response);
		
		 } else if ("".equals(firstName) || "".equals(lastName) || "".equals(mail) || "".equals(password)) { //les champs sont vides
		 	out.println("<p class='invalid'>Les champs ne peuvent pas être vides !</p>");
		 	rd.include(request, response);
	     }

		try {
			//créé l'utilisateur (pas encore persistant)
		    Utilisateur user = new Utilisateur(lastName, firstName, mail, gender, password);
		    String [] admin;
		    admin = request.getParameterValues("admin");
		    if (admin != null) {
		    	user.setRole(admin[0]);
			} 
			user.save(); //rendre l'utilisateur persistant
			out.println("<p class='valid'>Utilisateur ajouté !</p>");
			rd.include(request, response);
		} catch (Exception e) {
			System.out.println(e);
			out.println("<p class='invalid'>Utilisateur n'a pas pu être ajouté !</p>");
			rd.include(request, response);
		}
	}
}