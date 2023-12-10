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

	public FilmFilterIterator(List<Film> films, String nameFilter, List<Author> authorFilter,
			List<Actor> actorFilter, List<Category> categoryFilter) {
		this.films = films;
		this.nameFilter = nameFilter;
		this.authorFilter = authorFilter;
		this.actorFilter = actorFilter;
		this.categoryFilter = categoryFilter;
		this.currentIndex = 0;
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
		
		if (!nameFilter.isEmpty() && !film.getName().trim().toLowerCase().contains(nameFilter.trim().toLowerCase())) {
			return false;
		}

		if (!authorFilter.isEmpty() && !film.getAuthors().containsAll(authorFilter)) {
			return false;
		}

		if (!actorFilter.isEmpty() && !film.getActors().containsAll(actorFilter)) {
			return false;
		}

		if (!categoryFilter.isEmpty() && !film.getCategories().containsAll(categoryFilter)) {
			return false;
		}

		return true;
	}
}
