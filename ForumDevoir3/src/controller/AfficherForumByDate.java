package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AfficherForumByDate
 */
@WebServlet("/AfficherForumByDate")
public class AfficherForumByDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherForumByDate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=null;
		response.setContentType("text/html");  
		HttpSession session = request.getSession();
		String date = request.getParameter("date");
		
		if(date == null || date == "") {
			rd = request.getRequestDispatcher("Deconnexion");
			rd.forward(request, response);
		}

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
	        Date parsed = (Date) format.parse(date);
	        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
	        
			session.setAttribute("option", "date");
			session.setAttribute("arg1", null);
			session.setAttribute("arg2", sqlDate);
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
