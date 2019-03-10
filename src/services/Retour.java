package services;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mediatheque.Document;
import mediatheque.Mediatheque;

/**
 * Servlet implementation class Retour
 */
@WebServlet("/Retour")
public class Retour extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Retour() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("message", "");
		RequestDispatcher dispatcher;
		dispatcher = getServletContext().getRequestDispatcher("/indexAbonné.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Mediatheque media = Mediatheque.getInstance();
		String numDoc = request.getParameter("numDoc2");
		Document doc = media.getDocument(Integer.parseInt(numDoc));		

		media.retour(doc);
		request.setAttribute("message", "Votre document a été retourné correctement.");
		RequestDispatcher dispatcher;
		dispatcher = getServletContext().getRequestDispatcher("/indexAbonné.jsp");
		dispatcher.forward(request, response);
	}

}
