package beans;

import java.util.ArrayList;
import java.util.List;

public class Film {
	
	private long id;
	private String name;
	private String description;
	private int duration;
	private String path;
	private List<Author> authors = new ArrayList<Author>();
	private List<Actor> actors = new ArrayList<Actor>();
	private List<Category> categories = new ArrayList<Category>();

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> author) {
		this.authors = author;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actorsList) {
		this.actors = actorsList;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDuration() {
		return duration;
	}

	public long getId() {
		return id;
	}
	
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Film ID: ").append(id).append("\n");
	    sb.append("Name: ").append(name).append("\n");
	    sb.append("Description: ").append(description).append("\n");
	    sb.append("Duration: ").append(duration).append(" minutes\n");

	    sb.append("Authors: ");
	    if (authors.isEmpty()) {
	        sb.append("None");
	    } else {
	        for (Author author : authors) {
	            sb.append(author.getFirstName()).append(" ").append(author.getLastName()).append(", ");
	        }
	        sb.setLength(sb.length() - 2);
	    }
	    sb.append("\n");

	    sb.append("Actors: ");
	    if (actors.isEmpty()) {
	        sb.append("None");
	    } else {
	        for (Actor actor : actors) {
	            sb.append(actor.getFirstName()).append(" ").append(actor.getLastName()).append(", ");
	        }
	        sb.setLength(sb.length() - 2);
	    }
	    sb.append("\n");

	    sb.append("Categories: ");
	    if (categories.isEmpty()) {
	        sb.append("None");
	    } else {
	        for (Category category : categories) {
	            sb.append(category.getId()).append(" : ").append(category.getCategoryName()).append(", ");
	        }
	        sb.setLength(sb.length() - 2);
	    }

	    return sb.toString() + "\n";
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
