package sera.sera;


import com.google.gson.JsonObject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.sql.SQLException;

import static sera.sera.LoginServlet.fetchCurrencyAPI;
import static sera.sera.LoginServlet.fetchStockAPI;

/**
 * Servlet class run when user clicks on profile page
 */

@WebServlet("/profile")
@MultipartConfig
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // either dealing with when a new currency is selected OR redirected here from other pages

        // establish connection with user session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // if it's not dealing with a new currency
        if (request.getParameter("selectCur") == null) {
            // then the user is coming here from other pages - so just load the profile page as normal
            response.sendRedirect(request.getContextPath() + "/profile.jsp");
        } else {
            // select currency
            System.out.println(request.getParameter("selectCur"));
            try {
                // update database - this method is defined in the User class
                user.setDefaultCurrency(request.getParameter("selectCur"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                // and subsequently all stock information - this method is defined in the User class
                user.updatestocks();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // fetch exchange rate for this currency
            JsonObject j = fetchCurrencyAPI(user, session);
            // fetch all stocks information in this new currency
            fetchStockAPI(user, j);

            request.setAttribute("message", "Currency change success!");

            // redirect to profile page with everything loaded with changed currency
            request.getRequestDispatcher("/profile.jsp").forward(request, response);


        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // doPost only used for update CSV

        // connect with user session
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        // csv file is fetched from http request
        if (request.getPart("file") == null) {
            System.out.println("no file");
        } else {
            String str = getFileName(request.getPart("file"));
            // check if it's a csv file
            str=str.substring(str.length()-3);
            if (str.equals("csv")){
                System.out.println("successs");
                // Fetch <input type="file" name="file">
                Part filePart = request.getPart("file");
                InputStream fileContent = filePart.getInputStream();
                // call upload csv method to update database
                try {
                    user.uploadCSV(fileContent);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                // update user stocks in session with new stocks stored in database
                try {
                    user.updatestocks();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                // fetch data from APIs again for updated stock list
                JsonObject j = fetchCurrencyAPI(user, session);
                fetchStockAPI(user, j);
                // send success message back
                request.setAttribute("message", "Portfolio update success!");
            }
            // if not a csv file then alert user invalid file type
            else{
                request.setAttribute("message", "Invalid file type, please try uploading again.");
            }
            // reload the profile page with alert
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        }
    }
    // to get the file name through content disposition (of a FilePart)
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}

