package ie.wenderson.ozorio.app.demo.common;

public class BaseEntity<T> {
	
	
	private boolean isSuccess;
	
	private String message;
	
	private T entity;
	
	public BaseEntity() {
		
	}
	public BaseEntity(boolean isSuccess, String message) {
		super();
		this.isSuccess = isSuccess;
		this.message = message;
	}


	public boolean isSuccess() {
		return isSuccess;
	}


	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	public T getEntity() {
		return entity;
	}
	public void setEntity(T entity) {
		this.entity = entity;
	}

}
