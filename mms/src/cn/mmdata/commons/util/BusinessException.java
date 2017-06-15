package cn.mmdata.commons.util;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7135599708702009517L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
}
