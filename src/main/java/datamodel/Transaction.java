package datamodel;

import javax.persistence.*;

@Entity
@Table(name = "Transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "amount")
	private Float amount;
	
	@Column(name = "payer_id")
	private Integer payer_id;
	
	@Column(name = "payee_id")
	private Integer payee_id;
	
	public Transaction() {}
	
	public Transaction(Float amount, Integer payer_id, Integer payee_id) {
		this.amount = amount;
		this.payer_id = payer_id;
		this.payee_id = payee_id;
	}
	
	// getters
	public Integer getID() { return id; }
	public Float getAmount() { return amount; }
	public Integer getPayeeID() { return payee_id; }
	public Integer getPayerID() { return payer_id; }
	
	// setters
	public void setID(Integer id) { this.id = id; }
	public void setAmount(Float amount) { this.amount = amount; }
	public void setPayeeID(Integer payee_id) { this.payee_id = payee_id; }
	public void setPayerID(Integer payer_id) { this.payer_id = payer_id; }
	
	@Override
	public String toString() {
	   return "Transaction { id: " + this.id + 
   						  ", amount: " + this.amount +
   						  ", payer_id: " + this.payer_id +
   						  ", payee_id: " + this.payee_id + " }";
	}
}

