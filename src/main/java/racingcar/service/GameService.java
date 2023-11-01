package racingcar.service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import racingcar.model.Car;
import racingcar.model.Game;
import racingcar.util.Constant;

public class GameService {

    private final Validator validator;

    public GameService() {
        this.validator = new Validator();
    }

    public List<Car> validateInput(List<Car> carList) {
        validator.validate(carList);
        return carList;
    }

    public List<Car> createCars(String carNames) {
        String[] names = carNames.split(",");
        List<Car> cars = new ArrayList<>();

        for (String name : names) {
            cars.add(new Car(name.trim()));
        }
        return cars;
    }

    public void decideMovement(List<Car> carList) {
        for (Car car : carList) {
            int randomNumber = Game.generateRandomNumber();
            if (randomNumber >= Constant.NUMBER_FOR_MOVE) {
                car.move();
                car.moveCount();
            }
        }
    }

    public List<Car> winRacing(List<Car> carList) {
        if (carList.isEmpty()) {
            throw new NoSuchElementException();
        }

        int maxCount = carList.stream()
                .mapToInt(Car::moveCount)
                .max()
                .orElseThrow(NoSuchElementException::new);

        return carList.stream()
                .filter(car -> car.moveCount() == maxCount)
                .collect(Collectors.toList());
    }
}
