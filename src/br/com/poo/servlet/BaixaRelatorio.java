package br.com.poo.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(
        name = "baixaTxt",
        urlPatterns = {"/baixaTxt"}
)
public class BaixaRelatorio extends HttpServlet  {
		public void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		String filename = "banco.txt";
		File f = new File(filename);
	
		int BUFSIZE = 1024;
		int length = 0;
		ServletOutputStream op = resp.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(filename);

		resp.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
		resp.setContentLength((int) f.length());
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

		byte[] bbuf = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(new FileInputStream(f));

		while ((in != null) && ((length = in.read(bbuf)) != -1)) {
		    op.write(bbuf, 0, length);
		}

		in.close();
		op.flush();
		op.close();
	    }
}
