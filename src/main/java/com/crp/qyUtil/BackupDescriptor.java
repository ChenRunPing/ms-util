package com.crp.qyUtil;
public class BackupDescriptor
{
	private String id   = "";  //备份id既备份目录名
	private String name = "";  //备份名称
	private String user = "";  //备份用户
	private String date = "";  //备份时间
	private String desc = "";  //备份说明
	private String version=""; //软件版本号
    
    public BackupDescriptor() {
    }
    
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
   
