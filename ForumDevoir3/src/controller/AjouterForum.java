package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Forum;
import model.Utilisateur;

/**
 * Servlet implementation class AjouterForum
 */
@WebServlet("/AjouterForum")
public class AjouterForum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterForum() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd=null;
		response.setContentType("text/html");  
		rd = request.getRequestDispatcher("/gererForum.jsp");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    }
		
		String titre = request.getParameter("titre");
		String description = request.getParameter("description");
		if(titre.isEmpty() || description.isEmpty()) { //la description du forum et le titre ne peuvent pas être nuls
			rd.include(request, response);
			out.println("<p style=\"color:red\">Les champs ne peuvent pas etre vide.</p>");
			return;
		}

		try {
			//on créé le nouveau forum en tant qu'objet (pas encore persistant)
			Forum f = new Forum(titre, description, (Utilisateur)session.getAttribute("utilisateur"));
			f.save(); //rendre le forum persistant
			rd.include(request, response);
			out.println("<p style='color:green;'>Forum cree.</p>");
		} catch (ClassNotFoundException | SQLException | IOException e) { //si il y a une erreur on prévient l'utilisateur
			rd.include(request, response);
			out.println("<p style='color:red;'>Le forum n'a pas pu etre cree.</p>");
		}
	}
}