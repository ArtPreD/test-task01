package servlets;

import database.entity.Cooperator;
import database.entity.Department;
import database.service.CooperatorsService;
import database.service.DepartmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CoopController", urlPatterns = "/list")
public class CoopServlet extends HttpServlet {

    private CooperatorsService cooperatorsService;
    private DepartmentService departmentService;

    @Override
    public void init() throws ServletException {
        super.init();
        departmentService = new DepartmentService();
        cooperatorsService = new CooperatorsService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));

            try {
                if (action != null && action.equals("delete")){
                    cooperatorsService.deleteCoop(id);
                    response.sendRedirect("/list?id=" + Integer.parseInt(request.getParameter("depid")));
                    return;
                }
                List cooperators = cooperatorsService.findAllCoopFromDep(id);
                Department department = departmentService.findDepById(id);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String date = df.format(new Date());

                request.setAttribute("dep", department);
                request.setAttribute("coopList", cooperators);
                request.setAttribute("error", false);
                request.setAttribute("date", date);

                response.setContentType("text/html;charset=utf-8");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/coopList.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("depid"));
        try {
            List cooperators = cooperatorsService.findAllCoopFromDep(id);
            Department department = departmentService.findDepById(id);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(request.getParameter("date"));
            int salary = 0;
            try {
                salary = Integer.parseInt(request.getParameter("salary"));
            }catch (NumberFormatException ex){
                request.setAttribute("dep", department);
                request.setAttribute("coopList", cooperators);
                request.setAttribute("error", true);
                request.setAttribute("errorMessage", "Для оклада можно использовать только цифры от 0 до 9.");
                request.setAttribute("salary", request.getParameter("salary"));
                request.setAttribute("name", request.getParameter("name"));
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("date", request.getParameter("date"));
                response.setContentType("text/html;charset=utf-8");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/coopList.jsp");
                requestDispatcher.forward(request, response);
            }

            Cooperator cooperator = cooperatorsService.findByEmail(request.getParameter("email"));
            if (cooperator != null){
                request.setAttribute("dep", department);
                request.setAttribute("coopList", cooperators);
                request.setAttribute("error", true);
                request.setAttribute("errorMessage", "Пользователь с такой почтой уже зарегистрирован.");
                request.setAttribute("salary", request.getParameter("salary"));
                request.setAttribute("name", request.getParameter("name"));
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("date", request.getParameter("date"));
                response.setContentType("text/html;charset=utf-8");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/coopList.jsp");
                requestDispatcher.forward(request, response);
                return;
            }
            cooperatorsService.createCoop(request.getParameter("name"), request.getParameter("email"),
                    id, salary, date);

            request.setAttribute("dep", department);
            request.setAttribute("coopList", cooperatorsService.findAllCoopFromDep(id));
            request.setAttribute("error", false);

            response.setContentType("text/html;charset=utf-8");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/coopList.jsp");
            requestDispatcher.forward(request, response);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
