package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class extends {@link HttpServlet}. It's job
 * is to create xls document using sent parameters - a, b, n. If
 * parameters are not sent, or if they are not integers, appropriate
 * message of error is written to the user in browser. Otherwise, xls
 * document that is created has n number of pages, on each page, 
 * i-page, in first column are written values from a to b, and
 * in second column are i-powers of those values
 * 
 * @author Lucija Valentić
 *
 */
@WebServlet(name="p", urlPatterns = {"/powers"})
public class PowersServlet extends HttpServlet {

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
		int b = 0;
		int n = 0;
		
		req.setAttribute("message", "Sent parameter is invalid");
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
		}catch(NumberFormatException ex) {
		
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
			return;
		}
		
		try {
			b = Integer.parseInt(req.getParameter("b"));
		}catch(NumberFormatException ex) {
			
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
			return;
		}
		
		try {
			n = Integer.parseInt(req.getParameter("n"));
		}catch(NumberFormatException ex) {
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
			return;
		}
		
		if( (a < -100 || a > 100) || (b < -100 || b > 100) || (n < 1 || n > 5)) {
			req.getRequestDispatcher("/error.jsp").forward(req, resp);
			return;
		}
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		for(int i = 0; i < n; i++) {
			
			HSSFSheet sheet = hwb.createSheet("sheet " + i);
			
			HSSFRow rowhead = sheet.createRow(0);
			rowhead.createCell(0).setCellValue("Values");
			rowhead.createCell(1).setCellValue("Powers");
			
			for(int j = a, k = 1; j <= b; j++, k++) {
				HSSFRow row = sheet.createRow(k);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		OutputStream out = resp.getOutputStream();
		hwb.write(out);
		
		hwb.close();
		
		req.getRequestDispatcher("/index.jsp");
	}
}
