package com.cloud.school.domain;

public class DownloadClassSection {
	private String classid,className,sectioid,sectionname;

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSectioid() {
		return sectioid;
	}

	public void setSectioid(String sectioid) {
		this.sectioid = sectioid;
	}

	public String getSectionname() {
		return sectionname;
	}

	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}

	public DownloadClassSection(String classid, String className, String sectioid, String sectionname) {
		super();
		this.classid = classid;
		this.className = className;
		this.sectioid = sectioid;
		this.sectionname = sectionname;
	}
	public DownloadClassSection() {
		
	}

}
