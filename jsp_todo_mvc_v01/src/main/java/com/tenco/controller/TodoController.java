package com.tenco.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.tenco.model.TodoDAO;
import com.tenco.model.TodoDAOImpl;
import com.tenco.model.TodoDTO;
import com.tenco.model.UserDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/*")
public class TodoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TodoDAO todoDAO;

    public TodoController() {
        todoDAO = new TodoDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession();
        UserDTO principal = (UserDTO) session.getAttribute("principal");
        if (principal == null) {
            response.sendRedirect("/mvc/user/signIn?message=invalid");
            return;
        }
        switch (action) {
            case "/todoForm":
                todoFormpage(request, response);
                break;
            case "/list":
                todoListPage(request, response, principal.getId());
                break;
            case "/detail":
                todoDetailPage(request, response, principal.getId());
                break;
            case "/todoUpdateForm":
                todoUpdateFormPage(request, response, principal.getId());
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserDTO principal = (UserDTO) session.getAttribute("principal");
        if (principal == null) {
            response.sendRedirect(request.getContextPath() + "/user/signIn?message=invalid");
            return;
        }
        String action = request.getPathInfo();
        switch (action) {
            case "/add":
                addTodo(request, response, principal.getId());
                break;
            case "/update":
                updateTodo(request, response, principal.getId());
                break;
            case "/delete":
                deleteTodo(request, response, principal.getId());
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void todoFormpage(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/todoForm.jsp").forward(request, response);
    }

    private void todoUpdateFormPage(HttpServletRequest request, HttpServletResponse response, int principalId)
            throws IOException, ServletException {
        int todoId = Integer.parseInt(request.getParameter("id"));
        TodoDTO todo = todoDAO.getTodoById(todoId);
        if (todo.getUserId() == principalId) {
            request.setAttribute("todo", todo);
            request.getRequestDispatcher("/WEB-INF/views/todoUpdateForm.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/todo/list?error=unauthorized");
        }
    }

    private void todoListPage(HttpServletRequest request, HttpServletResponse response, int principalId)
            throws IOException, ServletException {
        List<TodoDTO> list = todoDAO.getTodosByUserId(principalId);
        request.setAttribute("list", list);
        request.getRequestDispatcher("/WEB-INF/views/todoList.jsp").forward(request, response);
    }

    private void todoDetailPage(HttpServletRequest request, HttpServletResponse response, int principalId)
            throws IOException, ServletException {
        int todoId = Integer.parseInt(request.getParameter("id"));
        TodoDTO dto = todoDAO.getTodoById(todoId);
        if (dto.getUserId() == principalId) {
            request.setAttribute("todo", dto);
            request.getRequestDispatcher("/WEB-INF/views/todoDetail.jsp").forward(request, response);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script> alert('권한이 없습니다'); history.back() </script>");
        }
    }

    private void addTodo(HttpServletRequest request, HttpServletResponse response, int principalId) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDate = request.getParameter("dueDate");
        boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));
        TodoDTO dto = TodoDTO.builder().userId(principalId).title(title).description(description).dueDate(dueDate)
                .completed(String.valueOf(completed)).build();
        todoDAO.addTodo(dto, principalId);

        response.sendRedirect(request.getContextPath() + "/todo/list");
    }

    private void deleteTodo(HttpServletRequest request, HttpServletResponse response, int principalId)
            throws IOException {
        try {
            int todoId = Integer.parseInt(request.getParameter("id"));
            todoDAO.deleteTodo(todoId, principalId);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/todo/list?message=invlid");
        }
        response.sendRedirect(request.getContextPath() + "/todo/list");
    }

    private void updateTodo(HttpServletRequest request, HttpServletResponse response, int principalId)
            throws IOException {

        try {
            int todoId = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String dueDate = request.getParameter("dueDate");
            boolean completed = "on".equalsIgnoreCase(request.getParameter("completed"));
            TodoDTO dto = TodoDTO.builder().id(todoId).userId(principalId).title(title).description(description)
                    .dueDate(dueDate).completed(String.valueOf(completed)).build();

            todoDAO.updateTodo(dto, principalId);
        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script> alert('잘못된 요청입니다.'); history.back() </script>");
        }

        response.sendRedirect(request.getContextPath() + "/todo/list");
    }
}
