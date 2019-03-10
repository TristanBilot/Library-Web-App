package services;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mediatheque.Mediatheque;

/**
 * Servlet implementation class Ajout
 */
@WebServlet("/Ajout")
public class Ajout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("message", "");
		RequestDispatcher dispatcher;
		dispatcher = getServletContext().getRequestDispatcher("/indexBibliothécaire.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nom = request.getParameter("nom");
		String auteur = request.getParameter("auteur");
		String type = request.getParameter("type");
		int typeInt = 0;
		Mediatheque media = Mediatheque.getInstance();
		
		switch (type) {
		case "Livre":
			typeInt = 1;
			break;
		case "CD":
			typeInt = 2;
			break;
		case "DVD":
			typeInt = 3;
			break;

		default:
			break;
		}
		media.nouveauDocument(typeInt, nom, auteur);
		
		request.getSession().setAttribute("message", "Le nouveau document a bien été ajouté !");
		RequestDispatcher dispatcher;
		dispatcher = getServletContext().getRequestDispatcher("/indexBibliothécaire.jsp");
		dispatcher.forward(request, response);
	}

}
