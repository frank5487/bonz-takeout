package en.upenn.bonz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import en.upenn.bonz.entity.Employee;

public interface EmployeeService extends IService<Employee> {
    Employee findUser(Employee employee);
}
