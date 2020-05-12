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
		String forum = request.getParameter("forum");
		
		if(forum == null || forum == "") {
			rd = request.getRequestDispatcher("Deconnexion");
			rd.forward(request, response);
		}
		
		int forumId = Integer.parseInt(forum);
		Forum f;
		try {
			f = new Forum(forumId);
			session.setAttribute("forum", f);
			rd = request.getRequestDispatcher("/afficherForum.jsp");
			rd.forward(request, response);
		} catch (ClassNotFoundException | SQLException | IOException e) {
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