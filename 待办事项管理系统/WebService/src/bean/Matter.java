package bean;

import java.io.Serializable;
import java.util.Date;

import javax.jws.WebService;
/**
 * 待办事项
 * @author shijiahui
 * @date 2019-1-4
 */
@WebService
public class Matter implements Serializable{
	private long id;
	private String description;
	private Date start;
	private Date end;
	

	/**
	 * 
	 * @param id
	 * @param description
	 * @param start
	 * @param end
	 */
	public Matter(long id, String description, Date start, Date end) {
		super();
		this.id = id;
		this.description = description;
		this.start = start;
		this.end = end;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getdescription() {
		return description;
	}

	public void setdescription(String label) {
		this.description = label;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	
}
