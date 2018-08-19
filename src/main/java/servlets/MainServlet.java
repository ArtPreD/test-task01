package servlets;

import database.entity.Department;
import database.service.DepartmentsService;
import database.service.DepartmentsServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MainServlet extends HttpServlet {

    private DepartmentsService departmentsService;

    @Override
    public void init() throws ServletException {
        super.init();
        departmentsService = new DepartmentsServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action != null && action.equals("delete")){
            try {
                departmentsService.deleteDepartment(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("/");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            List departments = null;
            try {
                departments = departmentsService.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.setAttribute("depList", departments);
            request.setAttribute("error", false);

            response.setContentType("text/html;charset=utf-8");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        List departments;
        request.setCharacterEncoding("UTF-8");
        try {
           Department departmentFromDB = departmentsService.findByName(request.getParameter("name"));
            if(departmentFromDB == null){
                departmentsService.createDepartment(request.getParameter("name"));
                departments = departmentsService.findAll();
                request.setAttribute("depList", departments);
                request.setAttribute("error", false);
            }else {
                departments = departmentsService.findAll();
                request.setAttribute("depList", departments);
                request.setAttribute("error", true);
                request.setAttribute("name", request.getParameter("name"));
            }
            response.setContentType("text/html;charset=utf-8");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
