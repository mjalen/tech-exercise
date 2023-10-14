package datamodel;

import javax.persistence.*;

@Entity
@Table(name = "Person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;
	
	public Person() {}
	
	public Person(String name) { this.name = name; }
	
	// Getters
	public Integer getId() { return id; }
	public String getName() { return name; }
	
	// Setters
	public void setId(Integer id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	
	@Override
	public String toString() {
	   return "Person { id: " + this.id + ", name: " + this.name + "}";
	}
}
