package com.case1.case1.controller;

import com.case1.case1.dao.MessageDAO;
import com.case1.case1.dao.UserDAO;
import com.case1.case1.model.Message;
import com.case1.case1.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login");
            return;
        }

        UserDAO userDAO = new UserDAO();
        MessageDAO messageDAO = new MessageDAO();

        if(request.getParameter("delete") != null) {
            try {
                messageDAO.deleteAllMessage((int) session.getAttribute("user_id"), Integer.parseInt(request.getParameter("delete")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            users = userDAO.getUsersForChat((int) session.getAttribute("user_id"));
            messages = messageDAO.getAllMessage((int) session.getAttribute("user_id"));
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("messages", messages);
        request.setAttribute("newUsers", users);
        request.getRequestDispatcher("pages/message.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
