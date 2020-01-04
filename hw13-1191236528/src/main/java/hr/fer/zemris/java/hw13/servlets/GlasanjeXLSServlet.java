package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This class extends {@link HttpServlet}. It's job is to
 * create xls document showing voting results from
 * voting webapp (sorted of course).
 * @author Lucija Valentić
 *
 */
@WebServlet(name="glxls", urlPatterns={"/glasanje-xls"})
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String fileNameResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		String fileNameInformations = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		Map<String, String> bandNames = GlasanjeUtil.returnMapIdAndBandName(fileNameInformations);
		Map<String, String> results = GlasanjeUtil.returnMapResults(fileNameResults);
		
		GlasanjeUtil.sortByValueAndReturnWinners(results);
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFSheet sheet = hwb.createSheet("Rezultati");
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 1000);
		
		HSSFRow rowhead = sheet.createRow(0);
		
		rowhead.createCell(0).setCellValue("Bend");
		rowhead.createCell(1).setCellValue("Broj glasova");
		
		int i = 1;
		
		for(String s : results.keySet()) {
			HSSFRow row = sheet.createRow(i);
			i++;
			row.createCell(0).setCellValue(bandNames.get(s));
			row.createCell(1).setCellValue(results.get(s));
		}
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		OutputStream out = resp.getOutputStream();
		hwb.write(out);
		
		hwb.close();
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp");
	}

}
