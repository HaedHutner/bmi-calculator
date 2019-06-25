package dev.mvvasilev.bmicalculator.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.Set;

import dev.mvvasilev.bmicalculator.R;
import dev.mvvasilev.bmicalculator.entity.BMICalculation;
import dev.mvvasilev.bmicalculator.service.BMIService;

public class History extends Fragment {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");

    public History() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Set<BMICalculation> calculations = new BMIService(getContext()).getCalculationHistory();

        LinearLayout listLayout = view.findViewById(R.id.calculationList);

        calculations.forEach(calc -> {
            CardView calcView = new CardView(getContext());

            LinearLayout cardLayout = new LinearLayout(getContext());
            cardLayout.setOrientation(LinearLayout.VERTICAL);

            TextView timestampView = new TextView(getContext());
            timestampView.setTextSize(24);
            timestampView.setText(DATE_TIME_FORMATTER.format(calc.getTimestamp()));

            TextView weightLabel = new TextView(getContext());
            weightLabel.setText("Weight: " + calc.getWeight());

            TextView heightLabel = new TextView(getContext());
            heightLabel.setText("Height: " + calc.getHeight());

            TextView resultLabel = new TextView(getContext());
            resultLabel.setTextSize(18);
            resultLabel.setText("Result: " + calc.getResult());

            cardLayout.addView(timestampView);
            cardLayout.addView(weightLabel);
            cardLayout.addView(heightLabel);
            cardLayout.addView(resultLabel);

            calcView.addView(cardLayout);

            listLayout.addView(calcView);

            Space space = new Space(getContext());
            space.setMinimumHeight(15);
            listLayout.addView(space);
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
