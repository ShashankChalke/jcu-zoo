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

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
		if (!Configuration.isLoaded()){
			Configuration.load();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		if (request.getParameter("action") != null){
			if (request.getParameter("action").equals("modify")){
			sb.append(showCageEditor(Cage.getCage(Integer.valueOf(request.getParameter("cageId"))),request));
			} else if (request.getParameter("action").equals("save")){
				save(request);
			} else if (request.getParameter("action").equals("delete")){
				delete(request);
			} else if (request.getParameter("action").equals("set")){
				Cage.setNumOfCages(Integer.valueOf(request.getParameter("numOfCages")));
			}
		}
		sb.append(showCageListing());
		
		out.println(sb);
		out.close();		
	}

	private void delete(HttpServletRequest request) {
		Cage.removeCage(Cage.getCage(Integer.valueOf(request.getParameter("cageId"))));
		
	}

	private void save(HttpServletRequest request) {
		int cageId = Integer.valueOf(request.getParameter("cageId"));
		String cageName = request.getParameter("cageName");
		float longitude = Float.valueOf(request.getParameter("longitude"));
		float latitude = Float.valueOf(request.getParameter("latitude"));
		String exhibitName = request.getParameter("exhibitName");
		String exhibitDesc = request.getParameter("exhibitDesc");
		String cageType = request.getParameter("cageType");
		Cage.updateCage(new Cage(cageId,cageName,latitude,longitude,cageType,false,false,exhibitName,exhibitDesc));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// if (post
		doGet(request,response);
	}


	private String showCageEditor(Cage cage,HttpServletRequest request){
		//action=modify
		if (request.getParameter("numGates") != null){
			if (request.getParameter("numGates").equals("+1")){
				cage.addGate();
		    } else if (request.getParameter("numGates").equals("-1")) {
		    	cage.removeGate();
		    }
		}
		StringBuffer sb = new StringBuffer();
		sb.append("<div>");
		
		sb.append("<h1>Modify cage</h1>"); 
		sb.append("<p>Number of gates: " + cage.getNumOfGates() + "</p>");
		sb.append("<form action='Admin' method='POST'>");
		sb.append("<input type='hidden' name='cageId' value='" + Integer.toString(cage.getCageId()) + "' />");
		sb.append("<input type='hidden' name='action' value='modify' />");
		sb.append("<input type='submit' name='numGates' value='+1' />");
		sb.append("</form>");
		sb.append("<form action='Admin' method='POST'>");
		sb.append("<input type='hidden' name='cageId' value='" + Integer.toString(cage.getCageId()) + "' />");
		sb.append("<input type='hidden' name='action' value='modify' />");
		sb.append("<input type='submit' name='numGates' value='-1' />");
		sb.append("</form>");
		sb.append("<form action='Admin' method='POST'>");
		sb.append("<input type='hidden' name='cageId' value='" + Integer.toString(cage.getCageId()) + "' />");
		sb.append("<label for='cageName'>Cage name: </label><input type='textbox' name='cageName' value='" + cage.getCageName() + "' />");
		sb.append("<label for='cageType'>Cage type: </label><input type='textbox' name='cageType' value='" + cage.getCageType() + "' />");
		sb.append("<label for='latitude'>Latitude: </label><input type='textbox' name='latitude' value='" + Float.valueOf(cage.getLatitude()) + "' />");
		sb.append("<label for='longitude'>Longitude: </label><input type='textbox' name='longitude' value='" + Float.valueOf(cage.getLongitude()) + "' />");
		sb.append("<label for='exhibitName'>Exhibit Name: </label><input type='textbox' name='exhibitName' value='" + cage.getExhibitName() + "' />");
		sb.append("<label for='exhibitDesc'>Exhibit Description: </label>" +
				"<textarea name='exhibitDesc'>" + cage.getExhibitDesc()  + "</textarea>");
		sb.append("<p>" + cage.toString() + "</p>");
		sb.append("<input type='submit' name='action' value='save' /><a href='Admin'>Go Back</a>");
		sb.append("</form>");
		sb.append("</div>");
		
		return sb.toString();
	}
	private String showCageListing(){
		StringBuffer sb = new StringBuffer();
		ArrayList<Cage> cages = Cage.getCages();
		sb.append("<div>");
		sb.append("<h1>Cage Listings</h1>");
		sb.append("<form method='POST' action='Admin'>" +
				"<label for='numOfCages'>Enter desired number of cages:</label><input type='textbox' name='numOfCages' value='"+Cage.getNumOfCages()+"' />" +
				"<input type='submit' name='action' value='set' /></form>");
		if (cages.isEmpty()){
			sb.append("<p>No cages yet available</p>");
		} else {
			sb.append("<table><tr><td>Cage Id</td><td>Cage Name</td><td>Cage Type</td><td>Latitude</td><td>Longitude</td><td>Number of gates</td><td>Exhibit name</td><td>Exhibit Description</td><td>Controls</td></tr>");
			for (Cage cage : cages){
				sb.append("<tr>");
				sb.append("<td>" + Integer.toString(cage.getCageId()) + "</td>");
				sb.append("<td>" + cage.getCageName() + "</td>");
				sb.append("<td>" + cage.getCageType() + "</td>");
				sb.append("<td>"+ cage.getLatitude() + "</td>");
				sb.append("<td>"+ cage.getLongitude() + "</td>");
				sb.append("<td>"+ cage.getNumOfGates() + "</td>");
				sb.append("<td>" + cage.getExhibitName() + "</td>");
				sb.append("<td>" + cage.getExhibitDesc() + "</td>");
				sb.append("<td><form method='POST' action='Admin'>" +
						"<input type='hidden' name='cageId' value='"+ Integer.toString(cage.getCageId()) + "' />" +
						"<input name='action' type='submit' value='modify' />" +
						"<input name='action' type='submit' value='delete' />" +
						"</form></td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			
		}
		
		sb.append("</div>");
		return sb.toString();
	}

}
