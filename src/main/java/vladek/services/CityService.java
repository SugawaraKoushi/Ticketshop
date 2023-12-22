package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.models.City;
import vladek.services.interfaces.ICityService;
import vladek.services.repositories.CityRepository;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CityService implements ICityService {
    @Autowired
    private CityRepository repository;

    @Override
    public City create(City city) {
        repository.save(city);
        return city;
    }

    @Override
    public City update(City city) throws NoSuchObjectException {
        City c = repository.findById(city.getId()).orElse(null);

        if (c == null) {
            throw new NoSuchObjectException("No such object with id " + city.getId());
        }

        repository.save(city);
        return city;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public City get(UUID id) throws NoSuchObjectException {
        City city = repository.findById(id).orElse(null);

        if (city == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        return city;
    }

    @Override
    public List<City> getAll() {
        List<City> cities = new ArrayList<>();
        repository.findAll().forEach(cities::add);
        return cities;
    }
}
