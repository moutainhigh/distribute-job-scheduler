package com.ylpu.thales.scheduler.executor.sql;

import java.util.Map;

public class SQLConfig {
	
	private Map<String,Object> dataSource;
	private String operator;
	private String sql;
	private Map<String,Object> parameters;
	
	public Map<String, Object> getDataSource() {
		return dataSource;
	}
	public void setDataSource(Map<String, Object> dataSource) {
		this.dataSource = dataSource;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
}
