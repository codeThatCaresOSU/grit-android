package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.GritUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupLocation extends Fragment {

    private GritUser user;
    private EditText addressEditText, cityEditText, zipEditText, stateEditText;

    public SignupLocation() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupNamesAge.
     */
    public static SignupLocation newInstance(Bundle signupInfo) {
        SignupLocation fragment = new SignupLocation();
        fragment.setArguments(signupInfo);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = GritUser.readFromBundle(getArguments(), this.getContext());
        } else {
            this.user = new GritUser();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup_location, container, false);

        // find the views
        Button backButton = view.findViewById(R.id.button_location_back);
        Button nextButton = view.findViewById(R.id.button_location_next);
        addressEditText = view.findViewById(R.id.et_street_address);
        cityEditText = view.findViewById(R.id.et_City);
        zipEditText = view.findViewById(R.id.et_zip);
        stateEditText = view.findViewById(R.id.et_state);

        // Set on click listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goNext();
            }
        });

        /*
         * Populate views with data if it already exists
         */
        if (user.getAddress() != null && !user.getAddress().isEmpty()) {
            addressEditText.setText(user.getAddress());
        }
        if (user.getCity() != null && !user.getCity().isEmpty()) {
            cityEditText.setText(user.getCity());
        }
        if (user.getZip() != null && !user.getZip().isEmpty()) {
            zipEditText.setText(user.getZip());
        }
        if (user.getState() != null && !user.getState().isEmpty()) {
            stateEditText.setText(user.getState());
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupGender.newInstance(signUpBundle), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        if (!noEmptyFields()) {
            Toast.makeText(getContext(), R.string.empty_fields_error, Toast.LENGTH_LONG).show();
        } else {
            Bundle signUpBundle = createBundleFromThis();
            ((MainActivity) getActivity()).navigateTo(SignupBio.newInstance(signUpBundle), true);
        }
    }

    private boolean noEmptyFields() {
        return !addressEditText.getText().toString().isEmpty()
                && !zipEditText.getText().toString().isEmpty()
                && !cityEditText.getText().toString().isEmpty()
                && !stateEditText.getText().toString().isEmpty();
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {

        // Update instance variables based on user input
        user.setCity(cityEditText.getText().toString());
        user.setAddress(addressEditText.getText().toString());
        user.setZip(zipEditText.getText().toString());
        user.setState(stateEditText.getText().toString());

        // return the bundle
        return GritUser.writeToBundle(user, this.getContext());
    }
}
