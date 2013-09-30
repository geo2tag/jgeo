/**
 * 
 */
package ru.spb.osll.json;

/**
 * @author Mark Zaslavskiy
 *
 */
public class RequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1890753726410242635L;
	private int m_errno;
	
	/**
	 * @param message
	 */
	public RequestException(int errno) {
		super(Errno.getErrorByCode(errno));
		// TODO Auto-generated constructor stub
	}

	public int getErrno() {
		return m_errno;
	}

	public void setErrno(int errno) {
		m_errno = errno;
	}



}
