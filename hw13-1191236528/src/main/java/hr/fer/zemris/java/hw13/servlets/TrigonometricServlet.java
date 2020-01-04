package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class implements {@link HttpServlet}. It job is to
 * create html table (more specifically, just prepare informations
 * for html table). Values shown in table are values between two sent
 * parameters, and their sinus and cosinus.  Default parameters are
 * 0 and 360, if some parameters are not sent, or if they are not integers.
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="a", urlPatterns={"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int a = 0;
		int b = 360;
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
		}catch(NumberFormatException ignorable) {
		}
		
		try {
			b = Integer.parseInt(req.getParameter("b"));
		}catch(NumberFormatException ignorable) {
		}
		
		if(a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		
		if(b > a + 720) {
			b = a + 720;
		}
		
		List<Integer> values = new ArrayList<Integer>();
		List<Double> sinus = new ArrayList<Double>();
		List<Double> cosinus = new ArrayList<Double>();
		
		for(int i = a; i <= b; i++) {
			values.add(i);
			
			double valueInRadians = Math.toRadians(i);
			double sin = Math.sin(valueInRadians);
			double cos = Math.cos(valueInRadians);
			
			if(i % 360 == 0) {
				sin = 0.0;
				cos = 1.0;
			}else if(i % 270 == 0) {
				sin = -1.0;
				cos = 0.0;
			}else if(i % 180 == 0) {
				sin = 0.0;
				cos = -1.0;
			}else if(i % 90 == 0) {
				sin = 1.0;
				cos = 0.0;
			}
			
			sinus.add(sin);
			cosinus.add(cos);
		}
		
		req.setAttribute("values", values);
		req.setAttribute("sinus", sinus);
		req.setAttribute("cosinus", cosinus);
		req.setAttribute("end", values.size());
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
		
	}
	
}
