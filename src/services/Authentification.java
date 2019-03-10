package services;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mediatheque.Mediatheque;
import users.Abonné;

/**
 * Servlet implementation class Authentification
 */
@WebServlet("/Authentification")
public class Authentification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @see HttpServlet#HttpServlet()
     */
    public Authentification() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        super();
        // TODO Auto-generated constructor stub
        //Class.forName("persistance.MediathequeData").newInstance();
        //Class.forName("mediatheque.PersistentMediatheque").newInstance();
        
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		String nextJSP = "/connexion.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Mediatheque m = Mediatheque.getInstance();
		String pw = request.getParameter("password");
		String login = request.getParameter("mail");
		RequestDispatcher dispatcher;
		try {
			if (persistance.Authentification.userExists(login, pw)) {
				Abonné client = (Abonné) m.getUser(login, pw);		
				
				if (client.isBibliothecaire()) 
					dispatcher = getServletContext().getRequestDispatcher("/indexBibliothécaire.jsp");
				else
					dispatcher = getServletContext().getRequestDispatcher("/indexAbonné.jsp");
				//chargerAttributs(request, login);
				
				/* SESSION */
				HttpSession session = request.getSession();
		        session.setAttribute("login", login);
		        session.setAttribute("password", pw);
		        
				dispatcher.forward(request, response);
			}
			else {
				request.setAttribute("error", "Vos identifiants sont erronés.");
				dispatcher = getServletContext().getRequestDispatcher("/connexion.jsp");
				dispatcher.forward(request, response);
			}				
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} 
	}

}
