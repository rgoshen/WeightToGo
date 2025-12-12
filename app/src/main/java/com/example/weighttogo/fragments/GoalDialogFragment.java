package com.example.weighttogo.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.weighttogo.R;
import com.example.weighttogo.database.GoalWeightDAO;
import com.example.weighttogo.database.WeighToGoDBHelper;
import com.example.weighttogo.models.GoalWeight;
import com.example.weighttogo.utils.DateUtils;
import com.example.weighttogo.utils.GoalUtils;
import com.example.weighttogo.utils.WeightUtils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Dialog fragment for creating and editing weight goals.
 * Reusable across MainActivity and GoalsActivity.
 *
 * Usage:
 * <pre>
 * GoalDialogFragment dialog = GoalDialogFragment.newInstance(userId, currentWeight, currentUnit);
 * dialog.setListener(this);  // Activity implements GoalDialogListener
 * dialog.show(getSupportFragmentManager(), "GoalDialogFragment");
 * </pre>
 */
public class GoalDialogFragment extends DialogFragment {

    // ============================================================
    // Constants
    // ============================================================

    private static final String TAG = "GoalDialogFragment";
    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_CURRENT_WEIGHT = "current_weight";
    private static final String ARG_CURRENT_UNIT = "current_unit";
    private static final String ARG_EXISTING_GOAL_ID = "existing_goal_id";

    // ============================================================
    // Listener Interface
    // ============================================================

    /**
     * Callback interface for goal dialog events.
     * Activities must implement this interface to receive goal creation results.
     */
    public interface GoalDialogListener {
        /**
         * Called when a goal is successfully saved to the database.
         *
         * @param goal The newly created goal with goalId populated
         */
        void onGoalSaved(GoalWeight goal);

        /**
         * Called when goal save fails (e.g., database error, constraint violation).
         *
         * @param errorMessage Human-readable error message
         */
        void onGoalSaveError(String errorMessage);
    }

    // ============================================================
    // Instance Fields
    // ============================================================

    // Listener
    private GoalDialogListener listener;

    // DAOs
    private GoalWeightDAO goalWeightDAO;

    // Arguments (parsed from Bundle)
    private long userId;
    private double currentWeight;
    private String currentUnit;
    private long existingGoalId;

    // UI Elements
    private TextView textCurrentWeight;
    private TextInputEditText inputGoalWeight;
    private TextView unitLbs;
    private TextView unitKg;
    private TextView btnSelectTargetDate;
    private TextView textSelectedTargetDate;

    // Dialog State
    private String selectedUnit;
    private LocalDate selectedTargetDate;

    // ============================================================
    // Public Methods
    // ============================================================

    /**
     * Creates a new instance of GoalDialogFragment with required arguments.
     *
     * @param userId        Current user ID for goal creation
     * @param currentWeight User's current weight for display and validation
     * @param currentUnit   Current weight unit ("lbs" or "kg")
     * @return Configured GoalDialogFragment instance
     */
    public static GoalDialogFragment newInstance(long userId, double currentWeight, String currentUnit) {
        GoalDialogFragment fragment = new GoalDialogFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_USER_ID, userId);
        args.putDouble(ARG_CURRENT_WEIGHT, currentWeight);
        args.putString(ARG_CURRENT_UNIT, currentUnit);
        args.putLong(ARG_EXISTING_GOAL_ID, -1); // Default: create mode
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Sets the listener for goal dialog events.
     * Must be called before showing the dialog.
     *
     * @param listener Callback for goal save events
     * @throws IllegalArgumentException if listener is null
     */
    public void setListener(@NonNull GoalDialogListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("GoalDialogListener cannot be null");
        }
        this.listener = listener;
    }

    // ============================================================
    // Lifecycle Methods
    // ============================================================

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Parse arguments
        Bundle args = getArguments();
        if (args == null) {
            throw new IllegalStateException("GoalDialogFragment requires arguments. Use newInstance()");
        }

        userId = args.getLong(ARG_USER_ID, -1);
        currentWeight = args.getDouble(ARG_CURRENT_WEIGHT, 0.0);
        currentUnit = args.getString(ARG_CURRENT_UNIT, "lbs");
        existingGoalId = args.getLong(ARG_EXISTING_GOAL_ID, -1);

        // Validate required arguments
        if (userId <= 0 || currentWeight <= 0.0) {
            throw new IllegalArgumentException("Invalid arguments: userId and currentWeight must be positive");
        }

        // Initialize DAOs
        WeighToGoDBHelper dbHelper = WeighToGoDBHelper.getInstance(requireContext());
        goalWeightDAO = new GoalWeightDAO(dbHelper);

        // Initialize state
        selectedUnit = currentUnit;
        selectedTargetDate = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate layout
        View dialogView = requireActivity().getLayoutInflater()
                .inflate(R.layout.dialog_set_goal, null);

        // Initialize views
        initViews(dialogView);

        // Setup interactions
        setupUnitToggle();
        setupDatePicker();

        // Create dialog
        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setView(dialogView)
                .setCancelable(true)
                .create();

        // Button listeners
        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(v -> dismiss());
        dialogView.findViewById(R.id.btn_save).setOnClickListener(v -> handleSaveGoal());

        return dialog;
    }

    // ============================================================
    // Private Helper Methods
    // ============================================================

    /**
     * Initialize all view references and set current weight display.
     *
     * @param dialogView The inflated dialog view
     */
    private void initViews(View dialogView) {
        textCurrentWeight = dialogView.findViewById(R.id.text_current_weight);
        inputGoalWeight = dialogView.findViewById(R.id.input_goal_weight);
        unitLbs = dialogView.findViewById(R.id.unit_lbs);
        unitKg = dialogView.findViewById(R.id.unit_kg);
        btnSelectTargetDate = dialogView.findViewById(R.id.btn_select_target_date);
        textSelectedTargetDate = dialogView.findViewById(R.id.text_selected_target_date);

        // Set current weight display
        textCurrentWeight.setText(String.format("%.1f %s", currentWeight, currentUnit));

        // Set initial unit toggle state
        updateUnitButtonUI(selectedUnit);
    }

    /**
     * Setup unit toggle button listeners (lbs/kg switching).
     */
    private void setupUnitToggle() {
        unitLbs.setOnClickListener(v -> {
            selectedUnit = "lbs";
            updateUnitButtonUI("lbs");
            textCurrentWeight.setText(String.format("%.1f lbs", currentWeight));
        });

        unitKg.setOnClickListener(v -> {
            selectedUnit = "kg";
            updateUnitButtonUI("kg");
            // Convert current weight to kg for display
            double currentKg = WeightUtils.convertLbsToKg(currentWeight);
            textCurrentWeight.setText(String.format("%.1f kg", currentKg));
        });
    }

    /**
     * Setup target date picker button listener.
     */
    private void setupDatePicker() {
        btnSelectTargetDate.setOnClickListener(v -> showDatePicker());
    }

    /**
     * Shows a Material Date Picker for selecting the optional target date.
     */
    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Target Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Convert epoch milliseconds to LocalDate
            Instant instant = Instant.ofEpochMilli(selection);
            LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            selectedTargetDate = date;
            textSelectedTargetDate.setText(String.format(getString(R.string.target_date_format),
                    DateUtils.formatDateFull(date)));
            textSelectedTargetDate.setVisibility(View.VISIBLE);
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    /**
     * Handles saving the goal after validation.
     * Validates inputs, creates GoalWeight object, and saves to database.
     */
    private void handleSaveGoal() {
        // Get goal weight input
        String goalWeightStr = inputGoalWeight.getText() != null ?
                inputGoalWeight.getText().toString().trim() : "";

        // Validation: goal weight required
        if (goalWeightStr.isEmpty()) {
            inputGoalWeight.setError(getString(R.string.error_goal_required));
            inputGoalWeight.requestFocus();
            return;
        }

        // Parse goal weight
        double goalWeight;
        try {
            goalWeight = Double.parseDouble(goalWeightStr);
        } catch (NumberFormatException e) {
            inputGoalWeight.setError(getString(R.string.error_invalid_weight));
            inputGoalWeight.requestFocus();
            return;
        }

        // Validation: goal must differ from current weight and be in valid range
        if (!GoalUtils.isValidGoal(currentWeight, goalWeight, selectedUnit)) {
            // Determine specific error message
            if (Math.abs(goalWeight - currentWeight) < 0.1) {
                inputGoalWeight.setError(getString(R.string.error_goal_same_as_current));
            } else if ("lbs".equals(selectedUnit)) {
                inputGoalWeight.setError(getString(R.string.error_goal_out_of_range_lbs));
            } else {
                inputGoalWeight.setError(getString(R.string.error_goal_out_of_range_kg));
            }
            inputGoalWeight.requestFocus();
            return;
        }

        // Validation: target date must be in future (if provided)
        if (selectedTargetDate != null && !GoalUtils.isValidTargetDate(selectedTargetDate)) {
            Toast.makeText(requireContext(), R.string.error_target_date_past, Toast.LENGTH_SHORT).show();
            return;
        }

        // Create and save goal
        validateAndSaveGoal(goalWeight);
    }

    /**
     * Creates GoalWeight object and saves to database via DAO.
     * Calls listener callback on success or failure.
     *
     * @param goalWeight The validated goal weight value
     */
    private void validateAndSaveGoal(double goalWeight) {
        // Create new goal
        GoalWeight newGoal = new GoalWeight();
        newGoal.setUserId(userId);
        newGoal.setGoalWeight(goalWeight);
        newGoal.setGoalUnit(selectedUnit);
        newGoal.setStartWeight(currentWeight);
        newGoal.setTargetDate(selectedTargetDate); // Optional, can be null
        newGoal.setActive(true);
        newGoal.setAchieved(false);
        newGoal.setCreatedAt(LocalDateTime.now());
        newGoal.setUpdatedAt(LocalDateTime.now());

        // Save to database (deactivates previous goal automatically)
        long goalId = goalWeightDAO.setNewActiveGoal(newGoal);

        if (goalId > 0) {
            newGoal.setGoalId(goalId); // Set ID for callback
            Toast.makeText(requireContext(), R.string.success_goal_created, Toast.LENGTH_SHORT).show();

            // Callback to activity BEFORE dismiss (ensure activity gets data)
            if (listener != null) {
                listener.onGoalSaved(newGoal);
            }

            dismiss();
        } else {
            String errorMsg = "Failed to create goal. Please try again.";
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show();

            // Callback to activity
            if (listener != null) {
                listener.onGoalSaveError(errorMsg);
            }
        }
    }

    /**
     * Updates unit toggle button UI (active/inactive state).
     *
     * @param selectedUnit The currently selected unit ("lbs" or "kg")
     */
    private void updateUnitButtonUI(String selectedUnit) {
        if ("lbs".equals(selectedUnit)) {
            // Lbs active
            unitLbs.setBackgroundResource(R.drawable.bg_unit_toggle_active);
            unitLbs.setTextColor(getResources().getColor(R.color.text_on_primary, null));
            // Kg inactive
            unitKg.setBackgroundResource(R.drawable.bg_unit_toggle_inactive);
            unitKg.setTextColor(getResources().getColor(R.color.text_secondary, null));
        } else {
            // Kg active
            unitKg.setBackgroundResource(R.drawable.bg_unit_toggle_active);
            unitKg.setTextColor(getResources().getColor(R.color.text_on_primary, null));
            // Lbs inactive
            unitLbs.setBackgroundResource(R.drawable.bg_unit_toggle_inactive);
            unitLbs.setTextColor(getResources().getColor(R.color.text_secondary, null));
        }
    }
}
