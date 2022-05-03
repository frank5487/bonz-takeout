package en.upenn.bonz.controller;

import en.upenn.bonz.common.R;
import en.upenn.bonz.entity.User;
import en.upenn.bonz.service.UserService;
import en.upenn.bonz.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // get phone #
        String phone = user.getPhone();

        // if phone is not empty, succeed...
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info(code);

            session.setAttribute(phone, code);
            return R.success("send SMS successfully...");
        }

        return R.error("send SMS failed...");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

        // get phone number
        String phone = map.get("phone").toString();
        // get code number
        String code = map.get("code").toString();

        // get code from session
        String codeInSession = (String) session.getAttribute(phone);

        // verify code
        if (codeInSession != null && codeInSession.equals(code)) {
            User user = userService.getUser(phone);

            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }

            session.setAttribute("user", user.getId());

            return R.success(user);
        }

        return R.error("wrong code");
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");

        return R.success("user has logged out...");
    }
}
