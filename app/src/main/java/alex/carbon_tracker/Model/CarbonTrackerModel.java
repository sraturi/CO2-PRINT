package alex.carbon_tracker.Model;

/**
 * Created by Sachin on 2017-03-01.
 * <p>
 * Carbon Tracker Model class which acts as a facade class for the
 * rest of the project. This class connects together JourneyManager,
 * RouteManager, and VehicleManager.
 */
public class CarbonTrackerModel {
    private static CarbonTrackerModel ourInstance = new CarbonTrackerModel();

    private JourneyManager journeyManager;
    private RouteManager routeManager;
    private VehicleManager vehicleManager;

    public static CarbonTrackerModel getInstance() {
        return ourInstance;
    }

    private CarbonTrackerModel() {
        journeyManager = new JourneyManager();
        routeManager = new RouteManager();
        vehicleManager = new VehicleManager();
    }
}
