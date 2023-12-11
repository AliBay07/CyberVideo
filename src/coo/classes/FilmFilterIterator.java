package coo.classes;
import java.util.Iterator;
import java.util.List;

import beans.Actor;
import beans.Author;
import beans.Category;
import beans.Film;

public class FilmFilterIterator implements Iterator<Film> {

	private List<Film> films;
	private String nameFilter;
	private List<Author> authorFilter;
	private List<Actor> actorFilter;
	private List<Category> categoryFilter;
	private int currentIndex;

	public FilmFilterIterator() {
		this.currentIndex = 0;
	}
	
	public List<Film> getFilms() {
		return films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public List<Author> getAuthorFilter() {
		return authorFilter;
	}

	public void setAuthorFilter(List<Author> authorFilter) {
		this.authorFilter = authorFilter;
	}

	public List<Actor> getActorFilter() {
		return actorFilter;
	}

	public void setActorFilter(List<Actor> actorFilter) {
		this.actorFilter = actorFilter;
	}

	public List<Category> getCategoryFilter() {
		return categoryFilter;
	}

	public void setCategoryFilter(List<Category> categoryFilter) {
		this.categoryFilter = categoryFilter;
	}

	public void reset() {
		this.currentIndex = 0;
	}

	@Override
	public boolean hasNext() {
		while (currentIndex < films.size()) {
			Film currentFilm = films.get(currentIndex);
			if (matchesFilter(currentFilm)) {
				return true;
			}
			currentIndex++;
		}
		return false;
	}

	@Override
	public Film next() {
		Film nextFilm = films.get(currentIndex);
		currentIndex++;
		return nextFilm;
	}

	private boolean matchesFilter(Film film) {
		
		if (nameFilter != null && !nameFilter.isEmpty() && !film.getName().trim().toLowerCase().contains(nameFilter.trim().toLowerCase())) {
			return false;
		}

		if (authorFilter != null && !authorFilter.isEmpty() && !film.getAuthors().containsAll(authorFilter)) {
			return false;
		}

		if (actorFilter != null && !actorFilter.isEmpty() && !film.getActors().containsAll(actorFilter)) {
			return false;
		}

		if (categoryFilter != null &&  !categoryFilter.isEmpty() && !film.getCategories().containsAll(categoryFilter)) {
			return false;
		}

		return true;
	}
}
