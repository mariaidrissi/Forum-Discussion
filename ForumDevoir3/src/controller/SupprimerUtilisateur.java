package controller;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation class SupprimerUtilisateur
 */
@WebServlet("/SupprimerUtilisateur")
public class SupprimerUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerUtilisateur() {
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
		rd = request.getRequestDispatcher("/gererUtilisateur.jsp"); //est forcément un admin pour supprimer un forum, test en dessous
		HttpSession session = request.getSession();
		
		//si aucun utilisateur n'est connecté ou que ce n'est pas un administrateur
		if (session.getAttribute("login") == null || !"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    } 
		try {
			String idU = request.getParameter("idU");
			
			if(idU == null || idU == "") { //si on ne connaît pas l'utilisateur à supprimer
				out.println("<p class='invalid'>Le champ ne peut pas être vide !</p>");
	            rd.include(request, response);
			}
			
			int idUtil = Integer.parseInt(idU);

			Utilisateur.supprimerUtilisateur(idUtil);
			if(idUtil != ((Utilisateur)session.getAttribute("utilisateur")).getId()) {
				out.println("<p class='valid'>Utilisateur supprimé !</p>");
				rd.include(request, response);
			} else {
				rd = request.getRequestDispatcher("Deconnexion");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
			out.println("<p class='invalid'>Utilisateur n'a pas pu être supprimé !</p>");
			rd.include(request, response);
		}
	}

}
