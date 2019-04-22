package vnu.uet.boatsafe.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response<T>{

	@SerializedName("Code")
	@Expose
	private int code;

	@SerializedName("Result")
	@Expose
	private T data;

	@SerializedName("Message")
	@Expose
	private String message;

	@SerializedName("Version")
	@Expose
	private String version;

	@SerializedName("TotalPages")
	@Expose
	private int totalPages;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isSuccess(){
		return code == 200;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}