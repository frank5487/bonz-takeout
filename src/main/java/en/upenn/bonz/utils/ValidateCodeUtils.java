package en.upenn.bonz.utils;

import java.util.Random;

public class ValidateCodeUtils {

    /**
     * generate random verified code
     * @param length
     * @return
     */
    public static Integer generateValidateCode(int length) {
        Integer code = null;
        if (length == 4) {
            code = new Random().nextInt(9999);
            if (code < 1000) {
                code += 1000;
            }
        } else if (length == 6) {
            code = new Random().nextInt(999999);
            if (code < 100000) {
                code += 100000;
            }
        } else {
            throw new RuntimeException("could only generate 4 or 6 digits verified code");
        }

        return code;
    }
}
