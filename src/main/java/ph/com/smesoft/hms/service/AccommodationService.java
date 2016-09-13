package ph.com.smesoft.hms.service;
import java.util.List;
import ph.com.smesoft.hms.domain.Accommodation;

public interface AccommodationService {

	public abstract long countAllAccommodations();


	public abstract void deleteAccommodation(Accommodation accommodation);


	public abstract Accommodation findAccommodation(Long id);


	public abstract List<Accommodation> findAllAccommodations();


	public abstract List<Accommodation> findAccommodationEntries(int firstResult, int maxResults);


	public abstract void saveAccommodation(Accommodation accommodation);


	public abstract Accommodation updateAccommodation(Accommodation accommodation);

}
