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
        if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
           rd=request.getRequestDispatcher("Deconnexion");
           rd.forward(request, response);
        } else  {
        	PrintWriter out = response.getWriter();
    		rd = request.getRequestDispatcher("/nouvelUtilisateur.jsp");
    		rd.forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd=null;
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	     } 
		rd = request.getRequestDispatcher("/nouvelUtilisateur.jsp");
		
		 String firstName = request.getParameter("User first name");
         String lastName = request.getParameter("User familly name");
         String mail = request.getParameter("User login");
         String gender = request.getParameter("gender");
         String password = request.getParameter("User password");
         
         if (firstName == null || lastName == null || mail == null || password == null) {
             out.println("<p style='color:red;'>Les champs ne peuvent pas etre vides !</p>");
             rd.include(request, response);

         } else if ("".equals(firstName) || "".equals(lastName) || "".equals(mail) || "".equals(password)) {
         	out.println("<p style='color:red;'>Les champs ne peuvent pas etre vides !</p>");
             rd.include(request, response);
         }
       
         Utilisateur user = new Utilisateur(lastName, firstName, mail, gender, password);
         if (request.getParameter("role") != null) {
             user.setRole(request.getParameter("role"));
         }
         
        try {
			user.save();
			rd.include(request, response);
			out.println("<p style='color:green;'>Utilisateur ajoute !</p>");
	        
		} catch (Exception e) {
			rd.include(request, response);
			out.println("<p style='color:red;'>Utilisateur n'a pas pu etre ajoute !</p>");
	    
		}
	}
}