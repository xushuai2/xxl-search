package com.xxl.search.client.es;

import java.util.Date;

/**
 * @author xushuai
 * @date 2019年1月18日
 * @note 
 */
public class Person {
	private int id;
	private String name;
    private String num;
    
    private Date date;
    
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return this.id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getName() {
		return this.name;
	}
 
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", num=" + num + "]";
	}

}
