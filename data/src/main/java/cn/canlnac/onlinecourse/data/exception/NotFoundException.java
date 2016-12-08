package cn.canlnac.onlinecourse.data.exception;

/**
 * 找不到异常.
 */
public class NotFoundException extends Exception {

  public NotFoundException() {
    super();
  }

  public NotFoundException(final String message) {
    super(message);
  }

  public NotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public NotFoundException(final Throwable cause) {
    super(cause);
  }
}
