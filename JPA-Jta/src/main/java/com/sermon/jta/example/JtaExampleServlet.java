package com.sermon.jta.example;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class JtaExampleServlet
 */
@WebServlet("/JtaExampleServlet")
public class JtaExampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JtaExampleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InitialContext cxt;
		try {
			cxt = new InitialContext();
			if ( cxt != null ) {
				DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/hpracticeDB" );
			
				if ( ds == null ) {
				   System.out.println("Data source not found!");
				}else {
					System.out.println("Data source found !!!!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
