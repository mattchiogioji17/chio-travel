package com.chio.util

import com.chio.data.entity.Trip
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.qpxExpress.QPXExpress
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer
import com.google.api.services.qpxExpress.model.*
import org.springframework.stereotype.Service

@Service
class QpxUtil {
    private static final String APPLICATION_NAME = "MyFlightApplication";

    private static final String API_KEY = "AIzaSyCYQEnveuROoXuFSoqMsavSiAz3ggIVXko";

    /** Global instance of the HTTP transport. */
    private static HttpTransport httpTransport;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    static List<TripOption> getFlightOptions(Trip trip) {

        try {
            TripOptionsRequest tripOptionsRequest = createTripsOptionsRequest(trip)

            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            TripsSearchRequest tripsSearchRequest = new TripsSearchRequest();
            tripsSearchRequest.setRequest(tripOptionsRequest);
            QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, JSON_FACTORY, null)
                    .setApplicationName(APPLICATION_NAME)
                    .setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();

            TripsSearchResponse tripsSearchResponse = qpXExpress.trips().search(tripsSearchRequest).execute();

            List<TripOption> tripResults = tripsSearchResponse.getTrips().getTripOption();

            String id;

            for(int i=0; i < tripResults.size(); i++){
                //Trip Option ID
                id= tripResults.get(i).getId();
                System.out.println("id "+id);

                //Slice
                List<SliceInfo> sliceInfo = tripResults.get(i).getSlice();
                for(int j=0; j < sliceInfo.size(); j++){
                    int duration= sliceInfo.get(j).getDuration();
                    System.out.println("duration "+duration);
                    List<SegmentInfo> segmentInfo= sliceInfo.get(j).getSegment();
                    for(int k=0; k < segmentInfo.size(); k++){
                        String bookingCode= segmentInfo.get(k).getBookingCode();
                        System.out.println("bookingCode "+bookingCode);
                        FlightInfo flightInfo=segmentInfo.get(k).getFlight();
                        String flightNum= flightInfo.getNumber();
                        System.out.println("flightNum "+flightNum);
                        String flightCarrier= flightInfo.getCarrier();
                        System.out.println("flightCarrier "+flightCarrier);
                        List<LegInfo> leg = segmentInfo.get(k).getLeg();
                        for(int l=0; l<leg.size(); l++){
                            String aircraft= leg.get(l).getAircraft();
                            System.out.println("aircraft "+aircraft);
                            String arrivalTime= leg.get(l).getArrivalTime();
                            System.out.println("arrivalTime "+arrivalTime);
                            String departTime=leg.get(l).getDepartureTime();
                            System.out.println("departTime "+departTime);
                            String dest=leg.get(l).getDestination();
                            System.out.println("Destination "+dest);
                            String destTer= leg.get(l).getDestinationTerminal();
                            System.out.println("DestTer "+destTer);
                            String origin=leg.get(l).getOrigin();
                            System.out.println("origun "+origin);
                            String originTer=leg.get(l).getOriginTerminal();
                            System.out.println("OriginTer "+originTer);
                            int durationLeg= leg.get(l).getDuration();
                            System.out.println("durationleg "+durationLeg);
                            int mil= leg.get(l).getMileage();
                            System.out.println("Milleage "+mil);
                        }
                    }
                }

                //Pricing
                List<PricingInfo> priceInfo= tripResults.get(i).getPricing();
                for(int p=0; p<priceInfo.size(); p++){
                    String price= priceInfo.get(p).getSaleTotal();
                    System.out.println("Price "+price);
                }

            }
            return tripResults
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(1);
    }

    private static TripOptionsRequest createTripsOptionsRequest(Trip trip) {
        PassengerCounts passengers = new PassengerCounts();
        passengers.setAdultCount(1);

        List<SliceInput> slices = new ArrayList<SliceInput>();

        SliceInput slice = new SliceInput();
        slice.setOrigin(trip.getOrigin())
        slice.setDestination(trip.getDestination())
        slice.setDate(trip.getDate())
        slices.add(slice);

        TripOptionsRequest request = new TripOptionsRequest();
        request.setSolutions(2);
        request.setPassengers(passengers);
        request.setSlice(slices);
        request
    }

}
