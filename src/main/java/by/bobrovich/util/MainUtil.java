package by.bobrovich.util;

import by.bobrovich.model.Car;

import java.util.function.Predicate;

public class MainUtil {
    private static final Predicate<Car> isJaguar = (c) -> "Jaguar".equals(c.getCarMake());
    private static final Predicate<Car> isWhite = (c) -> "White".equals(c.getCarMake());
    private static final Predicate<Car> isMassLessThen1500= (c) -> c.getMass() < 1500;
    private static final Predicate<Car> isBMW = (c) -> "BMW".equals(c.getCarMake());
    private static final Predicate<Car> isLexus = (c) -> "Lexus".equals(c.getCarMake());
    private static final Predicate<Car> isChrysler = (c) -> "Chrysler".equals(c.getCarMake());
    private static final Predicate<Car> isToyota = (c) -> "Toyota".equals(c.getCarMake());
    private static final Predicate<Car> isGMC = (c) -> "GMC".equals(c.getCarMake());
    private static final Predicate<Car> isDodge = (c) -> "Dodge".equals(c.getCarMake());
    private static final Predicate<Car> isRed = (c) -> "Red".equals(c.getCarMake());
    private static final Predicate<Car> isMassMoreThen4000 = (c) -> c.getMass() > 4000;
    private static final Predicate<Car> isCivic = (c) -> "Civic".equals(c.getCarMake());
    private static final Predicate<Car> isCherokee = (c) -> "Cherokee".equals(c.getCarMake());
    private static final Predicate<Car> isReleaseYearAfter1982 = (c) -> c.getReleaseYear() < 1982;
    private static final Predicate<Car> isNotYellow = (c) -> !("Yellow".equals(c.getColor()));
    private static final Predicate<Car> isGreen = (c) -> "Green".equals(c.getCarMake());
    private static final Predicate<Car> isBlue = (c) -> "Blue".equals(c.getCarMake());
    private static final Predicate<Car> isPriceMoreThen40000 = (c) -> c.getPrice() > 40000;
    private static final Predicate<Car> isVinContains59 = (c) -> c.getVin().contains("59");
    private static final Predicate<Car> isVinNonNull = (c) -> c.getVin() != null;


    private MainUtil(){}

    public static Predicate<Car> getmongoliaCarPredicate() {
        return isVinNonNull.and(isVinContains59);
    }

    public static Predicate<Car> getRussianCarPredicate() {
        return isNotYellow.or(isRed).or(isGreen).or(isBlue).or(isPriceMoreThen40000);
    }
    public static Predicate<Car> getKurgustanCarPredicate() {
        return isCivic.or(isCherokee).or(isReleaseYearAfter1982);
    }

    public static Predicate<Car> getTurkmenistanCarPredicate() {
        return isJaguar.or(isWhite);
    }
    public static Predicate<Car> getUzbekistanCarPredicate() {
        return isMassLessThen1500.and(isBMW.or(isLexus).or(isChrysler).or(isToyota));
    }
    public static Predicate<Car> getKazahstanCarPredicate() {
        return isGMC.or(isDodge).or(isRed.and(isMassMoreThen4000));
    }
}
