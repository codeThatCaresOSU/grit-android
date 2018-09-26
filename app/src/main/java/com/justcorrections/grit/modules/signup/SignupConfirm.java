package com.justcorrections.grit.modules.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.justcorrections.grit.MainActivity;
import com.justcorrections.grit.R;
import com.justcorrections.grit.data.model.GritUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupNamesAge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupConfirm extends Fragment {

    private GritUser user;
    private TextView tvFirstName, tvLastName, tvAge, tvCity, tvAddress, tvZip, tvBio, tvEmail, tvGender;

    public SignupConfirm() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param signupInfo a bundle of the signup details.
     * @return A new instance of fragment SignupConfirm.
     */
    public static SignupConfirm newInstance(Bundle signupInfo) {
        SignupConfirm fragment = new SignupConfirm();
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

        View view = inflater.inflate(R.layout.fragment_signup_confirm, container, false);

        // find the views
        Button backButton = view.findViewById(R.id.button_confirm_back);
        Button nextButton = view.findViewById(R.id.button_confirm_next);

        tvAddress = view.findViewById(R.id.tv_confirm_address_value);
        tvCity = view.findViewById(R.id.tv_confirm_city_value);
        tvZip = view.findViewById(R.id.tv_confirm_zip_value);
        tvAge = view.findViewById(R.id.tv_confirm_age_value);
        tvFirstName = view.findViewById(R.id.tv_confirm_first_name_value);
        tvLastName = view.findViewById(R.id.tv_confirm_last_name_value);
        tvBio = view.findViewById(R.id.tv_confirm_bio_value);
        tvEmail = view.findViewById(R.id.tv_confirm_email_value);
        tvGender = view.findViewById(R.id.tv_confirm_gender_value);


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
        tvAddress.setText(user.getAddress());
        tvCity.setText(user.getCity());
        tvZip.setText(user.getZip());
        tvFirstName.setText(user.getFirstName());
        tvLastName.setText(user.getLastName());
        tvBio.setText(user.getBio());
        tvEmail.setText(user.getEmail());
        tvAge.setText(user.getBirthday());

        if (!user.getGender().contains(getString(R.string.other_prefix))) {
            tvGender.setText(user.getGender());
        } else {
            tvGender.setText(user.getGender().substring(6));
        }

        // Inflate the layout for this fragment
        return view;
    }

    /*
     * Navigates to the previous signup screen
     */
    private void goBack() {
        Bundle signUpBundle = createBundleFromThis();
        ((MainActivity) getActivity()).navigateTo(SignupEmailPasswords.newInstance(signUpBundle), true);
    }

    /*
     * Navigates to the next signup screen
     */
    private void goNext() {
        GritUser.saveToDatabase(null, null);
    }

    /*
     * Creates a Bundle with all of the signup information contained in this fragment's instance variables
     * and views.
     */
    private Bundle createBundleFromThis() {
        // return the bundle
        return GritUser.writeToBundle(user, this.getContext());
    }
}
