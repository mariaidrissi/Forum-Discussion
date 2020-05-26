package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
 * Servlet implementation class AfficherForum
 */
@WebServlet("/AfficherForum")
public class AfficherForum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherForum() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=request.getRequestDispatcher("/afficherForum.jsp");;
		rd.forward(request, response);
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
		
		//si on accède au forum depuis la page de menu 
		if(session.getAttribute("forum") == null) { 
			try {
				//sauvegarder le forum dans la session 
				String forum = request.getParameter("forum");
				int forumId = Integer.parseInt(forum);
				Forum f  = new Forum(forumId);
				session.setAttribute("forum", f);
			} catch (ClassNotFoundException | SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		//bloc pour déterminer le fil de discussion à afficher
		try {
			if(request.getParameter("option") == null || request.getParameter("option").equals("all")) {
				session.setAttribute("option", "all");
				session.setAttribute("arg1", null);
				session.setAttribute("arg2", null);
			}
			else {
				session.setAttribute("option", request.getParameter("option"));
				
				if(session.getAttribute("option").equals("nom")) {
					if(request.getParameter("arg1") != null && request.getParameter("arg1") != "") //si on a le nom, on l'ajoute dans la session
						session.setAttribute("arg1", request.getParameter("arg1"));
					else {  //si le nom n'est pas précisé, affiche tout par défaut
						session.setAttribute("option", "all");
						session.setAttribute("arg1", null);
						session.setAttribute("arg2", null);
					}
				} else if (session.getAttribute("option").equals("date")) {
					if(request.getParameter("arg2") != null && request.getParameter("arg2") != "") { //si on a la date, on l'ajoute dans la session
						try {
							System.out.println(request.getParameter("arg2"));
							java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("arg2"));
							java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
							session.setAttribute("arg2", sqlDate);
						} catch (ParseException e) {
							session.setAttribute("option", "all");
							session.setAttribute("arg2", null);
							e.printStackTrace();
						}
					} else { //si la date n'est pas précisée, on affiche tout par défaut
						session.setAttribute("option", "all");
						session.setAttribute("arg1", null);
						session.setAttribute("arg2", null);
					}
				}
			}
			rd = request.getRequestDispatcher("/afficherForum.jsp"); //afficherForum s'occupe du calcul du fil de discussion a partir des informations dans la session
			rd.forward(request, response);
		} catch (IOException e) { //on revient au menu si il a une erreur
			e.printStackTrace();
		}
	}
}