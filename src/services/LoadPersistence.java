package services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(urlPatterns = "/LoadPersistence", loadOnStartup = 1)
public class LoadPersistence extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Class.forName("persistance.MediathequeData");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}