package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.models.Airport;
import vladek.services.interfaces.IAirportService;
import vladek.services.repositories.AirportRepository;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AirportService implements IAirportService {
    @Autowired
    private AirportRepository repository;

    @Override
    public Airport create(Airport airport) {
        repository.save(airport);
        return airport;
    }

    @Override
    public Airport update(Airport airport) throws NoSuchObjectException {
        Airport a = repository.findById(airport.getId()).orElse(null);

        if (a == null) {
            throw new NoSuchObjectException("No such object with id " + airport.getId());
        }

        repository.save(airport);
        return airport;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Airport get(UUID id) throws NoSuchObjectException {
        Airport airport = repository.findById(id).orElse(null);

        if (airport == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        return airport;
    }

    @Override
    public List<Airport> getAll() {
        List<Airport> airports = new ArrayList<>();
        repository.findAll().forEach(airports::add);
        return airports;
    }
}
