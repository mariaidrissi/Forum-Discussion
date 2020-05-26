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
 * Servlet implementation class EcrireMessage
 */
@WebServlet("/EcrireMessage")
public class EcrireMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EcrireMessage() {
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
		rd = request.getRequestDispatcher("/afficherForum.jsp"); 
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		//si aucun utilisateur n'est connecté
		if (session.getAttribute("login") == null) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    }
		
		String contenu = request.getParameter("contenu");
		Forum f = (Forum)session.getAttribute("forum");
		if(contenu.isEmpty()) {
			out.println("<p class='invalid'>Message ne peut pas être vide.</p>");
			rd.include(request, response);
			return;
		}
		
		try {
			f.addMessage(contenu, (Utilisateur)session.getAttribute("utilisateur"));
			rd.include(request, response);
		} catch (Exception e) {
			out.println("<p class='invalid'>Message n'a pas pu être posté.</p>");
			rd.include(request, response);
		}
	}
}