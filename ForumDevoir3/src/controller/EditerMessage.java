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

import model.Message;
import model.Utilisateur;

/**
 * Servlet implementation class EditerMessage
 */
@WebServlet("/EditerMessage")
public class EditerMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditerMessage() {
        super();
        // TODO Auto-generated constructor stub
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
		rd = request.getRequestDispatcher("/afficherForum.jsp");
		response.setContentType("text/html");  
		String message = request.getParameter("messageId");
		String contenu = request.getParameter("contenu");
		if(message != null && message != "" && contenu != null && contenu != "") {
			int messageId = Integer.parseInt(message);
			System.out.println(messageId);
			Message m = Message.FindbyId(messageId);
			if(m == null) {
				System.out.println("couldn't find it");
				rd.forward(request, response);
				return;
			}
			m.setContenu(contenu);
			java.util.Date utilDate = new java.util.Date();
			m.setDatePub(new java.sql.Date(utilDate.getTime()));
			try {
				m.save();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		rd.forward(request, response);
	}

}
