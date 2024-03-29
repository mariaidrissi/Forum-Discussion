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
 * Servlet implementation class RetourForums
 */
@WebServlet("/RetourMenu")
public class RetourMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetourMenu() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd =null;
		HttpSession session = request.getSession();
		
		//si aucun utilisateur n'est connecté
		if (session.getAttribute("login") == null) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    }
		
		session.setAttribute("forum", null); //oublier le forum visité
		rd = request.getRequestDispatcher("/menu.jsp"); 
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}