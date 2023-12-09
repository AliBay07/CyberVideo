package beans;

import java.util.Objects;

public class Actor {
	
	private Long id;
	private String first_name;
	private String last_name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Actor other = (Actor) obj;
        return Objects.equals(this.first_name.trim().toLowerCase(), other.first_name.trim().toLowerCase())
                && Objects.equals(this.last_name.trim().toLowerCase(), other.last_name.trim().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name.trim().toLowerCase(), last_name.trim().toLowerCase());
    }
}
