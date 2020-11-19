package br.com.fiap.handler;

import br.com.fiap.model.HandlerRequest;
import br.com.fiap.model.HandlerResponse;
import br.com.fiap.model.Trip;
import br.com.fiap.repository.TripRepository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetTripsByPeriod implements RequestHandler<HandlerRequest, HandlerResponse> {

    private final TripRepository repository = new TripRepository();

    @Override
    public HandlerResponse handleRequest(HandlerRequest request, Context context) {

        final String starts = request.getQueryStringParameters().get("starts");
        final String ends = request.getQueryStringParameters().get("ends");

        context.getLogger().log("Searching for registered trips between " + starts + " and " + ends);

        List<Trip> trips = this.repository.findByPeriod(starts, ends);

        if (trips == null || trips.isEmpty()) {
            return HandlerResponse.builder().setStatusCode(200).setObjectBody(List.of()).build();
        }

        return HandlerResponse.builder().setStatusCode(200).setObjectBody(trips).build();
    }
}
