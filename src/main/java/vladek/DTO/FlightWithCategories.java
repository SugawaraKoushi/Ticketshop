package vladek.DTO;

import lombok.Getter;
import lombok.Setter;
import vladek.models.Category;
import vladek.models.Flight;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FlightWithCategories {
    private UUID flightId;
    private List<Category> categories;
}
