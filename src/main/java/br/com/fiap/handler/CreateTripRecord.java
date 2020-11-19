package br.com.fiap.handler;

import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;
import br.com.fiap.repository.TripRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CreateTripRecord implements RequestHandler<HandlerRequest, HandlerResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {

        Trip trip = null;
        try {
            trip = new ObjectMapper().readValue(request.getBody(), Trip.class);
        } catch (IOException e) {
            return HandlerResponse.builder().setStatusCode(400).setRawBody("There is a error in your trip!").build();
        }
        context.getLogger().log("Creating a new Trip record for the country " + trip.getCountry());
        final Trip tripRecorded = repository.save(trip);
        return HandlerResponse.builder().setStatusCode(201).setObjectBody(tripRecorded).build();


    }
}
