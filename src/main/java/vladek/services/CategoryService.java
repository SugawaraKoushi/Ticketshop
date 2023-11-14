package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.model.Category;
import vladek.model.repositories.CategoryRepository;
import vladek.services.interfaces.ICategoryService;

import java.rmi.NoSuchObjectException;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository repository;

    @Override
    public Category create(String type, int sits) {
        Category category = new Category();
        category.setType(type);
        category.setSits(sits);
        repository.save(category);
        return category;
    }

    @Override
    public Category update(UUID id, String type, int sits) throws NoSuchObjectException {
        Category category = repository.findById(id).orElse(null);

        if (category == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        category.setType(type);
        category.setSits(sits);
        repository.save(category);
        return category;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Category get(UUID id) throws NoSuchObjectException {
        Category category = repository.findById(id).orElse(null);

        if (category == null) {
            throw new NoSuchObjectException("No such object with id " + id);
        }

        return category;
    }
}
