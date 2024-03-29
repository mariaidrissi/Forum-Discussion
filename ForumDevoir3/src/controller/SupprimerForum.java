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
 * Servlet implementation class SupprimerForum
 */
@WebServlet("/SupprimerForum")
public class SupprimerForum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerForum() {
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
		PrintWriter out = response.getWriter();
		rd = request.getRequestDispatcher("/gererForum.jsp"); //est forcément un admin pour supprimer un forum, test en dessous
		HttpSession session = request.getSession();
		
		//si aucun utilisateur n'est connecté ou que ce n'est pas un administrateur
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    }
		
		String forum = request.getParameter("forum");
		if(forum == null || forum == "") { //si on ne connaît pas le forum
			out.println("<p class='invalid'>Forum doit être renseigné.</p>");
			rd.include(request, response);
			return;
		}
		
		int forumId = Integer.parseInt(forum);

		try {
			Forum.supprimerForum(forumId);
			out.println("<p class='valid'>Forum supprimé !</p>");
			rd.include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.println("<p class='invalid'>Le forum n'a pas pu être supprimé.</p>");
			rd.include(request, response);
		}
	}
}
