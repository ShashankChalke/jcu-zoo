package adminUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cage;

import database.Configuration;
import database.admin.AdminDAO;

/**
 * Servlet implementation class Hello
 */
@WebServlet("/Hello")
public class Hello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!Configuration.isLoaded()){
			Configuration.load();
		}
		AdminDAO adminDAO = AdminDAO.getInstanceOf();
		ArrayList<Cage> cages = adminDAO.getCages();
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("<h1>Hi</h1>");
		for (Cage c : cages){
			sb.append("<p>" + c.toString() + "</p>");
		}
		out.println(sb);
		out.close();
		
	}
	
	public static void main(String[] args){

		AdminDAO adminDAO = AdminDAO.getInstanceOf();
		ArrayList<Cage> cages = adminDAO.getCages();
		StringBuffer sb = new StringBuffer();
		sb.append("<h1>Hi</h1>");
		for (Cage c : cages){
			sb.append("<p>" + c.toString() + "</p>");
		}
		System.out.println(sb);
	}

}
