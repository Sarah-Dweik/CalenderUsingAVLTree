package application;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.GregorianCalendar;

import Components.AVL;
import Components.Manipulations;

public class CreateCalendar extends Application {

    private int currentYear = 2017;
    private int currentMonth = 0; // January is 0, December is 11
  //  Operations op = new Operations(); // the class exist in the main class
    AVL yearTree ;

    public CreateCalendar(AVL yearTree) {
    	this.yearTree = yearTree;
    }
    
    @Override
    public void start(Stage primaryStage) {
        GridPane mainPane = new GridPane();
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(10));

        Button next = new Button("Next");
        Button prev = new Button("Previous");

        Label yearLabel = new Label("Year: " + currentYear);
        Label monthLabel = new Label("Month: " + monthInWord(currentMonth));

        next.setOnAction(e -> {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
                if (currentYear > 2025) {
                    currentYear = 2017; // Reset to 2017 after 2025
                }
            }
            updateCalendar(mainPane, yearLabel, monthLabel);
        });

        prev.setOnAction(e -> {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
                if (currentYear < 2017) {
                    currentYear = 2025; // Reset to 2025 after 2017
                }
            }
            updateCalendar(mainPane, yearLabel, monthLabel);
        });

        // Add the Next and Previous buttons to the mainPane
        mainPane.add(prev, 0, 0);
        mainPane.add(next, 1, 0);
        mainPane.add(yearLabel, 2, 0);
        mainPane.add(monthLabel, 3, 0);

        // Initialize the calendar with the initial month
        updateCalendar(mainPane, yearLabel, monthLabel);

        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateCalendar(GridPane mainPane, Label yearLabel, Label monthLabel) {
        mainPane.getChildren().removeIf(node -> node instanceof VBox);

        // Create a new VBox for each month
        VBox monthVBox = new VBox(10);

        // Set the calendar to the first day of the month
        Calendar calendar = new GregorianCalendar(currentYear, currentMonth, 1);

        int maxDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        GridPane monthGridPane = new GridPane();
        monthGridPane.setHgap(5);
        monthGridPane.setVgap(5);

        for (int day = 1; day <= maxDaysInMonth; day++) {
            Button dayButton = new Button(String.valueOf(day));
            monthGridPane.add(dayButton, (day + calendar.get(Calendar.DAY_OF_WEEK) - 1) % 7,
                    (day + calendar.get(Calendar.DAY_OF_WEEK) - 1) / 7);
            int y = currentYear;
            String m = monthInWord(currentMonth);
            int d = day;
            Manipulations manipulate = new Manipulations();
            
            dayButton.setOnAction(e->{
            	  Operations op = new Operations(manipulate, yearTree, y, m, d);
            	  op.managementScreen();
            });
        }

        // Update year and month labels
        yearLabel.setText("Year: " + currentYear);
        monthLabel.setText("Month: " + monthInWord(currentMonth));

        // Add the GridPane with days to the VBox
        monthVBox.getChildren().addAll(monthGridPane);

        // Add the VBox to the mainPane
        mainPane.add(monthVBox, 2, 1, 2, 1); // span 2 columns for the VBox
    }

	// convert the month int to a String
	public static String monthInWord(int month) {
		// use cases, start from 0 becasue the built in Calender has Jan as 0
		switch (month) {
		case 0:
			return "Jan";
		case 1:
			return "Feb";
		case 2:
			return "Mar";
		case 3:
			return "Apr";
		case 4:
			return "May";
		case 5:
			return "Jun";
		case 6:
			return "July";
		case 7:
			return "Aug";
		case 8:
			return "Sep";
		case 9:
			return "Oct";
		case 10:
			return "Nov";
		case 11:
			return "Dec";

		}
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
