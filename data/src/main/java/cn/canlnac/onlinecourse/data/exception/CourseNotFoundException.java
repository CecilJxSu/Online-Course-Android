package cn.canlnac.onlinecourse.data.exception;

/**
 * 课程找不到异常.
 */
public class CourseNotFoundException extends Exception {

  public CourseNotFoundException() {
    super();
  }

  public CourseNotFoundException(final String message) {
    super(message);
  }

  public CourseNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CourseNotFoundException(final Throwable cause) {
    super(cause);
  }
}
