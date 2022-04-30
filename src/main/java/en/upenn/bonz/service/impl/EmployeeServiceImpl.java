package en.upenn.bonz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import en.upenn.bonz.entity.Employee;
import en.upenn.bonz.mapper.EmployeeMapper;
import en.upenn.bonz.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}