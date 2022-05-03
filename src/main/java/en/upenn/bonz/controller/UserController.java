package en.upenn.bonz.controller;

import en.upenn.bonz.common.R;
import en.upenn.bonz.entity.User;
import en.upenn.bonz.service.UserService;
import en.upenn.bonz.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * send verified code
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // get phone #
        String phone = user.getPhone();

        // if phone is not empty, succeed...
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info(code);

            // store code in session
            //session.setAttribute(phone, code);

            // store code in Redis, and set expiration up to 5 mins
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("send SMS successfully...");
        }

        return R.error("send SMS failed...");
    }

    /**
     * user login
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

        // get phone number
        String phone = map.get("phone").toString();
        // get code number
        String code = map.get("code").toString();

        // get code from session
        //String codeInSession = (String) session.getAttribute(phone);

        // get code from redis
        String codeInRedis = (String) redisTemplate.opsForValue().get(phone);

        // compare two verified code
        //if (codeInSession != null && codeInSession.equals(code)) {
        if (codeInRedis != null && codeInRedis.equals(code)) {
            User user = userService.getUser(phone);

            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }

            session.setAttribute("user", user.getId());

            // if user login successfully, we can delete code in Redis
            redisTemplate.delete(phone);

            return R.success(user);
        }

        return R.error("wrong code");
    }

    /**
     * user log out
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");

        return R.success("user has logged out...");
    }
}
