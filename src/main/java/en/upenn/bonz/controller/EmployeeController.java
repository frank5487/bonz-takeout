package en.upenn.bonz.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import en.upenn.bonz.common.R;
import en.upenn.bonz.entity.Employee;
import en.upenn.bonz.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        log.info("add new employee, {}", employee.toString());

        // init password for employee: 123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employeeService.save(employee);

        return R.success("add employee...");
    }

    /**
     * employees' info are displayed in the form of pagination
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page: {}, pageSize: {}, name: {}", page, pageSize, name);
        Page<Employee> pageInfo = employeeService.showListInPage(page, pageSize, name);

        return R.success(pageInfo);
    }

    /**
     * update employee's info by id
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        log.info(employee.toString());

        employeeService.updateById(employee);

        return R.success("employee status updated...");
    }

    /**
     * get employee's info by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("query employee's info by id...");

        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return R.error("this employee doesn't exist...");
        }

        return R.success(employee);
    }
}
