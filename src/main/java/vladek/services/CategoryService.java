package vladek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vladek.models.Category;
import vladek.models.Vehicle;
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
    private final static String NUMBER_OF_SEATS_ERROR = "Сумма мест всех категорий для данного транспорта превышает количество мест транспорта.";
    private final static String NUMBER_OF_SEATS_IS_NEGATIVE_ERROR = "Количество мест категории должно быть больше 0";

    @Override
    public Category create(Category category) {
        if (!isNumberOfSeatsValid(category)) {
            throw new IllegalArgumentException(NUMBER_OF_SEATS_ERROR);
        }

        if (!isNumberOfSeatsGreaterThanZero(category)) {
            throw new IllegalArgumentException(NUMBER_OF_SEATS_IS_NEGATIVE_ERROR);
        }

        repository.save(category);
        return category;
    }

    @Override
    public Category update(Category category) throws NoSuchObjectException {
        Category c = repository.findById(category.getId()).orElse(null);

        if (c == null) {
            throw new NoSuchObjectException("No such object with id " + category.getId());
        }

        if (!isNumberOfSeatsValid(category)) {
            throw new IllegalArgumentException(NUMBER_OF_SEATS_ERROR);
        }

        if (!isNumberOfSeatsGreaterThanZero(category)) {
            throw new IllegalArgumentException(NUMBER_OF_SEATS_IS_NEGATIVE_ERROR);
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

    private List<Category> getCategoriesWhereVehicle(Vehicle vehicle) {
        List<Category> categories = getAll();
        categories.removeIf(category -> !category.getVehicle().equals(vehicle));
        return categories;
    }

    private int getSumOfSeatsInCategories(List<Category> categories) {
        return categories.stream().mapToInt(Category::getSits).sum();
    }

    private boolean isNumberOfSeatsValid(Category category) {
        List<Category> vehicleCategories = getCategoriesWhereVehicle(category.getVehicle());
        int sumOfSeats = getSumOfSeatsInCategories(vehicleCategories);
        int vehicleSeats = category.getVehicle().getSits();
        return (sumOfSeats + category.getSits()) < vehicleSeats;
    }

    private boolean isNumberOfSeatsGreaterThanZero(Category category) {
        return category.getSits() > 0;
    }
}
