package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");  
		HttpSession session = request.getSession();
		
		//si aucun utilisateur n'est connecté
		if (session.getAttribute("login") == null) {
	           rd=request.getRequestDispatcher("Deconnexion");
	           rd.forward(request, response);
	           return;
	    }
		
		String message = request.getParameter("messageId");
		String contenu = request.getParameter("contenu");
		
		if(message != null && message != "" && contenu != null && contenu != "") {
			int messageId = Integer.parseInt(message);
			
			Message m = Message.FindbyId(messageId); //retrouver le message concerné
			if(m == null) {
				System.out.println("Le message n'existe pas !");
			} else {
				m.setContenu(contenu + " (édité)"); //mettre à jour le contenu
				java.util.Date utilDate = new java.util.Date(); 
				m.setDatePub(new java.sql.Date(utilDate.getTime())); //mettre à jour la date de publication à maintenant
				try {
					m.save(); //mettre à jour dans la base de données.
					rd.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
					out.println("<p class='invalid'>Message n'a pas pu être édité.</p>");
					rd.include(request, response);
				}
			}
		} else {
			out.println("<p class='invalid'>Message ne peut pas être vide.</p>");
			rd.include(request, response);
			return;
		}
	}

}
