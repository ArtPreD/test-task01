package servlets;

import database.entity.Cooperator;
import database.entity.Department;
import database.service.CooperatorsService;
import database.service.DepartmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditServlet extends HttpServlet{

    private CooperatorsService cooperatorsService;
    private DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.cooperatorsService = new CooperatorsService();
        this.departmentService = new DepartmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String q = request.getParameter("q");
        int id = Integer.parseInt(request.getParameter("id"));
        String depId = request.getParameter("depid");

        if(q.equals("dep")){
            try {
                request.setAttribute("dep", departmentService.findDepById(id));
                request.setAttribute("isDep", true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(q.equals("coop")){
            try {
                Cooperator cooperator = cooperatorsService.findById(id);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(cooperator.getDate());
                request.setAttribute("depId", depId);
                request.setAttribute("coop", cooperator);
                request.setAttribute("isCoop", true);
                request.setAttribute("date", date);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        response.setContentType("text/html;charset=utf-8");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/edit.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String q = request.getParameter("q");
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String salary = request.getParameter("salary");
        String depId = request.getParameter("depid");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if(q.equals("dep")){
            try {
                Department department = departmentService.findByName(name);
                if (department == null) {
                    departmentService.updateDepartment(new Department(id, name));
                    response.sendRedirect("/");
                    return;
                }else {
                    request.setAttribute("error", true);
                    request.setAttribute("errorMessage", "Департамент с таким именем уже существует!");
                    request.setAttribute("dep", departmentService.findDepById(id));
                    request.setAttribute("isDep", true);
                    request.setAttribute("name", name);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(q.equals("coop")){
            try {
                Cooperator coopById = cooperatorsService.findById(id);
                Cooperator cooperator = cooperatorsService.findByEmail(email);
                Date date = format.parse(request.getParameter("date"));
                int parseSalary = 0;
                try{
                    parseSalary = Integer.parseInt(salary);
                }catch (NumberFormatException e){
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String d = df.format(coopById.getDate());
                    request.setAttribute("error", true);
                    request.setAttribute("depId", depId);
                    request.setAttribute("errorMessage", "Для оклада можно использовать только цифры от 0 до 9.");
                    request.setAttribute("salary", request.getParameter("salary"));
                    request.setAttribute("name", request.getParameter("name"));
                    request.setAttribute("email", request.getParameter("email"));
                    request.setAttribute("isCoop", true);
                    request.setAttribute("date", d);

                    response.setContentType("text/html;charset=utf-8");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/edit.jsp");
                    requestDispatcher.forward(request, response);
                }

                if(cooperator == null){
                    cooperatorsService.updateCooperator(new Cooperator(id, name, email, parseSalary, date));
                    response.sendRedirect("/list?id=" + request.getParameter("depid"));
                    return;
                }else if (coopById.getEmail().equals(email)){
                    cooperatorsService.updateCooperator(new Cooperator(id, name, email, parseSalary, date));
                    response.sendRedirect("/list?id=" + request.getParameter("depid"));
                    return;
                }else {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String d = df.format(coopById.getDate());
                    request.setAttribute("error", true);
                    request.setAttribute("depId", depId);
                    request.setAttribute("errorMessage", "Такая почта уже зарегистрирована!");
                    request.setAttribute("salary", request.getParameter("salary"));
                    request.setAttribute("name", request.getParameter("name"));
                    request.setAttribute("email", request.getParameter("email"));
                    request.setAttribute("isCoop", true);
                    request.setAttribute("date", d);
                }
            }catch (ParseException | SQLException e){
                e.printStackTrace();
            }
        }

        response.setContentType("text/html;charset=utf-8");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/edit.jsp");
        requestDispatcher.forward(request, response);
    }
}
