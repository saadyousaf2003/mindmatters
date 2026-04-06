package com.example.mindmatters.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.mindmatters.R;
import com.example.mindmatters.classes.Appointment;
import com.example.mindmatters.classes.Counsellor;
import com.example.mindmatters.classes.User;
import com.example.mindmatters.fragments.AppointmentDetailsFragment;
import com.example.mindmatters.fragments.AppointmentsFragment;
import com.example.mindmatters.fragments.BookingFragment;
import com.example.mindmatters.fragments.CounsellorDetailsFragment;
import com.example.mindmatters.fragments.StudentHomeFragment;
import com.example.mindmatters.fragments.StudentProfilePlaceholderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Host activity for the student flow that coordinates bottom navigation and detail fragment transitions.
 * Outstanding issues: the profile tab is still a placeholder and the activity does not preserve deep navigation state.
 */
public class StudentHomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    // Sets up the student shell activity and bottom navigation.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.student_bottom_nav);
        bottomNavigationView.setItemActiveIndicatorEnabled(false);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_appointments) {
                replaceTopLevelFragment(new AppointmentsFragment());
                return true;
            } else if (itemId == R.id.navigation_profile) {
                replaceTopLevelFragment(new StudentProfilePlaceholderFragment());
                return true;
            } else if (itemId == R.id.navigation_home) {
                replaceTopLevelFragment(new StudentHomeFragment());
                return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }

    // Replaces the currently selected top-level tab fragment.
    private void replaceTopLevelFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.student_fragment_container, fragment)
                .commit();
    }

    // Opens the booking fragment for a counsellor.
    public void openBooking(Counsellor counsellor) {
        openDetailFragment(BookingFragment.newInstance(counsellor));
    }

    // Opens the counsellor details fragment.
    public void openCounsellorDetails(Counsellor counsellor) {
        openDetailFragment(CounsellorDetailsFragment.newInstance(counsellor));
    }

    // Opens the appointment details fragment.
    public void openAppointmentDetails(Appointment appointment) {
        openDetailFragment(AppointmentDetailsFragment.newInstance(appointment));
    }

    // Switches the UI to the appointments tab.
    public void openAppointmentsTab() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_appointments);
    }

    // Opens a detail fragment on the back stack.
    private void openDetailFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.student_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
