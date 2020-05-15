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

/**
 * Servlet implementation class AfficherForumByNom
 */
@WebServlet("/AfficherForumByNom")
public class AfficherForumByNom extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherForumByNom() {
        super();
        // TODO Auto-generated constructor stub
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
		String nom = request.getParameter("nom");
		
		if(nom == null || nom == "") {
			rd = request.getRequestDispatcher("Deconnexion");
			rd.forward(request, response);
		}

		try {
			session.setAttribute("option", "nom");
			session.setAttribute("arg1", nom);
			session.setAttribute("arg2", null);
			rd = request.getRequestDispatcher("/afficherForum.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			if(!"admin".equalsIgnoreCase((String) session.getAttribute("role"))) {
				rd = request.getRequestDispatcher("/menuUtilisateur.jsp");
				rd.forward(request, response);
			} else {
				rd = request.getRequestDispatcher("/menuAdmin.jsp");
				rd.forward(request, response);
			}
		}
	}

}
