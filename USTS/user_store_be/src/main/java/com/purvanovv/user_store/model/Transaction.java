package com.purvanovv.user_store.model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Transaction {
	private Integer id;
	
	private Integer userId;
	
	private boolean status;
	
	private Date cratedDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getCratedDate() {
		return cratedDate;
	}

	public void setCratedDate(Date cratedDate) {
		this.cratedDate = cratedDate;
	}
	
}
