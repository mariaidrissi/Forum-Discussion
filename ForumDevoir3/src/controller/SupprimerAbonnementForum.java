package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utilisateur;

/**
 * Servlet implementation class SupprimerAbonnementForum
 */
@WebServlet("/SupprimerAbonnementForum")
public class SupprimerAbonnementForum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerAbonnementForum() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd=null;
		response.setContentType("text/html");  
		HttpSession session = request.getSession();
		
		//si aucun utilisateur n'est connecté
		if (session.getAttribute("login") == null) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    }
		
		String forum = request.getParameter("forum");
		if(forum == null || forum == "") { //si on ne connaît pas le forum
			rd = request.getRequestDispatcher("Deconnexion");
			rd.forward(request, response);
		}
		
		int forumId = Integer.parseInt(forum);

		try {
			((Utilisateur)session.getAttribute("utilisateur")).deleteForumSubscription(forumId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		rd = request.getRequestDispatcher("/menu.jsp");
		rd.forward(request, response);
	}
}
