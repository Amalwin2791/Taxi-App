//package Binn.interfaces;
//
//import com.taxiapp.application.enums.TaxiTypes;
//import com.taxiapp.route.Point;
//import com.taxiapp.library.Driver;
//import com.taxiapp.library.History;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public interface CustomerService {
//    String signUp(String name, String mobileNumber,String password, String email);
//    ArrayList<Point> viewRoute();
//    Driver bookTaxi(String customerId, int pickUpPoint, int dropPoint, String pickupTime, TaxiTypes taxiType);
//    void giveFeedback(String feedback, double rating);
//    void payFare(int fare);
//    int getFare();
//    String confirmRide();
//    List<History> viewTrips(String id);
//    Driver rentTaxi(String id,int pickUpPoint, String pickUpTime, String hoursToRent,TaxiTypes taxiType);
//}
