package br.com.fiap.repository;

import br.com.fiap.dao.DynamoDBManager;
import br.com.fiap.model.Trip;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;
import java.util.Map;

public class TripRepository {

    private static final DynamoDBMapper mapper = DynamoDBManager.mapper();

    public Trip save(final Trip trip) {
        mapper.save(trip);
        return trip;
    }

    public List<Trip> findByPeriod(final String starts, final String ends) {

        final Map<String, AttributeValue> parameters = Map.of(
                ":val1", new AttributeValue().withS(starts),
                ":val2", new AttributeValue().withS(ends)
        );

        final DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
                .withFilterExpression("dateTrip between :val1 and :val2")
                .withExpressionAttributeValues(parameters);

        final List<Trip> trips = mapper.scan(Trip.class, queryExpression);

        return trips;
    }

    public List<Trip> findByCountry(final String country) {

        final Map<String, AttributeValue> parameters = Map.of(
                ":val1", new AttributeValue().withS(country)
        );

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withConsistentRead(false)
                .withKeyConditionExpression("country = :val1").withExpressionAttributeValues(parameters);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }

    public List<Trip> findByCity(final String country, final String city) {

        final Map<String, AttributeValue> parameters = Map.of(
                ":val1", new AttributeValue().withS(country),
                ":val2", new AttributeValue().withS(city)
        );

        final DynamoDBQueryExpression<Trip> queryExpression = new DynamoDBQueryExpression<Trip>()
                .withIndexName("cityIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("country = :val1 and city=:val2").withExpressionAttributeValues(parameters);

        final List<Trip> trips = mapper.query(Trip.class, queryExpression);

        return trips;
    }


}

