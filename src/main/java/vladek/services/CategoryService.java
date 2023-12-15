package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.model.Category;
import vladek.services.repositories.CategoryRepository;
import vladek.services.interfaces.ICategoryService;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository repository;

    @Override
    public Category create(Category category) {
        repository.save(category);
        return category;
    }

    @Override
    public Category update(Category category) throws NoSuchObjectException {
        Category c = repository.findById(category.getId()).orElse(null);

        if (c == null) {
            throw new NoSuchObjectException("No such object with id " + category.getId());
        }

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

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        repository.findAll().forEach(categories::add);
        return categories;
    }
}
