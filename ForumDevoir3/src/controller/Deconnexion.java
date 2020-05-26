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

/**
 * Servlet implementation class Deconnexion
 */
@WebServlet(name = "Deconnexion", urlPatterns = {"/Deconnexion"})
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        response.setContentType("text/html;charset=UTF-8");
	        HttpSession session = request.getSession();
	        session.invalidate(); //fermer la session
	        try (PrintWriter out = response.getWriter()) {
	            out.println("<!DOCTYPE html>");
	            out.println("<html>");
	            out.println("<head>");
	            out.println("<meta http-equiv='refresh' content='5; URL=connexion.html' />");
	            out.println("<title> Deconnexion</title>");
	            out.println("</head>");
	            out.println("<body>");
	            out.println("<h1>Vous êtes déconnecté et redirigé vers la page connexion </h1>");
	            out.println("<a href=' connexion.html'> Connexion </a>");
	            out.println("</body>");
	            out.println("</html>");
	        }
	    }


	 /**
	     * Handles the HTTP <code>GET</code> method.
	     *
	     * @param request servlet request
	     * @param response servlet response
	     * @throws ServletException if a servlet-specific error occurs
	     * @throws IOException if an I/O error occurs
	     */
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        processRequest(request, response);
	    }

	    /**
	     * Handles the HTTP <code>POST</code> method.
	     *
	     * @param request servlet request
	     * @param response servlet response
	     * @throws ServletException if a servlet-specific error occurs
	     * @throws IOException if an I/O error occurs
	     */
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        processRequest(request, response);
	    }
}