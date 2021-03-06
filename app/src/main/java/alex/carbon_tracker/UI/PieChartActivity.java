package alex.carbon_tracker.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alex.carbon_tracker.Model.CarbonTrackerModel;
import alex.carbon_tracker.Model.Journey;
import alex.carbon_tracker.Model.JourneyManager;
import alex.carbon_tracker.R;

/*
* Pie Chart Activity which displays the pie
* charts of the carbon footprint created by
* the user
* */
public class PieChartActivity extends AppCompatActivity {

    private CarbonTrackerModel carbonTrackerModel = CarbonTrackerModel.getInstance();
    private JourneyManager journeyManager = carbonTrackerModel.getJourneyManager();

    public static final String CHANGE_TO_TABLE = "Change to table";
    public static final String DISPLAY_DATA_DATE = "Pass data day from pie chart";

    private boolean handleDay = false;

    private Intent i = this.getIntent();

    private Date date;

    private List<String> journeyNumbers = new ArrayList<>();
    private List<Double> journeyCO2Emissions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        getExtrasFromIntent(getIntent());

//        setJourneyData();
        setupPieChart();
        setupChangeButton();
    }

    private void getExtrasFromIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        if (intent.hasExtra(DisplayCarbonFootPrintActivity.CHANGE_TO_GRAPHS)) {
            date = (Date) extras.get(DisplayCarbonFootPrintActivity.PASS_DATE_DATA);
            handleDay = true;
        }

    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();

        if (handleDay) {
            setupPieChartForDay();
        }
        for (int i = 0; i < journeyCO2Emissions.size(); i++) {
            PieEntry pieEntry = createNewPieEntry(journeyCO2Emissions.get(i).floatValue(), journeyNumbers.get(i));

            pieEntries.add(pieEntry);
            Log.i("PieChartAct", "Value " + journeyCO2Emissions.get(i).floatValue());
            Log.i("PieChartAct", "No. " + journeyNumbers.get(i));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextSize(13);

        PieData data = new PieData(dataSet);
        PieChart chart = (PieChart) findViewById(R.id.pieChart);

        chart.setData(data);
        chart.setCenterText("CO2 Emissions in kilograms");
        chart.animateY(1000);
        chart.setEntryLabelTextSize(13);
        chart.invalidate();

    }

    private void setupPieChartForDay() {
        List<PieEntry> pieEntries = new ArrayList<>();
        addJourneysOnSelectedDay(date);
        for (int i = 0; i < journeyCO2Emissions.size(); i++) {
            PieEntry pieEntry = createNewPieEntry(journeyCO2Emissions.get(i).floatValue(), journeyNumbers.get(i));
            pieEntries.add(pieEntry);
        }
    }

    private void addJourneysOnSelectedDay(Date date) {
        for (int i = 0; i < journeyManager.getJourneyList().size(); i++) {
            Journey journey = journeyManager.getJourney(i);
            boolean isSameDate = journey.getDate().equals(date);
            if (isSameDate) {
                journeyNumbers.add("Journey No." + (i + 1));
                journeyCO2Emissions.add(journey.getCarbonEmitted());
            }
        }
    }

    private PieEntry createNewPieEntry(float value, String string) {
        PieEntry pieEntry = new PieEntry(value, string);
        return pieEntry;
    }

    private void setupChangeButton() {
        Button changeButton = (Button) findViewById(R.id.buttonToTable);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DisplayCarbonFootPrintActivity.makeIntent(PieChartActivity.this);
                intent.putExtra(DisplayCarbonFootPrintActivity.PASS_DATE_DATA, date);
                intent.putExtra(CHANGE_TO_TABLE, 0);
                intent.putExtra(DISPLAY_DATA_DATE, 0);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = DateListActivity.makeIntent(PieChartActivity.this);
        startActivity(intent);
        finish();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, PieChartActivity.class);
    }

//    public void setJourneyData() {
//        for (int i = 0; i < journeyManager.getJourneyList().size(); i++) {
//            Journey journey = journeyManager.getJourney(i);
//            journeyNumbers.add("Journey No." + (i + 1));
//            journeyCO2Emissions.add(journey.getCarbonEmitted());
//        }
//        if (handleDay) {
//            setJourneyDataForDay();
//        }
//    }
//
//    private void setJourneyDataForDay() {
//        for (int i = 0; i < journeyManager.getJourneyList().size(); i++) {
//            Journey journey = journeyManager.getJourney(i);
//            journeyNumbers.add("Journey No." + (i + 1));
//            journeyCO2Emissions.add(journey.getCarbonEmitted());
//        }
//    }
}