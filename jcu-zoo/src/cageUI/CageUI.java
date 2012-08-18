package cageUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.Configuration;

import sun.awt.image.OffScreenImage;

import model.Cage;
import model.Gate;

/**
 * Servlet implementation class Cage
 */
@WebServlet("/Cage")
public class CageUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CageUI() {
        super();
        if(!Configuration.isLoaded()){
        	Configuration.load();
        }
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer(	);
		if (request.getParameter("action") != null){
			if (request.getParameter("action").equals("sensors")){
				sb.append(showSensorControls(Cage.getCage(Integer.valueOf(request.getParameter("cageId")))));
			} else if(request.getParameter("action").equals("gates")){
				sb.append(showGates(request));
			} else if (request.getParameter("action").equals("update")){
				if (request.getParameter("updateType").equals("sensors")){
					// update sensors
					Cage cage = Cage.getCage(Integer.valueOf(request.getParameter("cageId")));
					System.out.println(request.getParameter("hasAnimal"));
					cage.hasAnimal(false);
					cage.hasHuman(false);
					if (request.getParameter("hasAnimal") != null){
						cage.hasAnimal(true);
					}
					if (request.getParameter("hasHuman") != null){
						cage.hasHuman(true);
					}
					Cage.updateCage(cage);
				} else if (request.getParameter("updateType").equals("gates")){
					Gate gate = Gate.getGate(Integer.valueOf(request.getParameter("gateId")));
					if (request.getParameter("gateAction") != null)
						if (request.getParameter("gateAction").equals("open")){
							gate.open();
						} else if (request.getParameter("gateAction").equals("close")){
							gate.close();
						} else if (request.getParameter("gateAction").equals("lock")){
							gate.lock();
						} else if (request.getParameter("gateAction").equals("unlock")){
							gate.unlock();
						}
					gate.update();
					//Gate gate = Gate.getGate(Integer.valueOf(request.getParameter("gateId")));
					// show gates again...
					sb.append(showGates(request));
				}
			}
		}

		sb.append(listCages());
		out.print(sb);
	}
	
	private String showGates(HttpServletRequest request) {
		Cage cage = Cage.getCage(Integer.valueOf(request.getParameter("cageId")));
		ArrayList<Gate> gates = cage.getGates();
		StringBuffer sb = new StringBuffer();
		if (gates.isEmpty()){
			sb.append("<p>No gates found!</p>");
		}
		for (Gate gate : gates){
			sb.append("<p>" + gate.toString() + "</p>");
			sb.append("<form action='Cage' method='POST'>");
			sb.append("<input type='hidden' name='gateId' value='"+ Integer.toString(gate.getGateId()) + "' />");
			sb.append("<input type='hidden' name='cageId' value='"+ Integer.toString(cage.getCageId()) + "' />");
			sb.append("<input type='hidden' name='action' value='update'");
			sb.append("<input type='hidden' name='updateType' value='gates' />");
			if (gate.isClosed()){
				sb.append("<input type='submit' name='gateAction' value='open' />");
			} else {
				sb.append("<input type='submit' name='gateAction' value='close' />");
			}
			if (gate.isLocked()){
				sb.append("<input type='submit' name='gateAction' value='unlock' />");
			} else {
				sb.append("<input type='submit' name='gateAction' value='lock' />");
			}
			sb.append("</form>");
		}
		sb.append("<p><a href='Cage'>Close</a></p>");
		
		return sb.toString();
	}

	private String listCages(){
		StringBuffer sb = new StringBuffer();
		ArrayList<Cage> cages = Cage.getCages();
		sb.append("<div>");
		sb.append("<h1>Cage Listings</h1>");
		if (cages.isEmpty()){
			sb.append("<p>No cages yet available</p>");
		} else {
			sb.append("<table><tr><td>Cage Id</td><td>Cage Name</td><td>Cage Type</td><td>Number of gates</td><td>Exhibit name</td><td>Exhibit Description</td><td>Animal Inside</td><td>Human Inside</td><td>Controls</td></tr>");
			for (Cage cage : cages){
				sb.append("<tr>");
				sb.append("<td>" + Integer.toString(cage.getCageId()) + "</td>");
				sb.append("<td>" + cage.getCageName() + "</td>");
				sb.append("<td>" + cage.getCageType() + "</td>");
				sb.append("<td>"+ cage.getNumOfGates() + "</td>");
				sb.append("<td>" + cage.getExhibitName() + "</td>");
				sb.append("<td>" + cage.getExhibitDesc() + "</td>");
				sb.append("<td>" + boolToString(cage.hasAnimal()) + "</td>");
				sb.append("<td>" + boolToString(cage.hasHuman()) + "</td>");
				sb.append("<td><form method='POST' action='Cage'>" +
						"<input type='hidden' name='cageId' value='"+ Integer.toString(cage.getCageId()) + "' />" +
						"<input name='action' type='submit' value='sensors' />" +
						"<input name='action' type='submit' value='gates' />" +
						"</form></td>");
				sb.append("</tr>");
			}
			sb.append("</table>");
			
		}
		
		sb.append("</div>");
		return sb.toString();
	}
	private String boolToString(boolean b){
		if (b){
			return "Yes";
		} else {
			return "No";
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	private String showSensorControls(Cage cage){
		StringBuffer sb = new StringBuffer();
		String humanCheck = "";
		String animalCheck = "";
		if (cage.hasHuman()){
			humanCheck = "checked";
		}
		if (cage.hasAnimal()){
			animalCheck = "checked";
		}
		sb.append("<div><form method='POST' action='Cage'>" +
				"<input type='hidden' name='cageId' value='" + Integer.toString(cage.getCageId()) + "' />" +
				"<label for='hasHuman'>Humans inside</label><input type='checkbox' name='hasHuman' " + humanCheck +  "/>" + 
				"<label for='hasAnimal'>Animal inside</label><input type='checkbox' name='hasAnimal' " + animalCheck + " />" +
				"<input type='hidden' name='updateType' value='sensors' />" +
				"<input type='submit' name='action' value='update' /></form></div>");
		return sb.toString();
	}

}
