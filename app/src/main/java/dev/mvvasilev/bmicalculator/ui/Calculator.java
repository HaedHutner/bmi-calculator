package dev.mvvasilev.bmicalculator.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import dev.mvvasilev.bmicalculator.R;
import dev.mvvasilev.bmicalculator.entity.BMICalculation;
import dev.mvvasilev.bmicalculator.service.BMIService;

public class Calculator extends Fragment {

    public Calculator() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View parentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(parentView, savedInstanceState);

        parentView.findViewById(R.id.calculateBMIButton).setOnClickListener((v) -> {
            EditText weightInput = parentView.findViewById(R.id.weightInput);
            EditText heightInput = parentView.findViewById(R.id.heightInput);

            double weight = Double.valueOf(weightInput.getText().toString());
            double height = Double.valueOf(heightInput.getText().toString());

            BMICalculation result = new BMIService(getContext()).calculateBMI(weight, height);

            EditText resultOutput = parentView.findViewById(R.id.resultOutput);
            resultOutput.setText(result.getResult().toString());
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
