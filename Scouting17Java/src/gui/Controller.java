package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    //Check Boxes
    private @FXML CheckBox autoCheckBox;

    //Spinners
    private @FXML Spinner<Integer> autoBottomFuelSpinner;
    private @FXML Spinner<Integer> autoTopFuelSpinner;
    private @FXML Spinner<Integer> teleopGearsSpinner;
    private @FXML Spinner<Integer> teleopBottomFuelSpinner;
    private @FXML Spinner<Integer> teleopTopFuelSpinner;
    private @FXML Spinner<Integer> ratingSpinner;

    //Spinner Value Factories
    private SpinnerValueFactory<Integer> autoBottomFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> autoTopFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> teleopBottomFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> teleopTopFuelValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> teleopGearsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0, 1);
    private SpinnerValueFactory<Integer> ratingValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0, 1);



    //Combo Box
    private @FXML ComboBox<String> climbComboBox;
    private @FXML ComboBox<String> autoGearComboBox;

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

    //Text Field
    private @FXML TextField teamNameTextField;
    private @FXML TextField teamNumberTextField;
    private @FXML TextArea commentsTextArea;

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

        //Set Value for combo box
        climbComboBox.setItems(climbOptions);
        autoGearComboBox.setItems(autoGearOptions);

        //Set success label
        successMessageLabel.setText("RUNNING");

        commentsTextArea.setWrapText(true);
    }

    public void writeTeamData(){
        boolean success = false;
        try {
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

            //remove comma ',' and new lines '/n' from comments
            String comments = "";
            char[] chars = commentsTextArea.getText().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == ',') comments += '.'; //commas become periods
                else if (chars[i] == '\n') comments += ' '; //new lines become spaces
                else comments += chars[i]; //all is good
            }
            data[11] = comments;

            appendToFile(data); //write out the data

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


            successMessageLabel.setText("Successfully Wrote Field Data");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void appendToFile (String[] data) {

        BufferedWriter bw = null;

        try {
            // APPEND MODE SET HERE
            bw = new BufferedWriter(new FileWriter(System.getProperty("user.home") + "/Desktop/fieldScouting.txt", true));

            //write the data
            int counter = 0; //to know end of line
            for (String s : data) {
                if (s == null) bw.write(" ");
                else bw.write(s);

                if (counter == 11) bw.write("\n"); //next team entry
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
        } // end try/catch/finally

    } // end test()

} // end class

