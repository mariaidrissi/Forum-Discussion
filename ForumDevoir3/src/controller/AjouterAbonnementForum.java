package controller;

import java.io.IOException;
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
 * Servlet implementation class AjouterAbonnementForum
 */
@WebServlet("/AjouterAbonnementForum")
public class AjouterAbonnementForum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterAbonnementForum() {
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
		HttpSession session = request.getSession();
		
		if (session.getAttribute("login") == null) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    }
		
		String forum = request.getParameter("forum");
		if(forum == null || forum == "") { //si on a un problème avec la requête et que le forum n'est pas défini
			rd = request.getRequestDispatcher("Deconnexion");
			rd.forward(request, response);
		}
		
		int forumId = Integer.parseInt(forum);

		try {
			((Utilisateur)session.getAttribute("utilisateur")).addForumSubscription(forumId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//on redirige automatiquement vers le menu
			rd = request.getRequestDispatcher("/menu.jsp");
			rd.forward(request, response);
	}
}
