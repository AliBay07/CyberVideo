package beans;

import java.util.Objects;

public class Category {
	
	private long id;
	private String CategoryName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Category other = (Category) obj;
        return Objects.equals(this.CategoryName.trim().toLowerCase(), other.CategoryName.trim().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(CategoryName.trim().toLowerCase());
    }

    @Override
    public String toString() {
        return this.getId() + " : " + this.getCategoryName();
    }
    
}
