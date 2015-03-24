package com.lnho.aaplan.commons.web;


/**
 * 下拉选择框
 * 
 * @Company : cyou
 * @author yangz
 * @date 2012-10-12 下午04:33:22
 */
public class SelectOption {
	/**
	 * 下拉的值
	 */
	private String id;
	/**
	 * 下拉显示名称
	 */
	private String name;
	

	
	public static SelectOption build(String id , String name) {
		SelectOption op = new SelectOption();
		op.id = id;
		op.name = name;
		return op;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SelectOption(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SelectOption() {
		super();
	}

}
