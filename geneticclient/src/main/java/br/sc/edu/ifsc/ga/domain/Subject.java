package br.sc.edu.ifsc.ga.domain;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Objects;

public class Subject implements Serializable, Remote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String shortName;

	public Subject() {
	}

	public Subject(int id, String name, String shortName) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Subject subject = (Subject) o;
		return id == subject.id && name.equals(subject.name) && shortName.equals(subject.shortName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, shortName);
	}

	@Override
	public String toString() {
		return "Subject{" + "id=" + id + ", name='" + name + '\'' + ", shortName='" + shortName + '\'' + '}';
	}
}
