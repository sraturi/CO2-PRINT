package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.Model.VehicleManager;
import alex.carbon_tracker.R;

/*
* Main Activity class page which allows the
* user to view either their carbon footprint
* or add a new journey to the system
* */
public class JourneyListActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private VehicleManager vehicleManager = carbonTrackerModel.getVehicleManager();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);

        journeyListView();
        setupAddJourneyButton();
    }

    private void setupAddJourneyButton() {

        final DateFormat dateFormat = new SimpleDateFormat(getString(R.string.DateFormat));
        final Date date = new Date();

        Button btn = (Button) findViewById(R.id.AddJourneyButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("Date", dateFormat.format(date));
//                journeyManager.setCurrentDate(dateFormat.format(date));
                Intent intent = SelectDateActivity.makeIntent(JourneyListActivity.this);
                startActivity(intent);
            }
        });
    }




    private void journeyListView() {
        String[] journeyList = carbonTrackerModel.getJourneyManager().getJourneyDescriptions();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.jouney_list, journeyList);
        ListView list = (ListView) findViewById(R.id.journeyListView);
        list.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, R.string.Delete);
        menu.add(0, v.getId(), 0, R.string.Edit);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(R.string.Delete)) {
            // delete the entry
            return true;
        } else if (item.getTitle().equals(R.string.Edit)) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        journeyListView();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, JourneyListActivity.class);
    }
}