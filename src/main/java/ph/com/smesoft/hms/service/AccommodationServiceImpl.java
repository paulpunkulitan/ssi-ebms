package ph.com.smesoft.hms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.com.smesoft.hms.domain.Accommodation;
import ph.com.smesoft.hms.repository.AccommodationRepository;

@Service
@Transactional
public class AccommodationServiceImpl implements AccommodationService {

	@Autowired
    AccommodationRepository accommodationRepository;

	public long countAllAccommodations() {
        return accommodationRepository.count();
    }

	public void deleteAccommodation(Accommodation accommodation) {
        accommodationRepository.delete(accommodation);
    }

	public Accommodation findAccommodation(Long id) {
        return accommodationRepository.findOne(id);
    }

	public List<Accommodation> findAllAccommodations() {
        return accommodationRepository.findAll();
    }

	public List<Accommodation> findAccommodationEntries(int firstResult, int maxResults) {
        return accommodationRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveAccommodation(Accommodation accommodation) {
        accommodationRepository.save(accommodation);
    }

	public Accommodation updateAccommodation(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }
}
