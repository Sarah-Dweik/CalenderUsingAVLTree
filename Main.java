package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFileChooser;

import Components.AVL;
import Components.Manipulations;
import Components.StackQueue;
import Components.Tnode;
import Components.dNode;
import Components.mNode;
import Components.yNode;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Button bt1 = new Button("Click To Upload your File");

			bt1.setOnAction(e -> {

				AVL<yNode> yearsTree = new AVL<yNode>();
				Manipulations manipulation = new Manipulations(); // to use it insert method in the insertion process

				JFileChooser filechooser = new JFileChooser();
				filechooser.showSaveDialog(null);
				File electricityRecords = filechooser.getSelectedFile();
				try {
					Scanner scan = new Scanner(electricityRecords);

					// to skip the first line
					if (scan.hasNextLine()) {
						scan.nextLine();
					}
					while (scan.hasNext()) {
						String line = scan.next();// contain the dates along with the records
						System.out.println(line);
						System.out.println(line);
						String[] data = line.split(","); // seperate the dates from each record
						String[] times = data[0].split("-"); // seperate the years from the month from the day
						int yearInNum = Integer.valueOf(times[0]);
						int month = Integer.valueOf(times[1]);
						String monthName = monthInWord(month);
						int dayInNum = Integer.valueOf(times[2]);

						// because cells from 1075 to 1096 do not have valid numerical num, exception
						// handleing is done
						try {
							double sline = Double.parseDouble(data[1].trim());
							double gline = Double.parseDouble(data[2].trim());
							double eline = Double.parseDouble(data[3].trim());
							double totalSupply = Double.parseDouble(data[4].trim());
							double overallDemand = Double.parseDouble(data[5].trim());
							double cuts = Double.parseDouble(data[6].trim());
							double temp = Double.parseDouble(data[7].trim());

//							System.out.print(yearInNum+"\s");
//							System.out.print(monthName+ "\s");
//							System.out.print(dayInNum+ "\s");
//							System.out.print(sline+"\s");
//							System.out.print(gline+"\s");
//							System.out.print(totalSupply+"\s");
//							System.out.print(overallDemand+"\s");
//							System.out.print(cuts+"\s");
//							System.out.println(temp+"\s");

							// Fill the AVL
							manipulation.insert(yearsTree, yearInNum, monthName, dayInNum, sline, eline, gline,
									totalSupply, overallDemand, cuts, temp);

						} catch (NumberFormatException ex) {
							// skip the lines
							System.out.println("Error parsing integer: " + ex.getMessage());
							System.out.println("skip hashtags line:" + line);
							continue; // Skip to the next iteration of the loop

						}

					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
//				manipulation.traverseBST(yearsTree); //to check the insertion process
				CreateCalendar c = new CreateCalendar(yearsTree); // to show the calender after insert the data
				c.start(primaryStage); // appearance of the calndar

			});

			root.setCenter(bt1);
			Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// convert the month int to a String
	// this is different from the one in the calender because the calender starts it
	// months from 0 but the file starts from 1
	public static String monthInWord(int month) {
		// use cases, start from 0 becasue the built in Calender has Jan as 0
		switch (month) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "July";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";

		}
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}
}

class Operations {

	Manipulations manipulationMethods;
	AVL<yNode> yearsTree;
	int year;
	String month;
	int day;

	public Operations(Manipulations m, AVL yearsTree, int year, String month, int day) {
		this.manipulationMethods = m;
		this.yearsTree = yearsTree;
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public void managementScreen() {
		Stage stage = new Stage();
		Button bt1 = new Button("insert a record");
		Button bt2 = new Button("delete a record");
		Button bt3 = new Button("update a record");
		Button bt4 = new Button("search a record");
		Button bt5 = new Button("Statics Screen");
		Button bt6 = new Button("traverse this yearTree and show it height");
		Button bt7 = new Button("traverse this monthTree and show its height");
		Button bt8 = new Button("traverse this dayTree and show its height");

		// set events
		bt1.setOnAction(e -> {
			AllRecordEntry();

		});
		bt2.setOnAction(e -> {
			deleteChoices();
		});

		bt3.setOnAction(e -> {
			updateChoices();
		});

		bt4.setOnAction(e -> {
			boolean result = manipulationMethods.search1(yearsTree, year, month, day); // should return true or false
			String str = String.valueOf(result);
			textDisplay(str);

			// also print its content on the console
			AVL recordTree = manipulationMethods.search2(yearsTree, year, month, day);
			if (recordTree.root != null) {
				System.out.println("\n" + "The record of the day you are looking for are:--->");
				System.out.println(recordTree.traverseInOrder(recordTree.root));
			} else {
				System.out.println("this data has no record!");
			}

		});
		bt5.setOnAction(e -> {
			staticSceen();
		});
		bt6.setOnAction(e -> {
			System.out.println("----->this year node are:");
			yearsTree.levelTraversal();
			System.out.println("-----> this tree height is:");
			System.out.println(yearsTree.height());
		});
		bt7.setOnAction(e -> {
			System.out.println("----->this month nodes are");
			yNode year = yearsTree.root.getData();
			manipulationMethods.levelTraversal(year.monthTree.root);
			System.out.println("-----> this tree height is:");
			System.out.println(manipulationMethods.height(year.monthTree.root));
		});
		bt8.setOnAction(e -> {
			Tnode dayroot = findDayTreeNode1(yearsTree.root);
			System.out.println("----->this day nodes are:");
			manipulationMethods.levelTraversal(dayroot);
			System.out.println("-----> this tree height is:");
			System.out.println(manipulationMethods.height(dayroot));
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(bt1, bt2, bt3, bt4, bt5, bt6, bt7, bt8);
		Scene scene = new Scene(pane, 400, 400);
		stage.setScene(scene);
		stage.show();

	}

	public Tnode findDayTreeNode1(Tnode<yNode> yearNode) {
		yNode year = yearNode.getData();
		return findDayTreeNode2(year.monthTree.root);
	}

	public Tnode findDayTreeNode2(Tnode<mNode> monthNode) {
		mNode month = monthNode.getData();
		return findDayNode3(month.dayTree.root);
	}

	public Tnode findDayNode3(Tnode<dNode> daynode) {
		return daynode;
	}

//	public void monthTreeLevelTraversal(AVL<mNode> monthTree) {
//		monthTree.levelTraversal();
//	}
//	public void dayTreeLevelTraversal(AVL<dNode> dayTree) {
//		dayTree.levelTraversal();
//	}

	public void textDisplay(String str) {
		Stage stage = new Stage();
		TextField tf = new TextField();
		tf.setText(str);
		tf.setPrefWidth(300);
		HBox pane = new HBox();
		pane.getChildren().add(tf);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void saveScreen() {
		Stage stage = new Stage();
		Button bt = new Button("Save screen");

		bt.setOnAction(e -> {
			String updatedResult = "";
			JFileChooser filechooser = new JFileChooser();
			filechooser.showSaveDialog(null);
			File toWriteOn = filechooser.getSelectedFile();

			try {
				PrintWriter printWriter = new PrintWriter(toWriteOn);
				// to print the new updated record on the file
				AVL recordTree = manipulationMethods.search2(yearsTree, year, month, day);
				if (recordTree.root != null) {
					System.out.println("\n" + "The record of the day you are looking for are:--->");
					updatedResult += recordTree.traverseInOrder(recordTree.root);

				} else {
					System.out.println("this data has no record!");
				}

				printWriter.print(updatedResult);
				printWriter.close();

				System.out.println("Data written to the file successfully.");

			} catch (FileNotFoundException m) {
				// Handle the case where the file is not found
				m.printStackTrace();
			}
		});

		HBox pane = new HBox();
		pane.getChildren().add(bt);
		Scene scen = new Scene(pane);
		stage.setScene(scen);
		stage.show();
	}

	public void AllRecordEntry() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");

		Label lb1 = new Label("iLine");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		Label lb2 = new Label("eLine");
		TextField tf2 = new TextField();
		HBox box2 = new HBox();
		box2.getChildren().addAll(lb2, tf2);

		Label lb3 = new Label("gLine");
		TextField tf3 = new TextField();
		HBox box3 = new HBox();
		box3.getChildren().addAll(lb3, tf3);

		Label lb4 = new Label("supply");
		TextField tf4 = new TextField();
		HBox box4 = new HBox();
		box4.getChildren().addAll(lb4, tf4);

		Label lb5 = new Label("demand");
		TextField tf5 = new TextField();
		HBox box5 = new HBox();
		box5.getChildren().addAll(lb5, tf5);

		Label lb6 = new Label("cuts");
		TextField tf6 = new TextField();
		HBox box6 = new HBox();
		box6.getChildren().addAll(lb6, tf6);

		Label lb7 = new Label("temps");
		TextField tf7 = new TextField();
		HBox box7 = new HBox();
		box7.getChildren().addAll(lb7, tf7);

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, box2, box3, box4, box5, box6, box7, bt);

		bt.setOnAction(e -> {
			try {
				double iLine = Double.valueOf(tf1.getText());
				double eLine = Double.valueOf(tf2.getText());
				double gline = Double.valueOf(tf3.getText());
				double supply = Double.valueOf(tf4.getText());
				double demand = Double.valueOf(tf5.getText());
				double cuts = Double.valueOf(tf6.getText());
				double temps = Double.valueOf(tf7.getText());

			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void updateChoices() {
		Stage stage = new Stage();
		Button bt1 = new Button("iLine update");
		Button bt2 = new Button("eLine update");
		Button bt3 = new Button("gLine update");
		Button bt4 = new Button("supply update");
		Button bt5 = new Button("demand update");
		Button bt6 = new Button("cuts update");
		Button bt7 = new Button("temps update");

		bt1.setOnAction(e -> {
			iLineUpdate();
		});
		bt2.setOnAction(e -> {
			eLineUpdate();
		});
		bt3.setOnAction(e -> {
			gLineUpdate();
		});
		bt4.setOnAction(e -> {
			supplyUpdate();
		});
		bt5.setOnAction(e -> {
			DemandUpdate();
		});
		bt6.setOnAction(e -> {
			CutsUpdate();
		});
		bt7.setOnAction(e -> {
			TempUpdate();
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(bt1, bt2, bt3, bt4, bt5, bt6, bt7);
		Scene scen = new Scene(pane);
		stage.setScene(scen);
		stage.show();
	}

	public void iLineUpdate() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");
		Label lb1 = new Label("iLine");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		bt.setOnAction(e -> {
			try {
				double iLine = Double.valueOf(tf1.getText());
				manipulationMethods.updateRecord(yearsTree, year, month, day, "iLine", iLine);
				saveScreen();
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, bt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void eLineUpdate() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");
		Label lb1 = new Label("eLine");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		bt.setOnAction(e -> {
			try {
				double eLine = Double.valueOf(tf1.getText());
				manipulationMethods.updateRecord(yearsTree, year, month, day, "eLine", eLine);
				saveScreen();
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, bt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void gLineUpdate() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");
		Label lb1 = new Label("gLine");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		bt.setOnAction(e -> {
			try {
				double gLine = Double.valueOf(tf1.getText());
				manipulationMethods.updateRecord(yearsTree, year, month, day, "gLine", gLine);
				saveScreen();
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, bt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void supplyUpdate() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");
		Label lb1 = new Label("Supply");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		bt.setOnAction(e -> {
			try {
				double supply = Double.valueOf(tf1.getText());
				manipulationMethods.updateRecord(yearsTree, year, month, day, "supply", supply);
				saveScreen();
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, bt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void DemandUpdate() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");
		Label lb1 = new Label("Demand");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		bt.setOnAction(e -> {
			try {
				double demand = Double.valueOf(tf1.getText());
				manipulationMethods.updateRecord(yearsTree, year, month, day, "demand", demand);
				saveScreen();
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, bt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void CutsUpdate() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");
		Label lb1 = new Label("cut");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		bt.setOnAction(e -> {
			try {
				double cuts = Double.valueOf(tf1.getText());
				manipulationMethods.updateRecord(yearsTree, year, month, day, "cuts", cuts);
				saveScreen();
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, bt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	public void TempUpdate() {
		Stage stage = new Stage();
		Button bt = new Button("DONE");
		Label lb1 = new Label("Temps");
		TextField tf1 = new TextField();
		HBox box1 = new HBox();
		box1.getChildren().addAll(lb1, tf1);

		bt.setOnAction(e -> {
			try {
				double gLine = Double.valueOf(tf1.getText());
				manipulationMethods.updateRecord(yearsTree, year, month, day, "temp", gLine);
				saveScreen();
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input. Please enter a valid number.");
			}
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(box1, bt);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.show();
	}

	// delete method
	public void deleteChoices() {
		Stage stage = new Stage();
		Button bt1 = new Button("iLine delete");
		Button bt2 = new Button("eLine delete");
		Button bt3 = new Button("gLine delete");
		Button bt4 = new Button("supply delete");
		Button bt5 = new Button("demand delete");
		Button bt6 = new Button("cuts delete");
		Button bt7 = new Button("temps delete");

		bt1.setOnAction(e -> {
			manipulationMethods.DeleteRecord(yearsTree, year, month, day, "iLine");
		});
		bt2.setOnAction(e -> {
			manipulationMethods.DeleteRecord(yearsTree, year, month, day, "eLine");
		});
		bt3.setOnAction(e -> {
			manipulationMethods.DeleteRecord(yearsTree, year, month, day, "gLine");
		});
		bt4.setOnAction(e -> {
			manipulationMethods.DeleteRecord(yearsTree, year, month, day, "supply");
		});
		bt5.setOnAction(e -> {
			manipulationMethods.DeleteRecord(yearsTree, year, month, day, "demand");
		});
		bt6.setOnAction(e -> {
			manipulationMethods.DeleteRecord(yearsTree, year, month, day, "cuts");
		});
		bt7.setOnAction(e -> {
			manipulationMethods.DeleteRecord(yearsTree, year, month, day, "temp");
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(bt1, bt2, bt3, bt4, bt5, bt6, bt7);
		Scene scen = new Scene(pane);
		stage.setScene(scen);
		stage.show();
	}

	public void chooseDayMonthYear() {
		Stage stage = new Stage();
		Button bt1 = new Button("Across this clicked year");
		Button bt2 = new Button("Across this clicked month");
		Button bt3 = new Button("Across this clicked day");

		// set events
		bt1.setOnAction(e -> {
			float sum = manipulationMethods.tempSumYear(yearsTree, year);
			String sumString = String.valueOf(sum);
			textDisplay(sumString);
		});

		bt2.setOnAction(e -> {
			float sum = manipulationMethods.tempSumMonth(yearsTree.root, month);
			String sumString = String.valueOf(sum);
			textDisplay(sumString);

		});
		bt3.setOnAction(e -> {
			float sum = manipulationMethods.tempSumDay(yearsTree.root, day);
			String sumString = String.valueOf(sum);
			textDisplay(sumString);
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(bt1, bt2, bt3);
		Scene scene = new Scene(pane, 400, 400);
		stage.setScene(scene);
		stage.show();
	}

	public void staticSceen() {
		Stage stage = new Stage();
		Button bt1 = new Button("Sum of Temprature");
		Button bt2 = new Button("AVG of Temprature");
		Button bt3 = new Button("Max of Temprature");
		Button bt4 = new Button("Min of Temprature");

		// set events
		bt1.setOnAction(e -> {
			chooseDayMonthYear();
		});
		bt2.setOnAction(e -> {
			chooseDayMonthYear();
		});
		bt3.setOnAction(e -> {
			chooseDayMonthYear();
		});
		bt4.setOnAction(e -> {
			chooseDayMonthYear();
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(bt1, bt2, bt3, bt4);
		Scene scene = new Scene(pane, 400, 400);
		stage.setScene(scene);
		stage.show();
	}

}
