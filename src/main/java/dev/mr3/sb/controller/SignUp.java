package dev.mr3.sb.controller;

import dev.mr3.sb.model.User;
import dev.mr3.sb.repository.UserDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;


@WebServlet("/signup")
public class SignUp extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User newUser = new User(username, email, password);

        boolean isSuccess = UserDao.registerUser(newUser);

        if (isSuccess) {
            request.setAttribute("registeredUser", username);

            RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Failed to register. Email might already exist.");

            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }
    }
}


