package br.com.silvaaraujo.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "", 
	propOrder = {"tag"}
)
@XmlRootElement(name = "tags")
public class Tags {
	
	@XmlElement
	public List<String> tag;

	public Tags() {
		super();
		this.tag = new ArrayList<>();
	}

	public List<String> getTag() {
		return tag;
	}

	public void setTag(List<String> tag) {
		this.tag = tag;
	}

}