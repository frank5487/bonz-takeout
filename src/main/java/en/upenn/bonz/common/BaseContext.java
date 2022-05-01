package en.upenn.bonz.common;

/**
 * encapsulate tool class based on ThreadLocal, preserving and getting the current user's id.
 * each ThreadLocal is independent
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
