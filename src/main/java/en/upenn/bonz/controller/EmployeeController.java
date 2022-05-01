package en.upenn.bonz.controller;

import en.upenn.bonz.common.R;
import en.upenn.bonz.entity.Employee;
import en.upenn.bonz.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * employee login
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        Employee emp = employeeService.findUser(employee);

        if (emp == null) {
            return R.error("this employee doesn't exist...");
        }

        // convert password using MD5 encryption method
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        if (!emp.getPassword().equals(password)) {
            return R.error("wrong password...");
        }

        if (emp.getStatus() == 0) {
            return R.error("this employee has been banned...");
        }

        request.getSession().setAttribute("employee", emp.getId());

        return R.success(emp);
    }

    /**
     * employee logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee"); // clear session
        return R.success("logout...");
    }


}
