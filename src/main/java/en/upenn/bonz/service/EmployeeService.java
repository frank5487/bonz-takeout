package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.Employee;

public interface EmployeeService extends IService<Employee> {
    Employee findUser(Employee employee);

    Page<Employee> showListInPage(int page, int pageSize, String name);
}
