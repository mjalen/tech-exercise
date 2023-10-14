

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datamodel.Person;
import datamodel.Transaction;

/**
 * Servlet implementation class StatementServlet
 */
@WebServlet("/StatementServlet")
public class StatementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatementServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.getWriter().append("<a href='TransactionServlet'>Create Transaction</a><br/>");
		response.getWriter().append("<h1>Statement</h1>");
		
		// form to which person's statement to display.
		response.getWriter().append("<p>Select a person to view their current statement.</p>");
		
		List<Person> people = util.UtilDB.queryAllPeople();
		
		response.getWriter().append("<form action='StatementServlet' method='POST'>");
		response.getWriter().append("<select name='person'>");
		for (Person p : people) {
			response.getWriter().append("<option value='" + p.getId() + "'>" + p.getName() + "</option>");
		}
		response.getWriter().append("</select> <input type='submit' value='Submit'/>");
		response.getWriter().append("</form>");
		
		// Display the statement if a person is given.
		String person_id_param = request.getParameter("person");
		
		if (person_id_param != null) {
			int person_id = Integer.parseInt(person_id_param);
			List<Transaction> transactions = util.UtilDB.queryAllTransactions();
		
			List<Transaction> filter = transactions.stream()
					.filter(t -> {
						return t.getPayeeID() == person_id || t.getPayerID() == person_id;
					})
					.collect(Collectors.toList());
			
			response.getWriter().append("<h2>Statement for " + people.get(person_id - 1).getName() + "</h2>");
		
			response.getWriter().append("<ul>");
			for (Transaction t : filter) {
				// really lazy way to get the right person.
				Person payee = people.get(t.getPayeeID() - 1); 
				Person payer = people.get(t.getPayerID() - 1);
				
				String tstr = "From: " + payer.getName() + "<br/>To: " + payee.getName() + "<br/>Transaction Amount: $" + String.format("%.2f<br/>", t.getAmount());
			
				response.getWriter().append("<li>" + tstr + "</li>");
			}
			response.getWriter().append("</ul>");
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
