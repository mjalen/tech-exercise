

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.Person;
import datamodel.Transaction;

/**
 * Servlet implementation class TransactionServlet
 */
@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().append("<a href='StatementServlet'>View Statements</a><br/>");
		response.getWriter().append("<h1>Statement</h1>");
		
		// form to which person's statement to display.
		response.getWriter().append("<p>Select a person to view their current statement.</p>");
		
		List<Person> people = util.UtilDB.queryAllPeople();
		
		response.getWriter().append("<form action='TransactionServlet' method='POST'>");

		// payer select
		response.getWriter().append("Payer: <select name='payer'>");
		for (Person p : people) {
			response.getWriter().append("<option value='" + p.getId() + "'>" + p.getName() + "</option>");
		}
		response.getWriter().append("</select><br/>");
		
		// payee select
		response.getWriter().append("Payee: <select name='payee'>");
		for (Person p : people) {
			response.getWriter().append("<option value='" + p.getId() + "'>" + p.getName() + "</option>");
		}
		response.getWriter().append("</select><br/>");
		
		// amount spent
		response.getWriter().append("Transaction Amount: <input type='text' name='amount'/><br/>");

		response.getWriter().append("<input type='submit' value='Submit'/>");
		response.getWriter().append("</form>");
		
		// the following is the program that creates a transaction, if one exists in the http request.
		String payee = request.getParameter("payee");
		String payer = request.getParameter("payer");
		String amount = request.getParameter("amount");
		
		if (payee != null && payer != null && amount != null) {
			String stripped_amount = amount.replace("$", "");
			Transaction t = new Transaction(Float.parseFloat(stripped_amount), 
											Integer.parseInt(payer), 
											Integer.parseInt(payee));
			
			util.UtilDB.insertTableEntry(t);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
