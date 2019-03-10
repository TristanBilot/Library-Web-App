package services;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mediatheque.Document;
import mediatheque.EmpruntException;
import mediatheque.Mediatheque;
import mediatheque.Utilisateur;

/**
 * Servlet implementation class Emprunt
 */
@WebServlet("/Emprunt")
public class Emprunt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Emprunt() {
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
		String numDoc = request.getParameter("numDoc1");
		
		Document doc = media.getDocument(Integer.parseInt(numDoc));
		
		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("login");
		String password = (String) session.getAttribute("password");
		Utilisateur user = media.getUser(login, password);// session

		try {
			media.emprunt(doc, user);
			session.setAttribute("message", "Votre document a été emprunté correctement.");
		} catch (EmpruntException e) {
			session.setAttribute("message", "Vous ne pouvez pas emprunter ce document.");
			session.setAttribute("fail", "1");
			e.printStackTrace();
		}
		session.setAttribute("message", "Votre document a été emprunté correctement.");
		RequestDispatcher dispatcher;
		dispatcher = getServletContext().getRequestDispatcher("/indexAbonné.jsp");
		dispatcher.forward(request, response);
	}

}
