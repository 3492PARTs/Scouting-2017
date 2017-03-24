package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    private @FXML TabPane scoutingTabPane;

    //Check Boxes
    private @FXML CheckBox autoCheckBox;
    private @FXML CheckBox pitGearCheckBox;
    private @FXML CheckBox pitAutoCheckbox;
    private @FXML CheckBox pitFuelCheckbox;

    //Spinners
    private @FXML Spinner<Integer> autoBottomFuelSpinner;
    private @FXML Spinner<Integer> autoTopFuelSpinner;
    private @FXML Spinner<Integer> teleopGearsSpinner;
    private @FXML Spinner<Integer> teleopBottomFuelSpinner;
    private @FXML Spinner<Integer> teleopTopFuelSpinner;
    private @FXML Spinner<Integer> ratingSpinner;
    private @FXML Spinner<Integer> pitFuelPerSecSpinner;
    private @FXML Spinner<Integer> pitWheelsSpinner;
    private @FXML Spinner<Integer> pitCimsSpinner;

    //Spinner Value Factories
    private SpinnerValueFactory<Integer> autoBottomFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> autoTopFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> teleopBottomFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> teleopTopFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> teleopGearsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> ratingValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0, 1);
    private SpinnerValueFactory<Integer> pitFuelPerSecFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> pitWheelsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> pitCimsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);




    //Combo Box
    private @FXML ComboBox<String> climbComboBox;
    private @FXML ComboBox<String> autoGearComboBox;
    private @FXML ComboBox<String> pitGoalComboBox;

    //Combo Box List
    ObservableList<String> climbOptions =
            FXCollections.observableArrayList(
                    "Climbs Well",
                    "Attempt - FAIL",
                    "NO ATTEMPT"
            );
    ObservableList<String> autoGearOptions =
            FXCollections.observableArrayList(
                    "Gear - Center",
                    "Gear - Side",
                    "Attempt - FAIL",
                    "NO ATTEMPT"
            );
    ObservableList<String> pitGoalOptions =
            FXCollections.observableArrayList(
                    "Top Goal",
                    "Bottom Goal",
                    "DOES NOT SHOOT"
            );

    //Text Field
    private @FXML TextField teamNameTextField;
    private @FXML TextField teamNumberTextField;
    private @FXML TextField driveTrainTextField;

    //Text Area
    private @FXML TextArea commentsTextArea;
    private @FXML TextArea pitRobotDoTextArea;
    private @FXML TextArea pitStrategyTextArea;

    //Text Label
    private @FXML Label successMessageLabel;

    public void initialize(){

        //Set Value Factories to their spinners
        ratingSpinner.setValueFactory(ratingValueFactory);
        autoBottomFuelSpinner.setValueFactory(autoBottomFuelValueFactory);
        autoTopFuelSpinner.setValueFactory(autoTopFuelValueFactory);
        teleopBottomFuelSpinner.setValueFactory(teleopBottomFuelValueFactory);
        teleopTopFuelSpinner.setValueFactory(teleopTopFuelValueFactory);
        teleopGearsSpinner.setValueFactory(teleopGearsFactory);
        pitFuelPerSecSpinner.setValueFactory(pitFuelPerSecFactory);
        pitWheelsSpinner.setValueFactory(pitWheelsFactory);
        pitCimsSpinner.setValueFactory(pitCimsFactory);

        //Set Value for combo box
        climbComboBox.setItems(climbOptions);
        autoGearComboBox.setItems(autoGearOptions);
        pitGoalComboBox.setItems(pitGoalOptions);

        //Set success label
        successMessageLabel.setText("RUNNING");

        commentsTextArea.setWrapText(true);
        pitRobotDoTextArea.setWrapText(true);
        pitStrategyTextArea.setWrapText(true);
    }

    public void writeTeamData(){
        try {
            if (scoutingTabPane.getSelectionModel().getSelectedIndex() == 2) appendToFile(pitData(), 1); //pit tab selected
            else appendToFile(fieldData(), 0);

        }catch (Exception e){
            successMessageLabel.setText("ERROR\nSEE\nA\nPROGRAMMER");
        }
    }

    private void appendToFile (String[] data, int file) {

        BufferedWriter bw = null;
        String filePath;

        if (file == 1) filePath = System.getProperty("user.home") + "/Desktop/pitScouting.txt"; //pit file
        else filePath = System.getProperty("user.home") + "/Desktop/fieldScouting.txt";

        try {
            // APPEND MODE SET HERE
            bw = new BufferedWriter(new FileWriter(filePath, true));

            //write the data
            int counter = 0; //to know end of line
            for (String s : data) {
                if (s == null) bw.write(" ");
                else bw.write(s);

                if (counter == data.length-1) bw.write("\n"); //next team entry
                else bw.write(","); //separate the entries

                counter++;
            }

            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {                       // always close the file
            if (bw != null) try {
                bw.close();
            } catch (IOException ioe2) {
                // just ignore it
            }
        }

    }

    private String[] fieldData(){
        String[] data = new String[12];//array to hold all of the data

        //add the data to the array
        data[0] = teamNameTextField.getText();
        data[1] = teamNumberTextField.getText();

        //auto
        if (autoCheckBox.isSelected()) data[2] = "Has Auto";
        else data[2] = "No Auto";

        data[3] = autoGearComboBox.getValue();
        data[4] = autoBottomFuelSpinner.getValue().toString();
        data[5] = autoTopFuelSpinner.getValue().toString();

        //tele op
        data[6] = teleopGearsSpinner.getValue().toString();
        data[7] = teleopBottomFuelSpinner.getValue().toString();
        data[8] = teleopTopFuelSpinner.getValue().toString();
        data[9] = climbComboBox.getValue();

        data[10] = ratingSpinner.getValue().toString();
        data[11] = removeInvalidText(commentsTextArea.getText());

        //clear out the form
        autoCheckBox.setSelected(false);
        autoBottomFuelSpinner.getValueFactory().setValue(0);
        autoTopFuelSpinner.getValueFactory().setValue(0);
        teleopGearsSpinner.getValueFactory().setValue(0);
        teleopBottomFuelSpinner.getValueFactory().setValue(0);
        teleopTopFuelSpinner.getValueFactory().setValue(0);
        ratingSpinner.getValueFactory().setValue(0);
        climbComboBox.valueProperty().setValue(null);
        autoGearComboBox.valueProperty().setValue(null);
        teamNameTextField.clear();
        teamNumberTextField.clear();
        commentsTextArea.clear();


        successMessageLabel.setText("Successfully\nWrote\nField\nData");

        return data;
    }

    private String[] pitData(){
        String[] data = new String[14];//array to hold all of the data

        //add the data to the array
        data[0] = teamNameTextField.getText();
        data[1] = teamNumberTextField.getText();

        if (pitGearCheckBox.isSelected()) data[2] = "Picks up gears";
        else data[2] = "Doesn't pick up gears";

        if (pitAutoCheckbox.isSelected()) data[3] = "Has Auto";
        else data[3] = "No Auto";

        if (pitFuelCheckbox.isSelected()) data[4] = "Does fuel";
        else data[4] = "Doesn't do fuel";

        data[5] = pitFuelPerSecSpinner.getValue().toString();
        data[6] = pitGoalComboBox.getValue();

        data[7] = driveTrainTextField.getText(); //remove commas
        data[8] = pitWheelsSpinner.getValue().toString();
        data[9] = pitCimsSpinner.getValue().toString();
        data[10] = removeInvalidText(pitRobotDoTextArea.getText());
        data[11] = removeInvalidText(pitStrategyTextArea.getText());
        data[12] = ratingSpinner.getValue().toString();
        data[13] = removeInvalidText(commentsTextArea.getText());

        //clear out the form
        pitGearCheckBox.setSelected(false);
        pitAutoCheckbox.setSelected(false);
        pitFuelCheckbox.setSelected(false);
        pitFuelPerSecSpinner.getValueFactory().setValue(0);
        pitGoalComboBox.valueProperty().setValue(null);
        pitFuelPerSecSpinner.getValueFactory().setValue(0);
        pitGoalComboBox.valueProperty().setValue(null);
        driveTrainTextField.clear();
        pitWheelsSpinner.getValueFactory().setValue(0);
        pitCimsSpinner.getValueFactory().setValue(0);
        pitRobotDoTextArea.clear();
        pitStrategyTextArea.clear();
        ratingSpinner.getValueFactory().setValue(0);
        teamNameTextField.clear();
        teamNumberTextField.clear();
        commentsTextArea.clear();


        successMessageLabel.setText("Successfully\nWrote\nPit\nData");

        return data;
    }

    private String removeInvalidText(String s){
        //remove comma ',' and new lines '/n' from comments
        String comments = "";
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ',') comments += '.'; //commas become periods
            else if (chars[i] == '\n') comments += ' '; //new lines become spaces
            else comments += chars[i]; //all is good
        }
        return comments;
    }

}

