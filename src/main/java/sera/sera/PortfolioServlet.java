package sera.sera;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet class run when user clicks on portfolio page / a market in the portfolio page
 */


@WebServlet("/portfolio")
public class PortfolioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // connect to user session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // should have passed in a parameter from jsp side - which market did the user click on?
        String market = request.getParameter("action");
        if(market==null&&(user.getMarkets().get(0)!=null)){
            // default the first market is selected
            market = user.getMarkets().get(0);
        }
        System.out.println("market");
        request.setAttribute("currentMarket", market);

        // fetching stock code, current market price, %1D, 1DPnL. %div, 1Ydiv, divfreq, quantitty

        List<Stock> userstocks =  new ArrayList<>();
        double totalIncome = 0.0;
        // loop through all stocks stored in uesr session and add it to a list - also add their total div this year into a variable totalIncome
        for (int i=0;i<user.getStocks().size();i++){
            if (user.getStocks().get(i).getMarket().equals(market)){
                userstocks.add(user.getStocks().get(i));
                totalIncome += user.getStocks().get(i).getTotaldiv();
            }
        }

        request.setAttribute("totalIncome", String.format("%.2f", totalIncome));
        request.setAttribute("stockList", userstocks);
        System.out.println(((ArrayList<Stock>)request.getAttribute("stockList")).get(0).getStockCode());
        System.out.println(request.getAttribute("currentMarket"));
        // redirect to portfolio.jsp (loaded with the correct stocks)
        request.getRequestDispatcher("/portfolio.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}