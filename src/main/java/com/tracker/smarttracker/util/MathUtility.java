package com.tracker.smarttracker.util;

import ch.obermuhlner.math.big.BigDecimalMath;
import ch.obermuhlner.math.big.DefaultBigDecimalMath;

import java.math.BigDecimal;

public class MathUtility {

    private static final BigDecimal RADIUS_OF_EARTH_IN_KILOMETERS = BigDecimal.valueOf(6371);
    private static final BigDecimal TWO = BigDecimal.valueOf(2);
    private static final BigDecimal KILOMETERS_TO_METERS_CONSTANT = BigDecimal.valueOf(1000);

    public static BigDecimal getDistance(BigDecimal startLatitude, BigDecimal finishLatitude, BigDecimal startLongitude,
                                      BigDecimal finishLongitude) {

        var startLongitudeRes = BigDecimalMath.toRadians(startLongitude, DefaultBigDecimalMath.currentMathContext());
        var finishLongitudeRes = BigDecimalMath.toRadians(finishLongitude, DefaultBigDecimalMath.currentMathContext());
        var startLatitudeRes = BigDecimalMath.toRadians(startLatitude, DefaultBigDecimalMath.currentMathContext());
        var finishLatitudeRes = BigDecimalMath.toRadians(finishLatitude, DefaultBigDecimalMath.currentMathContext());


        var resLongitudeDistance = finishLongitudeRes.subtract(startLongitudeRes);
        var resLatitudeDistance = finishLatitudeRes.subtract(startLatitudeRes);

        var dividedDistanceLongitude = resLatitudeDistance.divide(TWO, DefaultBigDecimalMath.currentMathContext());
        var sinOfDivDistLongitude = BigDecimalMath.sin(dividedDistanceLongitude, DefaultBigDecimalMath.currentMathContext());
        var powFromDividedDistanceLongitudeSin = BigDecimalMath.pow(sinOfDivDistLongitude,
                2L, DefaultBigDecimalMath.currentMathContext());
        var cosOfMultiplicationOfStartAndFinishLatitude = BigDecimalMath.cos(startLatitudeRes,
                        DefaultBigDecimalMath.currentMathContext())
                .multiply(BigDecimalMath.cos(finishLatitudeRes, DefaultBigDecimalMath.currentMathContext()));
        var sinOfDistLongitude = BigDecimalMath.sin(
                resLongitudeDistance.divide(TWO, DefaultBigDecimalMath.currentMathContext()),
                DefaultBigDecimalMath.currentMathContext());
        var powFromSinOfLongitudeSin = BigDecimalMath.pow(sinOfDistLongitude, 2L, DefaultBigDecimalMath.currentMathContext());

        var result = TWO.multiply(BigDecimalMath.asin(
                BigDecimalMath.sqrt(powFromDividedDistanceLongitudeSin.add(
                                cosOfMultiplicationOfStartAndFinishLatitude.multiply(powFromSinOfLongitudeSin)),
                        DefaultBigDecimalMath.currentMathContext()),
                DefaultBigDecimalMath.currentMathContext()));

        return (result.multiply(RADIUS_OF_EARTH_IN_KILOMETERS).multiply(KILOMETERS_TO_METERS_CONSTANT));
    }
}
