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
import java.time.LocalDateTime;

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


    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("add new employee, {}", employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        Long empId = (Long) request.getSession().getAttribute("employee");

        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("add employee...");
    }
}
