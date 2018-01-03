package ua.kapitonenko.controller.helpers;

public class FlashMessage {
	private String status;
	private String message;
	
	public FlashMessage(String status, String message) {
		this.status = status;
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("FlashMessage{")
				       .append("status=").append(status)
				       .append(", message=").append(message)
				       .append("}")
				       .toString();
	}
}